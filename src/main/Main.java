/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import colormoments.ColorMoments;
import glcm.GLCM;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import morph.MorphFeatures;
import morph.Point;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.THRESH_OTSU;
import practice.CobaOpenCV;
import static practice.ColorMomentsExtractionTest.convertMatToBufferedImage;
import static practice.TestGLCM_SVM.getDataFromText;
import svm.SVM;

/**
 *
 * @author Filipus
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form ImageProcessing
     */
    JLabel image;
    public static BufferedImage imageProcessed = null;
    public MorphFeatures morph = new MorphFeatures();
    private int xFrom = 0;
    private int yFrom = 0;
    private int xTo = 0;
    private int yTo = 0;
    private double physicalLength = 0;
    private double physicalWidth = 0;

    private JLabel img;
    static String currentDirectory;
    static String savedDirectory;

    private int counter = 0;

    public Main() {
        initComponents();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        setResizable(false);
        setTitle("Image Processing");
        setSize(1300, 750);
        setLocationRelativeTo(null);

        JFileChooser c = new JFileChooser();

        image = new JLabel();

        JButton openButton = new JButton("Open Image");
        JButton saveButton = new JButton("Save Image");

        JButton btnGetLength = new JButton("Get Length Value");
        JButton btnGetWidth = new JButton("Get Width Value");
        JButton extractMorphButton = new JButton("Extract Morph Features");
        JButton extractGLCMButton = new JButton("Extract GLCM Features");
        JButton classifyGLCM = new JButton("Classify GLCM");
        JButton classifyColor = new JButton("Classify Color");

        String[] items = {"pubescent bamboo", "chinese horse chestnut",
            "chinese redbud", "true indigo",
            "japanese maple", "nanmu", "castor aralia", "goldenrain tree", "chinese cinnamon",
            "anhui barberry", "big fruited holly", "japanese cheesewood",
            "wintersweet", "camphortree", "japan arrowwood", "sweet osmanthus",
            "deodar", "gingko", "crepe myrtle", "oleander", "yew plum nine",
            "japanese flowering cherry", "glossy privet", "chinese toon", "peach",
            "ford woodlotus", "trident maple", "beals barberry",
            "southern magnolia", "canadian poplar", "chinese tulip tree", "tangerine"};

        JComboBox options = new JComboBox(items);
        options.setBounds(50, 150, 220, 30);

        JTextField inputField = new JTextField();
        inputField.setBounds(50, 200, 220, 30);

        openButton.setBounds(50, 50, 220, 30);
        saveButton.setBounds(50, 100, 220, 30);

        btnGetLength.setBounds(50, 260, 220, 30);
        btnGetWidth.setBounds(50, 300, 220, 30);

        extractMorphButton.setBounds(50, 400, 220, 30);
        extractMorphButton.setEnabled(false);

        extractGLCMButton.setBounds(50, 440, 220, 30);
        extractGLCMButton.setEnabled(false);

        classifyColor.setBounds(50, 500, 220, 30);
        classifyColor.setEnabled(false);
        classifyGLCM.setBounds(50, 540, 220, 30);
        classifyGLCM.setEnabled(false);

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rVal = c.showOpenDialog(Main.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    currentDirectory = c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName();
                    System.out.println("Dir : " + currentDirectory);
                    extractMorphButton.setEnabled(true);
                    extractGLCMButton.setEnabled(true);
                    classifyGLCM.setEnabled(true);
                    classifyColor.setEnabled(true);
                    imageProcessed = normal();
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rVal = c.showSaveDialog(Main.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    savedDirectory = c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName();
                    try {
                        ImageIO.write(imageProcessed, "png", new File(savedDirectory));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });

        btnGetLength.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                physicalLength = calculateDistance(xFrom, yFrom, xTo, yTo);
                System.out.println("Physical length : " + physicalLength);
                JOptionPane.showMessageDialog(null, "Panjang fisik didapatkan!");
            }
        });

        btnGetWidth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                physicalWidth = calculateDistance(xFrom, yFrom, xTo, yTo);
                System.out.println("Physical Width: " + physicalWidth);
                JOptionPane.showMessageDialog(null, "Lebar fisik didapatkan!");
            }
        });

        extractMorphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Click go: "+calculateDistance(xFrom, yFrom, xTo, yTo));
                String filename = "F:\\Dataset Text\\morphological-features-extracted-training.csv";
                //System.out.println("CUrr: "+currentDirectory);
//                Mat color = Imgcodecs.imread(currentDirectory);
                Mat color = morph.bufferedImageToMat(imageProcessed);
                Mat gray = new Mat();
                Mat binary = new Mat();
                Mat edge = new Mat();
                Imgproc.cvtColor(color, gray, COLOR_BGR2GRAY);

                Imgproc.GaussianBlur(gray, gray, new Size(15, 15), 0);

                //binarization
                Imgproc.threshold(gray, binary, 50, 250, THRESH_OTSU);
                //System.out.println("Threshold otsu " + THRESH_OTSU);

                //edge detection
                Imgproc.Canny(binary, edge, 200, 230);
                //Imgproc.dilate(binary, binary, kernel);

                BufferedImage edgeImage = morph.convertMatToBufferedImage(edge);
                BufferedImage binaryImage = morph.convertMatToBufferedImage(binary);
                ArrayList<Point> coordinates = new ArrayList();
                for (int i = 0; i < edgeImage.getWidth(); i++) {
                    for (int j = 0; j < edgeImage.getHeight(); j++) {
                        Color c = new Color(edgeImage.getRGB(i, j));
                        //253 warna putih di image binary, 0 hitam
                        if (c.getRed() == 255) {
                            coordinates.add(new Point(i, j));
                        }
                    }
                }

                Point init = new Point(xFrom, yFrom);
//                System.out.println(morph.findMaxDistance(init, coordinates));

                //fitur geometri
                double diameter = morph.findMaxDistance(init, coordinates);
                int perimeter = morph.calculatePerimeter(edgeImage);
                int area = morph.calculateArea(binaryImage);

                //fitur morfologi
                double aspectRatio = physicalLength / physicalWidth;
                double formFactor = area / Math.pow(perimeter, 2);
                double narrowFactor = diameter / physicalLength;
                double rectangularity = (physicalLength * physicalWidth) / area;
                double perimeterRatioOfDiameter = perimeter / diameter;
                double perimeterRatioOfLengthAndWidth = perimeter / (physicalLength + physicalWidth);

                try (FileWriter fw = new FileWriter(filename, true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw)) {
                    out.println(aspectRatio + "," + formFactor + "," + narrowFactor + ","
                            + rectangularity + "," + perimeterRatioOfDiameter + ","
                            + perimeterRatioOfLengthAndWidth
                            + "," + options.getSelectedItem().toString());
                } catch (IOException ex) {
                    //exception handling left as an exercise for the reader
                }
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
                counter++;
                System.out.println(counter + " Data berhasil disimpan!");
            }
        });

        extractGLCMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = "F:\\Dataset Text\\glcm-features-test.csv";
                Mat color = morph.bufferedImageToMat(imageProcessed);
                Mat gray = new Mat();
                Imgproc.cvtColor(color, gray, COLOR_BGR2GRAY);

                BufferedImage grayImage = CobaOpenCV.convertMatToBufferedImage(gray);
                GLCM glcm = new GLCM();

                double[][] glcmMatrix = glcm.getNormalisedGLCMMatrix(glcm.getGLCMMatrix(grayImage));

                double asm = glcm.angularSecondMoment(glcmMatrix);
                double contrast = glcm.contrast(glcmMatrix);
//        double variance = glcm.variance(glcmMatrix);
                double entropy = glcm.entropy(glcmMatrix);
                double homogenity = glcm.homogenity(glcmMatrix);
                double correlation = glcm.correlation(glcmMatrix);

                try (FileWriter fw = new FileWriter(filename, true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw)) {
                    out.println(asm + "," + contrast + ","
                            + entropy + "," + homogenity + "," + correlation + "," + options.getSelectedItem().toString());
                } catch (IOException ex) {
                    //exception handling left as an exercise for the reader
                }
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
            }
        });

        classifyGLCM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SVM svm = new SVM();
                try {

                    int kelas = Integer.parseInt(inputField.getText());

                    double[][] data = getDataFromText("F:\\Dataset Text\\glcm-extracted-dataset-training-5features.txt", 960, 5);
                    double[][] model = getDataFromText("F:\\Dataset Text\\model\\glcm\\model" + kelas + "-5features.txt", 961, 1);

                    //masih nguji kelas 1
                    double[] classList = new double[961];
                    for (int i = 0; i < (kelas - 1) * 30; i++) {
                        classList[i] = -1;
                    }
                    for (int i = (kelas - 1) * 30; i < (kelas * 30); i++) {
                        classList[i] = 1;
                    }
                    for (int i = (kelas * 30); i < classList.length - 1; i++) {
                        classList[i] = -1;
                    }
                    classList[960] = 0;

                    double[] rbfTest = svm.createRBFTestMatrix(data, 10, extractGLCMFeatures(imageProcessed));
                    double[] features = extractGLCMFeatures(imageProcessed);
                    System.out.println("ASM: " + features[0]);
                    System.out.println("Contast: " + features[1]);
                    System.out.println("Entropy: " + features[2]);
                    System.out.println("Homogenity: " + features[3]);
                    System.out.println("Correlation: " + features[4]);
//        for (int i = 0; i < rbfTest.length; i++) {
//            System.out.println(rbfTest[i]);
//        }
                    System.out.println("Model length : " + model[0].length);
                    System.out.println("Classlist length: " + classList.length);
                    System.out.println("RBF length : " + rbfTest.length);

                    double result = svm.classify(model, rbfTest, classList);
                    System.out.println("Result of classification: " + result);
                    if (result == 1) {
                        JOptionPane.showMessageDialog(null, "Objek dikenali! Hasil klasifikasi = 1!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Objek tak dikenali, gagal klasifikasi!");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        classifyColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SVM svm = new SVM();
                try {
                    double[][] data = getDataFromText("F:\\Dataset Text\\color-extracted-dataset-training.txt", 960, 9);
                    double[][] model = getDataFromText("F:\\Dataset Text\\model\\color\\model1-sigma5.txt", 961, 1);

                    //masih nguji kelas 1
                    double[] classList = new double[961];
                    for (int i = 0; i < 30; i++) {
                        classList[i] = 1;
                    }
                    for (int i = 30; i < classList.length - 1; i++) {
                        classList[i] = -1;
                    }
                    classList[960] = 0;

                    double[] rbfTest = svm.createRBFTestMatrix(data, 5, extractColorFeatures(imageProcessed));
//        for (int i = 0; i < rbfTest.length; i++) {
//            System.out.println(rbfTest[i]);
//        }          
                    double[] features = extractColorFeatures(imageProcessed);
                    System.out.println("Mean Red: " + features[0]);
                    System.out.println("Mean Green: " + features[1]);
                    System.out.println("Mean Blue: " + features[2]);
                    System.out.println("Std Dev Red: " + features[3]);
                    System.out.println("Std Dev Green: " + features[4]);
                    System.out.println("Std Dev Blue: " + features[5]);
                    System.out.println("Skewness Red: " + features[6]);
                    System.out.println("Skewness Green: " + features[7]);
                    System.out.println("Skewness Blue: " + features[8]);

                    System.out.println("Model length : " + model[0].length);
                    System.out.println("Classlist length: " + classList.length);
                    System.out.println("RBF length : " + rbfTest.length);

                    double result = svm.classify(model, rbfTest, classList);
                    System.out.println("Result of classification: " + result);
                    if (result == 1) {
                        JOptionPane.showMessageDialog(null, "Objek dikenali! Hasil klasifikasi = 1!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Objek tak dikenali, gagal klasifikasi!");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        add(image);
        add(openButton);
        add(saveButton);
        add(options);
        add(inputField);
        add(extractMorphButton);
        add(extractGLCMButton);
        add(classifyColor);
        add(classifyGLCM);
        add(btnGetLength);
        add(btnGetWidth);
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseMotionHandler);
    }

    public double[] extractGLCMFeatures(BufferedImage input) {
        Mat color = morph.bufferedImageToMat(input);
        Mat gray = new Mat();
        Imgproc.cvtColor(color, gray, COLOR_BGR2GRAY);

        BufferedImage grayImage = CobaOpenCV.convertMatToBufferedImage(gray);
        GLCM glcm = new GLCM();

        double[][] glcmMatrix = glcm.getNormalisedGLCMMatrix(glcm.getGLCMMatrix(grayImage));

        double asm = glcm.angularSecondMoment(glcmMatrix);
        double contrast = glcm.contrast(glcmMatrix);
//        double variance = glcm.variance(glcmMatrix);
        double entropy = glcm.entropy(glcmMatrix);
        double homogenity = glcm.homogenity(glcmMatrix);
        double correlation = glcm.correlation(glcmMatrix);

        double[] features = {asm, contrast, entropy, homogenity, correlation};

        return features;
    }

    public double[] extractColorFeatures(BufferedImage input) {
        Mat color = Imgcodecs.imread(currentDirectory);
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

        double[] features = {meanRed, meanGreen, meanBlue, stdDevRed, stdDevGreen, stdDevBlue, skewRed, skewGreen, skewBlue};
        return features;
    }

    public MouseListener mouseHandler = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            xFrom = xTo = e.getX();
            yFrom = yTo = e.getY();
            repaint();
            System.out.println("Titik 1: " + e.getPoint());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            xTo = e.getX();
            yTo = e.getY();
            repaint();
            System.out.println("Titik 2: " + e.getPoint());
            //System.out.println("Jarak Garis: "+calculateDistance(xFrom, yFrom, xTo, yTo));
        }
    };

    public MouseMotionAdapter mouseMotionHandler = new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            xTo = e.getX();
            yTo = e.getY();
            repaint();
            //System.out.println("Titik 2: "+e.getPoint());
        }
    };

    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(xFrom, yFrom, xTo, yTo);
    }

    public double calculateDistance(double xFrom, double yFrom, double xTo, double yTo) {
        return Math.sqrt(Math.pow(xFrom - xTo, 2) + Math.pow(yFrom - yTo, 2));
    }

    public BufferedImage normal() {
        BufferedImage input = null;
        try {
            input = ImageIO.read(new File(currentDirectory));
            image.setIcon(new ImageIcon(resizeImage(input)));

            for (int i = 0; i < input.getHeight(); i++) {
                for (int j = 0; j < input.getWidth(); j++) {
                    Color tmp = new Color(input.getRGB(j, i));
                    //System.out.print("("+tmp.getRed()+","+tmp.getGreen()+","+tmp.getBlue()+")\t");
                    //System.out.print(i+","+j);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    private Image resizeImage(BufferedImage img) {
        if (img.getWidth() > 970 && img.getHeight() > 600) {
            Image dimg = img.getScaledInstance(900, 675, Image.SCALE_SMOOTH);
            image.setBounds(300, 20, 900, 675);
            return dimg;
        } else {
            image.setBounds(300, 20, img.getWidth(), img.getHeight());
            return img;
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
