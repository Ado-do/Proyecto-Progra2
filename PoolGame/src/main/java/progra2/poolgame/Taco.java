package progra2.poolgame;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import java.awt.BasicStroke;
import geometricas.Angular;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;


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
        Graphics2D g2D = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHints(rh);

        // Punto cercano a blanca, respecto al angulo que se formo desde el mouse y el centro de la bola blanca
        Point p1 = Angular.generaPunto(blanca.getLocation(), Bola.RADIUS + DISTANCE, angle);
        // Punto lejano
        Point p2 = Angular.generaPunto(p1, LENGTH, angle);

        g.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(14f);
        Stroke defaultStroke = g2D.getStroke();
        g2D.setStroke(stroke);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
        g2D.setStroke(defaultStroke);
    }
}
