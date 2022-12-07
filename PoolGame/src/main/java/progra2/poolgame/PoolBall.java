package progra2.poolgame;

import static java.lang.Math.round;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

import geometricas.Angular;
import geometricas.Circle;
import geometricas.Vector2D;

public class PoolBall extends Circle {
    //* Constantes
    public static final int MASS = 160;
    public static final float FRICTION = 0.4f;

    //* Propiedades bola
    private Color color;
    private Vector2D speed;
    
    public PoolBall(int posX, int posY, Color color, int radius) {
        super(posX, posY, radius);
        this.color = color;

        this.speed = new Vector2D();
    }

    public void move() {
        //* MOVER BOLA SEGÚN VELOCIDAD
        x += speed.x;
        y += speed.y;

        descel();
    }

    private void descel() {
        //* APLICAR ROCE
        // La fuerza de la bola empieza a recibir el roce de la mesa
        float speedMag = speed.getMagnitude();
        if (speedMag <= FRICTION) { // Si la magnitud de la bola es menor al roce, significa que el roce venció el movimiento de la bola
            speed.escale(0);
        } else {                // El roce esta afectando a la bola, pero aún no la detiene.
            speed.toUnitVector();
            speed.escale(speedMag - FRICTION);            
            // System.out.println("x: " + x + "y: " + y);
            // System.out.println("Mag: " + mag);
        }
    }

    public void collide() {
        // Separar bolas

        // Calcular vectores resultantes de colisión
    }

    // public boolean collision(PoolBall otra) { // Revisamos si hay colisión con la bola
    //     Vector2D choko = new Vector2D(otra.x - x, otra.y - y);
    //     boolean isCollision = (choko.getMagnitude() < radius * 2); // si la magnitud del vector es menor que el diámetro de la bola, quiere decir
    //     return isCollision; // que están una encima de la otra
    // }

    public void checkBounces(PoolTable table) {
        int wid = table.WIDTH;
        int len = table.LENGTH;
        int bWid = table.BORDER_WIDTH;
        int bLen = table.BORDER_LENGHT;

        //* Bordes horizontales
        // Izquierda
        if (((x - radius) < bWid) && (speed.x < 0)) { 
            x -= 2 * ((x - radius) - bWid);
            speed.x *= -1;
        } 
        // Derecha
        else if (((x + radius) > (wid - bWid)) && (speed.x > 0)) {
            x -= 2 * ((x + radius) - (wid - bWid));
            speed.x *= -1;
        }

        //* Bordes verticales
        // Arriba
        if (((y - radius) < bLen) && (speed.y < 0)) {
            y -= 2 * ((y - radius) - bLen);
            speed.y *= -1;
        // Abajo
        } else if (((y + radius) > (len - bLen)) && (speed.y > 0)) {
            y -= 2 * ((y + radius) - (len - bLen));
            speed.y *= -1;
        }
    }

    // * Setters
    public void setSpeed(Vector2D v) {
        speed.setVector(v);
    }

    //* Getters
    public boolean isCollide(PoolBall otra) {
        return (Angular.distEntre2Puntos(this.getLocation(), otra.getLocation()) < diameter);
    }
    public boolean isMoving() {
        return (speed.x != 0 || speed.y != 0);
    }
    public boolean isPressed(Point posMouse) {
        return (Angular.distEntre2Puntos(posMouse, getLocation()) < radius + CueStick.DISTANCE + CueStick.LENGTH);
        //TODO Mejorar para mejor input¿
    }
    public Point getLocation() {
        return new Point(Math.round(x), Math.round(y));
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public Vector2D getSpeed() {
        return speed;
    }

    // * Paint
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(round(x - radius), round(y - radius), diameter, diameter);
        this.drawCircle(g);
    }
}
