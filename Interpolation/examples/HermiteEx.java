import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by evaran on 02.12.2017.
 * Part of interpolation package in math
 */
public class HermiteEx {
    public static void main(String[] args) {
        ArrayList<Point2D.Double> Points = new ArrayList<Point2D.Double>() {
            {
                add(new Point2D.Double(-1.0, 2.0));
                add(new Point2D.Double(0.0, 1.0));
                add(new Point2D.Double(1.0, 2.0));
            }
        };
        double derivs[][] = {
                {-8.0, 56.0},
                {0.0, 0.0},
                {8.0, 56.0}
        };
        Hermite ht = new Hermite(Points, derivs);
        Poly htRes = ht.run();
        System.out.print(Poly.toString(htRes));
        return;
    }
}
