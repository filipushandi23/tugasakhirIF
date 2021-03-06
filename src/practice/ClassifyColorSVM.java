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
public class ClassifyColorSVM {

    public static void main(String[] args) throws FileNotFoundException {
        SVM svm = new SVM();
        double[][] data = getDataFromText("F:\\Dataset Text\\color-extracted-dataset-training.txt", 960, 9);
        double[][] model = getDataFromText("F:\\Dataset Text\\model\\color\\model1-sigma5.txt", 961, 1);

        double[] classList = new double[961];
        for (int i = 0; i < 30; i++) {
            classList[i] = 1;
        }
        for (int i = 30; i < classList.length - 1; i++) {
            classList[i] = -1;
        }
        classList[960] = 0;

        double[] dataTestTraining = {225.0, 236.0, 228.0, 75.33923280734945, 48.48711168960263, 68.66585760041157, -97.78228079391772, -63.40869760297444, -89.52531976118183};
        double[] dataTest1 = {242.0 ,247.0, 244.0, 49.84977432245807, 32.01562118716424, 45.40925015897091, -78.51516333853942, -50.82072128810986, -72.04222060747969};
        double[] dataTest2 = {244.0 ,248.0, 245.0, 48.23898838076935, 29.017236257093817, 43.42810150121693, -78.57481339215857, -47.37529616474905, -70.86637566437149};
        double[] dataTest3 = {222.0 ,234.0, 225.0, 78.20485918406861, 48.815980989835694, 71.61005515987263, -98.1960188806133, -61.40720135378159, -90.24988170220551};
        double[] dataTest4 = {226.0 ,236.0, 228.0, 75.49834435270749, 48.12483766206386, 68.84765791223403, -99.20160935431048, -63.27460545486312, -90.33549677178924};
        double[] dataTest5 = {229.0 ,240.0, 232.0, 71.30217387990355, 41.38840417314975, 64.1482657598785, -96.15603242618728, -56.30046828585356, -86.96801572340243};
        double[] dataTest6 = {227.0 ,238.0, 230.0, 73.63423117002037, 43.58898943540674, 66.23443213314356, -97.21703625247855, -57.55093155879494, -87.70532461787099};
        double[] dataTest7 = {227.0 ,238.0, 230.0, 72.05553413860729, 44.799553569204235, 65.86349520030045, -94.8855500773958, -59.48729809129579, -87.22266475915289};
        double[] dataTest8 = {230.0 ,240.0, 232.0, 70.178344238091, 41.36423575989287, 62.75348595894893, -95.3410823353278, -56.383423191949554, -85.12328131783562};
        double[] dataTest9 = {223.0 ,236.0, 227.0, 78.37091297158659, 46.32493928760188, 69.89992846920518, -100.7058395172292, -59.835661402489585, -90.38056954415066};
        double[] dataTest10 = {220.0 ,235.0, 224.0, 81.44323176298937, 46.151923036857305, 71.97221686178632, -101.43769785913423, -57.81999284427786, -89.84545493676988};

        
        
        
        double[] rbfTest = svm.createRBFTestMatrix(data, 5, dataTest5);
//        for (int i = 0; i < rbfTest.length; i++) {
//            System.out.println(rbfTest[i]);
//        }
        System.out.println("Model length : " + model[0].length);
        System.out.println("Classlist length: " + classList.length);
        System.out.println("RBF length : " + rbfTest.length);
        System.out.println("Result of classification: " + svm.classify(model, rbfTest, classList));
    }
}
