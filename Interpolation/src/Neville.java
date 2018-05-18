import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by evaran on 09.12.2017.
 * Part of interpolation package in math
 */
public class Neville extends IInterpolation {
    private int threads = 4;
    /**
     * Default constructor
     */
    Neville() {
        Points = null;
    }

    /**
     * Custom threads constructor.
     * @param threads Number of threads to evaluate.
     */
    Neville(int threads) {
        Points = null;
        this.threads = threads;
    }

    Neville(ArrayList<Point2D.Double> points) {
        Points = points;
    }

    @Override
    public Poly run() {
        //Инициализация
        Poly result = new Poly();
        monomialsList = new ArrayList<>();
        fillMonomials();
        result = calculatePoly();
        return result;
    }

    @Override
    void fillMonomials(){
        for (Point2D.Double point : Points)
            monomialsList.add(Poly.minus(Poly.mono(point.x)));
    }

    private Poly calculatePoly(){
        Poly result = new Poly();
        ArrayList<Poly> prevLayer, nextLayer;
        Poly lMono, rMono, temp, lTemp, rTemp;
        prevLayer = new ArrayList<>();
        for (Point2D item: Points)
            prevLayer.add(new Poly(0, item.getY()));
        for (int layerCount = 0; layerCount < monomialsList.size() - 1; layerCount++)
        {
            nextLayer = new ArrayList<>();
            for (int stepCount = 0; stepCount < prevLayer.size() - 1; stepCount++)
            {
                temp = new Poly();
                lMono = monomialsList.get(stepCount);
                rMono = monomialsList.get(layerCount + stepCount + 1);
                lTemp = Poly.times(prevLayer.get(stepCount + 1), lMono);
                rTemp = Poly.times(prevLayer.get(stepCount), rMono);
                temp = Poly.add(lTemp, Poly.minus(rTemp));
                Double denom = Points.get(layerCount + stepCount + 1).getX() - Points.get(stepCount).getX();
                temp.times(1.0 / denom);
                nextLayer.add(temp);
            }
            prevLayer.clear();
            prevLayer = nextLayer;
        }
        result = prevLayer.get(0);

        return result;
    }
}
