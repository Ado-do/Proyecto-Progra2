package main;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;

public class Circle {

    private int radius;
    private Point centerPoint;

    public Circle(int x, int y, int r) {
        centerPoint = new Point(x, y);
        radius = r;
    }
    public Circle(Point p, int r) {
        centerPoint = p;
        radius = r;
    }

    public void setLocation(Point p) {
        centerPoint.setLocation(p);
    }

    // @Override
    // public void setLocation(int pX, int pY) {
    //     centerPoint.setLocation(pX, pY);
    // }

    public int getRadius() {
        return radius;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    public Point getPoint() {
        return centerPoint;
    }

    public Point getLocation() {
        return centerPoint.getLocation();
    }

    public void paint(Graphics g) {
        g.setColor(Color.RED);
        g.drawOval((int)Math.round(centerPoint.getX()) - radius, (int)Math.round(centerPoint.getY()) - radius, radius*2, radius*2);

        int miniRadius = 1;
        g.setColor(Color.BLACK);
        g.drawOval((int)Math.round(centerPoint.getX()) - miniRadius, (int)Math.round(centerPoint.getY()) - miniRadius, miniRadius*2, miniRadius*2);
    }
}
