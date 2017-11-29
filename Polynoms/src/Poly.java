import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

/**
 * Created by evaran on 23.11.2017.
 */
public class Poly extends HashMap<Integer, Double> {
    public Poly() {
        super();
    }

    /**
     * Creates monome (x - coeff).
     * @param coeff Bias of monome
     * @return Poly
     */
    public static Poly mono(Double coeff) {
        Poly result = new Poly();
        result.put(0, -coeff);
        result.put(1, 1.0);
        return result;
    }

    /**
     * Addition of number of polynomials.
     * @param left First argument of addition
     * @param vars Extra arguments
     * @return
     */
    public static Poly add(Poly left, Poly ... vars){
        Poly result = new Poly();
        result = (Poly)left.clone();
        if (vars.length == 0)
            return result;
        Double currValue;
        for (Poly poly : vars)
            for (Entry entry : poly.entrySet()) {
                currValue = result.putIfAbsent((Integer) entry.getKey(), (Double) entry.getValue());
                if (currValue != null)
                {
                    currValue += (Double) entry.getValue();
                    if (currValue == 0.0)
                        result.remove(entry.getKey());
                    else
                        result.put((Integer) entry.getKey(), currValue);
                }
            }
        return result;
    }

    public static Poly multiply(Poly left, Poly ... vars){
        Poly result = new Poly();
        Queue<Poly> polyQueue = new ArrayDeque<>();
        polyQueue.add(left);
        for (int i=0; i< vars.length; i++)
            polyQueue.offer(vars[i]);
        Poly leftArg, rightArg;
        int pow;
        Double coef, exCoef;
        while (polyQueue.size() != 1)
        {
            result.clear();
            leftArg = polyQueue.poll();
            rightArg = polyQueue.poll();
            for (Entry lEntry : leftArg.entrySet())
                for (Entry rEntry : rightArg.entrySet())
                {
                    //TODO Проверить работу
                    pow = (Integer)lEntry.getKey()+(Integer)rEntry.getKey();
                    coef = (Double)lEntry.getValue()*(Double)rEntry.getValue();
                    exCoef = result.putIfAbsent(pow, coef);
                    if (exCoef == null) exCoef = 0.0;
                    {
                        exCoef += coef;
                        if (exCoef == 0.0)
                            result.remove(pow);
                        else
                            result.put(pow, exCoef);
                    }
                }
            polyQueue.add((Poly)result.clone());
        }
        result = polyQueue.poll();
        polyQueue.clear();
        return result;
    }

    public static Poly add(ArrayList<Poly> vars){
        Poly result = new Poly();
        Double currValue;
        for (Poly poly : vars)
            for (Entry entry : poly.entrySet()) {
                currValue = result.putIfAbsent((Integer) entry.getKey(), (Double) entry.getValue());
                if (currValue != null)
                {
                    currValue += (Double) entry.getValue();
                    if (currValue == 0.0)
                        result.remove(entry.getKey());
                    else
                        result.put((Integer) entry.getKey(), currValue);
                }
            }
        return result;
    }

    public static Poly multiply(ArrayList<Poly> vars){
        Poly result = new Poly();
        Queue<Poly> polyQueue = new ArrayDeque<>();
        for (Poly p : vars)
            polyQueue.offer(p);
        Poly leftArg, rightArg;
        int pow;
        Double coef, exCoef;
        while (polyQueue.size() != 1)
        {
            result.clear();
            leftArg = polyQueue.poll();
            rightArg = polyQueue.poll();
            for (Entry lEntry : leftArg.entrySet())
                for (Entry rEntry : rightArg.entrySet())
                {
                    //TODO Проверить работу
                    pow = (Integer)lEntry.getKey()+(Integer)rEntry.getKey();
                    coef = (Double)lEntry.getValue()*(Double)rEntry.getValue();
                    exCoef = result.putIfAbsent(pow, coef);
                    if (exCoef == null) exCoef = 0.0;
                    {
                        exCoef += coef;
                        if (exCoef == 0.0)
                            result.remove(pow);
                        else
                            result.put(pow, exCoef);
                    }
                }
            polyQueue.add((Poly)result.clone());
        }
        result = polyQueue.poll();
        return (Poly)result.clone();
    }

    public static String print(Poly arg) {
        String result = "";
        for (Entry entry : arg.entrySet())
        {
            result+=elemString(entry);
        }
        result += '\n';
        return result;
    }
    private static String elemString(Entry entry) {
        String result = "";
        result += String.format("%+.3f*x^%d", entry.getValue(), entry.getKey());
        return result;
    }

    public Poly divideBy(double value) {
        for (Entry entry : this.entrySet())
        {
            entry.setValue((double)entry.getValue()/value);
        }
        return this;
    }
}
