/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colormoments;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Filipus
 */
public class ColorMoments {

    public double calculateMeanRed(BufferedImage input) {
        long mean = 0;
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                Color c = new Color(input.getRGB(i, j));
                mean += c.getRed();
            }
        }
        //System.out.println("Total Color at channel "+channel+" is "+mean);
        mean /= input.getHeight() * input.getWidth();
        return mean;
    }

    public double calculateMeanGreen(BufferedImage input) {
        long mean = 0;
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                Color c = new Color(input.getRGB(i, j));
                mean += c.getGreen();
            }
        }
        //System.out.println("Total Color at channel "+channel+" is "+mean);
        mean /= input.getHeight() * input.getWidth();
        return mean;
    }

    public double calculateMeanBlue(BufferedImage input) {
        long mean = 0;
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                Color c = new Color(input.getRGB(i, j));
                mean += c.getBlue();
            }
        }
        //System.out.println("Total Color at channel "+channel+" is "+mean);
        mean /= input.getHeight() * input.getWidth();
        return mean;
    }

    public double calculateStdDevRed(BufferedImage input, double mean) {
        long stdDev = 0;
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                Color c = new Color(input.getRGB(i, j));
                stdDev += Math.pow(c.getRed() - mean, 2);

            }
        }

        stdDev /= input.getHeight() * input.getWidth();
        return Math.sqrt(stdDev);
    }

    public double calculateStdDevGreen(BufferedImage input, double mean) {
        long stdDev = 0;
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                Color c = new Color(input.getRGB(i, j));
                stdDev += Math.pow(c.getGreen() - mean, 2);

            }
        }

        stdDev /= input.getHeight() * input.getWidth();
        return Math.sqrt(stdDev);
    }

    public double calculateStdDevBlue(BufferedImage input, double mean) {
        long stdDev = 0;
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                Color c = new Color(input.getRGB(i, j));
                stdDev += Math.pow(c.getBlue() - mean, 2);

            }
        }

        stdDev /= input.getHeight() * input.getWidth();
        return Math.sqrt(stdDev);
    }

    public double calculateSkewnessRed(BufferedImage input, double mean) {
        long skewness = 0;
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                Color c = new Color(input.getRGB(i, j));
                skewness += Math.pow(c.getRed() - mean, 3);
            }
        }

        skewness /= input.getHeight() * input.getWidth();
        return Math.cbrt(skewness);
    }

    public double calculateSkewnessGreen(BufferedImage input, double mean) {
        long skewness = 0;
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                Color c = new Color(input.getRGB(i, j));
                skewness += Math.pow(c.getGreen() - mean, 3);
            }
        }

        skewness /= input.getHeight() * input.getWidth();
        return Math.cbrt(skewness);
    }

    public double calculateSkewnessBlue(BufferedImage input, double mean) {
        long skewness = 0;
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                Color c = new Color(input.getRGB(i, j));
                skewness += Math.pow(c.getBlue() - mean, 3);
            }
        }

        skewness /= input.getHeight() * input.getWidth();
        return Math.cbrt(skewness);
    }

    public double calculateKurtosisRed(BufferedImage input, double mean) {
        long kurtosis = 0;
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                Color c = new Color(input.getRGB(i, j));
                kurtosis += Math.pow(c.getRed() - mean, 4);
            }
        }
        kurtosis /= input.getHeight() * input.getWidth();
        return Math.pow(kurtosis, 0.25);
    }
    public double calculateKurtosisGreen(BufferedImage input, double mean) {
        long kurtosis = 0;
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                Color c = new Color(input.getRGB(i, j));
                kurtosis += Math.pow(c.getRed() - mean, 4);
            }
        }
        kurtosis /= input.getHeight() * input.getWidth();
        return Math.pow(kurtosis, 0.25);
    }
    public double calculateKurtosisBlue(BufferedImage input, double mean) {
        long kurtosis = 0;
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                Color c = new Color(input.getRGB(i, j));
                kurtosis += Math.pow(c.getRed() - mean, 4);
            }
        }
        kurtosis /= input.getHeight() * input.getWidth();
        return Math.pow(kurtosis, 0.25);
    }
}
