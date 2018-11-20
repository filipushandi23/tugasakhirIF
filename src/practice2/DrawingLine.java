
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Filipus
 */
public class DrawingLine extends JFrame {

    private int xFrom = 0;
    private int yFrom = 0;
    private int xTo = 0;
    private int yTo = 0;
    private JLabel img;
    private double jarak = 0;
    private ArrayList<Double> lines = new ArrayList<>();
    private JButton btnGetLength = new JButton("Add Length Value");
    private JButton btnGetWidth = new JButton("Add Width Value");
    private JButton saveFeature = new JButton("Save Feature");
    private JButton clearData = new JButton("Clear");

    public DrawingLine() {
        setSize(1200, 700);
        setLayout(null);
        img = new JLabel(new ImageIcon("res/1077.jpg"));
        img.setBounds(400, 100, 600, 400);
        btnGetLength.setBounds(10, 10, 200, 50);
        btnGetWidth.setBounds(10, 70, 200, 50);
        saveFeature.setBounds(10, 130, 200, 50);
        clearData.setBounds(10, 200, 200, 50);
        
        btnGetWidth.setEnabled(false);
        saveFeature.setEnabled(false);
        clearData.setEnabled(false);
        
        add(img);
        add(btnGetLength);
        add(btnGetWidth);
        add(saveFeature);
        add(clearData);

        btnGetLength.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lines.add(jarak);
                btnGetLength.setEnabled(false);
                btnGetWidth.setEnabled(true);
                clearData.setEnabled(true);
            }
        });

        btnGetWidth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lines.add(jarak);
                btnGetWidth.setEnabled(false);
                saveFeature.setEnabled(true);
                clearData.setEnabled(true);
            }
        });

        saveFeature.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = "F:\\test-extract-length-width.txt";
                if (lines.isEmpty()) {
                    System.out.println("No data!\n");
                } else {
                    try (FileWriter fw = new FileWriter(filename, true);
                            BufferedWriter bw = new BufferedWriter(fw);
                            PrintWriter out = new PrintWriter(bw)) {
                        for (int i = 0; i < lines.size(); i++) {
                            out.print(lines.get(i) + " ");
                        }
                        out.println("");
                        System.out.println("Data saved!\n");
                        lines.clear();
                        btnGetLength.setEnabled(true);
                        btnGetWidth.setEnabled(false);
                        saveFeature.setEnabled(false);
                        clearData.setEnabled(false);
                    } catch (IOException ex) {
                        //exception handling left as an exercise for the reader
                    }

                }
            }
        });

        clearData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Data cleared!\n");
                btnGetLength.setEnabled(true);
                btnGetWidth.setEnabled(false);
                saveFeature.setEnabled(false);
                clearData.setEnabled(false);
                lines.clear();
            }
        });

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseMotionHandler);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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
            jarak = calculateDistance(xFrom, yFrom, xTo, yTo);
            System.out.println("Jarak Garis: " + jarak);
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
        double distance = 0;
        return Math.sqrt(Math.pow(xFrom - xTo, 2) + Math.pow(yFrom - yTo, 2));
    }

    public static void main(String[] args) {
        new DrawingLine().show();
    }
}
