/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import glcm.GLCM;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import practice.CobaOpenCV;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;

/**
 *
 * @author Filipus
 */
public class GLCMFeatureExtractionTest {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

//        File file = new File("C:\\Users\\user\\Pictures\\Leaf\\FlaviaSamples");
//        File file = new File("F:\\KULIAH\\TA\\Flavia Training Dataset");
        File file = new File("F:\\KULIAH\\TA\\Flavia Testing Dataset");
        File[] listOfFiles = file.listFiles();

        StringBuilder builder = new StringBuilder();
        try {
            for (int i = 0; i < listOfFiles.length; i++) {
                builder.append(processData(listOfFiles[i].getAbsolutePath()));
                builder.append(System.getProperty("line.separator"));
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\Dataset Text\\glcm-extracted-dataset-testing.txt"));
            writer.write(builder.toString());//save the string representation of the board
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String processData(String path) {
        Mat color = Imgcodecs.imread(path);
        Mat gray = new Mat();
        Imgproc.cvtColor(color, gray, COLOR_BGR2GRAY);

        BufferedImage grayImage = CobaOpenCV.convertMatToBufferedImage(gray);
        GLCM glcm = new GLCM();

        double[][] glcmMatrix = glcm.getNormalisedGLCMMatrix(glcm.getGLCMMatrix(grayImage));

//        System.out.println("\nFeature Calculation for Image "+path+"\n");
//        System.out.println("Angular second moment = " + glcm.angularSecondMoment(glcmMatrix));
//        System.out.println("Contrast = " + glcm.contrast(glcmMatrix));
//        System.out.println("Variance = " + glcm.variance(glcmMatrix));
//        System.out.println("Entropy = " + glcm.entropy(glcmMatrix));
//        System.out.println("Homogenity = " + glcm.homogenity(glcmMatrix));
//        System.out.println("Correlation = " + glcm.correlation(glcmMatrix));
        double asm = glcm.angularSecondMoment(glcmMatrix);
        double contrast = glcm.contrast(glcmMatrix);
        double variance = glcm.variance(glcmMatrix);
        double entropy = glcm.entropy(glcmMatrix);
        double homogenity = glcm.homogenity(glcmMatrix);
        double correlation = glcm.correlation(glcmMatrix);

        return asm + " " + contrast + " " + variance + " " + entropy + " " + homogenity + " " + correlation;
    }
}
