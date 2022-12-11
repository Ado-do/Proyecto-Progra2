package progra2.poolgame;

import static java.lang.Math.round;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

import geometricas.Angular;
import geometricas.Circle;
import geometricas.Vector2D;

public class Ball extends Circle {
    public final float FRICTION = 0.045f;
    
    private Color color;
    private Vector2D vel;
    private final int number;
    
    private int contBounces = 0;

    public Ball(int posX, int posY, Color color, int radius, int number) {
        super(posX, posY, radius);
        this.color = color;
        this.number = number;
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
        } else {                    // El roce esta afectando a la bola, pero aún no la detiene.
            Vector2D fricVec = new Vector2D(vel);
            fricVec.toUnitVector();
            fricVec.escale(FRICTION);
            vel.subtractVector(fricVec);
        }
    }

    public void collide(Ball b2) {
        Ball b1 = this;

        // * Separar antes de calcular fuerzas resultantes
        separateBalls(b2);

        // * Calcular vectores resultantes de colisión
        b1.vel.subtractVector(b2.vel);
        float mv = b1.vel.getMagnitude();

        // Calcular vectores unitarios
        Vector2D newVelB2 = new Vector2D(b2.x - b1.x, b2.y - b1.y);
        newVelB2.toUnitVector();
        Vector2D directVelB1 = new Vector2D(b1.vel);
        directVelB1.toUnitVector();

        // Coseno entre normal de las bolas y vector de velocidad de b1
        float cos = directVelB1.dotProduct(newVelB2);
        newVelB2.escale(cos * mv);

        // * Velocidades resultantes
        b1.vel.subtractVector(newVelB2);
        b1.vel.addVector(b2.vel);

        b2.vel.addVector(newVelB2);
    }
    private void separateBalls(Ball b2) {
        Ball b1 = this;

        // Punto medio entre bolas superpuestas (colisionadas)
        Point midPoint = new Point(round(b1.x + b2.x)/2, round(b1.y + b2.y)/2);
        
        // Calculamos el vector director, para que al separarlas se mantengan en el mismo angulo en el que colisionaron
        Vector2D direction = new Vector2D(b2.x - b1.x, b2.y - b1.y);
        direction.toUnitVector();

        // Separar bolas ubicandolas una unida a otra, tocándose solo en el punto medio de la colisión 
        b1.setLocation(midPoint.x - (direction.x * radius), midPoint.y - (direction.y * radius));
        b2.setLocation(midPoint.x + (direction.x * radius), midPoint.y + (direction.y * radius));
    }

    public void checkBounces(Table table) {
        int wid = table.WIDTH;
        int len = table.LENGTH;
        int borderWid = table.BORDER_WIDTH;
        int borderLen = table.BORDER_LENGHT;

        // * Bordes horizontales
        // Izquierda
        if (((x - radius) < borderWid) && (vel.x < 0)) { 
            x -= 2 * ((x - radius) - borderWid);
            vel.x *= -1;
            System.out.println("REBOTES: "+(++contBounces));
        } 
        // Derecha
        else if (((x + radius) > (wid - borderWid)) && (vel.x > 0)) {
            x -= 2 * ((x + radius) - (wid - borderWid));
            vel.x *= -1;
            System.out.println("REBOTES: "+(++contBounces));
        }

        // * Bordes verticales
        // Arriba
        if (((y - radius) < borderLen) && (vel.y < 0)) {
            y -= 2 * ((y - radius) - borderLen);
            vel.y *= -1;
            System.out.println("REBOTES: "+(++contBounces));
        // Abajo
        } else if (((y + radius) > (len - borderLen)) && (vel.y > 0)) {
            y -= 2 * ((y + radius) - (len - borderLen));
            vel.y *= -1;
            System.out.println("REBOTES: "+(++contBounces));
        }
    }

    public boolean isCollide(Ball otra) {
        return (Angular.distEntre2Puntos(this.getLocation(), otra.getLocation()) < diameter);
    }
    public boolean isMoving() {
        return (vel.x != 0 || vel.y != 0);
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
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public Vector2D getVel() {
        return vel;
    }
    public Color getColor() {
        return color;
    }

    // * Paint
    public void paint(Graphics g) {        
        this.fillCircle(g, color);
        this.drawCircle(g);

        if (number != 0) {
            Circle subCircle = new Circle(round(x), round(y), round(radius * 0.6f));
            subCircle.fillCircle(g, Color.WHITE);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString(""+number, round(x - (g.getFontMetrics().stringWidth(""+number))/2), round(y + (g.getFontMetrics().getHeight())/3));
        }
    }
}
