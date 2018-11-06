/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class Main {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        Mat color = Imgcodecs.imread("C:\\Users\\user\\Pictures\\Leaf\\FlaviaSamples\\2023.jpg");
        Mat gray = new Mat();
        Mat binary = new Mat();
        Mat edge = new Mat();
        Imgproc.cvtColor(color, gray, COLOR_BGR2GRAY );
        
        Mat kernel = getStructuringElement(MORPH_DILATE, new Size(5,5));
        
        Imgproc.GaussianBlur(gray, gray, new Size(15,15), 0);
        System.out.println("Threshold otsu "+THRESH_OTSU );
        
        //binarization
        Imgproc.threshold(gray, binary, 50, 250,THRESH_OTSU);
        
        //edge detection
        Imgproc.Canny(binary,edge,200,230);
        //Imgproc.dilate(binary, binary, kernel);
        
        Imgcodecs.imwrite("C:\\Users\\user\\Pictures\\Leaf\\Processed\\2023-binary.jpg", binary);
        //Imgproc.Canny(binary, edge, 100, 150);
        //Imgcodecs.imwrite("C:\\Users\\user\\Pictures\\Leaf\\1515-grayscale.jpg", gray);
        Imgcodecs.imwrite("C:\\Users\\user\\Pictures\\Leaf\\Processed\\2023-edge.jpg", edge);
    }
}
