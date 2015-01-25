/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fmi.corjava.tests;

import com.fmi.corejava.io.MatrixFileCommunicator;
import com.fmi.corejava.operations.MatrixMutiplier;
import java.io.File;
import org.junit.Test;

/**
 *
 * @author Dimitar
 */
public class IOTest {

    private static MatrixMutiplier multiplier;
    private static double[][] result;

    private static double[][] example1Matrix1;
    private static double[][] example1Matrix2;

    private static double[][] example3Matrix1;
    private static double[][] example3Matrix2;

    @Test
    public void readMatrixTest() {
        example1Matrix1 = MatrixFileCommunicator.loadMatrix(new File("ExampleMatrices\\Ex1\\left"));
        example1Matrix2 = MatrixFileCommunicator.loadMatrix(new File("ExampleMatrices\\Ex1\\right"));
    }

    @Test
    public void writeMatrixTest() {
        example1Matrix1 = MatrixFileCommunicator.loadMatrix(new File("ExampleMatrices\\Ex1\\left"));
        example1Matrix2 = MatrixFileCommunicator.loadMatrix(new File("ExampleMatrices\\Ex1\\right"));
        MatrixFileCommunicator.writeMatrix(new MatrixMutiplier(example1Matrix1, example1Matrix2).computeMultiThreaded(), new File("resultMatrix"));
    }

    @Test(expected = ArithmeticException.class)
    public void readFalseMatricesARexTest() {
        example3Matrix1 = MatrixFileCommunicator.loadMatrix(new File("ExampleMatrices\\Ex3\\left"));
        example3Matrix2 = MatrixFileCommunicator.loadMatrix(new File("ExampleMatrices\\Ex3\\right"));
        new MatrixMutiplier(example3Matrix1, example3Matrix2);
    }
}
