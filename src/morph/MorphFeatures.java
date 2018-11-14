/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morph;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 *
 * @author Filipus
 */
public class MorphFeatures {
    public int calculateArea(BufferedImage image){
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
    
    public int calculatePerimeter(BufferedImage image){
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
    
    public double findMaxDistance(Point init, ArrayList<Point> coordinates) {
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

    public double calcDistance(Point a, Point b) {
        return Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow(a.getY() - b.getY(), 2));
    }
    
    public BufferedImage convertMatToBufferedImage(Mat input){
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
    
    public Mat bufferedImageToMat(BufferedImage bi) {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }
}
