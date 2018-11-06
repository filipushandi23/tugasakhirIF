/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rwfile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author Filipus
 */
public class ReadDataFromText {
    public static void main(String[] args) throws FileNotFoundException {
      Scanner sc = new Scanner(new BufferedReader(new FileReader("F:\\feature-dataset.txt")));
//      Scanner sc = new Scanner(new BufferedReader(new FileReader("F:\\data.txt")));
   
      double [][] myArray = new double[36][6];
      while(sc.hasNextLine()) {
         for (int i=0; i<myArray.length; i++) {
            String[] line = sc.nextLine().trim().split(" ");
            for (int j=0; j<line.length; j++) {
               myArray[i][j] = Double.parseDouble(line[j]);
            }
         }
      }
      
        for (int i = 0; i < myArray.length; i++) {
            for (int j = 0; j < myArray[0].length; j++) {
                System.out.print(myArray[i][j]+"\t");
            }
            System.out.println("");
        }

//          String a = "10.1";
//          double b = Double.parseDouble(a);
//          System.out.println(b);
    }
}
