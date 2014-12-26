/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fmi.corjava.tests;

import com.fmi.corejava.operations.MatrixMutiplier;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dimitar
 */
public class MultipliersTest {

    private static MatrixMutiplier multiplier;
    private static double[][] result;

    private static double[][] testMatrix1;
    private static double[][] testMatrix2;
    private static double[][] testResult;

    private static double[][] testSimple1;
    private static double[][] testSimple2;
    private static double[][] testSimpleResult;

    private static double[][] testImpossible1;
    private static double[][] testImpossible2;

    @BeforeClass
    public static void setUpClass() {

        testMatrix1 = new double[][]{{1, 2}, {4, 6}};
        testMatrix2 = new double[][]{{7, 6}, {3, 2}};
        testResult = new double[][]{{13, 10}, {46, 36}};

        testSimple1 = new double[][]{{2}};
        testSimple2 = new double[][]{{4, 6, 12}};
        testSimpleResult = new double[][]{{8, 12, 24}};

        testImpossible1 = new double[][]{{1, 2, 3}};
        testImpossible2 = new double[][]{{1, 2}, {3, 4}};

    }

    @Test(expected = ArithmeticException.class)
    public void singleThreadedMultiplicationTest() {

        multiplier = new MatrixMutiplier(testMatrix1, testMatrix2);
        result = multiplier.computeSingleThreaded();
        assertArrayEquals(result, testResult);

        multiplier.setMatrices(testSimple1, testSimple2);
        result = multiplier.computeSingleThreaded();
        assertArrayEquals(result, testSimpleResult);

        multiplier.setMatrices(testImpossible1, testImpossible2);

    }

    @Test(expected = ArithmeticException.class)
    public void multiThreadedMultiplicationTest() {

        multiplier = new MatrixMutiplier(testMatrix1, testMatrix2);
        result = multiplier.computeMultiThreaded();
        assertArrayEquals(result, testResult);

        multiplier.setMatrices(testSimple1, testSimple2);
        result = multiplier.computeMultiThreaded();
        assertArrayEquals(result, testSimpleResult);

        multiplier.setMatrices(testImpossible1, testImpossible2);

    }

    @Test(expected = NullPointerException.class)
    public void matricesSetterTestNPE() {
        multiplier = new MatrixMutiplier(null, null);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void matricesSetterARIOBex() {
        multiplier = new MatrixMutiplier(new double[][]{}, new double[][]{});
    }
}
