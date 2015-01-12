/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fmi.corjava.tests;

import com.fmi.corejava.io.MatrixFileInteraction;
import com.fmi.corejava.matrix.Matrix;
import java.io.File;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dimitar
 */
public class MatrixTest {

    private static Matrix result;

    private static Matrix testMatrix1;
    private static Matrix testMatrix2;
    private static Matrix testResult;

    private static Matrix testSimple1;
    private static Matrix testSimple2;
    private static Matrix testSimpleResult;

    private static Matrix testImpossible1;
    private static Matrix testImpossible2;

    @BeforeClass
    public static void setUpClass() {

        testMatrix1 = new Matrix(new double[][]{{1, 2}, {4, 6}});
        testMatrix2 = new Matrix(new double[][]{{7, 6}, {3, 2}});
        testResult = new Matrix(new double[][]{{13, 10}, {46, 36}});

        testSimple1 = new Matrix(new double[][]{{2}});
        testSimple2 = new Matrix(new double[][]{{4, 6, 12}});
        testSimpleResult = new Matrix(new double[][]{{8, 12, 24}});

        testImpossible1 = new Matrix(new double[][]{{1, 2, 3}});
        testImpossible2 = new Matrix(new double[][]{{1, 2}, {3, 4}});

    }

    @Test
    public void testMatrixConsructors(){
        File f = new File("ExampleMatrices\\Ex1\\left");
        
        double[][] matrixFromFile = MatrixFileInteraction.readMatrix(f);
        Matrix matrix1 = new Matrix(f);
        Matrix matrix2 = new Matrix(matrixFromFile);
        Matrix matrix3 = new Matrix(matrix2);
        
        assertTrue(matrix1.equals(matrix2));
        assertTrue(matrix1.equals(matrix3));
        
    }
    
    @Test
    public void testGetters(){
        int rows = testMatrix1.getRows();
        int cols = testMatrix1.getCols();
        
        assertEquals(rows, 2);
        assertEquals(cols, 2);
    }
    
    @Test
    public void testNotEquals(){
        assertFalse(testMatrix1.equals(testMatrix2));
    }
    
    @Test(expected = ArithmeticException.class)
    public void singleThreadedMultiplicationTest() {

        result = testMatrix1.multipy(testMatrix2);
        assertTrue(result.equals(testResult));

        result = testSimple1.multipy(testSimple2);
        assertTrue(result.equals(testSimpleResult));

        testImpossible1.multipy(testImpossible2);

    }

    @Test(expected = ArithmeticException.class)
    public void multiThreadedMultiplicationTest() {

        result = testMatrix1.multiplyMultiThreaded(testMatrix2);
        assertTrue(result.equals(testResult));

        result = testSimple1.multiplyMultiThreaded(testSimple2, Runtime.getRuntime().availableProcessors());
        assertTrue(result.equals(testSimpleResult));

        testImpossible1.multipy(testImpossible2);
    }

    @Test(expected = NullPointerException.class)
    public void matricesSetterTestNPE() {
        Matrix m = null;
        Matrix m1 = null;

        m.multipy(m1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void matricesSetterARIOBex() {
        new Matrix(new double[][]{}).multipy(new Matrix(new double[][]{}));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void startMultithreadedWithNegativeOrZeroCores(){
        testMatrix1.multiplyMultiThreaded(testMatrix2, 0);
        testMatrix1.multiplyMultiThreaded(testMatrix2, -1);
    }
}
