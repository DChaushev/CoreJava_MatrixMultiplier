package com.fmi.corejava.operations;

/**
 *
 * @author Dimitar
 */
public class MatrixMutiplier {

    private double[][] matrixOne;
    private double[][] matrixTwo;
    double[][] result;

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
        result = new double[matrixOne[0].length][matrixTwo.length];
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
}
