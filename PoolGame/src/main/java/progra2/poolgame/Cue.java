package progra2.poolgame;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import geometricas.Angular;
import geometricas.Circle;
import geometricas.Vector2D;

public class Cue {
    private final float forceCorrection = (-1 * 0.12f); // Corregir e invertir dirección de fuerza del golpe a la bola
    public final int LONG;
    public final int DIAM;
    public int distance; //TODO Cambiar según potencia de tiro (+ animación de pegar pelota)

    private Table table;
    private ArrayList<Ball> arrayBalls;
    private Ball blanca;

    private float mainAngle;
    private Line2D.Float cueLine;
    private Line2D.Float pathLine;
    private Circle ballPreview;
    
    public Cue(Table table) {
        this.table = table;
        this.arrayBalls = table.getArrayBalls();
        this.blanca = table.getBlanca();
        
        this.LONG = Math.round(table.rectMain.width * 0.6f);
        this.DIAM = Math.round(table.rectMain.height * 0.02f);
        this.distance = blanca.getRadius();
        
        this.mainAngle = 0;
        this.cueLine = new Line2D.Float();
        this.pathLine = new Line2D.Float();
    }

    public void hitBall(Vector2D hitVel) {
        hitVel.escale(forceCorrection);
        blanca.setVel(hitVel);
    }
    
    public void updateMousePos(Point posMouse) {
        // Generar angulo entre taco y blanca
        mainAngle = Angular.anguloPI(blanca.getLocation(), posMouse.getLocation());

        setCueLine();
        setPathLine();
    }
    private void setCueLine() {
        // * Linea de taco
        // Punto cercano a blanca, respecto al angulo que se formo desde el mouse y el centro de la bola blanca
        Point cueP1 = Angular.generaPunto(blanca.getLocation(), blanca.getRadius() + distance, mainAngle);
        Point cueP2 = Angular.generaPunto(cueP1, LONG, mainAngle);
        cueLine.setLine(cueP1, cueP2);
    }
    private void setPathLine() {
        // * Linea de trayectoria
        float oppositeAngle = mainAngle + 1f;

        Point pathP1 = Angular.generaPunto(blanca.getLocation(), blanca.getRadius() + 5, oppositeAngle);

        Point pathP2;
        boolean collition = false;
        int dist = 0;
        do {
            pathP2 = Angular.generaPunto(pathP1, ++dist, oppositeAngle);
            Circle intersecTest = new Circle(pathP2.x, pathP2.y, blanca.getRadius());

            // Verificar si intersecta con una bola
            for (int i = 0; i < arrayBalls.size(); i++) {
                Ball ball = arrayBalls.get(i);
                if (ball == blanca) continue;

                if (ball.intersecs(intersecTest)) {
                    ballPreview = intersecTest;
                    collition = true;
                    break;
                }
            }
            // Verificar si intersecta con paredes de area de juego
            if (!table.rectPlayfield.contains(intersecTest.getBounds())) {
                pathP2 = Angular.generaPunto(pathP1, dist-2, oppositeAngle);
                intersecTest.setLocation(pathP2);

                ballPreview = intersecTest;
                collition = true;
            }
        } while (!collition);

        pathLine.setLine(pathP1, pathP2);
        /////TODO Cambiar el 600 por la distancia con el objeto con el que habrá colisión
        //TODO NI UNA WEA PQ SOY UN PUTO GENIOOOOOOOOOOOOOO (toy feli pq me salio)
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        if (blanca != null) {
            paintCue(g2D);
            paintPath(g2D);
        }
    }
    private void paintCue(Graphics2D g2D) {
        // * Dibujar taco
        Point cueLineP1 = new Point(Math.round(cueLine.x1), Math.round(cueLine.y1));
        Point cueLineP2 = new Point(Math.round(cueLine.x2), Math.round(cueLine.y2));
        Stroke stroke = new BasicStroke(DIAM);
        Stroke defaultStroke = g2D.getStroke();
        
        g2D.setStroke(stroke);

        // Principal
        g2D.setColor(new Color(200, 157, 124));
        g2D.draw(cueLine);
        // Punta
        Point cueTipP2 = Angular.generaPunto(cueLineP1, LONG * 0.015f, mainAngle);
        Line2D cueTip = new Line2D.Float(cueLineP1, cueTipP2);
        g2D.setColor(Color.blue);
        g2D.draw(cueTip);
        // Final
        Point cueEndP1 = Angular.generaPunto(cueLineP2, LONG * 0.025f, mainAngle + 1f);
        Line2D cueEnd = new Line2D.Float(cueLineP2, cueEndP1);
        g2D.setColor(Color.black);
        g2D.draw(cueEnd);

        g2D.setStroke(defaultStroke);
    }
    private void paintPath(Graphics2D g2D) {
        // * Dibujar linea de trayectoria
        float[] patron = {10f, 4f};
        Stroke stroke = new BasicStroke(2.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, patron, 0f);
        Stroke defaultStroke = g2D.getStroke(); // Guardamos la borde predeterminada
        
        g2D.setStroke(stroke); // Utilizar borde con patron de la linea de trayectoria
        g2D.setColor(Color.WHITE);
        g2D.draw(pathLine);
        g2D.setStroke(defaultStroke); // Regresamos al borde predeterminado

        // * Dibujar vista previa de colisión de blanca
        ballPreview.drawCircle(g2D, Color.WHITE);
    }
}
