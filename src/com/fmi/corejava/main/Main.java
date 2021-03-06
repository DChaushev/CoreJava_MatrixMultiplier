/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fmi.corejava.main;

import com.fmi.corejava.io.MatrixFileCommunicator;
import com.fmi.corejava.matrix.Matrix;
import java.io.File;

/**
 *
 * @author Dimitar
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Matrix m1 = new Matrix(new File("ExampleMatrices\\Ex1\\left"));
        Matrix m2 = new Matrix(new File("ExampleMatrices\\Ex1\\right"));
        Matrix result = new Matrix(new File("ExampleMatrices\\Ex1\\result"));

        int threads = Runtime.getRuntime().availableProcessors();
        double T;
        int a;

        long startTime;
        Matrix res = null;
        long endTime;
        
        int attempts = 10;

        //1.
        try {

            System.out.println("Starting linear multiplication...");
            startTime = System.currentTimeMillis();
            for (int i = 0; i < attempts; i++) {
                res = m1.multipy(m2);
            }
            endTime = System.currentTimeMillis();
            T = (endTime - startTime) / attempts;
            System.out.printf("    Sync: %.2f\n", T);
            System.out.printf("    Sync result: %b\n", res.equals(result));

            //2.
            System.out.println("Starting milti-threaded multiplication...");
            for (int i = 1; i <= threads; i++) {
                startTime = System.currentTimeMillis();
                for (int j = 0; j < attempts; j++) {
                    res = m1.multiplyMultiThreaded(m2, i);
                }
                endTime = System.currentTimeMillis();
                a = (int) (endTime - startTime) / attempts;
                System.out.printf("    a[%d] = Speed: %d Acc: %.4f times\n", i, a, T / a);
                System.out.printf("    Async result: %b\n", res.equals(result));
            }

            MatrixFileCommunicator.writeMatrix(res.getMatrix(), new File("calculated_result"));

        } catch (ArithmeticException | ArrayIndexOutOfBoundsException | NullPointerException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
