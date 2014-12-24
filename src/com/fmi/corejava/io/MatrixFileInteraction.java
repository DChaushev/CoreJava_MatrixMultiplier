/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fmi.corejava.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dimitar
 */
public class MatrixFileInteraction {

    public static double[][] readMatrix(File file) {

        double[][] result = null;
        int rows;
        int cols;

        try (DataInputStream dip = new DataInputStream(new FileInputStream(file));) {

            rows = dip.readInt();
            cols = dip.readInt();
            System.out.printf("%d %d\n", rows, cols);
            result = new double[rows][cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (dip.available() > 0) {
                        result[i][j] = dip.readDouble();
                    } else {
                        i = rows;
                        j = cols;
                    }
                }
            }

            System.out.println("Matrix loaded!");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MatrixFileInteraction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MatrixFileInteraction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public static void writeMatrix(double[][] matrix, File file) {

        try (DataOutputStream dop = new DataOutputStream(new FileOutputStream(file));) {

            System.out.printf("%d %d\n", matrix.length, matrix[0].length);
            dop.writeInt(matrix.length);
            dop.writeInt(matrix[0].length);

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    dop.writeDouble(matrix[i][j]);
                }
            }

            System.out.println("Matrix written!");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MatrixFileInteraction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MatrixFileInteraction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}