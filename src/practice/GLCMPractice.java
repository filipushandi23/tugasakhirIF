/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;
import Jama.Matrix;
/**
 *
 * @author Filipus
 */
public class GLCMPractice {
    public static void main(String[] args) {
        //initialize glcm matrix
        double[][] glcmMatrix = new double[8][8];
        
        double[][] image = {
            {1,5,7,2,4,6},
            {4,6,4,7,0,0},
            {1,1,4,2,5,7},
            {4,5,6,2,5,3},
            {3,3,4,6,7,7},
            {1,2,4,5,1,4},
        };
        
        //ini cuma yg jarak 1 dan angle 0 derajat (horizontal)
        int asal=0;
        int tetangga=0;
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image.length-1; j++) {
//                System.out.println("Piksel ("+i+","+j+") dgn nilai - "+image[i][j]
//                        +" bertetangga dengan "+image[i][j+1]);
                asal = (int) image[i][j];
                tetangga = (int) image[i][j+1];
                glcmMatrix[asal][tetangga]++;
            }
        }
        
        //matriksGLCM asal
        System.out.println("Matriks Asal\n===========================\n");
        for (int i = 0; i < glcmMatrix.length; i++) {
            for (int j = 0; j < glcmMatrix.length; j++) {
                System.out.print(glcmMatrix[i][j]+"\t");
            }
            System.out.println("");
        }
        
        double sum = 0;
        for (int i = 0; i < glcmMatrix.length; i++) {
            for (int j = 0; j < glcmMatrix.length; j++) {
                sum += glcmMatrix[i][j];
            }
        }
        
        System.out.println("SUM is "+sum);
        //matriksGLCM jd nilai probabilitas
        for (int i = 0; i < glcmMatrix.length; i++) {
            for (int j = 0; j < glcmMatrix.length; j++) {
                glcmMatrix[i][j] /= sum;
            }
        }
        
        System.out.println("\nMatriks Probabilitas\n============================\n");
        for (int i = 0; i < glcmMatrix.length; i++) {
            for (int j = 0; j < glcmMatrix.length; j++) {
                System.out.print(glcmMatrix[i][j]+"\t");
            }
            System.out.println("");
        }
        
        //transpose matrix
        System.out.println("\nMatriks GLCM trasnpose\n=======================\n");
        double[][] glcmTranspose = new double[8][8];
        for (int i = 0; i < glcmTranspose.length; i++) {
            for (int j = 0; j < glcmTranspose.length; j++) {
                glcmTranspose[i][j] = glcmMatrix[j][i];
            }
        }
        for (int i = 0; i < glcmTranspose.length; i++) {
            for (int j = 0; j < glcmTranspose.length; j++) {
                System.out.print(glcmTranspose[i][j]+"\t");
            }
            System.out.println("");
        }
        
        //sum of matriks
        System.out.println("\nMatriks GLCM Normalised\n=======================\n");
        for (int i = 0; i < glcmMatrix.length; i++) {
            for (int j = 0; j < glcmMatrix.length; j++) {
                glcmMatrix[i][j] += glcmTranspose[i][j];
            }
        }
        for (int i = 0; i < glcmMatrix.length; i++) {
            for (int j = 0; j < glcmMatrix.length; j++) {
                System.out.print(glcmMatrix[i][j]+"\t");
            }
            System.out.println("");
        }
        
    }
}
