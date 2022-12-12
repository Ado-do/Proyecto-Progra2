package progra2.poolgame;

import static java.lang.Math.round;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import geometricas.Angular;
import geometricas.Circle;
import geometricas.Vector2D;

public class Ball extends Circle {
    public final float FRICTION = 0.02f;
    
    private final int number;
    private Color color;
    private Vector2D vel;

    public Ball(int posX, int posY, Color color, int radius, int number) {
        super(posX, posY, radius);
        this.color = color;
        this.number = number;
        this.vel = new Vector2D();
    }

    public static void setRandomLocation(Ball ball, Table table) {
        ArrayList<Ball> arrayBalls = table.getArrayBalls();
        ArrayList<Pocket> arrayPockets = table.getArrayPockets();

        Random rand = new Random();
        int borderWidth = table.rectPlayfield.x,  width = table.rectMain.width;
        int borderHeight = table.rectPlayfield.y, height = table.rectPlayfield.height;
        int Xmax = width-borderWidth-ball.getRadius(),   Xmin = borderWidth + ball.getRadius();
        int Ymax = height-borderHeight-ball.getRadius(), Ymin = borderHeight + ball.getRadius();

        boolean wrongLocation;
        do {
            wrongLocation = false;
            ball.setLocation(rand.nextInt(Xmax - Xmin) + Xmin, rand.nextInt(Ymax - Ymin) + Ymin);

            for (int i = 0; i < arrayBalls.size(); i++) {
                Ball b2 = arrayBalls.get(i);
                if (ball == b2) continue;

                if (ball.intersecs(arrayBalls.get(i))) {
                    wrongLocation = true;
                    break;
                }
            }
            for (Pocket pocket : arrayPockets) {
                if (pocket.isPocketed(ball)) {
                    wrongLocation = true;
                    break;
                }
            }
        } while (wrongLocation);
    }

    public void move() {
        // * MOVER BOLA SEGÚN VELOCIDAD
        x += vel.x;
        y += vel.y;
        this.syncBounds();

        descel();
    }
    private void descel() {
        // * APLICAR ROCE
        // La fuerza de la bola empieza a recibir el roce de la mesa
        if (vel.getMagnitude() <= FRICTION) { // Si la magnitud de la bola es menor al roce, significa que el roce venció el movimiento de la bola
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

        if (b1.vel.getMagnitude() < FRICTION) { // Bugfix
            b1.vel.escale(0);
        } else {
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
        int width = table.rectMain.width;
        int height = table.rectMain.height;
        int borderWidth = table.rectPlayfield.x;
        int borderHeight = table.rectPlayfield.y;

        // * Bordes horizontales
        // Izquierda
        if (((x - radius) < borderWidth) && (vel.x < 0)) {
            x -= 2 * ((x - radius) - borderWidth);
            vel.x *= -1;
        } 
        // Derecha
        else if (((x + radius) > (width - borderWidth)) && (vel.x > 0)) {
            x -= 2 * ((x + radius) - (width - borderWidth));
            vel.x *= -1;
        }

        // * Bordes verticales
        // Arriba
        if (((y - radius) < borderHeight) && (vel.y < 0)) {
            y -= 2 * ((y - radius) - borderHeight);
            vel.y *= -1;
        // Abajo
        } else if (((y + radius) > (height - borderHeight)) && (vel.y > 0)) {
            y -= 2 * ((y + radius) - (height - borderHeight));
            vel.y *= -1;
        }
        this.syncBounds();
    }

    public boolean intersecs(Circle otra) {
        return (Angular.distEntre2Puntos(this.getLocation(), otra.getLocation()) <= diameter);
    }
    public boolean isMoving() {
        return (vel.x != 0 || vel.y != 0);
    }

    // * Setters
    public void setVel(Vector2D v) {
        vel.setVector(v);
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
    public void paint(Graphics2D g2D) {        
        this.fillCircle(g2D, color);
        this.drawCircle(g2D, Color.BLACK);

        if (number != 0) {
            Circle subCircle = new Circle(round(x), round(y), round(radius * 0.6f));
            subCircle.fillCircle(g2D, Color.WHITE);

            g2D.setColor(Color.BLACK);
            g2D.setFont(new Font("Arial", Font.BOLD, round(radius * 0.9f)));
            g2D.drawString(""+number, round(x - (g2D.getFontMetrics().stringWidth(""+number))/2), round(y + (g2D.getFontMetrics().getHeight())/3));
        }
    }
}
