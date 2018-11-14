/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.MORPH_DILATE;
import static org.opencv.imgproc.Imgproc.THRESH_OTSU;
import static org.opencv.imgproc.Imgproc.getStructuringElement;

/**
 *
 * @author Filipus
 */
public class MorphFeatureExtractionTest {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        //File file = new File("F:\\KULIAH\\TA\\Flavia Training Dataset");
        File file = new File("F:\\KULIAH\\TA\\Leaves");
        File[] listOfFiles = file.listFiles();
        
        for (int i = 0; i < listOfFiles.length; i++) {
            process(listOfFiles[i].getAbsolutePath(), i);
        }

//        StringBuilder builder = new StringBuilder();
//        try {
//            for (int i = 0; i < listOfFiles.length; i++) {
//                builder.append(processData(listOfFiles[i].getAbsolutePath()));
//                builder.append(System.getProperty("line.separator"));
//            }
//            BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\Dataset Text\\morphfeatures-extracted-dataset-training.txt"));
//            writer.write(builder.toString());//save the string representation of the board
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        

    }
    
    public static void process(String path, int imageNumber){
        Mat color = Imgcodecs.imread(path);
        Mat gray = new Mat();
        Mat binary = new Mat();
        Mat edge = new Mat();
        Imgproc.cvtColor(color, gray, COLOR_BGR2GRAY);
        
       
        Mat kernel = getStructuringElement(MORPH_DILATE, new Size(5, 5));

        Imgproc.GaussianBlur(gray, gray, new Size(15, 15), 0);
        //Imgproc.dilate(gray, binary, kernel);
        
        //binarization
        Imgproc.threshold(gray, binary, 200, 250, THRESH_OTSU);
        //System.out.println("Threshold otsu " + THRESH_OTSU);

        //edge detection
        Imgproc.Canny(binary, edge, 200, 230);
        
        Imgcodecs.imwrite("F:\\Dataset Text\\processed_image_2\\"+imageNumber+"-edge.jpg", edge);
        Imgcodecs.imwrite("F:\\Dataset Text\\processed_image_2\\"+imageNumber+"-binary.jpg", binary);
        //Imgcodecs.imwrite("F:\\Dataset Text\\processed_image\\"+imageNumber+"-blackImg.jpg", imgConv);
    }
    
    public static String processData(String path) {
        Mat color = Imgcodecs.imread(path);
        Mat gray = new Mat();
        Mat binary = new Mat();
        Mat edge = new Mat();
        Imgproc.cvtColor(color, gray, COLOR_BGR2GRAY);
        //Imgproc.equalizeHist(gray, gray);
                
        Mat kernel = getStructuringElement(MORPH_DILATE, new Size(5, 5));

        Imgproc.GaussianBlur(gray, gray, new Size(3, 3), 0);

        //binarization
        Imgproc.threshold(gray, binary, 50, 250, THRESH_OTSU);
        //System.out.println("Threshold otsu " + THRESH_OTSU);

        //edge detection
        Imgproc.Canny(binary, edge, 200, 230);
        //Imgproc.dilate(binary, binary, kernel);

        BufferedImage edgeImage = convertMatToBufferedImage(edge);
        BufferedImage binaryImage = convertMatToBufferedImage(binary);
//        System.out.println("keliling: "+calculatePerimeter(edgeImage));
//        System.out.println("luas: "+calculateArea(binaryImage));
        int perimeter = calculatePerimeter(edgeImage);
        int area = calculateArea(binaryImage);
//        double diameter = findMaxDistance(init, coordinates);
        return perimeter + " " + area;
    }

    public static int calculateArea(BufferedImage image) {
        int totalPixel = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color c = new Color(image.getRGB(i, j));
                //253 warna putih di image binary, 0 hitam
                if (c.getRed() == 0) {
                    totalPixel++;
                }
            }
        }
        return totalPixel;
    }

    public static int calculatePerimeter(BufferedImage image) {
        int totalPixel = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color c = new Color(image.getRGB(i, j));
                //253 warna putih di image binary, 0 hitam
                if (c.getRed() == 255) {
                    totalPixel++;
                }
            }
        }

        return totalPixel;
    }

    public static double findMaxDistance(Point init, ArrayList<Point> coordinates) {
        double max = 0;
        double temp = 0;
        Point target = new Point();
        for (Point coordinate : coordinates) {
            temp = calcDistance(init, coordinate);
            if (temp > max) {
                max = temp;
                target = coordinate;
            }
        }

//        return "Jarak maksimal antara titik (" + init.getX() + "," + init.getY()
//                + ") dengan titik (" + target.getX() + "," + target.getY() + ") dengan jarak "
//                + max;
        return max;
    }

    public static double calcDistance(Point a, Point b) {
        return Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    static class Point {

        int x;
        int y;

        public Point() {

        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

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
    
    public static Mat bufferedImageToMat(BufferedImage bi) {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }

    
}
