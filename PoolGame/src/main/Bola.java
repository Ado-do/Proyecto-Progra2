package main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

public class Bola {
    public static final int RADIUS = 15; // Constante
    private Point centerPoint;
    private Color color;
    
    public Bola(Color color, int posX, int posY) {
        super();

        this.color = color;
        this.centerPoint = new Point(posX, posY);
    }
    public Bola(Color color, Point p) {
        super();

        this.color = color;
        this.centerPoint = p;
    }

//* Setters
    public void setLocation(Point p) {
        centerPoint.setLocation(p);
    }
    
//* Getters
    public Point getPoint() { // Devuelve referencia a punto central (acceso a cambiar posición)
        return centerPoint;
    }
    public Point getLocation() { // Devuelve referencia a punto nuevo con la posición de centro (para obtener posición actual)
        return centerPoint.getLocation();
    }

//* Paint
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval((int)Math.round(centerPoint.getX()) - RADIUS, (int)Math.round(centerPoint.getY()) - RADIUS, RADIUS*2, RADIUS*2);
    }
}
