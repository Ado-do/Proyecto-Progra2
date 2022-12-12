package geometricas;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

public class Circle {
    public float x, y;
    protected int radius;
    protected int diameter;
    public Rectangle bounds;

    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.diameter = radius * 2;
        this.bounds = new Rectangle(x-radius, y-radius, diameter, diameter);
    }

    protected void syncBounds() {
        bounds.setLocation(Math.round(x - radius), Math.round(y - radius));
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
    public Rectangle getBounds() {
        return bounds;
    }
    
    // * Setters
    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
        syncBounds();
    }
    public void setLocation(Point p) {
        this.x = (float)p.getX();
        this.y = (float)p.getY();
        syncBounds();
    }

    // * Para dibujar
    public void drawCircle(Graphics2D g2D, Color color) {
        g2D.setColor(color);
        g2D.drawOval(Math.round(x - radius), Math.round(y - radius), diameter, diameter);
    }
    public void fillCircle(Graphics2D g2D, Color c) {
        g2D.setColor(c);
        g2D.fillOval(Math.round(x - radius), Math.round(y - radius), diameter, diameter);
    }
}
