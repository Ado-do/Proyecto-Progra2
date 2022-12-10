package geometricas;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

public class Circle {
    public float x, y;
    protected int radius;
    protected int diameter;

    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.diameter = radius * 2;
    }

    // * Getters
    public int getRadius() {
        return radius;
    }
    public float getArea() {
        return (float)(Math.PI * (radius * radius));
    }
    public float getPerimeter() {
        return (float)(2 * Math.PI * radius);
    }
    public Point getLocation() {
        return new Point(Math.round(x), Math.round(y));
    }

    // * Para dibujar
    public void drawCircle(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawOval(Math.round(x - radius), Math.round(y - radius), diameter, diameter);
    }
    public void fillCircle(Graphics g, Color c) {
        g.setColor(c);
        g.fillOval(Math.round(x - radius), Math.round(y - radius), diameter, diameter);
    }
}
