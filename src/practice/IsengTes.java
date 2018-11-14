/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import svm.SVM;

/**
 *
 * @author Filipus
 */
public class IsengTes {

    public static void main(String[] args) {
        double[][] solutions = {
            {0.8982},
            {1.0604}, 
            {0.9531}, 
            {0.9542}, 
            {0.9787}, 
            {0.9787}, 
            {-0.0468}};
        double[] dotProductTest = {0.1353, 0.0003, 0.1353, 0.0005, 0.0301, 0.0067};
        double[] classList = {1, 1, 1, -1, -1, -1, 0};
        
//        for (int i = 0; i < solutions.length; i++) {
//            System.out.println(solutions[i][0]);
//        }
        
        
        System.out.println("SVM Classify: " + classify(solutions, dotProductTest, classList));

        System.out.println("Model length : " + solutions.length);
        System.out.println("Classlist length: " + classList.length);
        System.out.println("RBF length : " + dotProductTest.length);
    }

    public static double classify(double[][] solutions, double[] dotProductTesting, double[] classList) {
        //f(x) = sign(sum of alpha-i*Yi*K(X,Xi) + b)
        double value = 0;
        //Jumlah dari perkalian alpha-i dengan K(X,Xi) dengan Yi
        for (int i = 0; i < classList.length - 1; i++) {
            value += solutions[i][0] * dotProductTesting[i] * classList[i];
        }

        //jumlah perkalian diatas ditambah dengan bias
        value += solutions[classList.length - 1][0];

        System.out.println("Value = " + value);
        return Math.signum(value);
    }
}
