package com.fmi.corejava.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
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

    public void setMatrices(double[][] matrixOne, double[][] matrixTwo) {

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
            for (int l = 0; l < matrixTwo.length; l++) {
                for (int j = 0; j < matrixTwo[0].length; j++) {
                    result[i][j] += matrixOne[i][l] * matrixTwo[l][j];
                }
            }
        }
        return result;
    }
    
    public double[][] computeMultiThreaded(){
        return computeMultiThreaded(Runtime.getRuntime().availableProcessors());
    }

    public double[][] computeMultiThreaded(int cores) {

        ForkJoinPool pool = new ForkJoinPool(cores);
        List<RecursiveAction> threads = new ArrayList<>();

        for (int i = 0; i < result.length; i++) {
            RecursiveAction r = new MatrixMultiplierMultiThreaded(i);
            threads.add(r);
            pool.execute(r);
        }

        threads.stream().forEach((thread) -> {
            thread.join();
        });

        return result;
    }

    private class MatrixMultiplierMultiThreaded extends RecursiveAction {

        private int row;

        public MatrixMultiplierMultiThreaded(int row) {
            this.row = row;
        }

        @Override
        protected void compute() {
            for (int i = 0; i < matrixTwo.length; i++) {
                for (int j = 0; j < matrixTwo[0].length; j++) {
                    result[row][j] += matrixOne[row][i] * matrixTwo[i][j];
                }

            }
        }
    }
}
