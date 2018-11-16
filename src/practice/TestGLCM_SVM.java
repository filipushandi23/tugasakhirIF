/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import Jama.Matrix;
import glcm.GLCM;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import svm.SVM;

/**
 *
 * @author Filipus
 */
public class TestGLCM_SVM {

    public static void main(String[] args) throws FileNotFoundException {
        SVM svm = new SVM();
        double[][] data = getDataFromText("F:\\Dataset Text\\glcm-extracted-dataset-training-5features.txt",960,5);
//        double[][] data = getDataFromText("F:\\Dataset Text\\glcm-extracted-dataset-training.txt",960,6);
//        for (int i = 0; i < data.length; i++) {
//            for (int j = 0; j < data[0].length; j++) {
//                System.out.print(data[i][j]+"\t");
//            }
//            System.out.println("");
//        }
//        double[] classList = {
//            1, 1, 1,
//            1,1,1,
//            1,1,1,
//            1,1,1,
//            1,1,1,
//            -1,-1,-1,
//            -1,-1,-1,
//            -1,-1,-1,
//            -1,-1,-1,
//            -1,-1,-1,
//            -1,-1,-1,
//            0};
        double[] classList = new double[961];
        for (int i = 0; i < 30; i++) {
            classList[i] = -1;
        }
        for (int i = 30; i < 60; i++) {
            classList[i] = 1;
        }
        for (int i = 60; i < classList.length-1; i++) {
            classList[i] = -1;
        }
        classList[960] = 0;
        //double[] classList = {1, 1, 1, -1, -1, 0};
        
        
        //sigma = 10
        double[][] rbfMatrix = svm.createRBFMatrix(data, 10);

//        System.out.println("Matriks Hasil Perhitungan RBF");
//        svm.printMatrix(rbfMatrix);
//        System.out.println("");

        double[][] linearEquation = svm.createLinearEquationMatrix(rbfMatrix, classList);

//        System.out.println("Matriks Persamaan Linear");
//        svm.printMatrix(linearEquation);
        Matrix solutions = svm.getSolutions(linearEquation, classList);

        System.out.println("\nSolusi (nilai alpha dan beta)");
        for (int i = 0; i < linearEquation.length; i++) {
            System.out.println("X-" + i + " = " + solutions.get(i, 0));
        }
        
        StringBuilder builder = new StringBuilder();
        try {
            for (int i = 0; i < linearEquation.length; i++) {
                builder.append(solutions.get(i,0));
                builder.append(System.getProperty("line.separator"));
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\Dataset Text\\model\\glcm\\model2-5features.txt"));
            writer.write(builder.toString());//save the string representation of the board
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double[][] getDataFromText(String path, int n, int features) throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(path)));
//      Scanner sc = new Scanner(new BufferedReader(new FileReader("F:\\data.txt")));

        double[][] data = new double[n][features];
        while (sc.hasNextLine()) {
            for (int i = 0; i < data.length; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j = 0; j < line.length; j++) {
                    data[i][j] = Double.parseDouble(line[j]);
                }
            }
        }

        return data;
    }
}
