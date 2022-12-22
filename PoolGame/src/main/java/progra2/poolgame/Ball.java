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

/**
 * Enumeración que define los tipos de bolas que hay en el juego
 * Solid: Bolas de color sólido
 * Stripe: Bolas de color rayado
 */
enum BallType { SOLID, STRIPE }

/**
 * Clase que crea las bolas puestas en la mesa
 *
 * @author Alonso Bustos
 */
public class Ball extends Circle {
    private final int number;
    private final Color ballColor;
    private final BallType ballType;
    private Vector2D vel;

    /**
    * Constructor que define la posición y el numero que tiene la bola
    *
    * @param posX   Parámetro que define la posición X de la bola en la mesa
    * @param posY   Parámetro que define la posición y de la bola en la mesa
    * @param radius Radio que tendrá la bola
    * @param number Numero que tendrá la bola 
    */
    public Ball(int posX, int posY, int radius, int number) {
        super(posX, posY, radius);
        this.number = number;
        this.vel = new Vector2D();

        // Asignar color y tipo de bola
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

        // Tipo de bola
        if (number <= 8) {
            ballType = BallType.SOLID;
        } else if (number <= 15) {
            ballType = BallType.STRIPE;
        } else {
            if (number % 2 == 0) 
                ballType = BallType.SOLID;
            else 
                ballType = BallType.STRIPE;
        }
    }

    /**
    * Método estático que posiciona de manera aleatoria a la bola en la mesa.
    *
    * @param b1 La bola que será puesta en un lugar aleatorio.
    */
    public static void setRandomLocation(Ball b1) {
        ArrayList<Ball> arrayBalls = PoolGame.table.getArrayBalls();
        Pockets pockets = PoolGame.table.getPockets();
        int borderWidth  = PoolGame.table.playfield.x, width  = PoolGame.table.main.width;
        int borderHeight = PoolGame.table.playfield.y, height = PoolGame.table.main.height;
        
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

    /**
    * Método que le da movimiento a la bola para que se mueva en la mesa.
    *
    * @param friction la fricción que tendrá la bola
    */
    public void move(float friction) {
        // * MOVER BOLA SEGÚN VELOCIDAD
        x += vel.x;
        y += vel.y;
        this.syncBounds();

        descel(friction);
    }

    /**
    * Método que desacelera la bola
    *
    * @param friction La fricción que se le aplicará    
    */
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

    /**
    * Método que colisiona la bola con la otra y luego las separa.
    *
    * @param b2 La segunda bola con la que se comprobara la colisión.
    * @param friction la fricción que se aplicará.
    */
    public void collide(Ball b2, float friction) {
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

    /**
    * Método que revisa si la bola choca con los bordes y si es asi, mueve a la bola.
    *
    * @param rectMain el contorno de la mesa.
    * @param RectPlayfield el contorno del area de juego.
    */
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

    /**
    * Método que analiza si la bola intersecta con otra bola
    *
    * @param otra la clase Circle que se le analizará
    * @return true si intersecta, false si no.
    */
    public boolean intersecs(Circle otra) {
        return (Angular.distEntre2Puntos(this.getLocation(), otra.getLocation()) <= diameter);
    }

    /**
    * Método que analiza si la bola esta moviéndose.
    *
    * @return si no lo está, true, si sigue moviéndose false.
    */
    public boolean isMoving() {
        return (vel.x != 0 || vel.y != 0);
    }

    /**
    * Método setter para asignar la velocidad a la bola
    *
    * @param v el vector de la magnitud.
    */
    public void setVel(Vector2D v) {
        vel.setVector(v);
    }

    /**
    * Método getter para recibir la posicion X de la bola
    *
    * @return la posicion x.     
    */
    public float getX() {
        return x;
    }

    /**
    * Método getter para recibir la posicion Y de la bola
    *
    * @return la posicion y.
    */
    public float getY() {
        return y;
    }

    /**
    * Método getter para recibir la velocidad de la bola
    *
    * @return la velocidad.
    */
    public Vector2D getVel() {
        return vel;
    }

    /**
    * Método getter para recibir el numero de la bola
    *
    * @return el numero de la bola.
    */
    public int getNumber() {
        return number;
    }

    /**
    * Método getter para recibir el tipo de la bola
    *
    * @return el tipo de la bola.
    */
    public BallType getBallType() {
        return ballType;
    }

    /**
     * Método que pinta la bola
     * @param g2D el objeto Graphics2D para dibujar
     */
    public void paint(Graphics2D g2D) {
        // Dibujar bola completamente o rayada
        switch (ballType) {
            case SOLID  -> paintSolidBall(g2D);
            case STRIPE -> paintStripedBall(g2D);
        }

        // Dibujar subcirculo blanco y numero;
        if (number != 0) {
            paintNumber(g2D);
        }
        
        this.drawCircle(g2D, Color.BLACK);
    }

    private void paintNumber(Graphics2D g2D) {
        Circle subCircle = new Circle(round(x), round(y), round(radius * 0.55f));
        subCircle.fillCircle(g2D, Color.white);
        subCircle.drawCircle(g2D, Color.black);
        
        g2D.setColor(Color.BLACK);
        g2D.setFont(new Font("Arial", Font.BOLD, round(radius * 0.66f)));
        g2D.drawString(""+number, round(x - (g2D.getFontMetrics().stringWidth(""+number))/2), round(y + (g2D.getFontMetrics().getHeight())/3));
    }

    private void paintSolidBall(Graphics2D g2D) {
        this.fillCircle(g2D, ballColor);
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
