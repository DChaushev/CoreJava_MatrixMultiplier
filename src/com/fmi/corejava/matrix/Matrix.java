/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fmi.corejava.matrix;

import com.fmi.corejava.io.MatrixFileInteraction;
import com.fmi.corejava.operations.MatrixMutiplier;
import java.io.File;

/**
 *
 * @author Dimitar
 */
public class Matrix {

    private double[][] matrix;

    public Matrix(File file) {
        this.setContent(MatrixFileInteraction.readMatrix(file));
    }

    public Matrix(double[][] matrix) {
        this.setContent(matrix);
    }
    
    public Matrix(Matrix matrix){
        this.setContent(matrix.getContent());
    }

    public final double[][] getContent() {
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = matrix[i][j];
            }
        }
        return result;
    }

    public synchronized void setContent(double[][] matrix) {
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
    
    public Matrix multiplyMultiThreaded(Matrix m, int cores) {
        return multiply(m, true, cores);
    }

    private Matrix multiply(Matrix m, boolean multy, int cores){

        final double[][] matrixTwo = m.getContent();

        MatrixMutiplier multiplyer = new MatrixMutiplier(matrix, matrixTwo);
        
        if(!multy){
            return new Matrix(multiplyer.computeSingleThreaded());
        }else
            return new Matrix(multiplyer.computeMultiThreaded(cores));
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
