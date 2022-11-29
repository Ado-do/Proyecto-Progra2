package main;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

public class Bola extends JComponent {
    public static final int radius = 15; // Constante
    private Point centerPoint;
    private Color color;
    
    public Bola(Color color, int posX, int posY) {
        super();

        this.color = color;
        this.centerPoint = new Point(posX, posY);
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

    public Point getPoint() {
        return centerPoint;
    }

    public Point getCenterLocation() {
        return centerPoint.getLocation();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.isVisible()) {
            g.setColor(color);
            g.fillOval((int)centerPoint.getX() - radius, (int)centerPoint.getY() - radius, radius*2, radius*2);
        }
    }
}
