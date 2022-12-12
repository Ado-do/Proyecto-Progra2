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
import java.awt.Polygon;

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
        g2D.setColor(Color.black);
        g2D.draw(rectMain);
        
        int escaleSubBorder = round(((rectPlayfield.x * rectPlayfield.y) * 0.003f));
        Point pSubBorder = new Point(rectPlayfield.x-escaleSubBorder, rectPlayfield.y-escaleSubBorder);
        Dimension dimsSubBorder = new Dimension((rectMain.width - (rectPlayfield.x*2)) + escaleSubBorder*2, (rectMain.height - (rectPlayfield.y*2)) + escaleSubBorder*2);
        Rectangle rectSubBorder = new Rectangle(pSubBorder, dimsSubBorder);
        g2D.setColor(new Color(153, 102, 0));
        g2D.fill(rectSubBorder);
        g2D.setColor(Color.black);
        g2D.draw(rectSubBorder);

        // * Mesa
        g2D.setColor(new Color(0, 200, 0));
        g2D.fill(rectPlayfield);

        // * Diamantes
        Polygon diamond = new Polygon();
        int diamX = rectPlayfield.x, diamY = rectSubBorder.y/2;
        int diamWidth = rectPlayfield.y/8;
        diamond.addPoint(diamX, diamY - diamWidth); //Norte
        diamond.addPoint(diamX - diamWidth, diamY); //Oeste
        diamond.addPoint(diamX, diamY + diamWidth); //Este
        diamond.addPoint(diamX + diamWidth, diamY); //Sur

        // Vertical
        int deltaX1 = rectPlayfield.width/8;
        int deltaY1 = rectSubBorder.height + rectSubBorder.y;
        for (int i = 0; i < 8; i++) {
            diamond.translate(deltaX1, 0);
            if (i != 3 && i != 7) {
                g2D.setColor(Color.yellow);
                g2D.fill(diamond);
                g2D.setColor(Color.black);
                g2D.draw(diamond);
            }
            diamond.translate(0, deltaY1);
            if (i != 3 && i != 7) {
                g2D.setColor(Color.yellow);
                g2D.fill(diamond);
                g2D.setColor(Color.black);
                g2D.draw(diamond);
            }
            deltaY1 *= -1;
        }

        // Horizontal
        diamond.translate((rectPlayfield.x-rectSubBorder.x)+(rectSubBorder.x/2), (rectSubBorder.y/2)+(rectPlayfield.y-rectSubBorder.y));
        int deltaY2 = rectPlayfield.height/4;
        int deltaX2 = rectSubBorder.width + rectSubBorder.x;
        for (int i = 0; i < 3; i++) {
            diamond.translate(0, deltaY2);
            if (i != 3) {
                g2D.setColor(Color.yellow);
                g2D.fill(diamond);
                g2D.setColor(Color.black);
                g2D.draw(diamond);
            }
            deltaX2 *= -1;
            diamond.translate(deltaX2, 0);
            if (i != 3) {
                g2D.setColor(Color.yellow);
                g2D.fill(diamond);
                g2D.setColor(Color.black);
                g2D.draw(diamond);
            }
        }

        // * Troneras
        for (Pocket pocket : arrayPockets) {
            pocket.paint(g2D);
        }
    }
}
