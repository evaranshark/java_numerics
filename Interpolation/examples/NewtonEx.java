import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by evaran on 02.12.2017.
 * Part of interpolation package in math
 */
public class NewtonEx {
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
        Newton nt = new Newton(Points);
        Poly ntRes = nt.run();
        System.out.print(Poly.toString(ntRes));
        return;
    }
}
