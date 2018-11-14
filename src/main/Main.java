/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    private JLabel img;
    static String currentDirectory;
    static String savedDirectory;

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
        JButton confirmButton = new JButton("GO");
        JButton pseudoColorButton = new JButton("Train");

        String[] items = {"Pubescent Bamboo", "Chinese horse chestnut",
            "Anhui Barberry", "Chinese Redbud", "True Indigo",
            "Japanese Maple", "Nanmu", "Castor Aralia", "Chinese Cinnamon",
            "Goldenrain Tree", "Big-fruited Holly", "Japanese Cheesewood",
            "Wintersweet", "Camphortree", "Japan Arrowwood", "Sweet Osmanthus",
            "Deodar", "Gingko", "Crape Myrtle", "Oleander", "Yew Plum Nine",
            "Japanese Cherry", "Glossy Privet", "Chinese Toon", "Peach",
            "Ford Woodlotus", "Trident Maple", "Beale's Barberry",
            "Southern Magnolia", "Canadian Poplar", "Chinese Tulip Tree", "Tangerine"};

        JComboBox options = new JComboBox(items);
        options.setBounds(50, 150, 220, 30);

        JTextField inputField = new JTextField();
        inputField.setBounds(50, 200, 220, 30);

        confirmButton.setBounds(50, 250, 220, 30);
        confirmButton.setEnabled(false);
        openButton.setBounds(50, 50, 220, 30);
        saveButton.setBounds(50, 100, 220, 30);
        pseudoColorButton.setBounds(50, 350, 220, 30);
        pseudoColorButton.setEnabled(false);

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rVal = c.showOpenDialog(Main.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    currentDirectory = c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName();
                    System.out.println("Dir : " + currentDirectory);
                    confirmButton.setEnabled(true);
                    pseudoColorButton.setEnabled(true);
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

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Click go: "+calculateDistance(xFrom, yFrom, xTo, yTo));
                String filename = "F:\\Dataset Text\\geometric-features-extracted.txt";
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
                
                double physicalLength = calculateDistance(xFrom, yFrom, xTo, yTo);
                double diameter = morph.findMaxDistance(init, coordinates);
                int perimeter = morph.calculatePerimeter(edgeImage);
                int area = morph.calculateArea(binaryImage);

                try (FileWriter fw = new FileWriter(filename, true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw)) {
                    out.println( physicalLength+ " " + diameter + " "
                            + perimeter + " " + area);
                } catch (IOException ex) {
                    //exception handling left as an exercise for the reader
                }
            }
        });

        add(image);
        add(openButton);
        add(saveButton);
        add(options);
        add(inputField);
        add(confirmButton);
        add(pseudoColorButton);
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseMotionHandler);
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
