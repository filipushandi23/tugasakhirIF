/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morph;

import java.util.ArrayList;

/**
 *
 * @author Filipus
 */
public class Vector {

    private double x;
    private double y;
    private Point p1;
    private Point p2;

    public Vector(Point p1, Point p2) {
        this.x = p2.getX() - p1.getX();
        this.y = p2.getY() - p1.getY();
        this.p1 = p1;
        this.p2 = p2;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public Line getLine() {
        Line line = new Line(this.p1, this.p2);
        return line;
    }

    public double getVectorLength() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public double calculateAngle(Vector v1, Vector v2) {
        double angleValue = 0;
        angleValue = (v1.getX() * v2.getX()) + (v1.getY() * v2.getY()) / v1.getVectorLength() * v2.getVectorLength();

        return Math.toDegrees(Math.acos(angleValue));
    }
    
    public Line getWidth(Vector pLength, ArrayList<Vector> listOfVector){
        double max = 0;
        int index=0;
        for (int i = 0; i < listOfVector.size(); i++) {
            if(listOfVector.get(i).getVectorLength()>max && calculateAngle(pLength, listOfVector.get(i))==90){
                max = listOfVector.get(i).getVectorLength();
                index = i;
            }
        }
//        System.out.println("Vector "+index+": ["
//                +listOfVector.get(index).getX()+","
//                +listOfVector.get(index).getY()+"] --> "
//                +listOfVector.get(index).getVectorLength()
//        );
        return listOfVector.get(index).getLine();
    }
}
