package com.fmi.corejava.operations;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            for (int l = 0; l < matrixTwo.length; l++) {
                for (int j = 0; j < matrixTwo[0].length; j++) {
                    result[i][j] += matrixOne[i][l] * matrixTwo[l][j];
                }
            }
        }
        return result;
    }

    public double[][] computeMultiThreaded() {
        return computeMultiThreaded(Runtime.getRuntime().availableProcessors());
    }

    public double[][] computeMultiThreaded(final int cores) {

        int rowsPerThread = result.length / cores;
        if (rowsPerThread == 0) {
            rowsPerThread = 1;
        }

        ExecutorService service = Executors.newFixedThreadPool(cores);

        for (int i = 0; i < result.length; i += rowsPerThread) {
            Runnable r = new MatrixMultiplierMultiThreaded(i, i + Math.min(rowsPerThread, result.length - i));
            service.submit(r);
        }
        
        service.shutdown();
  
        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(MatrixMutiplier.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    private class MatrixMultiplierMultiThreaded implements Runnable {

        final private int startRow;
        final private int endRow;

        public MatrixMultiplierMultiThreaded(int startRow, int endRow) {
            //System.out.printf("%d %d\n", startRow, endRow);
            this.startRow = startRow;
            this.endRow = endRow;
        }

        @Override
        public void run() {
            for (int row = startRow; row < endRow; row++) {
                for (int i = 0; i < matrixTwo.length; i++) {
                    for (int j = 0; j < matrixTwo[0].length; j++) {
                        result[row][j] += matrixOne[row][i] * matrixTwo[i][j];
                    }
                }
            }
        }
    }
}
