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
public class Latian {

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
//            {1.625, 0.076, 1, 2, 2, 1.238, 0.1318, 0.8988, 2.5905, 0.2397, 27.286},
//            {1.654, 0.081, 1.2, 2.3, 2.2, 1.333, 0.1451, 0.8789, 2.7101, 0.2444, 28.1124},
//            {2.1512, 0.055, 1, 3, 3, 2.52, 0.2352, 0.9101, 4.1251, 0.4412, 20.5123},
//            {3.1124, 0.0691, 1, 3.7721, 3.5512, 2.125, 0.3221, 1.3422, 5.1231, 0.5512, 24.1241},
//            
//        };
                    double[][] data = {
                        {0.2, 0.1, 0.1, 0.3, 0.1, 0.1, 0.1},
                        {0.1, 0.2, 0.1, 0.2, 0.2, 0.1, 0.1},
                        {0.5, 0, 0, 0.1, 0.2, 0.1, 0.1},
                        {0.1, 0, 0.4, 0.1, 0.1, 0.2, 0.1},
                        {0.3, 0, 0.2, 0, 0.2, 0.1, 0.2},};

            double[] classList = {1, 1, 1, -1, -1, 0};
//        
        double[][] rbfMatrix = svm.createRBFMatrix(data, 0.1);

        System.out.println("Matriks Fitur");
        svm.printMatrix(data);
        System.out.println("");

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

//        //contoh dotProduct data test
//        double[] dotProduct = {0.0183, 0.1353, 0, 0.0067, 0.0067};
//        double[] classes = {1, 1, 1, -1, -1};
//
//        System.out.println("\nResult of classification : " + svm.classify(solutions, dotProduct, classes));
    }
}
