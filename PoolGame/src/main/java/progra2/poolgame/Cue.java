package progra2.poolgame;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Stroke;

import geometricas.Angular;
import geometricas.Vector2D;

public class Cue {
    private final float forceCorrection = (-1 * 0.1f); // Corregir e invertir dirección de fuerza del golpe a la bola
    public final int LONG;
    public final int DIAM;
    public int distance; //TODO Cambiar según potencia de tiro (+ animación de pegar pelota)

    private Ball blanca;
    private float angle;
    
    public Cue(int longitude, int diameter, Ball blanca) {
        this.LONG = longitude;
        this.DIAM = diameter;
        this.blanca = blanca;
        this.distance = blanca.getRadius();
    }

    public void hitBall(Vector2D hitVel) {
        hitVel.escale(forceCorrection);
        blanca.setVel(hitVel);
    }

    public void sendMousePos(Point posMouse) {
        // Generar angulo
        angle = Angular.anguloPI(blanca.getLocation(), posMouse.getLocation());
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        if (blanca != null) {
            paintCue(g2D);
            paintPath(g2D);
        }
    }
    private void paintCue(Graphics2D g2D) {
        // Punto cercano a blanca, respecto al angulo que se formo desde el mouse y el centro de la bola blanca
        Point p1 = Angular.generaPunto(blanca.getLocation(), blanca.getRadius() + distance, angle);
        Point p2 = Angular.generaPunto(p1, LONG, angle);

        g2D.setColor(new Color(102,51,0));
        Stroke stroke = new BasicStroke(DIAM);
        Stroke defaultStroke = g2D.getStroke();
        g2D.setStroke(stroke);
        g2D.drawLine(p1.x, p1.y, p2.x, p2.y);
        g2D.setStroke(defaultStroke);
    }
    private void paintPath(Graphics2D g2D) {
        // Calcular angulo opuesto para la linea que dibuja la dirección de la bola (sumarle 180° (1pi))
        float opositeAngle = angle + 1f;

        Point pInicioTrayectoria = Angular.generaPunto(blanca.getLocation(), blanca.getRadius() + 4, opositeAngle);
        Point pFinalTrayectoria = Angular.generaPunto(pInicioTrayectoria, 600, opositeAngle);
        //TODO Cambiar el 600 por la distancia con el objeto con el que habrá colisión
        
        float[] patron = {10f, 4f};
        Stroke stroke = new BasicStroke(4f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, patron, 0.0f);
        Stroke defaultStroke = g2D.getStroke(); // Guardamos la borde predeterminada
        
        g2D.setStroke(stroke); // Utilizar borde con patron de la linea de trayectoria
        g2D.setColor(Color.WHITE);
        g2D.drawLine(pInicioTrayectoria.x, pInicioTrayectoria.y, pFinalTrayectoria.x, pFinalTrayectoria.y);
        g2D.setStroke(defaultStroke); // Regresamos al borde predeterminado
    }
}
