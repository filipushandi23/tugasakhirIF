/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

/**
 *
 * @author Filipus
 */
public class CobaCoba {

    public static void main(String[] args) {
        double[][] data = {
            {0.2, 0.1, 0.2, 0.2, 0.1, 0.1, 0.1},
            {0.4, 0.1, 0, 0.1, 0.2, 0.1, 0.1},
            {0.1, 0.2, 0.1, 0.2, 0.2, 0.1, 0.1},
            {0.1, 0, 0.5, 0.1, 0.1, 0.1, 0.1},
            {0.1, 0.1, 0.3, 0, 0.2, 0.1, 0.2},
            {0.3, 0, 0.2, 0, 0.2, 0.1, 0.2}
        };

//        double[][] data = {
//            {1.625, 0.076, 1, 2, 2, 1.238, 0.132, 0.898, 2.590, 0.239, 27.29, 0.712},
//            {1.654, 0.0814, 1.2, 2.3, 2.2, 1.333, 0.145, 0.879, 2.710, 0.244, 28.11, 0.961},
//            {2.151, 0.055, 1, 3, 3, 2.52, 0.235, 0.910, 4.125, 0.441, 20.51, 0.881},
//            {3.112, 0.069, 1, 3.772, 3.551, 2.125, 0.322, 1.342, 4.123, 0.551, 24.12, 0.662},
//        };
        double[][] rbfMatrix = createRBFMatrix(data, 0.1);
//        double[] test = {1.630, 0.081, 1.2, 2.2, 2.3, 1.256, 0.188, 0.087, 2.499, 0.255, 28.23, 0.825};
        double[] test = {0.1, 0.2, 0.2, 0.1, 0.1, 0.1, 0.2};

        //double[][] rbf = createRBFMatrix(data, 1);
        for (int i = 0; i < rbfMatrix.length; i++) {
            for (int j = 0; j < rbfMatrix[0].length; j++) {
                System.out.print(+rbfMatrix[i][j] + "\t");
            }
            System.out.println("");
        }

        System.out.println("RBF UJI");
        double[] rbfUji = createRBFTestMatrix(data, 0.1, test);
        for (int i = 0; i < rbfUji.length; i++) {
            System.out.print(rbfUji[i] + "\t");
        }
        System.out.println("");
//        System.out.println(""+Math.exp(-50*0.08));
    }

    private static double calculateRBFKernel(double[][] data, double sigma,
            int classSource, int classTarget) {
        double value = 0;
        //data[0].length --> banyaknya fitur untuk 1 data
        for (int i = 0; i < data[0].length; i++) {
            //System.out.println(data[classSource][i]+" - "+data[classTarget][i]);
            value += Math.pow(data[classSource][i] - data[classTarget][i], 2);
        }
        return Math.exp(-(value) / (2 * Math.pow(sigma, 2)));
    }

    public static double[][] createRBFMatrix(double data[][], double sigma) {
        double[][] rbfMatrix = new double[data.length][data.length];
        for (int i = 0; i < rbfMatrix.length; i++) {
            for (int j = 0; j < rbfMatrix.length; j++) {
                rbfMatrix[i][j] = calculateRBFKernel(data, sigma, i, j);
            }
        }
        return rbfMatrix;
    }

    public static double[] createRBFTestMatrix(double data[][], double sigma, double[] test) {
        double[] rbfMatrixUji = new double[data.length];
        double temp = 0;
        for (int i = 0; i < data.length; i++) {
            temp = 0;
            for (int j = 0; j < data[0].length; j++) {
                temp += Math.pow(test[j] - data[i][j], 2);
                //System.out.println("(" + test[j] + " - " + data[i][j] + ")^2 = " + temp);
            }
            rbfMatrixUji[i] = Math.exp(-(temp) / (2 * Math.pow(sigma, 2)));
//            System.out.println("---------------------------");
        }
        return rbfMatrixUji;
    }
}
