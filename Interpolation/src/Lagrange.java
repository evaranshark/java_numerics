import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Represents Lagrange interpolation. <br>
 * Uses multithreading. Default number of threads is 4. <br>
 * You can set custom number of threads while initializing with specified constructor. <br>
 * Note that recommended value for best performance is (number_of_cores + 1).
 * @author evaran
 *
 */
public class Lagrange extends IInterpolation {
    private int threads = 4;

    /**
     * Default constructor
     */
    Lagrange() {
        Points = null;
    }

    /**
     * Custom threads constructor.
     * @param threads Number of threads to compute.
     */
    Lagrange(int threads) {
        Points = null;
        this.threads = threads;
    }

    Lagrange(ArrayList<Point2D.Double> points) {
        Points = points;
    }

    /**
     * Start computing polynomial.
     *
     * @return Poly
     */
    @Override
    public Poly run() {
        Poly result = new Poly();
        monomialsList = new ArrayList<>();
        fillMonomials();
        summandsList = Collections.synchronizedList(new ArrayList<Poly>());
        calculateSummands();
        ArrayList<Poly> summandsDesync = new ArrayList<Poly>();
        for (Object v : summandsList)
            summandsDesync.add((Poly) v);
        result = Poly.add(summandsDesync);
        summandsList.clear();

        return (Poly) result.clone();
    }

    @Override
    void calculateSummands() {
        ExecutorService threadPool;
        ArrayList<Callable<Object>> tasks = new ArrayList<>();
        threadPool = Executors.newFixedThreadPool(threads);
        for (Poly monomial : monomialsList) {
            tasks.add(() -> {
                //Начало блока вычисления младших полиномов
                ArrayList<Poly> otherMonomials = (ArrayList<Poly>) monomialsList.clone();
                otherMonomials.remove(monomial);
                Poly lagrangian = new Poly();
                lagrangian = Poly.multiply(otherMonomials);
                //TODO Dividing on coefficients
                double denom = 1.0;
                for (Poly mono : otherMonomials)
                    denom *= (mono.get(0) - monomial.get(0));
                double nom = 1.0;
                for (Point2D.Double point : Points)
                    if (point.x == -monomial.get(0)) {
                        nom = point.y;
                        break;
                    }
                lagrangian.multBy(nom / denom);
                summandsList.add(lagrangian);
                //Конец блока
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
