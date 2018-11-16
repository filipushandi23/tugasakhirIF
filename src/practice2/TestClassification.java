/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice2;

import Jama.Matrix;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import svm.SVM;

/**
 *
 * @author Filipus
 */
public class TestClassification {

    public static void main(String[] args) throws FileNotFoundException {
        SVM svm = new SVM();
        String[] classes = {"Iris-setosa", "Iris-versicolor", "Iris-virginica"};
        //coba load data
        String inputClass = "Iris-setosa";
//        String inputClass = "Iris-versicolor";
//        String inputClass = "Iris-virginica";
        String[][] dataset = getDataFromText("F:\\dataset-asal.txt", 15, 5);
        double[][] data = new double[15][4];
//        System.out.println(dataset[0].length);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] = Double.parseDouble(dataset[i][j]);
            }
        }

        double[] classList = new double[16];
        for (int i = 0; i < classList.length - 1; i++) {
            if (dataset[i][4].equals(inputClass)) {
                classList[i] = 1;
            } else {
                classList[i] = -1;
            }
        }
        classList[15] = 0;

//        for (int i = 0; i < classList.length; i++) {
//            System.out.println(classList[i]);
//        }
//        for (int i = 0; i < data.length; i++) {
//            for (int j = 0; j < data[0].length; j++) {
//                System.out.print(data[i][j]+"\t");
//            }
//            System.out.println("");
//        }
        double[][] rbfMatrix = svm.createRBFMatrix(data, 1);
        double[][] linearEquation = svm.createLinearEquationMatrix(rbfMatrix, classList);
        
        for (int i = 0; i < rbfMatrix.length; i++) {
            for (int j = 0; j < rbfMatrix.length; j++) {
                System.out.print(rbfMatrix[i][j]+"\t");
            }
            System.out.println("");
        }

        Matrix solutions = svm.getSolutions(linearEquation, classList);

        double[][] solutionsLoad = new double[linearEquation.length][1];
        for (int i = 0; i < linearEquation.length; i++) {
            solutionsLoad[i][0] = solutions.get(i, 0);
        }
//        
        double[] dataTest1 = {1.22551,2.57912,1.231254,12.44124,};
        double[] dataTest2 = {1.9812,2.874153,1.67852,13.998325};
        double[] dataTest3 = {3.13563,2.433126,1.791369,11.9844};
        double[] rbfMatrixTest = svm.createRBFTestMatrix(data, 1, dataTest1);
        
        double result = svm.classify(solutionsLoad, rbfMatrixTest, classList);
        System.out.println("Result of classification: "+result);
    }

    public static String[][] getDataFromText(String path, int n, int features) throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(path)));
//      Scanner sc = new Scanner(new BufferedReader(new FileReader("F:\\data.txt")));

        String[][] data = new String[n][features];
        while (sc.hasNextLine()) {
            for (int i = 0; i < data.length; i++) {
                String[] line = sc.nextLine().trim().split(",");
                for (int j = 0; j < line.length; j++) {
                    data[i][j] = line[j];
                }
            }
        }

        return data;
    }
}
