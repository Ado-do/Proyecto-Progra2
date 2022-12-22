package progra2.poolgame;

import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import geometricas.Angular;
import geometricas.Circle;
import geometricas.Vector2D;

/**
 * Clase que representa el taco
 * 
 * @author Alonso Bustos
 */
public class Cue {
    private final float forceCorrection; // Corregir e invertir dirección de fuerza del golpe a la bola
    private final int longitude, diameter;
    private final int baseDistance;

    private Rectangle playfield;
    private ArrayList<Ball> arrayBalls;
    private Ball cueBall;

    private float angle;
    private Line2D.Float cueLine, pathLine;
    private Circle ballPreview;

    private Color color;

    /**
     * Método constructor para el taco
     *
     * @param color Color que tendrá el taco
     */
    public Cue(Color color) {
        this.playfield = PoolGame.table.playfield;
        this.arrayBalls = PoolGame.table.getArrayBalls();
        this.cueBall = PoolGame.table.getCueBall();

        this.longitude = Math.round(PoolGame.table.main.width * 0.6f);
        this.diameter = Math.round(PoolGame.table.main.height * 0.02f);
        this.baseDistance = cueBall.getRadius();
        this.forceCorrection = -(PoolGame.table.main.width * 8e-5f); // 8e-5f == 0.00008f

        this.angle = 1f;
        this.cueLine = new Line2D.Float();
        this.pathLine = new Line2D.Float();

        this.color = color;
    }

    /**
     * Método para que el taco dispare
     * 
     * @param hitvel la velocidad del golpe
     */
    public void shotBall(Vector2D hitVel) {
        hitVel.escale(forceCorrection);
        cueBall.setVel(hitVel);
    }

    /**
     * Método que actualiza el taco
     * 
     * @param cueAngle    Angulo del taco
     * @param cueDistance Distancia de bola blanca a taco
     */
    public void update(float cueAngle, float cueDistance) {
        angle = cueAngle;

        setCueLine(cueDistance);
        setPathLine();
    }

    private void setCueLine(float hitForce) {
        // * Linea de taco
        // Punto cercano a blanca, respecto al angulo que se formo desde el mouse y el
        // centro de la bola blanca
        Point cueP1 = Angular.generaPunto(cueBall.getLocation(), cueBall.getRadius() + baseDistance + hitForce, angle);
        Point cueP2 = Angular.generaPunto(cueP1, longitude, angle);
        cueLine.setLine(cueP1, cueP2);
    }
    
    private void setPathLine() {
        // * Linea de trayectoria
        float oppositeAngle = angle + 1f;

        Point pathP1 = Angular.generaPunto(cueBall.getLocation(), cueBall.getRadius(), oppositeAngle);
        // Point pathP1 = Angular.generaPunto(cueBall.getLocation(), cueBall.getRadius()
        // + 5, oppositeAngle);

        Point pathP2;
        int dist = 0;
        boolean collition = false;
        do {
            pathP2 = Angular.generaPunto(pathP1, ++dist, oppositeAngle);
            Circle intersecTest = new Circle(pathP2.x, pathP2.y, cueBall.getRadius());

            // Verificar si intersecta con una bola
            for (int i = 0; i < arrayBalls.size(); i++) {
                Ball ball = arrayBalls.get(i);
                if (ball == cueBall)
                    continue;

                if (ball.intersecs(intersecTest)) {
                    ballPreview = intersecTest;
                    collition = true;
                    break;
                }
            }
            // Verificar si intersecta con paredes de area de juego
            if (!playfield.contains(intersecTest.getBounds())) {
                pathP2 = Angular.generaPunto(pathP1, dist - 2, oppositeAngle);
                intersecTest.setLocation(pathP2);

                ballPreview = intersecTest;
                collition = true;
            }
        } while (!collition);

        pathLine.setLine(pathP1, pathP2);
    }

    /**
     * Método para dibujar el taco
     * 
     * @param g Objeto Graphics para dibujar
     */
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        if (cueBall != null) {
            paintCue(g2D);
            paintPath(g2D);
        }
    }
    private void paintCue(Graphics2D g2D) {
        // * Dibujar taco
        Point cueLineP1 = new Point(Math.round(cueLine.x1), Math.round(cueLine.y1));
        Point cueLineP2 = new Point(Math.round(cueLine.x2), Math.round(cueLine.y2));
        Stroke newStroke = new BasicStroke(diameter);
        Stroke oldStroke = g2D.getStroke();

        g2D.setStroke(newStroke);

        // Principal
        g2D.setColor((color != null) ? color : new Color(200, 157, 124));
        g2D.draw(cueLine);

        // Punta
        Point cueTipP2 = Angular.generaPunto(cueLineP1, longitude * 0.015f, angle);
        Line2D cueTip = new Line2D.Float(cueLineP1, cueTipP2);
        g2D.setColor(new Color(0, 150, 200));
        g2D.draw(cueTip);

        // Final
        Point cueEndP1 = Angular.generaPunto(cueLineP2, longitude * 0.025f, angle + 1f);
        Line2D cueEnd = new Line2D.Float(cueLineP2, cueEndP1);
        g2D.setColor(Color.black);
        g2D.draw(cueEnd);

        g2D.setStroke(oldStroke);
    }

    private void paintPath(Graphics2D g2D) {
        // * Dibujar linea de trayectoria
        float[] patron = { 10f, 4f };
        Stroke newStroke = new BasicStroke(2.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, patron, 0f);
        Stroke oldStroke = g2D.getStroke(); // Guardamos la borde predeterminada

        g2D.setStroke(newStroke); // Utilizar borde con patron de la linea de trayectoria
        g2D.setColor(Color.white);
        g2D.draw(pathLine);
        g2D.setStroke(oldStroke); // Regresamos al borde predeterminado

        // * Dibujar vista previa de colisión de blanca
        ballPreview.drawCircle(g2D, Color.white);
    }
}
