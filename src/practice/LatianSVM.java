/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import Jama.Matrix;
import svm.SVM;

/**
 *
 * @author Filipus
 */
public class LatianSVM {

    public static void main(String[] args) {
        SVM svm = new SVM();
//        double[][] rbfMatrix = {
//            {1,0.1353,0.0003,0.0003,0.0009},
//            {0.1353,1,0,0.0003,0.0009},
//            {0.0003,0,1,0,0.0067},
//            {0.0003,0.0003,0,1,0.0025},
//            {0.0009,0.0009,0.0067,0.0025,1},
//        };
//        double[][] data = {
//            {1.625, 0.076, 1, 2, 2, 1.238, 0.132, 0.898, 2.590, 0.239, 27.29, 0.712},
//            {1.654, 0.0814, 1.2, 2.3, 2.2, 1.333, 0.145, 0.879, 2.710, 0.244, 28.11, 0.961},
//            {2.151, 0.055, 1, 3, 3, 2.52, 0.235, 0.910, 4.125, 0.441, 20.51, 0.881},
//            {3.112, 0.069, 1, 3.772, 3.551, 2.125, 0.322, 1.342, 4.123, 0.551, 24.12, 0.662},};
        double[][] data = {
            {0.2, 0.1, 0.1, 0.3, 0.1, 0.1, 0.1},
            {0.1, 0.2, 0.1, 0.2, 0.2, 0.1, 0.1},
            {0.5, 0, 0, 0.1, 0.2, 0.1, 0.1},
            {0.1, 0, 0.4, 0.1, 0.1, 0.2, 0.1},
            {0.3, 0, 0.2, 0, 0.2, 0.1, 0.2},};

//        double[] classList = {1, 1, -1, -1, 0};
        double[] classList = {1, 1, 1, -1, -1, 0};
//        
        double[][] rbfMatrix = svm.createRBFMatrix(data, 0.1);

        System.out.println("Matriks Hasil Perhitungan RBF");
        svm.printMatrix(rbfMatrix);
        System.out.println("");

        double[][] linearEquation = svm.createLinearEquationMatrix(rbfMatrix, classList);

        System.out.println("Matriks Persamaan Linear");
        svm.printMatrix(linearEquation);
        Matrix solutions = svm.getSolutions(linearEquation, classList);

        System.out.println("\nSolusi (nilai alpha dan beta)");
        for (int i = 0; i < linearEquation.length; i++) {
            System.out.println("X-" + i + " = " + solutions.get(i, 0));
        }

        //contoh dotProduct data test
//        double[] dotProduct = {0.0183, 0.1353, 0, 0.0067, 0.0067};
//        double[] classes = {1, 1, 1, -1, -1};
//
//        System.out.println("\nResult of classification : " + svm.classify(solutions, dotProduct, classes));

    }
}
