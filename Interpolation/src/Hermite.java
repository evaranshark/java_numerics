import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by evaran on 02.12.2017.
 * Part of interpolation package in math
 */
public class Hermite extends IInterpolation {

    private int threads = 4;
    private double derivs[][];
    /**
     * Default constructor
     */
    Hermite() {
        Points = null;
    }

    /**
     * Custom threads constructor.
     * @param threads Number of threads to evaluate.
     */
    Hermite(int threads) {
        Points = null;
        this.threads = threads;
    }

    Hermite(ArrayList<Point2D.Double> points, double ders[][]) {
        Points = new ArrayList<>();
        int rows = ders.length;
        int cols = ders[0].length;
        derivs = ders.clone();
        for (int i=0; i < rows * (cols + 1); i++)
            Points.add(points.get(i / (cols + 1)));
    }

    @Override
    public Poly run() {
        //Инициализация
        Poly result = new Poly();
        monomialsList = new ArrayList<>();
        fillMonomials();

        //Вычисление разделённых разностей
        diffCoefficients = new ArrayList<>();
        calculateDifferences();

        //Вычисление слагаемых
        summandsList = Collections.synchronizedList(new ArrayList<Poly>());
        calculateSummands();

        //Вывод результата
        ArrayList<Poly> summandsDesync = new ArrayList<Poly>();
        for (Object v : summandsList)
            summandsDesync.add((Poly) v);
        result = Poly.add(summandsDesync);
        summandsList.clear();

        return (Poly) result.clone();
    }


    @Override
    void calculateDifferences() {
        ArrayList<Double> prevLayer, nextLayer;
        prevLayer = new ArrayList<>();
        for (Point2D.Double point : Points)
            prevLayer.add(point.getY());
        int step = 1;
        while (prevLayer.size() != 1)
        {
            diffCoefficients.add(prevLayer.get(0));
            nextLayer = new ArrayList<>();
            for (int i = 0; i < prevLayer.size() - 1; i++)
            {
                double nom = prevLayer.get(i+1) - prevLayer.get(i);
                double denom = Points.get(i+step).getX() - Points.get(i).getX();
                if (denom != 0.0)
                    nextLayer.add(nom / denom);
                else {
                    nextLayer.add(derivs[i/derivs.length][step-1]/factorial(step));
                }
            }
            prevLayer = (ArrayList<Double>) nextLayer.clone();
            nextLayer.clear();
            step++;
        }
        diffCoefficients.add(prevLayer.get(0));
    }

    @Override
    void calculateSummands() {
        ExecutorService threadPool;
        ArrayList<Callable<Object>> tasks = new ArrayList<>();
        threadPool = Executors.newFixedThreadPool(threads);
        for (int i=0; i<monomialsList.size(); i++) {
            final int count = i;
            tasks.add(() -> {
                //Начало блока вычисления младших полиномов
                ArrayList<Poly> monomialsClone = (ArrayList<Poly>) monomialsList.clone();
                ArrayList<Poly> factors = new ArrayList<>();
                factors.add(new Poly(0, 1.0));
                double nom;
                for (int j = 0; j < count; j++)
                        factors.add(monomialsClone.get(j));
                nom = diffCoefficients.get(count);
                Poly summand = new Poly();
                summand = Poly.times(factors);
                summand.times(nom);
                summandsList.add(summand);
                return null;
            });
        }
        try {
            threadPool.invokeAll(tasks);
            threadPool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private int factorial (int n)
    {
        int result = 1;
        for (int i = 1; i <= n; i++)
            result *= i;
        return result;
    }
}
