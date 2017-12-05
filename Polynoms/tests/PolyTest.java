import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by evaran on 23.11.2017.
 */
public class PolyTest {
    @Test
    public void print() throws Exception {
        Poly poly1 = new Poly();
        poly1.put(1, 1.0);
        String result = Poly.toString(poly1);
        String expected = "1=1.0\n";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testAddBi() {
        //standart adding
        Poly poly1 = new Poly();
        Poly poly2 = new Poly();
        poly1.put(0, 1.0);
        poly2.put(3, 2.0);
        Poly res = new Poly();
        Poly expected = new Poly();
        expected.put(3, 2.0);
        expected.put(0, 1.0);
        res = Poly.add(poly1, poly2);
        Assert.assertEquals("Standart  ", expected, res);

        //adding w/ one power
        poly1 = new Poly();
        poly2 = new Poly();
        poly1.put(3, 1.0);
        poly2.put(3, 2.0);
        res = new Poly();
        expected = new Poly();
        expected.put(3, 3.0);
        res = Poly.add(poly1, poly2);
        Assert.assertEquals("One power: ", expected, res);

        //adding w/ zero coeff
        poly1 = new Poly();
        poly2 = new Poly();
        poly1.put(3, 1.0);
        poly2.put(3, -1.0);
        res = new Poly();
        expected = new Poly();
        res = Poly.add(poly1, poly2);
        Assert.assertEquals("Empty result: ", expected, res);

        //empty argument
        poly1 = new Poly();
        poly2 = new Poly();
        poly2.put(3, -1.0);
        res = new Poly();
        expected = new Poly();
        expected.put(3, -1.0);
        res = Poly.add(poly1, poly2);
        Assert.assertEquals("Empty argument: ", expected, res);
    }

    @Test
    public void testAddBiArray() {
        //standart adding
        Poly poly1 = new Poly();
        Poly poly2 = new Poly();
        poly1.put(0, 1.0);
        poly2.put(3, 2.0);
        ArrayList<Poly> polys = new ArrayList<>();
        polys.add(poly1);
        polys.add(poly2);
        Poly res = new Poly();
        Poly expected = new Poly();
        expected.put(3, 2.0);
        expected.put(0, 1.0);
        res = Poly.add(polys);
        Assert.assertEquals("Standart  ", expected, res);

        //adding w/ one power
        poly1 = new Poly();
        poly2 = new Poly();
        poly1.put(3, 1.0);
        poly2.put(3, 2.0);
        polys = new ArrayList<>();
        polys.add(poly1);
        polys.add(poly2);
        res = new Poly();
        expected = new Poly();
        expected.put(3, 3.0);
        res = Poly.add(polys);
        Assert.assertEquals("One power: ", expected, res);

        //adding w/ zero coeff
        poly1 = new Poly();
        poly2 = new Poly();
        poly1.put(3, 1.0);
        poly2.put(3, -1.0);
        polys = new ArrayList<>();
        polys.add(poly1);
        polys.add(poly2);
        res = new Poly();
        expected = new Poly();
        res = Poly.add(polys);
        Assert.assertEquals("Empty result: ", expected, res);

        //empty argument
        poly1 = new Poly();
        poly2 = new Poly();
        poly2.put(3, -1.0);
        polys = new ArrayList<>();
        polys.add(poly1);
        polys.add(poly2);
        res = new Poly();
        expected = new Poly();
        expected.put(3, -1.0);
        res = Poly.add(polys);
        Assert.assertEquals("Empty argument: ", expected, res);
    }

    @Test
    public void testMultiplyBi() {
        //standart mult
        Poly poly1 = new Poly();
        Poly poly2 = new Poly();
        poly1.put(1, 1.0);
        poly2.put(3, 2.0);
        Poly res = new Poly();
        Poly expected = new Poly();
        expected.put(4, 2.0);
        res = Poly.times(poly1, poly2);
        Assert.assertEquals("Standart  ", expected, res);

        //mult on zero
        poly1 = new Poly();
        poly2 = new Poly();
        poly1.put(0, 0.0);
        poly2.put(3, -1.0);
        res = new Poly();
        expected = new Poly();
        res = Poly.times(poly1, poly2);
        Assert.assertEquals("Empty on smth: ", expected, res);

        //mult empty on smth
        poly1 = new Poly();
        poly2 = new Poly();
        poly2.put(3, -1.0);
        res = new Poly();
        expected = new Poly();
        res = Poly.times(poly1, poly2);
        Assert.assertEquals("Smth on empty: ", expected, res);

        //mult on empty
        poly1 = new Poly();
        poly2 = new Poly();
        poly1.put(3, -1.0);
        res = new Poly();
        expected = new Poly();
        res = Poly.times(poly1, poly2);
        Assert.assertEquals("onZero: ", expected, res);
    }

    @Test
    public void testAddMany() {
        //standart adding
        Poly poly1 = new Poly();
        Poly poly2 = new Poly();
        Poly poly3 = new Poly();
        poly1.put(0, 1.0);
        poly2.put(3, 2.0);
        poly3.put(3, -1.0);
        Poly res = new Poly();
        Poly expected = new Poly();
        expected.put(3, 1.0);
        expected.put(0, 1.0);
        res = Poly.add(poly1, poly2, poly3);
        Assert.assertEquals("Standart  ", expected, res);

        //adding w/ one power
        poly1 = new Poly();
        poly2 = new Poly();
        poly3 = new Poly();
        poly1.put(3, 1.0);
        poly2.put(3, 2.0);
        poly3.put(3, 2.0);
        res = new Poly();
        expected = new Poly();
        expected.put(3, 5.0);
        res = Poly.add(poly1, poly2, poly3);
        Assert.assertEquals("One power: ", expected, res);

        //adding w/ zero coeff
        poly1 = new Poly();
        poly2 = new Poly();
        poly3 = new Poly();
        poly1.put(3, 1.0);
        poly2.put(3, -1.0);
        poly3.put(3, -1.0);
        res = new Poly();
        expected = new Poly();
        expected.put(3, -1.0);
        res = Poly.add(poly1, poly2, poly3);
        Assert.assertEquals("Triple over zero coeff: ", expected, res);

        //empty argument
        poly1 = new Poly();
        poly2 = new Poly();
        poly3 = new Poly();
        poly2.put(3, -1.0);
        poly3.put(15, 2.0);
        poly3.put(3, 3.0);
        res = new Poly();
        expected = new Poly();
        expected.put(3, 2.0);
        expected.put(15, 2.0);
        res = Poly.add(poly1, poly2, poly3);
        Assert.assertEquals("Empty argument: ", expected, res);
    }

    @Test
    public void multiplyMany() {
        //standart mult
        Poly poly1 = new Poly();
        Poly poly2 = new Poly();
        Poly poly3 = new Poly();
        poly1.put(0, 1.0);
        poly2.put(3, 2.0);
        poly3.put(1, 1.0);
        Poly res = new Poly();
        Poly expected = new Poly();
        expected.put(4, 2.0);
        res = Poly.times(poly1, poly2, poly3);
        Assert.assertEquals("Standart  ", expected, res);
    }

    @Test
    public void testMult() {
        Poly poly1 = new Poly();
        poly1.put(0, 1.0);
        poly1.put(1, 2.0);
        ArrayList<Poly> polys = new ArrayList<>();
        polys.add(poly1);
        Poly res = new Poly();
        Poly expected = new Poly();
        expected.put(0, 1.0);
        expected.put(1, 2.0);
        res = Poly.times(polys);
        Assert.assertEquals("Standart  ", expected, res);
    }
}