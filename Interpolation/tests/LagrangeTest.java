import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by evaran on 24.11.2017.
 */
public class LagrangeTest {
    @Test
    public void start() throws Exception {
        ArrayList<Point2D.Double> Points = new ArrayList<>();
        Points.add(new Point2D.Double(1.0, 1.0));
        Points.add(new Point2D.Double(2.0, 2.0));
        Lagrange lg = new Lagrange(Points);
        Poly lgRes = lg.run();
        Poly expected = new Poly();
        expected.put(1, 1.0);
        Assertions.assertEquals(expected, lgRes);
    }
}