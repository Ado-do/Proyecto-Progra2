package main;

import geometricas.Angular;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

public class Taco {
    public final static int WIDTH = 6;
    public final static int LENGTH = 150;
    public final static int DISTANCE = 15;

    private Bola blanca;
    private float angle;
    
    public Taco(Bola bolaBlanca) {
        this.blanca = bolaBlanca;
    }

    public void sendMousePos(Point posMouse) {
        // Generar angulo
        angle = Angular.anguloPI(blanca.getLocation(), posMouse.getLocation());
    }

    public void paint(Graphics g) {
        // Punto cercano a blanca, respecto al angulo que se formo desde el mouse y el centro de la bola blanca
        Point p1 = Angular.generaPunto(blanca.getLocation(), Bola.RADIUS + DISTANCE, angle);
        int p1X = (int)Math.round((p1.getX()));
        int p1Y = (int)Math.round((p1.getY()));

        // Punto lejano
        Point p2 = Angular.generaPunto(p1, LENGTH, angle);
        int p2X = (int)Math.round((p2.getX()));
        int p2Y = (int)Math.round((p2.getY()));

        g.setColor(Color.BLACK);
        g.drawLine(p1X, p1Y, p2X, p2Y);
    }
}
