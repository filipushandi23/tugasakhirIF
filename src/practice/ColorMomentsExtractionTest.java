/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import colormoments.ColorMoments;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author Filipus
 */
public class ColorMomentsExtractionTest {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//
//        String path = "C:\\Users\\user\\Pictures\\Leaf\\FlaviaSamples\\1032.jpg";
//        System.out.println(processData(path));

//        File file = new File("C:\\Users\\user\\Pictures\\Leaf\\FlaviaSamples");
        File file = new File("F:\\KULIAH\\TA\\Flavia Testing Dataset");
        File[] listOfFiles = file.listFiles();

        StringBuilder builder = new StringBuilder();
        try {
            for (int i = 0; i < listOfFiles.length; i++) {
                builder.append(processData(listOfFiles[i].getAbsolutePath()));
                builder.append(System.getProperty("line.separator"));
            }
//            BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\Dataset Text\\color-extracted-dataset-training.txt"));
//            BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\Dataset Text\\color-extracted-dataset-training-2.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\Dataset Text\\color-extracted-dataset-testing.txt"));
            writer.write(builder.toString());//save the string representation of the board
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String processData(String path) throws IOException {
        Mat color = Imgcodecs.imread(path);
        BufferedImage rgbImage = convertMatToBufferedImage(color);
//        BufferedImage rgbImage = ImageIO.read(new File(path));
        ColorMoments cm = new ColorMoments();
        double meanRed = cm.calculateMeanRed(rgbImage);
        double meanGreen = cm.calculateMeanGreen(rgbImage);
        double meanBlue = cm.calculateMeanBlue(rgbImage);

        double stdDevRed = cm.calculateStdDevRed(rgbImage, meanRed);
        double stdDevGreen = cm.calculateStdDevGreen(rgbImage, meanGreen);
        double stdDevBlue = cm.calculateStdDevBlue(rgbImage, meanBlue);

        double skewRed = cm.calculateSkewnessRed(rgbImage, meanRed);
        double skewGreen = cm.calculateSkewnessGreen(rgbImage, meanGreen);
        double skewBlue = cm.calculateSkewnessBlue(rgbImage, meanBlue);

//        double kurtosisRed = cm.calculateKurtosisRed(rgbImage, meanRed);
//        double kurtosisGreen = cm.calculateKurtosisGreen(rgbImage, meanGreen);
//        double kurtosisBlue = cm.calculateKurtosisBlue(rgbImage, meanBlue);

        return meanRed + " " + meanGreen + " " + meanBlue
                + " " + stdDevRed + " " + stdDevGreen + " " + stdDevBlue + " "
                + skewRed + " " + skewGreen + " " + skewBlue;
//        return meanRed + " " + meanGreen + " " + meanBlue
//                + " " + stdDevRed + " " + stdDevGreen + " " + stdDevBlue + " "
//                + skewRed + " " + skewGreen + " " + skewBlue + " "
//                + kurtosisRed + " " + kurtosisGreen + " " + kurtosisBlue;
    }

    public static BufferedImage convertMatToBufferedImage(Mat input) {
        BufferedImage out;
        byte[] data = new byte[input.width() * input.height() * (int) input.elemSize()];
        int type;
        input.get(0, 0, data);

        if (input.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        out = new BufferedImage(input.width(), input.height(), type);

        out.getRaster().setDataElements(0, 0, input.width(), input.height(), data);
        return out;
    }
}
