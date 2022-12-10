package progra2.poolgame;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import java.awt.BasicStroke;
import geometricas.Angular;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Cue {
    public static float WIDTH = 10f;
    
    public final static int LENGTH = 150;
    public final static int DISTANCE = 10;

    private Ball blanca;
    private float angle;
    
    public Cue(Ball bolaBlanca) {
        this.blanca = bolaBlanca;
    }

    public void sendMousePos(Point posMouse) {
        // Generar angulo
        angle = Angular.anguloPI(blanca.getLocation(), posMouse.getLocation());
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        if (blanca != null) {
            // Punto cercano a blanca, respecto al angulo que se formo desde el mouse y el centro de la bola blanca
            Point p1 = Angular.generaPunto(blanca.getLocation(), blanca.getRadius() + DISTANCE, angle);
            // Punto lejano
            Point p2 = Angular.generaPunto(p1, LENGTH, angle);

            g.setColor(Color.BLACK);
            Stroke stroke = new BasicStroke(WIDTH);
            Stroke defaultStroke = g2D.getStroke();
            g2D.setStroke(stroke);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
            g2D.setStroke(defaultStroke);
        }
    }
}
