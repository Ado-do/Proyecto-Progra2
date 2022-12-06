package progra2.poolgame;

import static geometricas.Angular.*;

import java.util.ArrayList;
import javax.swing.JPanel;

import geometricas.Vector2D;

import java.awt.*;

public class PoolTable extends JPanel {
    public final int WIDTH;
    public final int LENGTH;
    public final int BORDER_WIDTH;
    public final int BORDER_LENGHT;
    public final int BALLS_RADIUS;
    
    private ArrayList<PoolBall> balls;
    private PoolBall blanca;
    private CueStick taco;
    
    private Point mousePosition;

    public boolean movement;

    public PoolTable(int width, int length) {
        super(true);
        
        //* PROPIEDADES
        this.WIDTH = width;
        this.LENGTH = length;

        //* CALCULAR PROPORCIONES
        this.BORDER_WIDTH = (WIDTH - Math.round(WIDTH * 0.9f)) / 2;
        this.BORDER_LENGHT = (LENGTH - Math.round(LENGTH * 0.8f)) / 2;

        this.BALLS_RADIUS = Math.round((WIDTH * 0.03f) / 2);

        System.out.println("ANCHO Y LARGO: " + WIDTH + "x" + LENGTH);
        System.out.println("ANCHO Y LARGO BORDES: " + BORDER_WIDTH + "x" + BORDER_LENGHT);
        System.out.println("RADIO BOLAS: " + BALLS_RADIUS);

        //* BOOLEANOS
        movement = false;

        //* INICIALIZAR
        initClasses();

        //* CONFIGURAR JPANEL
        this.setPreferredSize(new Dimension(WIDTH, LENGTH));
        this.setBackground(Color.YELLOW);
        this.setOpaque(false);
    }

    public void sendMousePosition(Point position) {
        mousePosition = position;
    }

    public void updateGame() {
        movement = checkMovement();

        if (movement) {
            for (int i = 0; i < balls.size(); i++) {
                PoolBall currentBall = balls.get(i);
                currentBall.move();
                checkWallCollition(currentBall);

                for (int j = 0; j < balls.size(); j++) {
                    if (i == j) continue;

                    PoolBall nextBall = balls.get(j);
                    if (currentBall.collition(nextBall)) {
                        // System.out.println("Choco bola " + i + " con bola " + j);
                        // bolaActual.descolisonarBolas(nextBall);
                        // bolaActual.colisionar(nextBall);
                    }
                }
            }
        }
	}

    private boolean checkMovement() {
        for (PoolBall ball : balls) {
            if (ball.isMoving()) return true;
        }
        return false;
    }

    private void checkWallCollition(PoolBall b) {
        float radius = b.getRadius();
        Vector2D speed = b.getSpeed();

        //* Bordes horizontales
        // Izquierda
        if ((b.x - radius) < BORDER_WIDTH && speed.x < 0) { 
            speed.x *= -1;
            b.x -= 2 * ((b.x - radius) - BORDER_WIDTH);
        } 
        // Derecha
        else if (((b.x + radius) > (WIDTH - BORDER_WIDTH)) && (speed.x > 0)) {
            speed.x *= -1;
            b.x -= 2 * ((b.x + radius) - (WIDTH - BORDER_WIDTH));
        }

        //* Bordes verticales
        // Arriba
        if (((b.y - radius) < BORDER_LENGHT) && (speed.y < 0)) {
            speed.y *= -1;
            b.y -= 2 * ((b.y - radius) - BORDER_LENGHT);
        // Abajo
        } else if (((b.y + radius) > (LENGTH - BORDER_LENGHT)) && (speed.y > 0)) {
            speed.y *= -1;
            b.y -= 2 * ((b.y + radius) - (LENGTH - BORDER_LENGHT));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        //! Dibujos
        paintTable(g2D); // * Mesa
        for (PoolBall bola : balls) bola.paint(g2D); // * Bolas
        
        // Si el mouse esta cerca de la bola blanca
        if (distEntre2Puntos(mousePosition.getLocation(), blanca.getLocation()) <= (blanca.getRadius() + CueStick.DISTANCE + CueStick.LENGTH) && !movement) {
            // Punto central bola blanca
            // g2D.setColor(Color.RED);
            // g2D.fillOval(blanca.getLocation().x - 2, blanca.getLocation().y - 2, 4, 4);

            //! Dibujar taco
            taco.sendMousePos(mousePosition);
            taco.paint(g2D);
            
            // Generar circulo que se mueve según mouse alrededor de bola blanca
            float angle = anguloPI(blanca.getLocation(), mousePosition.getLocation()); // * Calcular angulo que se forma entre posición de mouse y el centro de la bola blanca

            // Dibujar linea de trayectoria
            float opositeAngle = angle + 1f; // * Calcular angulo opuesto para la linea que dibuja la dirección de la bola (sumarle 180° (1pi))

            Point pInicioTrayectoria = generaPunto(blanca.getLocation(), blanca.getRadius() + 4, opositeAngle);
            Point pFinalTrayectoria = generaPunto(pInicioTrayectoria, 600, opositeAngle);
            //TODO Cambiar el 600 por la distancia con el objeto con el que habrá colisión
            
            float[] patron = {10f, 4f};
            Stroke stroke = new BasicStroke(4f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, patron, 0.0f);
            Stroke defaultStroke = g2D.getStroke(); //Guardamos la borde predeterminada
            
            g2D.setStroke(stroke);
            g2D.setColor(Color.WHITE);
            g2D.drawLine(pInicioTrayectoria.x, pInicioTrayectoria.y, pFinalTrayectoria.x, pFinalTrayectoria.y);
            g2D.setStroke(defaultStroke); // Regresamos al borde normal

            //! Dibujar circunferencia formada por taco
            int radioTotal = blanca.getRadius() + CueStick.DISTANCE + CueStick.LENGTH;

            g2D.setColor(Color.YELLOW);
            g2D.drawOval(blanca.getLocation().x - radioTotal, blanca.getLocation().y - radioTotal, radioTotal*2, radioTotal*2);
        }
    }

    //* Getters
    public PoolBall getBlanca() {
        return blanca;
    }
    public boolean hasMovement() {
        return movement;
    }

    //! Subfunciones
    private void initClasses() {
        mousePosition = new Point();

        balls = new ArrayList<PoolBall>();
        
        int radius = Math.round((WIDTH * 0.03f) / 2);
        blanca = new PoolBall(WIDTH/4, LENGTH/2, Color.white, radius);
        
        taco = new CueStick(blanca);
        
        balls.add(blanca);
        
        balls.add(new PoolBall(WIDTH * 3/4, LENGTH/2, Color.yellow, radius));

        balls.add(new PoolBall((WIDTH * 3/4) + (radius*2) - 2, (LENGTH/2) - (radius + 2), Color.black, radius)); 
        balls.add(new PoolBall((WIDTH * 3/4) + (radius*2) - 2, (LENGTH/2) + (radius + 2), Color.gray, radius));

        balls.add(new PoolBall(((WIDTH * 3/4) + (radius*4) - 4), (LENGTH/2) - ((radius*2) + 2), Color.red, radius));
        balls.add(new PoolBall(((WIDTH * 3/4) + (radius*4) - 4), LENGTH/2, Color.orange, radius));
        balls.add(new PoolBall(((WIDTH * 3/4) + (radius*4) - 4), (LENGTH/2) + ((radius*2) + 2), Color.blue, radius));

    }
    private void paintTable(Graphics g) {
        // * Borde
        g.setColor(new Color(184, 115, 51));
        g.fillRect(0, 0, WIDTH, LENGTH);
        
        int subBorder = Math.round(((BORDER_WIDTH * BORDER_LENGHT) * 0.003f));
        g.setColor(new Color(153, 102, 0));
        g.fillRect(BORDER_WIDTH-subBorder, BORDER_LENGHT-subBorder, (WIDTH - (BORDER_WIDTH*2)) + subBorder*2, (LENGTH - (BORDER_LENGHT*2)) + subBorder*2);

        // * Mesa
        g.setColor(Color.green);
        g.setColor(new Color(0, 200, 0));
        g.fillRect(BORDER_WIDTH, BORDER_LENGHT, WIDTH-(BORDER_WIDTH * 2), LENGTH-(BORDER_LENGHT * 2));
    }
}
