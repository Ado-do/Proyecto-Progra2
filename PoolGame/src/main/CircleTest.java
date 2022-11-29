package main;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JComponent;

public class CircleTest extends JComponent {

    private int radius;
    private Point centerPoint;

    public CircleTest(int x, int y, int r) {
        centerPoint = new Point(x, y);
        radius = r;
    }
    public CircleTest() {
        centerPoint = new Point(0, 0);
        radius = 1;
    }

    public void setCenterLocation(Point p) {
        centerPoint.setLocation(p);
    }

    public void setCenterLocation(int pX, int pY) {
        centerPoint.setLocation(pX, pY);
    }

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

    public Point getCenterLocation() {
        return centerPoint.getLocation();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.drawOval((int)centerPoint.getX() - radius, (int)centerPoint.getY() - radius, radius*2, radius*2);

        int miniRadius = 1;
        g.setColor(Color.BLACK);
        g.drawOval((int)centerPoint.getX() - miniRadius, (int)centerPoint.getY() - miniRadius, miniRadius*2, miniRadius*2);
    }
}
