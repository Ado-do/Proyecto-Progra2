package progra2.poolgame;

import static java.lang.Math.round;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;

import geometricas.PVector;

public class Bola {
    //* Constantes
    public static final int RADIUS = 15;
    public static final int MASS = 150;
    public static final float ROCE = 0.25f;

    //* Propiedades bola
    private float x, y;
    private Color color;
    private PVector speed;

    private boolean moving;
    
    public Bola(Color color, int posX, int posY) {
        super();
        this.color = color;
        this.x = posX;
        this.y = posY;

        this.speed = new PVector(0, 0);
        moving = false;
    }
    public Bola(Color color, Point p) {
        super();
        this.color = color;
        this.x = p.x;
        this.y = p.y;
        
        this.speed = new PVector(0, 0);
        moving = false;
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

            moving = false;
        } else { // El roce esta afectando a la bola, pero aun no la detiene.
            speed.normalizar();
            speed.escalar(mag - ROCE);
            
            // System.out.println("x: " + x + "y: " + y);
            // System.out.println("Mag: " + mag);
        }

        // * Revisar colisiones
        checkWallCollition();
    }

    private void checkWallCollition() {
        //* Bordes horizontales
        // Izquierda
        if ((x - RADIUS) < Mesa.BORDE && speed.x < 0) {  
            speed.x *= -1;
            x -= 2 * ((x - RADIUS) - Mesa.BORDE);
        } 
        // Derecha
        else if (((x + RADIUS) > (1280 - Mesa.BORDE)) && (speed.x > 0)) {
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

    public boolean hayColision(Bola bola) { // Revisamos si hay colision con la bola
        PVector choko = new PVector(bola.x-x, bola.y-y);
        boolean colision = (choko.getMagnitud() < RADIUS * 2); // si la magnitud del vector es menor que el diametro de la bola, quiere decir
        return colision; // que estan una encima de la otra
    }
    public boolean hayColision(Tronera tronera) { // Revisamos si hay colision con la bola
        PVector choko = new PVector(tronera.x-x, tronera.y-y);
        boolean colision = (choko.getMagnitud() < RADIUS*2); // si la magnitud del vector es menor que el diametro de la bola, quiere decir
        return colision; // que estan una encima de la otra
    }
    

    //! PRUEBA DE CODIGO
    // public void descolisonarBolas(Bola bola) {
    //     PVector puntomedio = new PVector((bola.x+x)/2f, (bola.y+y)/2);
    //     PVector normal = new PVector(bola.x - x, bola.y-y);
    //     normal.normalizar();

    //     bola.x =  puntomedio.x + normal.x * RADIUS;
    //     bola.y =  puntomedio.y + normal.y * RADIUS;

    //     this.x =  puntomedio.x - normal.x * RADIUS;
    //     this.y =  puntomedio.y - normal.y * RADIUS;
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
    public void setSpeed(PVector speed) {
        this.speed = speed;
        moving = true;
    }

    //* Getters
    public boolean isMoving() {
        return moving;
    }
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
