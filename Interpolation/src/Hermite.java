import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by evaran on 02.12.2017.
 * Part of interpolation package in math
 */
public class Hermite extends IInterpolation {
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
