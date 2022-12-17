package progra2.poolgame;

import static java.lang.Math.round;

import java.util.ArrayList;
import java.util.Random;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;

import geometricas.Angular;
import geometricas.Circle;
import geometricas.Vector2D;

public class Ball extends Circle {
    private final int number;
    private final Color ballColor;
    private Vector2D vel;

    public Ball(int posX, int posY, int radius, int number) {
        super(posX, posY, radius);
        this.number = number;
        this.vel = new Vector2D();

        switch (number) {
            case 0:          ballColor = Color.white; break;
            case 1: case 9:  ballColor = Color.yellow; break;
            case 2: case 10: ballColor = Color.blue; break;
            case 3: case 11: ballColor = Color.red; break;
            case 4: case 12: ballColor = new Color(255, 20, 147); break;
            case 5: case 13: ballColor = Color.orange; break;
            case 6: case 14: ballColor = Color.green; break;
            case 7: case 15: ballColor = Color.red.darker(); break;
            case 8:          ballColor = Color.black; break;
            default:
                Random r = new Random();
                ballColor = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
                break;
        }
    }

    public static void setRandomLocation(Ball b1, Table table) {
        ArrayList<Ball> arrayBalls = table.getArrayBalls();
        Pockets pockets = table.getPockets();
        int borderWidth = table.playfield.x,  width = table.main.width;
        int borderHeight = table.playfield.y, height = table.main.height;
        
        int Xmax = width-borderWidth-b1.getRadius(),   Xmin = borderWidth + b1.getRadius();
        int Ymax = height-borderHeight-b1.getRadius(), Ymin = borderHeight + b1.getRadius();
        
        Random rand = new Random();
        boolean wrongLocation;
        do {
            wrongLocation = false;
            b1.setLocation(rand.nextInt(Xmax - Xmin) + Xmin, rand.nextInt(Ymax - Ymin) + Ymin);

            // Verificar si intersecta con otra bola
            for (int i = 0; i < arrayBalls.size(); i++) {
                Ball b2 = arrayBalls.get(i);
                if (b1 == b2) continue;

                if (b1.intersecs(arrayBalls.get(i))) {
                    wrongLocation = true;
                    break;
                }
            }
            // Verificar si intersecta con una tronera
            if (pockets.isPocketed(b1)) {
                wrongLocation = true;
            }
        } while (wrongLocation); // Repetir en caso de mala ubicación
    }

    public void move(float friction) {
        // * MOVER BOLA SEGÚN VELOCIDAD
        x += vel.x;
        y += vel.y;
        this.syncBounds();

        descel(friction);
    }
    private void descel(float friction) {
        // * APLICAR ROCE
        // La fuerza de la bola empieza a recibir el roce de la mesa
        if (vel.getMagnitude() < friction) { // Si la magnitud de la bola es menor al roce, significa que el roce venció el movimiento de la bola
            vel.escale(0);
        } else {                    // El roce esta afectando a la bola, pero aún no la detiene.
            Vector2D fricVec = new Vector2D(vel);
            fricVec.toUnitVector();
            fricVec.escale(friction);
            vel.subtractVector(fricVec);
        }
    }

    public void collide(Ball b2, float friction) {
    // public void collide(Ball b2) {
        Ball b1 = this;

        if (b1.vel.getMagnitude() < friction) {
            b1.vel.escale(0); // Bugfix
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

    public void checkBounces(Rectangle rectMain, Rectangle rectPlayfield) {
        int width = rectMain.width;
        int height = rectMain.height;
        int playX = rectPlayfield.x;
        int playY = rectPlayfield.y;

        // * Bordes horizontales
        // Izquierda
        if (((x - radius) < playX) && (vel.x < 0)) {
            x -= 2 * ((x - radius) - playX);
            vel.x *= -1;
        } 
        // Derecha
        else if (((x + radius) > (width - playX)) && (vel.x > 0)) {
            x -= 2 * ((x + radius) - (width - playX));
            vel.x *= -1;
        }

        // * Bordes verticales
        // Arriba
        if (((y - radius) < playY) && (vel.y < 0)) {
            y -= 2 * ((y - radius) - playY);
            vel.y *= -1;
        // Abajo
        } else if (((y + radius) > (height - playY)) && (vel.y > 0)) {
            y -= 2 * ((y + radius) - (height - playY));
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
    public int getNumber() {
        return number;
    }

    // * Paint
    public void paint(Graphics2D g2D) {
        // Dibujar bola completamente o rayada
        if (number <= 8) {
            this.fillCircle(g2D, ballColor);
        } else
        if (number <= 15) {
            this.paintStripedBall(g2D);
        } else {
            if (number % 2 == 0) 
                this.fillCircle(g2D, ballColor);
            else 
                this.paintStripedBall(g2D);
        }

        // Dibujar subcirculo blanco y numero;
        if (number != 0) {
            Circle subCircle = new Circle(round(x), round(y), round(radius * 0.55f));
            subCircle.fillCircle(g2D, Color.white);
            subCircle.drawCircle(g2D, Color.black);
            
            g2D.setColor(Color.BLACK);
            g2D.setFont(new Font("Arial", Font.BOLD, round(radius * 0.66f)));
            g2D.drawString(""+number, round(x - (g2D.getFontMetrics().stringWidth(""+number))/2), round(y + (g2D.getFontMetrics().getHeight())/3));
        }
        
        this.drawCircle(g2D, Color.BLACK);
    }
    private void paintStripedBall(Graphics2D g2D) {
        this.fillCircle(g2D, Color.white);
        float y = bounds.y + (bounds.height * 0.2f);
        float h = bounds.height * (0.62f);
        RoundRectangle2D.Float rect = new RoundRectangle2D.Float(bounds.x + 1, y, bounds.width - 1, h, radius, radius);
        g2D.setColor(ballColor);
        g2D.fill(rect);
    }
}
