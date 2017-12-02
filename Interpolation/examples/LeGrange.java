import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by evaran on 24.11.2017.
 */
public class LeGrange {
    public static void main(String[] args) {
        ArrayList<Point2D.Double> Points = new ArrayList<Point2D.Double>() {
            {
                add(new Point2D.Double(1.0, 5.0));
                add(new Point2D.Double(3.0, 5.0));
                add(new Point2D.Double(4.0, 9.0));
                add(new Point2D.Double(6.0, 3.0));
                add(new Point2D.Double(7.0, 4.0));
                add(new Point2D.Double(9.0, 5.0));
                //add(new Point2D.Double(10.0, 9.0));
                add(new Point2D.Double(15.0, 11.0));
            }
        };
        Lagrange lg = new Lagrange(Points);
        Poly lgRes = lg.run();
        System.out.print(Poly.toString(lgRes));
        return;
    }
}
