/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morph;

/**
 *
 * @author Filipus
 */
public class Line {

    Point a;
    Point b;

    public Line() {

    }

    public Line(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public Point getA() {
        return a;
    }

    public void setA(Point a) {
        this.a = a;
    }

    public Point getB() {
        return b;
    }

    public void setB(Point b) {
        this.b = b;
    }
}
