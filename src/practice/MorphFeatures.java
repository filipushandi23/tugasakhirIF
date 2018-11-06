/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.MORPH_DILATE;
import static org.opencv.imgproc.Imgproc.THRESH_OTSU;
import static org.opencv.imgproc.Imgproc.getStructuringElement;
import static practice.CobaOpenCV.convertMatToBufferedImage;

/**
 *
 * @author Filipus
 */
public class MorphFeatures {

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

        //Imgcodecs.imwrite("C:\\Users\\user\\Pictures\\Leaf\\Processed\\1032-edge.jpg", edge);
        BufferedImage edgeImg = convertMatToBufferedImage(edge);
        ArrayList<Point> coordinates = new ArrayList();
        for (int i = 0; i < edgeImg.getWidth(); i++) {
            for (int j = 0; j < edgeImg.getHeight(); j++) {
                Color c = new Color(edgeImg.getRGB(i, j));
                //253 warna putih di image binary, 0 hitam
                if (c.getRed() == 255) {
                    coordinates.add(new Point(i, j));
                }
            }
        }
//        
        for (Point coordinate : coordinates) {
            System.out.println("X: " + coordinate.getX() + ", Y: " + coordinate.getY());
        }

        Point init = new Point(171, 96);
        System.out.println(findMaxDistance(init, coordinates));
    }

    public static String findMaxDistance(Point init, ArrayList<Point> coordinates) {
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

        return "Jarak maksimal antara titik (" + init.getX() + "," + init.getY()
                + ") dengan titik (" + target.getX() + "," + target.getY() + ") dengan jarak "
                + max;
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
}
//        BufferedImage output = new BufferedImage(edgeImg.getWidth(), edgeImg.getHeight(), BufferedImage.TYPE_INT_RGB);
//        for (int i = 0; i < edgeImg.getWidth(); i++) {
//            for (int j = 0; j < edgeImg.getHeight(); j++) {
//                Color t1 = new Color(255, 0, 0);
//                Color t2 = new Color(255, 0, 0);
//                output.setRGB(171, 96, t1.getRGB());
//                output.setRGB(1501, 984, t2.getRGB());
//            }
//        }
//        try {
//            ImageIO.write(output, "png", new File("C:\\Users\\user\\Pictures\\Leaf\\Processed\\1032-points.jpg"));
//        } catch (IOException ex) {
//            Logger.getLogger(MorphFeatures.class.getName()).log(Level.SEVERE, null, ex);
//        }
