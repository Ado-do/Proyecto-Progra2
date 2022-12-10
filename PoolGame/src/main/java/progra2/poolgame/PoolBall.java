package progra2.poolgame;

import static java.lang.Math.round;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

import geometricas.Angular;
import geometricas.Circle;
import geometricas.Vector2D;

public class PoolBall extends Circle {
    // * Constantes
    public static final int MASS = 160;
    public static final float FRICTION = 0.2f;

    // * Propiedades bola
    private Color color;
    private Vector2D vel;
    
    public PoolBall(int posX, int posY, Color color, int radius) {
        super(posX, posY, radius);
        this.color = color;

        this.vel = new Vector2D();
    }

    public void move() {
        // * MOVER BOLA SEGÚN VELOCIDAD
        x += vel.x;
        y += vel.y;

        descel();
    }
    private void descel() {
        // * APLICAR ROCE
        // La fuerza de la bola empieza a recibir el roce de la mesa
        float speedMag = vel.getMagnitude();
        if (speedMag <= FRICTION) { // Si la magnitud de la bola es menor al roce, significa que el roce venció el movimiento de la bola
            vel.escale(0);
        } else {                // El roce esta afectando a la bola, pero aún no la detiene.
            vel.toUnitVector();
            vel.escale(speedMag - FRICTION);            
            // System.out.println("x: " + x + "y: " + y);
            // System.out.println("Mag: " + mag);
        }
    }

    public void collide(PoolBall b2) {
        PoolBall b1 = this;

        // * Separar antes de calcular fuerzas resultantes
        separateBalls(b2);

        // * Calcular vectores resultantes de colisión
        b1.vel.subtractVector(b2.getVel());
        float mv = b1.vel.getMagnitude();

        // Calcular vectores unitarios
        Vector2D directBalls = new Vector2D(b2.x - b1.x, b2.y - b1.y);
        directBalls.toUnitVector();
        Vector2D directVel = new Vector2D(b1.vel);
        directVel.toUnitVector();

        // Coseno entre normal de las bolas y velocidad de esta bola
        float cos = directVel.dotProduct(directBalls); // Angulo entre ambos vectores unitarios

        directBalls.escale(cos * mv);
        b1.vel.subtractVector(directBalls);
        b1.vel.addVector(b2.vel);

        b2.vel.addVector(directBalls);
    }
    private void separateBalls(PoolBall b2) {
        PoolBall b1 = this;

        // Punto medio entre bolas superpuestas (colisionadas)
        Point midPoint = new Point(Math.round(b1.x + b2.x)/2, Math.round(b1.y + b2.y)/2);
        
        // Calculamos el vector director, para que al separarlas se mantengan en el mismo angulo en el que colisionaron
        Vector2D direction = new Vector2D(b2.x - b1.x, b2.y - b1.y);
        direction.toUnitVector();

        // Separar bolas ubicandolas una unida a otra, tocándose solo en el punto medio de la colisión 
        b1.setLocation(midPoint.x - (direction.x * radius), midPoint.y - (direction.y * radius));
        b2.setLocation(midPoint.x + (direction.x * radius), midPoint.y + (direction.y * radius));
    }

    public void checkBounces(PoolTable table) {
        int wid = table.WIDTH;
        int len = table.LENGTH;
        int bWid = table.BORDER_WIDTH;
        int bLen = table.BORDER_LENGHT;

        // * Bordes horizontales
        // Izquierda
        if (((x - radius) < bWid) && (vel.x < 0)) { 
            x -= 2 * ((x - radius) - bWid);
            vel.x *= -1;
        } 
        // Derecha
        else if (((x + radius) > (wid - bWid)) && (vel.x > 0)) {
            x -= 2 * ((x + radius) - (wid - bWid));
            vel.x *= -1;
        }

        // * Bordes verticales
        // Arriba
        if (((y - radius) < bLen) && (vel.y < 0)) {
            y -= 2 * ((y - radius) - bLen);
            vel.y *= -1;
        // Abajo
        } else if (((y + radius) > (len - bLen)) && (vel.y > 0)) {
            y -= 2 * ((y + radius) - (len - bLen));
            vel.y *= -1;
        }
    }

    public boolean isCollide(PoolBall otra) {
        return (Angular.distEntre2Puntos(this.getLocation(), otra.getLocation()) < diameter);
    }
    public boolean isMoving() {
        return (vel.x != 0 || vel.y != 0);
    }
    public boolean isPressed(Point posMouse) {
        return (Angular.distEntre2Puntos(posMouse, getLocation()) < radius + CueStick.DISTANCE + CueStick.LENGTH);
        //TODO Mejorar para mejor input¿
    }

    // * Setters
    public void setVel(Vector2D v) {
        vel.setVector(v);
    }
    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // * Getters
    public Point getLocation() {
        return new Point(Math.round(x), Math.round(y));
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public Vector2D getVel() {
        return vel;
    }

    // * Paint
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(round(x - radius), round(y - radius), diameter, diameter);
        this.drawCircle(g);
    }
}
