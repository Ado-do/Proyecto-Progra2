package progra2.poolgame;

import static java.lang.Math.round;

import java.util.ArrayList;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.Rectangle;

public class Table extends JPanel {
    public final Rectangle rectMain;
    public final Rectangle rectPlayfield;
    
    private ArrayList<Ball> arrayBalls;
    private Ball blanca;
    private int contBalls = 0;
    private ArrayList<Pocket> arrayPockets;
    private Cue taco;

    private Point mousePosition;
    private boolean blancaPocketed;
    
    public Table(int width, int height) {
        super(true);
        
        // * PROPIEDADES
        this.rectMain = new Rectangle(width, height);
        Point pPlayfiled = new Point((width - round(width * 0.9f))/2, (height - round(height * 0.8f))/2);
        Dimension dim = new Dimension(rectMain.width-(pPlayfiled.x * 2), rectMain.height-(pPlayfiled.y * 2));
        this.rectPlayfield = new Rectangle(pPlayfiled, dim);

        // * INICIALIZAR
        initClasses();

        // * CONFIGURAR JPANEL
        this.setPreferredSize(new Dimension(rectMain.width, rectMain.height));
    }

    public void sendMouseInfo(MouseEvent e) {
        mousePosition = e.getPoint();
    }

    public void updateGame() {
        taco.sendMousePos(mousePosition);

        // * Si alguna bola esta en movimiento, entonces...
        if (hasMovement()) {
            // * Mover y revisar colisiones
            for (int i = 0; i < arrayBalls.size(); i++) {
                Ball currentBall = arrayBalls.get(i);
                currentBall.move();

                // Revisar si entro en tronera
                for (Pocket pocket : arrayPockets) {
                    if (pocket.isPocketed(currentBall)) {
                        if (currentBall == blanca) blancaPocketed = true;
                        pocket.receive(arrayBalls, currentBall);
                    }
                }

                // Revisar rebotes con paredes
                currentBall.checkBounces(this);
                
                // Revisar colisiones
                for (int j = 0; j < arrayBalls.size(); j++) {
                    if (i == j) continue;
                    Ball nextBall = arrayBalls.get(j);
                    if (currentBall.intersecs(nextBall)) {
                        currentBall.collide(nextBall);
                    }
                }
            }
        } else if (blancaPocketed) {
            Ball.setRandomLocation(blanca, this);
            arrayBalls.add(0, blanca);
            taco.sendMousePos(mousePosition);
            blancaPocketed = false;
        }
	}

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        this.paintTable(g2D); // * Mesa
        
        for (Ball bola : arrayBalls) bola.paint(g2D); // * Bolas
        
        if (!this.hasMovement()) {
            taco.paint(g2D); // * Taco
        }
    }

    public boolean hasMovement() {
        for (Ball ball : arrayBalls) {
            if (ball.isMoving()) return true;
        }
        return false;
    }

    // * Getters
    public Ball getBlanca() {
        return blanca;
    }
    public Cue getCue() {
        return taco;
    }
    public ArrayList<Ball> getArrayBalls() {
        return arrayBalls;
    }
    public ArrayList<Pocket> getArrayPockets() {
        return arrayPockets;
    }

    //! Subfunciones
    private void initClasses() {
        // * Inicializar bolas
        // Radio de bolas
        int ballRadius = round((rectMain.width * 0.025f) / 2);
        System.out.println("RADIO BOLAS: " + ballRadius);

        arrayBalls = new ArrayList<Ball>();
        blanca = new Ball(rectMain.width/4, rectMain.height/2, Color.white, ballRadius, contBalls++);
        
        //TODO Implementar las 16 bolas de pool
        arrayBalls.add(blanca);
        
        arrayBalls.add(new Ball(rectMain.width * 3/4, rectMain.height/2, Color.yellow, ballRadius, contBalls++));
        
        arrayBalls.add(new Ball((rectMain.width * 3/4) + (ballRadius*2) - 2, (rectMain.height/2) - (ballRadius + 2), Color.black, ballRadius, contBalls++)); 
        arrayBalls.add(new Ball((rectMain.width * 3/4) + (ballRadius*2) - 2, (rectMain.height/2) + (ballRadius + 2), Color.gray, ballRadius, contBalls++));
        
        arrayBalls.add(new Ball(((rectMain.width * 3/4) + (ballRadius*4) - 4), (rectMain.height/2) - ((ballRadius*2) + 2), Color.red, ballRadius, contBalls++));
        arrayBalls.add(new Ball(((rectMain.width * 3/4) + (ballRadius*4) - 4), rectMain.height/2, Color.orange, ballRadius, contBalls++));
        arrayBalls.add(new Ball(((rectMain.width * 3/4) + (ballRadius*4) - 4), (rectMain.height/2) + ((ballRadius*2) + 2), Color.blue, ballRadius, contBalls++));
        //TODO Usar "fors" para crear automáticamente triangulo al iniciar según num de bolas (crear clase de para crear colores de bolas de pool random¿)
        
        // * Taco
        taco = new Cue(this, arrayBalls, blanca);

        // * Troneras
        arrayPockets = new ArrayList<Pocket>();
        int pocketRadius = round((rectMain.width * 0.06f) / 2);
        int offsetX = (rectMain.width/2) - rectPlayfield.x;
        float correctionMid = 0.2f;

        for (int i = 0; i < 3; i++) {
            int posY = (i != 1) ? (rectPlayfield.y) : (rectPlayfield.y - round(pocketRadius*correctionMid));
            arrayPockets.add(new Pocket(rectPlayfield.x + (offsetX*i), posY, pocketRadius, this));
        }
        for (int i = 0; i < 3; i++) {
            int posY = (i != 1) ? (rectMain.height - rectPlayfield.y) : ((rectMain.height - rectPlayfield.y) + round(pocketRadius*correctionMid));
            arrayPockets.add(new Pocket(rectPlayfield.x + (offsetX*i), posY, pocketRadius, this));
        }

        // * Posicion inicial de taco
        Point defaultPosition = blanca.getLocation();
        defaultPosition.x -= blanca.getRadius();
        mousePosition = new Point(defaultPosition);
    }
    private void paintTable(Graphics2D g2D) {
        // * Borde
        g2D.setColor(new Color(184, 115, 51));
        g2D.fill(rectMain);
        
        int subBorder = round(((rectPlayfield.x * rectPlayfield.y) * 0.003f));
        Point pSubBorder = new Point(rectPlayfield.x-subBorder, rectPlayfield.y-subBorder);
        Dimension dimsSubBorder = new Dimension((rectMain.width - (rectPlayfield.x*2)) + subBorder*2, (rectMain.height - (rectPlayfield.y*2)) + subBorder*2);

        Rectangle rectSubBorder = new Rectangle(pSubBorder, dimsSubBorder);
        g2D.setColor(new Color(153, 102, 0));
        g2D.fill(rectSubBorder);
        // g2D.fillRect(playfieldRect.width-subBorder, playfieldRect.height-subBorder, (mainRect.width - (playfieldRect.width*2)) + subBorder*2, (mainRect.height - (playfieldRect.height*2)) + subBorder*2);

        // * Mesa
        g2D.setColor(new Color(0, 200, 0));
        g2D.fill(rectPlayfield);

        //TODO Dibujar diamantes de los bordes

        // * Troneras
        for (Pocket pocket : arrayPockets) {
            pocket.paint(g2D);
        }
    }
}
