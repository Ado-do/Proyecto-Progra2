package progra2.poolgame;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

import geometricas.PVector;

public class Bola {
    //TODO: Agregar vector y masa
    //* Constantes
    public static final int RADIUS = 15;
    public static final int MASS = 150;
    public static final float ROCE = 0.0925f;

    //* Propiedades bola
    private float x, y;
    private Color color;
    private PVector velocidad;
    
    public Bola(Color color, int posX, int posY) {
        super();

        this.color = color;
        this.x = posX;
        this.y = posY;
    }
    public Bola(Color color, Point p) {
        super();

        this.color = color;
        this.x = p.x;
        this.y = p.y;
    }

    public void move() {
        // La bola se mueve a cierta velocidad
        x += velocidad.x;
        y += velocidad.y;

        // La fuerza de la bola empieza a recibir el roce de la mesa
        float mag = velocidad.getMagnitud();
        if (mag < ROCE) { // Si la magnitud de la bola es menor al roce, significa que el roce venció el movimiento de la bola
            velocidad.escalar(0); 
        } else if(mag > 1) {
            velocidad.x /= 100;
            velocidad.y /= 100;
            System.out.println("VAS MUY RAPIDO PARA WEON");

        } else { // El roce esta afectando a la bola, pero aun no la detiene.
            velocidad.normalizar();
            velocidad.escalar(mag - ROCE);
            
            System.out.println("x: " + x + "y: " + y);
            System.out.println("Mag: " + mag);
        }
        checkCollition();
    }

    public void checkCollition() {
        if (x + RADIUS < Mesa.BORDE || x + RADIUS > 1280 - Mesa.BORDE) {
            x -= velocidad.x;
            velocidad.x *= -1;
        }
        if (y + RADIUS < Mesa.BORDE || y + RADIUS > 600 - Mesa.BORDE) {
            y -= velocidad.y;
            velocidad.y *= -1;
        }
    }

    public void bounce() {

    }

    // * Setters
    public void setSpeed(PVector velocidad) {
        this.velocidad = velocidad;
    }

    //* Getters
    public Point getLocation() {
        return new Point((int)x, (int)y);
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    // * Paint
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(Math.round(x - RADIUS), Math.round(y - RADIUS), RADIUS*2, RADIUS*2);
        g.setColor(Color.BLACK);
        g.drawOval(Math.round(x - RADIUS), Math.round(y - RADIUS), RADIUS*2, RADIUS*2);
    }
}
