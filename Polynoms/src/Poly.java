import java.util.*;
/**
 * @author evaran
 */
public class Poly extends HashMap<Integer, Double> {
    public Poly() {
        super();
    }
    public Poly(int pow, double coef)
    {
        super();
        put(pow, coef);
    }

    /**
     * Creates monome (x - coeff).
     *
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
     *
     * @param args Extra arguments
     * @return Poly
     */
    public static Poly add(Poly... args) {
        Poly result = new Poly();
        ArrayList<Poly> summands = new ArrayList<>(Arrays.asList(args));
        result = add(summands);
        return result;
    }

    /**
     * Multiplying of number of polynomials.
     *
     * @param args List of polynomials to times.
     * @return Poly
     */
    public static Poly times(Poly... args) {
        Poly result = new Poly();
        ArrayList<Poly> factors = new ArrayList<>(Arrays.asList(args));
        result = times(factors);
        return result;
    }

    /**
     * Addition of number of polynomials sent in ArrayList.
     *
     * @param args List of polynomials to add up.
     * @return Poly
     */
    public static Poly add(ArrayList<Poly> args) {
        Poly result = new Poly();
        Double currValue;
        Integer entryKey;
        Double entryValue;
        for (Poly poly : args)
            for (Entry entry : poly.entrySet()) {
                entryKey = (Integer) entry.getKey();
                entryValue = (Double) entry.getValue();
                currValue = result.putIfAbsent(entryKey, entryValue);
                if (currValue != null) {
                    currValue += entryValue;
                    if (currValue == 0.0)
                        result.remove(entryKey);
                    else
                        result.put(entryKey, currValue);
                }
            }
        return result;
    }

    /**
     * Multiplying of number of polynomials sent in ArrayList.
     *
     * @param args List of polynomials to times.
     * @return Poly
     */
    public static Poly times(ArrayList<Poly> args) {
        Poly result = new Poly();
        Queue<Poly> factorsQueue = new ArrayDeque<>();
        for (Poly p : args)
            factorsQueue.offer(p);
        Poly leftArg, rightArg;
        int pow;
        Double coef, exCoef; // exCoef presents coefficient for power that already exsists in result
        while (factorsQueue.size() != 1) {
            result.clear();
            leftArg = factorsQueue.poll();
            rightArg = factorsQueue.poll();
            for (Entry lEntry : leftArg.entrySet())
                for (Entry rEntry : rightArg.entrySet()) {
                    pow = (Integer) lEntry.getKey() + (Integer) rEntry.getKey();
                    coef = (Double) lEntry.getValue() * (Double) rEntry.getValue();
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
            factorsQueue.add((Poly) result.clone());
        }
        result = factorsQueue.poll();
        return result;
    }

    public static String toString(Poly arg) {
        String result = "";
        for (Entry entry : arg.entrySet()) {
            result += elemString(entry);
        }
        result += '\n';
        return result;
    }

    private static String elemString(Entry entry) {
        String result = "";
        if (!entry.getValue().equals(0.0))
            result += String.format("%+.3f*x^%d", entry.getValue(), entry.getKey());
        return result;
    }

    public Poly times(double value) {
        for (Entry entry : this.entrySet()) {
            entry.setValue((double) entry.getValue() * value);
        }
        return this;
    }

    public static Poly minus (Poly arg) {
        Poly result =  new Poly();
        for (Entry entry : arg.entrySet())
            result.put((Integer)entry.getKey(), -(Double)entry.getValue());
        return result;
    }

    public double evaluate(double arg)
    {
        double result = 0.0;
        for (Entry entry : entrySet()) {
            double pow = (Integer)entry.getKey();
            double val = (Double)entry.getValue();
            result += val * Math.pow(arg, pow);
        }
        return result;
    }
}
