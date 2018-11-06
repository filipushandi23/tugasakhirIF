/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.io.FileNotFoundException;
import static practice.TestGLCM_SVM.getDataFromText;
import svm.SVM;

/**
 *
 * @author Filipus
 */
public class ClassifySVM {
    public static void main(String[] args) throws FileNotFoundException {
        SVM svm = new SVM();
        double[][] data = getDataFromText("F:\\Dataset Text\\glcm-extracted-dataset-training.txt",960,6);
        double[][] model = getDataFromText("F:\\Dataset Text\\model\\glcm\\model1.txt",961,1);
//        for (int i = 0; i < model.length; i++) {
//            System.out.println(model[i][0]);
//        }
        
        double[] classList = new double[961];
        for (int i = 0; i < 30; i++) {
            classList[i] = 1;
        }
        for (int i = 30; i < classList.length-1; i++) {
            classList[i] = -1;
        }
        classList[960] = 0;
        
        double[] dataTest = {1.9201417475042843,10.818820892410308,
            112613.65780521985,3.579943901656644,1.5780388449212308,
            8.879488769365322E-6};
        double[] rbfTest = svm.createRBFTestMatrix(data, 5, dataTest);
        for (int i = 0; i < rbfTest.length; i++) {
            System.out.println(rbfTest[i]);
        }
//        System.out.println("Result of classification: "+svm.classify(model, rbfTest, classList));
    }
}
