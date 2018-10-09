import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by evaran on 19.05.2018.
 * Part of interpolation package in math
 */
public class EulerSolverTests {
    @Test
    public void Start() throws Exception {
        double a = 0, b=3;
        double h = 0.1;
        EulerSolver solver;
        solver = new EulerSolver(new IFunctionBehaviour() {
            @Override
            public double[] eval(double x, double[] y) {
                double[] result = new double[y.length];
                result[0] = y[1];
                result[1] = Math.exp(-x*y[0]);
                return result;
            }
        }, 0.0, 3.0, 0.1, new double[] {0, 0});

        solver.start();
        double[] result = solver.getResult();
        double[] expected = {2.7927089429494116, 1.2420897303832419};
        Assertions.assertArrayEquals(expected, result);
    }
}
