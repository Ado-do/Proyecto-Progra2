package progra2.poolgame;

import static java.lang.Math.round;
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
    private PVector speed;
    
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
        // * Mover bola según velocidad
        x += speed.x;
        y += speed.y;

        // * Aplicar roce
        // La fuerza de la bola empieza a recibir el roce de la mesa
        float mag = speed.getMagnitud();
        if (mag < ROCE) { // Si la magnitud de la bola es menor al roce, significa que el roce venció el movimiento de la bola
            speed.escalar(0);
        } else { // El roce esta afectando a la bola, pero aun no la detiene.
            speed.normalizar();
            speed.escalar(mag - ROCE);
            
            // System.out.println("x: " + x + "y: " + y);
            // System.out.println("Mag: " + mag);
        }

        // * Revisar colisiones
        checkCollition();
    }

    public void checkCollition() {
        //* Bordes horizontales
        //Izquierda
        if ((x - RADIUS) < Mesa.BORDE && speed.x < 0) {  
            System.out.println("izq");
            speed.x *= -1;
            x -= 2 * ((x - RADIUS) - Mesa.BORDE);
        } 
        //Derecha
        else if (((x + RADIUS) > (1280 - Mesa.BORDE)) && (speed.x > 0)) {
            System.out.println("DERECHA");
            // x -= speed.x;
            speed.x *= -1;
            x -= 2 * ((x + RADIUS) - (1280 - Mesa.BORDE));
        }

        //* Bordes verticales
        // Arriba
        if (((y - RADIUS) < Mesa.BORDE) && (speed.y < 0)) {
            speed.y *= -1;
            y -= 2 * ((y - RADIUS) - Mesa.BORDE);
        // Abajo
        } else if (((y + RADIUS) > (600 - Mesa.BORDE)) && (speed.y > 0)) {
            speed.y *= -1;
            y -= 2 * ((y + RADIUS) - (600 - Mesa.BORDE));
        }
    }

    public void bounce() {

    }

    // * Setters
    public void setSpeed(PVector speed) {
        this.speed = speed;
    }

    //* Getters
    public Point getLocation() {
        return new Point(round(x), round(y));
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
        g.fillOval(round(x - RADIUS), round(y - RADIUS), RADIUS*2, RADIUS*2);
        g.setColor(Color.BLACK);
        g.drawOval(round(x - RADIUS), round(y - RADIUS), RADIUS*2, RADIUS*2);
    }
}
