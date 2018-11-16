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
public class ClassifyGLCMSVM {

    public static void main(String[] args) throws FileNotFoundException {
        SVM svm = new SVM();
        double[][] data = getDataFromText("F:\\Dataset Text\\glcm-extracted-dataset-training-5features.txt", 960, 5);
        double[][] model = getDataFromText("F:\\Dataset Text\\model\\glcm\\model2-5features.txt", 961, 1);

//        double[] classList = new double[961];
//        for (int i = 0; i < 30; i++) {
//            classList[i] = 1;
//        }
//        for (int i = 30; i < classList.length - 1; i++) {
//            classList[i] = -1;
//        }
//        classList[960] = 0;
        double[] classList = new double[961];
        for (int i = 0; i < 30; i++) {
            classList[i] = -1;
        }
        for (int i = 30; i < 60; i++) {
            classList[i] = 1;
        }
        for (int i = 60; i < classList.length - 1; i++) {
            classList[i] = -1;
        }
        classList[960] = 0;

        double[] dataTestTraining = {1.5233464029239292, 12.90644495412839, 4.965428805032036, 1.4621013881532312, 9.697798214532768E-6};

        double[] dataTest1 = {1.3952308598665415, 15.319860300250092, 5.178701487720449, 1.429334231909059, 9.678879225380807E-6};
        double[] dataTest2 = {1.3530228624317095, 16.688611342785588, 5.28447450075799, 1.4112731531930869, 9.84185545252318E-6};
        double[] dataTest3 = {1.5820843117903107, 17.885410758965993, 4.767781555482961, 1.4484564064383232, 9.42280436992099E-6};
        double[] dataTest4 = {1.5321814680520707, 12.54507089241023, 4.889011150340284, 1.4604166829814988, 9.172991311084648E-6};
        double[] dataTest5 = {1.1865535841742598, 14.252293577981595, 5.669042278555996, 1.3652969728531967, 9.786730820518266E-6};
        double[] dataTest6 = {1.4093497109594388, 15.547823185988271, 5.255730289656106, 1.4164309149652998, 9.528159006317672E-6};
        double[] dataTest7 = {1.4310784025190884, 20.104894703920166, 5.333554232013448, 1.4133148452192748, 9.59149948468459E-6};
        double[] dataTest8 = {1.574060972573079, 8.327170558798974, 4.642827865013348, 1.5153439577526249, 9.09474947090646E-6};
        double[] dataTest9 = {1.659826475809895, 12.102735613010779, 4.436251083633913, 1.4942392239424989, 9.265154672781307E-6};
        double[] dataTest10 = {1.8955812099180336, 15.3774082568806, 3.9899973237531765, 1.5421505098734067, 9.188693567363678E-6};
        double[] dataTest11 = {1.4320063257946747, 17.98820788156817, 5.145285959186274, 1.4225794256723654, 9.62929783795993E-6};
        
        double[] rbfTest = svm.createRBFTestMatrix(data, 10, dataTestTraining);
//        for (int i = 0; i < rbfTest.length; i++) {
//            System.out.println(rbfTest[i]);
//        }
        System.out.println("Model length : " + model[0].length);
        System.out.println("Classlist length: " + classList.length);
        System.out.println("RBF length : " + rbfTest.length);
        System.out.println("Result of classification: " + svm.classify(model, rbfTest, classList));
    }
}
