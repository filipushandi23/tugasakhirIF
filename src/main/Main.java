/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

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
    static double rotateFactorRight;
    static double rotateFactorLeft;
    static String currentDirectory;
    static String savedDirectory;

    public Main() {
        initComponents();

        setResizable(false);
        setTitle("Image Processing");
        setSize(1300, 750);
        setLocationRelativeTo(null);

        JFileChooser c = new JFileChooser();

        JLabel text1 = new JLabel("Image Processed");
        text1.setBounds(500, 40, 250, 35);
        text1.setFont(new Font("Arial", Font.PLAIN, 30));

        image = new JLabel();

        JButton openButton = new JButton("Open Image");
        JButton saveButton = new JButton("Save Image");
        JButton confirmButton = new JButton("GO");
        JButton pseudoColorButton = new JButton("Train");

        String[] items = {"Pubescent Bamboo", "Chinese horse chestnut",
            "Anhui Barberry", "Chinese Redbud", "True Indigo",
            "Japanese Maple", "Nanmu", "Castor Aralia", "Chinese Cinnamon",
            "Goldenrain Tree", "Big-fruited Holly", "Japanese Cheesewood", 
            "Wintersweet","Camphortree", "Japan Arrowwood", "Sweet Osmanthus", 
            "Deodar","Gingko", "Crape Myrtle", "Oleander", "Yew Plum Nine", 
            "Japanese Cherry","Glossy Privet", "Chinese Toon", "Peach", 
            "Ford Woodlotus", "Trident Maple","Beale's Barberry", 
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
                int choice = options.getSelectedIndex();
                switch (choice) {
                    case 0:
                        imageProcessed = normal();
                        rotateFactorRight = 0;
                        rotateFactorLeft = 0;
                        System.out.println("Normal");
                        break;
                    case 1:
                        imageProcessed = negative();
                        System.out.println("Negatif");
                        break;
                    case 2: {
                        imageProcessed = greyscale();
                        System.out.println("Greyscale");
                        break;
                    }
                    case 3: {
                        imageProcessed = sobel();
                        System.out.println("Greyscale");
                        break;
                    }
                    case 4: {
                        imageProcessed = prewitt();
                        //System.out.println("Greyscale");
                        break;
                    }
                    case 5: {
                        //imageProcessed = robert();
                        //System.out.println("Greyscale");
                        break;
                    }
                    case 6: {
                        imageProcessed = imageEnhancementUsingKernel("Blur");
                        System.out.println("Blur");
                    }
                }
            }
        });

        add(text1);
        add(image);
        add(openButton);
        add(saveButton);
        add(options);
        add(inputField);
        add(confirmButton);
        add(pseudoColorButton);

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
                System.out.println("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    public BufferedImage sobel() {
        BufferedImage output = null;
        try {
            BufferedImage input = imageProcessed;
            output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());

            int[][] pixelMatrix = new int[3][3];
            for (int i = 1; i < input.getWidth() - 1; i++) {
                for (int j = 1; j < input.getHeight() - 1; j++) {
                    pixelMatrix[0][0] = new Color(input.getRGB(i - 1, j - 1)).getRed();
                    pixelMatrix[0][1] = new Color(input.getRGB(i - 1, j)).getRed();
                    pixelMatrix[0][2] = new Color(input.getRGB(i - 1, j + 1)).getRed();
                    pixelMatrix[1][0] = new Color(input.getRGB(i, j - 1)).getRed();
                    pixelMatrix[1][2] = new Color(input.getRGB(i, j + 1)).getRed();
                    pixelMatrix[2][0] = new Color(input.getRGB(i + 1, j - 1)).getRed();
                    pixelMatrix[2][1] = new Color(input.getRGB(i + 1, j)).getRed();
                    pixelMatrix[2][2] = new Color(input.getRGB(i + 1, j + 1)).getRed();

                    int edge = (int) convolutionSobel(pixelMatrix);
                    output.setRGB(i, j, (edge << 16 | edge << 8 | edge));
                }
            }

            image.setIcon(new ImageIcon(resizeImage(output)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public BufferedImage prewitt() {
        BufferedImage output = null;
        try {
            BufferedImage input = imageProcessed;
            output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());

            int[][] pixelMatrix = new int[3][3];
            for (int i = 1; i < input.getWidth() - 1; i++) {
                for (int j = 1; j < input.getHeight() - 1; j++) {
                    pixelMatrix[0][0] = new Color(input.getRGB(i - 1, j - 1)).getRed();
                    pixelMatrix[0][1] = new Color(input.getRGB(i - 1, j)).getRed();
                    pixelMatrix[0][2] = new Color(input.getRGB(i - 1, j + 1)).getRed();
                    pixelMatrix[1][0] = new Color(input.getRGB(i, j - 1)).getRed();
                    pixelMatrix[1][2] = new Color(input.getRGB(i, j + 1)).getRed();
                    pixelMatrix[2][0] = new Color(input.getRGB(i + 1, j - 1)).getRed();
                    pixelMatrix[2][1] = new Color(input.getRGB(i + 1, j)).getRed();
                    pixelMatrix[2][2] = new Color(input.getRGB(i + 1, j + 1)).getRed();

                    int edge = (int) convolutionPrewitt(pixelMatrix);
                    output.setRGB(i, j, (edge << 16 | edge << 8 | edge));
                }
            }

            image.setIcon(new ImageIcon(resizeImage(output)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public static double convolutionSobel(int[][] pixelMatrix) {
        int gx = (pixelMatrix[0][0] * -1) + (pixelMatrix[0][1] * -2) + (pixelMatrix[0][2] * -1)
                + (pixelMatrix[2][0]) + (pixelMatrix[2][1] * 2) + (pixelMatrix[2][2]);

//        int gy = (pixelMatrix[0][0]) + (pixelMatrix[0][2] * -1) + (pixelMatrix[1][0] * 2) +
//                (pixelMatrix[1][2] * -2) + (pixelMatrix[2][0]) + (pixelMatrix[2][2] * -1);
        int gy = (pixelMatrix[0][0] * -1) + (pixelMatrix[1][0] * -2) + (pixelMatrix[2][0] * -1)
                + (pixelMatrix[0][2]) + (pixelMatrix[1][2] * 2) + (pixelMatrix[2][2]);

        return Math.sqrt(Math.pow(gx, 2) + Math.pow(gy, 2));

    }

    public static double convolutionPrewitt(int[][] pixelMatrix) {
        int gy = (pixelMatrix[0][0] * -1) + (pixelMatrix[0][1] * -1) + (pixelMatrix[0][2] * -1)
                + (pixelMatrix[2][0]) + (pixelMatrix[2][1]) + (pixelMatrix[2][2]);

        int gx = (pixelMatrix[0][0] * -1) + (pixelMatrix[1][0] * -1) + (pixelMatrix[2][0] * -1)
                + (pixelMatrix[0][2]) + (pixelMatrix[1][2]) + (pixelMatrix[2][2]);

        return Math.sqrt(Math.pow(gx, 2) + Math.pow(gy, 2));

    }

    public BufferedImage negative() {
        BufferedImage output = null;
        try {
            BufferedImage input = imageProcessed;
            output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());

            for (int i = 0; i < output.getWidth(); i++) {
                for (int j = 0; j < output.getHeight(); j++) {
                    Color tmp = new Color(input.getRGB(i, j));

                    int negRed = 255 - tmp.getRed();
                    int negGreen = 255 - tmp.getGreen();
                    int negBlue = 255 - tmp.getBlue();

                    Color after = new Color(negRed, negGreen, negBlue);
                    output.setRGB(i, j, after.getRGB());
                }
            }

            image.setIcon(new ImageIcon(resizeImage(output)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public BufferedImage greyscale() {
        BufferedImage output = null;
        try {
            BufferedImage input = imageProcessed;
            output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());

            for (int i = 0; i < output.getWidth(); i++) {
                for (int j = 0; j < output.getHeight(); j++) {
                    Color tmp = new Color(input.getRGB(i, j));

                    int grey = (tmp.getRed() + tmp.getGreen() + tmp.getBlue()) / 3;

//                    int grey = (int)(tmp.getRed()*0.1 + tmp.getGreen()*0.8 + tmp.getBlue()*0.1);
                    Color after = new Color(grey, grey, grey);
                    output.setRGB(i, j, after.getRGB());
                }
            }
            image.setIcon(new ImageIcon(resizeImage(output)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public BufferedImage imageEnhancementUsingKernel(String type) {
        BufferedImage output = null;
        try {
            BufferedImage input = ImageIO.read(new File(currentDirectory));
            output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());

            Kernel kernel = null;

            switch (type) {
                case "Blur": {
                    input = imageProcessed;
                    kernel = new Kernel(3, 3, new float[]{
                        1f / 9f, 1f / 9f, 1f / 9f,
                        1f / 9f, 1f / 9f, 1f / 9f,
                        1f / 9f, 1f / 9f, 1f / 9f});

//                    kernel = new Kernel(3, 3, new float[]{
//                        1f / 16f, 2f / 16f, 1f / 16f,
//                        2f / 16f, 4f / 16f, 2f / 16f,
//                        1f / 16f, 2f / 16f, 1f / 16f});
                    break;
                }
                case "Sharpened": {
                    input = imageProcessed;
                    kernel = new Kernel(3, 3, new float[]{
                        -1, -1, -1,
                        -1, 9, -1,
                        -1, -1, -1});
                    break;
                }
                case "Emboss": {
                    input = imageProcessed;
                    kernel = new Kernel(3, 3, new float[]{-2, 0, 0, 0, 1, 0, 0, 0, 2});
                    break;
                }
                case "Edge": {
                    input = imageProcessed;
//                    kernel = new Kernel(3, 3, new float[]{0, -1, 0, -1, 4, -1, 0, -1, 0});

                    //kernel untuk sharpen  
//                    kernel = new Kernel(3, 3, new float[]{0, -1, 0, -1, 5, -1, 0, -1, 0});
                    //kernel untuk deteksi garis
//                    kernel = new Kernel(3, 3, new float[]{-1, -1, -1, -1, 8, -1, -1, -1, -1});
                    //Laplacian Gaussian Filter
                    kernel = new Kernel(5, 5, new float[]{
                        2f / 159f, 4f / 159f, 5f / 159f, 4f / 159f, 2f / 159f,
                        4f / 159f, 9f / 159f, 12f / 159f, 9f / 159f, 4f / 159f,
                        5f / 159f, 12f / 159f, 15f / 159f, 12f / 159f, 5f / 159f,
                        4f / 159f, 9f / 159f, 12f / 159f, 9f / 159f, 4f / 159f,
                        2f / 159f, 4f / 159f, 5f / 159f, 4f / 159f, 2f / 159f,});
                    break;
                }
            }

            BufferedImageOp op = new ConvolveOp(kernel);
            output = op.filter(input, output);
            image.setIcon(new ImageIcon(resizeImage(output)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    private Image resizeImage(BufferedImage img) {
        if (img.getWidth() > 970 && img.getHeight() > 600) {
            Image dimg = img.getScaledInstance(970, 600, Image.SCALE_SMOOTH);
            image.setBounds(300, 90, 970, 600);
            return dimg;
        } else {
            image.setBounds(300, 90, img.getWidth(), img.getHeight());
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
