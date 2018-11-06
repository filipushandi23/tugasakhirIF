/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rwfile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Filipus
 */
public class WriteDataToText {

    public static void main(String[] args) {
        try {
            double[][] matrix = {
                {1.231, 2.123, 3.123},
                {4.112, 5.123, 6.123},
                {7.123, 8.123, 9.321}
            };
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < matrix.length; i++)//for each row
            {
                for (int j = 0; j < matrix.length; j++)//for each column
                {
                    builder.append(matrix[i][j] + "");//append to the output string
                    if (j < matrix.length - 1)//if this is not the last row element
                    {
                        builder.append(" ");//then add comma (if you don't like commas you can use spaces)
                    }
                }
                builder.append(System.getProperty("line.separator"));//append new line at the end of the row
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\dataset.txt"));
            writer.write(builder.toString());//save the string representation of the board
            writer.close();
        } catch (IOException e) {
            //why does the catch need its own curly?
        }
    }
}
