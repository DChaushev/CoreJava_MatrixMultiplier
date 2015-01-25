/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fmi.corejava.matrix;

import com.fmi.corejava.io.MatrixFileCommunicator;
import com.fmi.corejava.operations.MatrixMutiplier;
import java.io.File;
import java.util.Arrays;

/**
 *
 * @author Dimitar
 */
public class Matrix {

    private double[][] matrix;

    public Matrix(File file) {
        this.setMatrix(MatrixFileCommunicator.loadMatrix(file));
    }

    public Matrix(double[][] matrix) {
        this.setMatrix(matrix);
    }
    
    public Matrix(Matrix matrix){
        this.setMatrix(matrix.getMatrix());
    }

    public final double[][] getMatrix() {
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = matrix[i][j];
            }
        }
        return result;
    }

    public synchronized void setMatrix(double[][] matrix) {
        this.matrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
    }

    public Matrix multipy(Matrix m){
        return multiply(m, false, 0 /*this value doesn't matter*/);
    }

    public Matrix multiplyMultiThreaded(Matrix m) {
        return multiply(m, true, Runtime.getRuntime().availableProcessors());
    }
    
    public Matrix multiplyMultiThreaded(Matrix m, int threads) {
        return multiply(m, true, threads);
    }

    private Matrix multiply(Matrix m, boolean multy, int threads){

        final double[][] matrixTwo = m.getMatrix();

        MatrixMutiplier multiplyer = new MatrixMutiplier(matrix, matrixTwo);
        
        if(!multy){
            return new Matrix(multiplyer.computeSingleThreaded());
        }else
            return new Matrix(multiplyer.computeMultiThreaded(threads));
    }

    public double getElement(int i, int j) {
        return matrix[i][j];
    }
    
    public int getRows(){
        return matrix.length;
    }
    
    public int getCols(){
        return matrix[0].length;
    }

    @Override
    public boolean equals(Object matrixTwo1) {
        Matrix matrixTwo = (Matrix) matrixTwo1;
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != matrixTwo.getElement(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Arrays.deepHashCode(this.matrix);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result.append(String.format("%f ", matrix[i][j]));
            }
            result.append("\n");
        }
        return result.toString();
    }

}
