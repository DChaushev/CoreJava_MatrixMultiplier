package com.fmi.corejava.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author Dimitar
 */
public class MatrixMutiplier {

    private double[][] matrixOne;
    private double[][] matrixTwo;
    private double[][] result;

    public MatrixMutiplier(double[][] matrixOne, double[][] matrixTwo) {
        this.setMatrices(matrixOne, matrixTwo);
    }

    private void setMatrices(double[][] matrixOne, double[][] matrixTwo) {

        if (matrixOne == null || matrixTwo == null) {
            throw new NullPointerException("The matrices are not initialized");
        }

        if (matrixOne.length == 0 || matrixTwo.length == 0) {
            throw new ArrayIndexOutOfBoundsException("The matrix has 0 columns => not matrix");
        }

        if (matrixOne[0].length != matrixTwo.length) {
            throw new ArithmeticException("The matrices cannot be multiplied");
        }

        this.matrixOne = matrixOne;
        this.matrixTwo = matrixTwo;
        result = new double[matrixOne.length][matrixTwo[0].length];
    }

    public double[][] computeSingleThreaded() {

        for (int i = 0; i < matrixOne.length; i++) {
            for (int j = 0; j < matrixTwo[0].length; j++) {
                for (int l = 0; l < matrixTwo.length; l++) {
                    result[i][j] += matrixOne[i][l] * matrixTwo[l][j];
                }
            }
        }
        return result;
    }

    public double[][] computeMultiThreaded() {

        List<RecursiveAction> threads = new ArrayList<>();

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                MatrixMultiplierMultiThreaded m = new MatrixMultiplierMultiThreaded(i, j);
                threads.add(m);
            }
        }

        ForkJoinTask.invokeAll(threads);
        threads.stream().forEach((thread) -> {
            thread.join();
        });

        return result;
    }

    private class MatrixMultiplierMultiThreaded extends RecursiveAction {

        private int row;
        private int col;

        public MatrixMultiplierMultiThreaded(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        protected void compute() {
            for (int i = 0; i < matrixTwo.length; i++) {
                result[row][col] += matrixOne[row][i] * matrixTwo[i][col];
            }
        }
    }
}
