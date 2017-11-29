import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.*;

/**
 * Created by evaran on 23.11.2017.
 */
public class Lagrange {
    ArrayList<Point2D.Double> Points;

    Lagrange() {
        Points = null;
    }
    Lagrange(ArrayList<Point2D.Double> points) {
        Points = points;
    }

    /**
     * Start computing polynome.
     * @return Poly
     */
    public Poly start() {
        Poly result = new Poly();
        ArrayList<Poly> monomials = new ArrayList<>();
        for (Point2D.Double point : Points)
            monomials.add(Poly.mono(point.x));
        ExecutorService pool;
        List lagrangians = Collections.synchronizedList(new ArrayList<Poly>());
        ArrayList<Callable<Object>> tasks = new ArrayList();
        pool = Executors.newFixedThreadPool(4);
        for (Poly monomial : monomials)
        {
            tasks.add(()->{
                //Начало блока вычисления младших полиномов
                ArrayList<Poly> otherMonomials = (ArrayList)monomials.clone();
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
                lagrangian.divideBy(denom/nom);

                lagrangians.add((Poly)lagrangian.clone());
                //Конец блока
                return null;
            });
        }
        try {
            pool.invokeAll(tasks);
            pool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<Poly> lagrangiansDesync = new ArrayList();
        for (Object v : lagrangians)
            lagrangiansDesync.add((Poly)v);
        result = Poly.add(lagrangiansDesync);
        lagrangians.clear();

        return (Poly)result.clone();
    }
}
