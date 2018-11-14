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
        double[][] data = getDataFromText("F:\\Dataset Text\\glcm-extracted-dataset-training.txt", 960, 6);
        double[][] model = getDataFromText("F:\\Dataset Text\\model\\glcm\\model1.txt", 961, 1);

        double[] classList = new double[961];
        for (int i = 0; i < 30; i++) {
            classList[i] = 1;
        }
        for (int i = 30; i < classList.length - 1; i++) {
            classList[i] = -1;
        }
        classList[960] = 0;

        double[] dataTest = {3.182686441574909, 10.14621142618838 ,121889.62351956153, 0.8010969392455602 ,1.835123630725844 ,8.203800333242088E-6};
        double[] dataTest2 = {3.6027151582610544 ,8.229279608006683, 121888.26125180592, -0.29403960160484677, 1.9232651469950564, 7.916878689394756E-6};
        double[] rbfTest = svm.createRBFTestMatrix(data, 5, dataTest2);
//        for (int i = 0; i < rbfTest.length; i++) {
//            System.out.println(rbfTest[i]);
//        }
        System.out.println("Model length : " + model[0].length);
        System.out.println("Classlist length: " + classList.length);
        System.out.println("RBF length : " + rbfTest.length);
        System.out.println("Result of classification: " + svm.classify(model, rbfTest, classList));
    }
}
