/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fmi.corejava.operations;

import com.fmi.corejava.io.MatrixFileInteraction;
import java.io.File;

/**
 *
 * @author Dimitar
 */
public class Main {

    public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.printf("%.2f ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static boolean compare(double[][] matrix1, double[][] matrix2) {
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                if (matrix1[i][j] != matrix2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {

        double[][] m1 = MatrixFileInteraction.readMatrix(new File("ExampleMatrices\\Ex2\\left"));
        double[][] m2 = MatrixFileInteraction.readMatrix(new File("ExampleMatrices\\Ex2\\right"));
        double[][] result = MatrixFileInteraction.readMatrix(new File("ExampleMatrices\\Ex2\\result"));
        System.out.println("Starting multiplication");

        long startTime;
        double[][] res = null;
        long endTime;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            res = new MatrixMutiplier(m1, m2).computeSingleThreaded();
        }
        endTime = System.currentTimeMillis();
        System.out.printf("Sync: %d\n", (endTime - startTime) / 10);
        System.out.printf("Sync result: %b\n", compare(res, result));

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            res = new MatrixMutiplier(m1, m2).computeMultiThreaded();
        }
        endTime = System.currentTimeMillis();
        System.out.printf("Async: %d\n", (endTime - startTime) / 10);
        System.out.printf("Async result: %b\n", compare(res, result));
        //printMatrix(res);

    }

}
