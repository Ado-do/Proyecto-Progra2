package progra2.poolgame;

import static java.lang.Math.round;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

import geometricas.Circle;
import geometricas.Vector2D;

public class PoolBall extends Circle {
    //* Constantes
    public static final int MASS = 160;
    public static final float ROCE = 0.75f;
    // public static final int RADIUS = 15;

    //* Propiedades bola
    private Color color;
    private Vector2D speed;

    private boolean moving;
    
    public PoolBall(int posX, int posY, Color color, int radius) {
        super(posX, posY, radius);
        this.color = color;

        this.speed = new Vector2D();
        moving = false;
    }

    public void move() {
        //* MOVER BOLA SEGÚN VELOCIDAD
        x += speed.x;
        y += speed.y;

        //* APLICAR ROCE
        // La fuerza de la bola empieza a recibir el roce de la mesa
        float speedMag = speed.getMagnitude();
        if (speedMag <= ROCE) { // Si la magnitud de la bola es menor al roce, significa que el roce venció el movimiento de la bola
            speed.escale(0);
            moving = false;
        } else {                // El roce esta afectando a la bola, pero aún no la detiene.
            speed.toUnitVector();
            speed.escale(speedMag - ROCE);
            
            // System.out.println("x: " + x + "y: " + y);
            // System.out.println("Mag: " + mag);
        }
    }

    public boolean collition(PoolBall bola) { // Revisamos si hay colisión con la bola
        Vector2D choko = new Vector2D(bola.x-x, bola.y-y);
        boolean colision = (choko.getMagnitude() < radius * 2); // si la magnitud del vector es menor que el diámetro de la bola, quiere decir
        return colision; // que están una encima de la otra
    }
    

    // //! PRUEBA DE CODIGO
    // public void descolisonarBolas(Bola bola) {
    //     PVector puntomedio = new PVector((bola.x+x)/2f, (bola.y+y)/2);
    //     PVector normal = new PVector(bola.x - x, bola.y-y);
    //     normal.normalizar();

    //     bola.x =  puntomedio.x + normal.x * radius;
    //     bola.y =  puntomedio.y + normal.y * radius;

    //     this.x =  puntomedio.x - normal.x * radius;
    //     this.y =  puntomedio.y - normal.y * radius;
    // }

    // public void colisionar(Bola b) {
    //     PVector normal = new PVector(b.x - x, b.y - y);
    //     normal.normalizar();
        
    //     PVector tangente = new PVector(normal.y * -1f, normal.x);
        
    //     float b1escalarNormal = PVector.dot(normal, this.speed);
    //     float b2escalarNormal = PVector.dot(normal, b.speed);
        
    //     float b1escalarTangente = PVector.dot(tangente, this.speed);
    //     float b2escalarTangente = PVector.dot(tangente, b.speed);
        
    //     PVector vectorB1_normal = new PVector(normal.x, normal.y);
    //     vectorB1_normal.escalar(b2escalarNormal); //no, no esta invertido, la formula es asi
        
    //     PVector vectorB2_normal = new PVector(normal.x, normal.y);
    //     vectorB2_normal.escalar(b1escalarNormal); //no, no esta invertido, la formula es asi
        
    //     PVector vectorB1_tan = new PVector(tangente.x, tangente.y);
    //     vectorB1_tan.escalar(b1escalarTangente);
        
    //     PVector vectorB2_tan = new PVector(tangente.x, tangente.y);
    //     vectorB2_tan.escalar(b2escalarTangente);
        
    //     this.setSpeed(new PVector(vectorB1_tan.x + vectorB1_normal.x, vectorB1_tan.y + vectorB1_normal.y));
    //     b.setSpeed(new PVector(vectorB2_tan.x + vectorB2_normal.x, vectorB2_tan.y + vectorB2_normal.y));
    // }

    public void bounce() {

    }

    // * Setters
    public void setSpeedVector(Vector2D speed) {
        this.speed = speed;
        moving = true;
    }

    //* Getters
    public boolean isMoving() {
        return moving;
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
        g.fillOval(round(x - radius), round(y - radius), radius*2, radius*2);
        g.setColor(Color.BLACK);
        g.drawOval(round(x - radius), round(y - radius), radius*2, radius*2);
    }
}
