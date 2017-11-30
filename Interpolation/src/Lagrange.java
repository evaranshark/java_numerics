import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by evaran on 23.11.2017.
 * Represents Lagrange interpolation. Uses multithreading.
 * Default number of threads is 4. You can set custom number of threads while initializing with specified constructor.
 * Note that recommended value for best performance is (number_of_cores) + 1.
 *
 */
public class Lagrange {
    ArrayList<Point2D.Double> Points;
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
    public Poly start() {
        Poly result = new Poly();
        ArrayList<Poly> monomialsList = new ArrayList<>();
        for (Point2D.Double point : Points)
            monomialsList.add(Poly.mono(point.x));
        ExecutorService threadPool;
        List summandsList = Collections.synchronizedList(new ArrayList<Poly>());
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
                lagrangian.divideBy(denom / nom);

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
        ArrayList<Poly> lagrangiansDesync = new ArrayList<Poly>();
        for (Object v : summandsList)
            lagrangiansDesync.add((Poly) v);
        result = Poly.add(lagrangiansDesync);
        summandsList.clear();

        return (Poly) result.clone();
    }
}
