/**
 * Created by evaran on 19.05.2018.
 * Part of interpolation package in math
 * Example for Euler's method
 */
public class EulerEx {
    public static void main(String[] args) {
        double a = 0, b=4;
        double h = 0.1;
        EulerSolver solver;
        IFunctionBehaviour behaviour = new myBehaviour();
        solver = new EulerSolver(behaviour, a, b, h, new double[] {0, 0});
        solver.start();
        double[] result = solver.getResult();
        System.out.println(String.format("y1 = %g \t y2 = %g", result[0], result[1]));
    }

    static class myBehaviour implements IFunctionBehaviour {
        @Override
        public double[] eval(double x, double[] y) {
            double[] result = new double[y.length];
            result[0] = Math.sin(Math.pow(x, 2) + Math.pow(y[1], 2));
            result[1] = Math.cos(x * y[0]);
            return result;
        }
    }

    static class secBehaviour implements IFunctionBehaviour {

        @Override
        public double[] eval(double x, double[] y) {
            double[] result = new double[y.length];
            result[0] = y[1];
            result[1] = Math.exp(-x*y[0]);
            return result;
        }
    }
}



