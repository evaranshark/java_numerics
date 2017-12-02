import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by evaran on 02.12.2017.
 * Part of interpolation package in math
 */
public class Hermite extends IInterpolation {

    private int threads = 4;

    /**
     * Default constructor
     */
    Hermite() {
        Points = null;
    }

    /**
     * Custom threads constructor.
     * @param threads Number of threads to compute.
     */
    Hermite(int threads) {
        Points = null;
        this.threads = threads;
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

    }

    @Override
    void calculateSummands() {

    }
}
