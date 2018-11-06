package practice;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import colormoments.ColorMoments;
import java.awt.Color;
import java.awt.image.BufferedImage;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.*;
import org.opencv.ml.SVM;

/**
 *
 * @author Filipus
 */
public class CobaOpenCV {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat color = Imgcodecs.imread("C:\\Users\\user\\Pictures\\Leaf\\FlaviaSamples\\1032.jpg");
        Mat gray = new Mat();
        Mat binary = new Mat();
        Mat edge = new Mat();
        Imgproc.cvtColor(color, gray, COLOR_BGR2GRAY);

        Mat kernel = getStructuringElement(MORPH_DILATE, new Size(5, 5));

        Imgproc.GaussianBlur(gray, gray, new Size(15, 15), 0);
        
        //binarization
        Imgproc.threshold(gray, binary, 50, 250, THRESH_OTSU);
        System.out.println("Threshold otsu " + THRESH_OTSU);

        //edge detection
        Imgproc.Canny(binary, edge, 200, 230);
        //Imgproc.dilate(binary, binary, kernel);

        //Imgcodecs.imwrite("C:\\Users\\user\\Pictures\\Leaf\\Processed\\1515-binary.jpg", binary);
        //Imgproc.Canny(binary, edge, 100, 150);
        //Imgcodecs.imwrite("C:\\Users\\user\\Pictures\\Leaf\\1515-grayscale.jpg", gray);
        //Imgcodecs.imwrite("C:\\Users\\user\\Pictures\\Leaf\\Processed\\1515-edge.jpg", edge);

//        BufferedImage out;
//        byte[] data = new byte[edge.height() * edge.width() * (int) edge.elemSize()];
//        int type;
//        edge.get(0, 0, data);
//
//        if (edge.channels() == 1) {
//            type = BufferedImage.TYPE_BYTE_GRAY;
//        } else{
//            if(edge.channels() == 3){
//                type = BufferedImage.TYPE_3BYTE_BGR;
//            }
//            else{
//                type = BufferedImage.TYPE_BYTE_BINARY;
//            }
//        }
//
//        out = new BufferedImage(edge.height(), edge.width(), type);
//
//        out.getRaster().setDataElements(0, 0, edge.height(), edge.width(), data);

        BufferedImage out = convertMatToBufferedImage(edge);
        BufferedImage rgbImage = convertMatToBufferedImage(color);
        
        //Keliling
        int totalPixel = 0;
        for (int i = 0; i < out.getWidth(); i++) {
            for (int j = 0; j < out.getHeight(); j++) {
                Color c = new Color(out.getRGB(i, j));
                //253 warna putih di image binary, 0 hitam
                if (c.getRed() == 253) {
                    totalPixel++;
                }
            }
        }
        
        int totalPixel2 = 0;
        for (int i = 0; i < out.getWidth(); i++) {
            for (int j = 0; j < out.getHeight(); j++) {
                Color c = new Color(out.getRGB(i, j));
                //253 warna putih di image binary, 0 hitam
                if (c.getRed() == 0) {
                    totalPixel2++;
                }
            }
        }
        
        System.out.println("Total piksel adalah " + totalPixel);
        System.out.println("Total piksel adalah " + totalPixel2);
        
        ColorMoments cm = new ColorMoments();
//        double meanRed = cm.calculateMean(rgbImage, 'R');
//        double meanGreen = cm.calculateMean(rgbImage, 'G');
//        double meanBlue = cm.calculateMean(rgbImage, 'B');
//        
//        System.out.println("\nMean");
//        System.out.println("Mean of Red : "+meanRed);
//        System.out.println("Mean of Green : "+meanGreen);
//        System.out.println("Mean of Blue : "+meanBlue+"\n");
//        
//        System.out.println("Std Dev");
//        System.out.println("StdDev of Red  : "+cm.calculateStdDev(rgbImage, 'R', meanRed));
//        System.out.println("StdDev of Green  : "+cm.calculateStdDev(rgbImage, 'G', meanGreen));
//        System.out.println("StdDev of Blue  : "+cm.calculateStdDev(rgbImage, 'B', meanBlue)+"\n");
//        
//        System.out.println("Skewness");
//        System.out.println("Skewness of Red  : "+cm.calculateSkewness(rgbImage, 'R', meanRed));
//        System.out.println("Skewness of Green  : "+cm.calculateSkewness(rgbImage, 'G', meanGreen));
//        System.out.println("Skewness of Blue  : "+cm.calculateSkewness(rgbImage, 'B', meanBlue)+"\n");
//        
//        System.out.println("Kurtosis");
//        System.out.println("Kurtosis of Red  : "+cm.calculateKurtosis(rgbImage, 'R', meanRed));
//        System.out.println("Kurtosis of Green  : "+cm.calculateKurtosis(rgbImage, 'G', meanGreen));
//        System.out.println("Kurtosis of Blue  : "+cm.calculateKurtosis(rgbImage, 'B', meanBlue)+"\n");
    }
    
    public static BufferedImage convertMatToBufferedImage(Mat input){
        BufferedImage out;
        byte[] data = new byte[input.width() * input.height() * (int)input.elemSize()];
        int type;
        input.get(0, 0, data);

        if(input.channels() == 1)
            type = BufferedImage.TYPE_BYTE_GRAY;
        else
            type = BufferedImage.TYPE_3BYTE_BGR;

        out = new BufferedImage(input.width(), input.height(), type);

        out.getRaster().setDataElements(0, 0, input.width(), input.height(), data);
        return out;
    }
}
