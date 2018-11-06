/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glcm;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Filipus
 */
public class GLCM {
   public static void main(String[] args) {
        double[][] glcm = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0.033707865, 0.056179775, 0.028089888, 0, 0, 0},
            {0, 0, 0.056179775, 0.08988764, 0.06741573, 0.005617978, 0, 0},
            {0, 0, 0.028089888, 0.06741573, 0.033707865, 0.016853933, 0.005617978, 0.005617978},
            {0, 0, 0, 0.005617978, 0.016853933, 0.011235955, 0.011235955, 0.011235955},
            {0, 0, 0, 0, 0.005617978, 0.011235955, 0.011235955, 0.04494382},
            {0, 0, 0, 0, 0.005617978, 0.011235955, 0.04494382, 0.314606742},};

        System.out.println("Matriks GLCM Normalisasi");
        for (int i = 0; i < glcm.length; i++) {
            for (int j = 0; j < glcm.length; j++) {
                System.out.print(glcm[i][j] + "\t");
            }
            System.out.println("");
        }
//        System.out.println("Angular second moment = " + angularSecondMoment(glcm));
//        System.out.println("Contrast = " + contrast(glcm));
//        System.out.println("Variance = " + variance(glcm));
//        System.out.println("Entropy = " + entropy(glcm));
//        System.out.println("Homogenity = " + homogenity(glcm));
//        System.out.println("Correlation = "+correlation(glcm));
    }
   
    public double[][] getGLCMMatrix(BufferedImage grayImage){
        double[][] glcmMatrix = new double[256][256];
        
        //untuk contoh perhitungan ini, jarak adalah 1 dengan sudut 0 derajat (horizontal ke kanan)
        int asal = 0;
        int tetangga = 0;
        for (int i = 0; i < grayImage.getWidth(); i++) {
            for (int j = 0; j < grayImage.getHeight()-1; j++) {
                Color cFrom = new Color(grayImage.getRGB(i, j));
                Color cTo = new Color(grayImage.getRGB(i, j+1));
                asal = cFrom.getRed();
                tetangga = cTo.getRed();
                glcmMatrix[asal][tetangga]++;
            }
        }
        
        return glcmMatrix;
    }
    
    public double[][] getNormalisedGLCMMatrix(double[][] glcmMatrix){
        //normalisasi matriks GLCM (siap untuk diekstrak fiturnya)
        
        //membuat matriks probabilitas
        double sum = 0;
        for (int i = 0; i < glcmMatrix.length; i++) {
            for (int j = 0; j < glcmMatrix.length; j++) {
                sum += glcmMatrix[i][j];
            }
        }
        
        for (int i = 0; i < glcmMatrix.length; i++) {
            for (int j = 0; j < glcmMatrix.length; j++) {
                glcmMatrix[i][j] /= sum;
            }
        }
        
        //membentuk matriks transpose
        double[][] glcmTranspose = new double[256][256];
        for (int i = 0; i < glcmTranspose.length; i++) {
            for (int j = 0; j < glcmTranspose.length; j++) {
                glcmTranspose[i][j] = glcmMatrix[j][i];
            }
        }
        
        //matriks normalisasi, yaitu matriks GLCM asal ditambah dengan matriks
        //transposenya supaya jadi simetris
        for (int i = 0; i < glcmMatrix.length; i++) {
            for (int j = 0; j < glcmMatrix.length; j++) {
                glcmMatrix[i][j] += glcmTranspose[i][j];
            }
        }
        
        return glcmMatrix;
    }
    public double angularSecondMoment(double[][] glcm) {
        double asm = 0;
        for (int i = 0; i < glcm.length; i++) {
            for (int j = 0; j < glcm.length; j++) {
                asm += glcm[i][j] * glcm[i][j];
//                System.out.println("Untuk elemen ("+i+","+j+") = "+asm);
            }
        }
        return asm;
    }

    public double contrast(double[][] glcm) {
        double cont = 0;
        for (int i = 0; i < glcm.length; i++) {
            for (int j = 0; j < glcm.length; j++) {
                cont += (i - j) * (i - j) * glcm[i][j];
            }
        }
        return cont;
    }
    
    //correlation belum benar sepenuhnya
    public double correlation(double[][] glcm) {
        double correl = 0;
        double px = 0;
        double py = 0;
        double meanx = 0.0;
        double meany = 0.0;
        double stdevx = 0.0;
        double stdevy = 0.0;

        for (int i = 0; i < glcm.length; i++) {
            for (int j = 0; j < glcm.length; j++) {
                px = px + i * glcm[i][j];
                py = py + j * glcm[i][j];

            }
        }

// Now calculate the standard deviations
        for (int i = 0; i < glcm.length; i++) {
            for (int j = 0; j < glcm.length; j++) {
                stdevx = stdevx + (i - px) * (i - px) * glcm[i][j];
                stdevy = stdevy + (j - py) * (j - py) * glcm[i][j];
            }
        }

// Now finally calculate the correlation parameter
        for (int i = 0; i < glcm.length; i++) {
            for (int j = 0; j < glcm.length; j++) {
                correl += ((i - px) * (j - py) * glcm[i][j] / (stdevx * stdevy));
            }
        }
        return correl;
    }

    public double entropy(double[][] glcm) {
        double ent = 0;
        for (int i = 0; i < glcm.length; i++) {
            for (int j = 0; j < glcm.length; j++) {
                if (glcm[i][j] != 0) {
                    ent -= glcm[i][j] * Math.log(glcm[i][j]);
                }
            }
        }
        return ent;
    }

    public double variance(double[][] glcm) {
        double mean = 0;
        double var = 0;
        for (int i = 0; i < glcm.length; i++) {
            for (int j = 0; j < glcm.length; j++) {
                mean += glcm[i][j];
            }
        }
        mean /= glcm.length * glcm.length;
        for (int i = 0; i < glcm.length; i++) {
            for (int j = 0; j < glcm.length; j++) {
                var += (i - mean) * (i - mean) * glcm[i][j];
            }
        }
        return var;
    }

    public double homogenity(double[][] glcm) {
        double idm = 0;
        for (int i = 0; i < glcm.length; i++) {
            for (int j = 0; j < glcm.length; j++) {
                idm += glcm[i][j] / (1 + (i - j) * (i - j));
            }
        }
        return idm;
    }
}
