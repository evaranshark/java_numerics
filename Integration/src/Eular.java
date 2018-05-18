/**
 * Created by evaran on 18.05.2018.
 * Part of interpolation package in math
 * Class for solving Koshi's problem by Eular's method.
 */
public class Eular {
    private double left;
    private double right;
    private double h;
    private double[] initials;
    private IFunctionBehaviour function;
    private double[] result;

    /**
     *
     * @param function Right part of equation(system)
     * @param left Left edge of integrating segment.
     * @param right Left edge of integrating segment.
     * @param initials Edge conditions.
     * @param h Step size.
     */
    public Eular(IFunctionBehaviour function, double left, double right, double h, double[] initials) {
        setFunction(function);
        setLeft(left);
        setRight(right);
        setInitials(initials);
        setH(h);
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public IFunctionBehaviour getFunction() {
        return function;
    }

    public void setFunction(IFunctionBehaviour function) {
        this.function = function;
    }

    public double[] getInitials() {
        return initials;
    }

    public void setInitials(double[] initials) {
        this.initials = initials;
    }

    public void start() {
        result = initials.clone();
        double currX = left;
        while ((right-currX)>=h) {
            currX+=h;
            doStep(currX);
        }
        if (right > currX) {
            h = right - currX;
            doStep(right);
        }
    }
    private void doStep(double x) {

    }
}
