/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fmi.corejava.main;

import com.fmi.corejava.io.MatrixFileInteraction;
import com.fmi.corejava.operations.MatrixMutiplier;
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

        int cores = Runtime.getRuntime().availableProcessors();
        double T;
        int a;
        double[][] m1 = MatrixFileInteraction.readMatrix(new File("ExampleMatrices\\Ex2\\left"));
        double[][] m2 = MatrixFileInteraction.readMatrix(new File("ExampleMatrices\\Ex2\\right"));
        double[][] result = MatrixFileInteraction.readMatrix(new File("ExampleMatrices\\Ex2\\result"));

        long startTime;
        double[][] res = null;
        long endTime;

        //1.
        System.out.println("Starting linear multiplication...");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            res = new MatrixMutiplier(m1, m2).computeSingleThreaded();
        }
        endTime = System.currentTimeMillis();
        T = (endTime - startTime) / 10;
        System.out.printf("    Sync: %.2f\n", T);
        System.out.printf("    Sync result: %b\n", compare(res, result));

        //2.
        System.out.println("Starting milti-threaded multiplication...");
        for (int i = 1; i <= cores; i++) {
            startTime = System.currentTimeMillis();
            for (int j = 0; j < 10; j++) {
                res = new MatrixMutiplier(m1, m2).computeMultiThreaded(i);
            }
            endTime = System.currentTimeMillis();
            a = (int) (endTime - startTime) / 10;
            System.out.printf("    a[%d] = Speed: %d Acc: %.4f times\n", i, a, T / a);
            System.out.printf("    Async result: %b\n", compare(res, result));
        }
        //printMatrix(res);

    }

}
