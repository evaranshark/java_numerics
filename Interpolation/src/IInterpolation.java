import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by evaran on 02.12.2017.
 * Part of interpolation package in math
 */
class IInterpolation {
    Poly run(){return null;}
    ArrayList<Point2D.Double> Points;
    ArrayList<Poly> monomialsList;
    List summandsList;
    ArrayList<Double> diffCoefficients;
    void fillMonomials(){
        for (Point2D.Double point : Points)
            monomialsList.add(Poly.mono(point.x));
    }
    void calculateSummands(){

    }
    void calculateDifferences(){}
}
