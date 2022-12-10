package progra2.poolgame;

import static geometricas.Angular.*;

import java.util.ArrayList;
import javax.swing.JPanel;

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

    public PoolTable(int width, int length) {
        super(true);
        
        // * PROPIEDADES
        this.WIDTH = width;
        this.LENGTH = length;

        // * CALCULAR PROPORCIONES
        this.BORDER_WIDTH = (WIDTH - Math.round(WIDTH * 0.9f)) / 2;
        this.BORDER_LENGHT = (LENGTH - Math.round(LENGTH * 0.8f)) / 2;

        this.BALLS_RADIUS = Math.round((BORDER_WIDTH * 0.03f) / 2);

        System.out.println("ANCHO Y LARGO: " + WIDTH + "x" + LENGTH);
        System.out.println("ANCHO Y LARGO BORDES: " + BORDER_WIDTH + "x" + BORDER_LENGHT);
        System.out.println("RADIO BOLAS: " + BALLS_RADIUS);

        // * INICIALIZAR
        initClasses();

        // * CONFIGURAR JPANEL
        this.setPreferredSize(new Dimension(WIDTH, LENGTH));
        this.setBackground(Color.YELLOW);
        this.setOpaque(false);
    }

    public void sendMousePosition(Point position) {
        mousePosition = position;
    }

    public void updateGame() {
        // * Si alguna bola esta en movimiento, entonces...
        if (hasMovement()) {
            for (int i = 0; i < balls.size(); i++) {
                PoolBall currentBall = balls.get(i);
                currentBall.move();
                currentBall.checkBounces(this);

                for (int j = 0; j < balls.size(); j++) {
                    if (i == j) continue;

                    PoolBall nextBall = balls.get(j);
                    if (currentBall.isCollide(nextBall)) {
                        System.out.println("Choco bola " + i + " con bola " + j);
                        currentBall.collide(nextBall);
                    }
                }
            }
        }
	}

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        paintTable(g2D); // * Mesa
        for (PoolBall bola : balls) bola.paint(g2D); // * Bolas
        
        // * Si el mouse esta cerca de la bola blanca
        if (!this.hasMovement() && blanca != null) {
            taco.sendMousePos(mousePosition);
            taco.paint(g2D);
            
            // * Generar circulo que se mueve según mouse alrededor de bola blanca
            // Calcular angulo que se forma entre posición de mouse y el centro de la bola blanca
            float angle = anguloPI(blanca.getLocation(), mousePosition.getLocation());

            // * Dibujar linea de trayectoria
            // Calcular angulo opuesto para la linea que dibuja la dirección de la bola (sumarle 180° (1pi))
            float opositeAngle = angle + 1f;

            Point pInicioTrayectoria = generaPunto(blanca.getLocation(), blanca.getRadius() + 4, opositeAngle);
            Point pFinalTrayectoria = generaPunto(pInicioTrayectoria, 600, opositeAngle);
            //TODO Cambiar el 600 por la distancia con el objeto con el que habrá colisión
            
            float[] patron = {10f, 4f};
            Stroke stroke = new BasicStroke(4f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, patron, 0.0f);
            Stroke defaultStroke = g2D.getStroke(); // Guardamos la borde predeterminada
            
            g2D.setStroke(stroke); // Utilizar borde con patron de la linea de trayectoria
            g2D.setColor(Color.WHITE);
            g2D.drawLine(pInicioTrayectoria.x, pInicioTrayectoria.y, pFinalTrayectoria.x, pFinalTrayectoria.y);
            g2D.setStroke(defaultStroke); // Regresamos al borde predeterminado

            // * Dibujar circunferencia formada por taco
            int radioTotal = blanca.getRadius() + CueStick.DISTANCE + CueStick.LENGTH;
            g2D.setColor(Color.YELLOW);
            g2D.drawOval(blanca.getLocation().x - radioTotal, blanca.getLocation().y - radioTotal, radioTotal*2, radioTotal*2);
        }
    }

    // * Getters
    public PoolBall getBlanca() {
        return blanca;
    }
    public boolean hasMovement() {
        for (PoolBall ball : balls) {
            if (ball.isMoving()) return true;
        }
        return false;
    }

    //! Subfunciones
    private void initClasses() {
        mousePosition = new Point();

        balls = new ArrayList<PoolBall>();
        
        int radius = Math.round((WIDTH * 0.025f) / 2);
        blanca = new PoolBall(WIDTH/4, LENGTH/2, Color.white, radius);
        
        taco = new CueStick(blanca);
        
        balls.add(blanca);
        
        balls.add(new PoolBall(WIDTH * 3/4, LENGTH/2, Color.yellow, radius));

        balls.add(new PoolBall((WIDTH * 3/4) + (radius*2) - 2, (LENGTH/2) - (radius + 2), Color.black, radius)); 
        balls.add(new PoolBall((WIDTH * 3/4) + (radius*2) - 2, (LENGTH/2) + (radius + 2), Color.gray, radius));

        balls.add(new PoolBall(((WIDTH * 3/4) + (radius*4) - 4), (LENGTH/2) - ((radius*2) + 2), Color.red, radius));
        balls.add(new PoolBall(((WIDTH * 3/4) + (radius*4) - 4), LENGTH/2, Color.orange, radius));
        balls.add(new PoolBall(((WIDTH * 3/4) + (radius*4) - 4), (LENGTH/2) + ((radius*2) + 2), Color.blue, radius));

        //TODO Usar "fors" para crear automáticamente triangulo al iniciar según num de bolas (crear clase de para crear colores de bolas de pool randoms?)
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
        
        //TODO Dibujar diamantes de los bordes
    }
}
