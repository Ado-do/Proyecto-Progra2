package progra2.poolgame;

import static java.lang.Math.round;

import java.util.ArrayList;
import javax.swing.JPanel;

import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Table extends JPanel {
    public final int WIDTH;
    public final int LENGTH;
    public final int BORDER_WIDTH;
    public final int BORDER_LENGHT;
    
    private ArrayList<Ball> ballsArray;
    private Ball blanca;
    private int contBalls = 0;
    private ArrayList<Pocket> pocketsArray;
    private Cue taco;
    
    public Table(int width, int length) {
        super(true);
        
        // * PROPIEDADES
        this.WIDTH = width;
        this.LENGTH = length;

        // * CALCULAR PROPORCIONES
        this.BORDER_WIDTH = (WIDTH - round(WIDTH * 0.9f)) / 2;
        this.BORDER_LENGHT = (LENGTH - round(LENGTH * 0.8f)) / 2;
        System.out.println("ANCHO Y LARGO: " + WIDTH + "x" + LENGTH);
        System.out.println("ANCHO Y LARGO BORDES: " + BORDER_WIDTH + "x" + BORDER_LENGHT);

        // * INICIALIZAR
        initClasses();

        // * CONFIGURAR JPANEL
        this.setPreferredSize(new Dimension(WIDTH, LENGTH));
    }

    public void sendMouseInfo(MouseEvent e) {
        taco.sendMousePos(e.getPoint());
    }

    public void updateGame() {
        // * Si alguna bola esta en movimiento, entonces...
        if (hasMovement()) {
            // * Mover y revisar colisiones
            for (int i = 0; i < ballsArray.size(); i++) {
                Ball currentBall = ballsArray.get(i);
                currentBall.move();
                for (Pocket pocket : pocketsArray) {
                    if (pocket.isPocketed(currentBall)) {
                        pocket.receive(ballsArray, currentBall);
                    }
                }
                currentBall.checkBounces(this);
                
                for (int j = 0; j < ballsArray.size(); j++) {
                    if (i == j) continue;
                    Ball nextBall = ballsArray.get(j);
                    if (currentBall.isCollide(nextBall)) {
                        currentBall.collide(nextBall);
                        // System.out.println("Choco bola " + i + " con bola " + j);
                    }
                }
            }
        }
	}

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        this.paintTable(g2D); // * Mesa
        
        for (Ball bola : ballsArray) bola.paint(g2D); // * Bolas
        
        if (!this.hasMovement()) {
            taco.paint(g2D); // * Taco
        }
    }

    public boolean hasMovement() {
        for (Ball ball : ballsArray) {
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

    //! Subfunciones
    private void initClasses() {
        // * Radio de bolas
        int ballRadius = round((WIDTH * 0.025f) / 2);
        System.out.println("RADIO BOLAS: " + ballRadius);

        // * Inicializar bolas
        ballsArray = new ArrayList<Ball>();
        blanca = new Ball(WIDTH/4, LENGTH/2, Color.white, ballRadius, contBalls++);
        
        //TODO Implementar las 16 bolas de pool
        ballsArray.add(blanca);
        
        ballsArray.add(new Ball(WIDTH * 3/4, LENGTH/2, Color.yellow, ballRadius, contBalls++));
        
        ballsArray.add(new Ball((WIDTH * 3/4) + (ballRadius*2) - 2, (LENGTH/2) - (ballRadius + 2), Color.black, ballRadius, contBalls++)); 
        ballsArray.add(new Ball((WIDTH * 3/4) + (ballRadius*2) - 2, (LENGTH/2) + (ballRadius + 2), Color.gray, ballRadius, contBalls++));
        
        ballsArray.add(new Ball(((WIDTH * 3/4) + (ballRadius*4) - 4), (LENGTH/2) - ((ballRadius*2) + 2), Color.red, ballRadius, contBalls++));
        ballsArray.add(new Ball(((WIDTH * 3/4) + (ballRadius*4) - 4), LENGTH/2, Color.orange, ballRadius, contBalls++));
        ballsArray.add(new Ball(((WIDTH * 3/4) + (ballRadius*4) - 4), (LENGTH/2) + ((ballRadius*2) + 2), Color.blue, ballRadius, contBalls++));
        //TODO Usar "fors" para crear automáticamente triangulo al iniciar según num de bolas (crear clase de para crear colores de bolas de pool random¿)
        
        taco = new Cue(round(WIDTH * 0.6f), round(LENGTH * 0.02f), blanca);

        pocketsArray = new ArrayList<Pocket>();
        int pocketRadius = round((WIDTH * 0.06f) / 2);
        int offsetX = (WIDTH/2) - BORDER_WIDTH;
        float correctionMid = 0.2f;

        for (int i = 0; i < 3; i++) {
            int posY = (i != 1) ? (BORDER_LENGHT) : (BORDER_LENGHT - round(pocketRadius*correctionMid));
            pocketsArray.add(new Pocket(BORDER_WIDTH + (offsetX*i), posY, pocketRadius, this));
        }
        for (int i = 0; i < 3; i++) {
            int posY = (i != 1) ? (LENGTH - BORDER_LENGHT) : ((LENGTH - BORDER_LENGHT) + round(pocketRadius*correctionMid));
            pocketsArray.add(new Pocket(BORDER_WIDTH + (offsetX*i), posY, pocketRadius, this));
        }
    }
    private void paintTable(Graphics g) {
        // * Borde
        g.setColor(new Color(184, 115, 51));
        g.fillRect(0, 0, WIDTH, LENGTH);
        
        int subBorder = round(((BORDER_WIDTH * BORDER_LENGHT) * 0.003f));
        g.setColor(new Color(153, 102, 0));
        g.fillRect(BORDER_WIDTH-subBorder, BORDER_LENGHT-subBorder, (WIDTH - (BORDER_WIDTH*2)) + subBorder*2, (LENGTH - (BORDER_LENGHT*2)) + subBorder*2);

        // * Mesa
        g.setColor(Color.green);
        g.setColor(new Color(0, 200, 0));
        g.fillRect(BORDER_WIDTH, BORDER_LENGHT, WIDTH-(BORDER_WIDTH * 2), LENGTH-(BORDER_LENGHT * 2));
        
        //TODO Dibujar diamantes de los bordes

        // * Troneras
        for (Pocket pocket : pocketsArray) {
            pocket.paint(g);
        }
    }
}
