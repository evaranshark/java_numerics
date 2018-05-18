import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by evaran on 29.11.2017.
 * Part of interpolation package in math
 */
public class Newton extends IInterpolation {

    private int threads = 4;

    /**
     * Default constructor
     */
    Newton() {
        Points = null;
    }

    /**
     * Custom threads constructor.
     * @param threads Number of threads to evaluate.
     */
    Newton(int threads) {
        Points = null;
        this.threads = threads;
    }

    Newton(ArrayList<Point2D.Double> points) {
        Points = points;
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
                nextLayer.add(nom / denom);
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
        for (Poly monomial : monomialsList) {
            tasks.add(() -> {
                //Начало блока вычисления младших полиномов
                ArrayList<Poly> monomialsClone = (ArrayList<Poly>) monomialsList.clone();
                ArrayList<Poly> factors = new ArrayList<>();
                factors.add(new Poly(0, 1.0));
                double nom = 0.0;
                for (Poly p : monomialsClone)
                    if (!p.equals(monomial))
                        factors.add(p);
                    else
                    {
                        nom = diffCoefficients.get(monomialsList.indexOf(p));
                        break;
                    }
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

}
