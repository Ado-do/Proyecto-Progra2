package progra2.poolgame;

import static java.lang.Math.round;

import java.util.ArrayList;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Polygon;
import java.awt.GradientPaint;

import geometricas.Circle;
import progra2.poolgame.PoolGame.GameModes;

public class Table extends JPanel {
    public final Rectangle main, playfield;
    private final float friction;

    private BallsFactory factory;

    private ArrayList<Ball> arrayBalls;
    private Ball cueBall;
    private ArrayList<Pocket> arrayPockets;
    private Cue cue;

    private float cueAngle;
    private float cueDistance;

    private int score;
    private int countBallsPocketed;
    private boolean cueBallPocketed;
    private boolean inGame;
    private boolean paused;
    
    public Table(int width, int height) {
        super(true);
        
        // * PROPIEDADES
        this.main = new Rectangle(width, height);

        Point pPlay = new Point(round((width - (width * 0.9f))/2), round((height - (height * 0.8f))/2));
        Dimension dimPlay = new Dimension(main.width-(pPlay.x * 2), main.height-(pPlay.y * 2));
        this.playfield = new Rectangle(pPlay, dimPlay);
        this.friction = (main.width * 1e-5f); // 1e-5f == 0.00001f

        this.score = 0;
        this.countBallsPocketed = 0;
        this.cueBallPocketed = false;
        this.inGame = false;
        this.paused = false;

        // * INICIALIZAR
        this.initTable();

        // * CONFIGURAR JPANEL
        this.setPreferredSize(new Dimension(main.width, main.height));
    }

    public void initGame(GameModes gameMode, int numBalls) {
        // * Ubicar bolas en mesa
        this.rackBalls(gameMode, numBalls);

        // * Taco
        cue = new Cue(this);

        score = 0;
        countBallsPocketed = 0;
        cueBallPocketed = false;
        paused = false;

        inGame = true;
    }

    public void updateGame() {
        if (inGame || !paused) {
            cue.update(cueAngle, cueDistance);

            // * Si alguna bola esta en movimiento, entonces...
            if (hasMovement()) {
                // * Mover y revisar colisiones
                for (int i = 0; i < arrayBalls.size(); i++) {
                    Ball currentBall = arrayBalls.get(i);
                    currentBall.move(friction);

                    // Revisar si entro en tronera
                    for (Pocket pocket : arrayPockets) {
                        if (pocket.isPocketed(currentBall)) {
                            if (currentBall == cueBall)
                                cueBallPocketed = true;
                            pocket.receive(arrayBalls, currentBall);
                            countBallsPocketed++;
                        }
                    }

                    // Revisar rebotes con paredes
                    currentBall.checkBounces(main, playfield);
                    
                    // Revisar colisiones
                    for (int j = 0; j < arrayBalls.size(); j++) {
                        if (i == j) continue;
                        Ball nextBall = arrayBalls.get(j);
                        
                        if (currentBall.intersecs(nextBall)) 
                            currentBall.collide(nextBall, friction);
                    }
                }
            } else {
                checkScore();
                if (cueBallPocketed) {
                    Ball.setRandomLocation(cueBall, this);
                    arrayBalls.add(0, cueBall);
                    cueBallPocketed = false;
                }
            }
        }
	}
    private void checkScore() {
        if (countBallsPocketed > 0) {
            if (cueBallPocketed) {
                score -= 20;
                countBallsPocketed--;
            }
            score += countBallsPocketed * 50;
            countBallsPocketed = 0;
        }
    }

    public void updateCue(float cueAngle, float cueDistance) {
        this.cueAngle = cueAngle;
        this.cueDistance = cueDistance;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        this.paintTable(g2D); // * Mesa
        
        if (inGame) 
            this.paintGame(g2D); // * Juego   
    }

    public boolean hasMovement() {
        for (Ball ball : arrayBalls) {
            if (ball.isMoving()) 
                return true;
        }
        return false;
    }

    public boolean isInGame() {
        return inGame;
    }

    // * Getters
    public Ball getCueBall() {
        return cueBall;
    }
    public Cue getCue() {
        return cue;
    }
    public ArrayList<Ball> getArrayBalls() {
        return arrayBalls;
    }
    public ArrayList<Pocket> getArrayPockets() {
        return arrayPockets;
    }
    public int getScore() {
        return score;
    }

    //! Subfunciones
    // * Inicializar mesa
    private void initTable() {
        // * Troneras
        arrayPockets = new ArrayList<Pocket>();
        int pocketRadius = round((main.width * 0.06f) / 2);
        int offsetX = (main.width/2) - playfield.x;
        float correctionMid = 0.2f;
        for (int i = 0; i < 3; i++) {
            int posY = (i != 1) ? (playfield.y) : (playfield.y - round(pocketRadius*correctionMid));
            arrayPockets.add(new Pocket(playfield.x + (offsetX*i), posY, pocketRadius));
        }
        for (int i = 0; i < 3; i++) {
            int posY = (i != 1) ? (main.height - playfield.y) : ((main.height - playfield.y) + round(pocketRadius*correctionMid));
            arrayPockets.add(new Pocket(playfield.x + (offsetX*i), posY, pocketRadius));
        }

        // * Bolas array y factory
        arrayBalls = new ArrayList<Ball>();
        factory = new BallsFactory(this);

        // * Angulo inicial del taco
        cueAngle = 1f;
    }
    
    // * Inicializar elementos de juego (initGame)
    private void rackBalls(GameModes gameMode, int numBalls) {
        if (gameMode == GameModes.STANDARD || gameMode == GameModes.STANDARD_MULTIPLAYER) {
            factory.getRackedBalls(arrayBalls);
        } else 
        if (gameMode == GameModes.RANDOM || gameMode == GameModes.RANDOM_MULTIPLAYER) {
            factory.getRandomBalls(arrayBalls, numBalls);
        }
        
        cueBall = arrayBalls.get(0);
    }

    // * Paint elementos de juego (paint)
    private void paintTable(Graphics2D g2D) {
        // * Borde
        g2D.setColor(new Color(184, 115, 51));
        g2D.fill(main);
        g2D.setColor(Color.black);
        g2D.draw(main);
        
        int     escaleSubBorder = round(((playfield.x * playfield.y) * 0.004f));
        Point        pSubBorder = new Point(playfield.x-escaleSubBorder, playfield.y-escaleSubBorder);
        Dimension dimsSubBorder = new Dimension((main.width - (playfield.x*2)) + escaleSubBorder*2, (main.height - (playfield.y*2)) + escaleSubBorder*2);
        Rectangle rectSubBorder = new Rectangle(pSubBorder, dimsSubBorder);
        // g2D.setColor(new Color(153, 102, 0));
        g2D.setColor(Color.green.darker().darker());
        g2D.fill(rectSubBorder);
        g2D.setColor(Color.black);
        g2D.draw(rectSubBorder);

        // * Area de juego
        Dimension quartDim = new Dimension(playfield.width/2, playfield.height/2);
        Point center = new Point(playfield.x + playfield.width/2, playfield.y + playfield.height/2);

        g2D.setPaint(new GradientPaint(new Point(center.x - quartDim.width, center.y - quartDim.height), Color.green.darker(), center, Color.green));
        g2D.fill(        new Rectangle(new Point(center.x - quartDim.width, center.y - quartDim.height), quartDim));

        g2D.setPaint(new GradientPaint(new Point(center.x + quartDim.width, center.y - quartDim.height), Color.green.darker(), center, Color.green));
        g2D.fill(        new Rectangle(new Point(center.x                 , center.y - quartDim.height), quartDim));

        g2D.setPaint(new GradientPaint(new Point(center.x - quartDim.width, center.y + quartDim.height), Color.green.darker(), center, Color.green));
        g2D.fill(        new Rectangle(new Point(center.x - quartDim.width, center.y                  ), quartDim));

        g2D.setPaint(new GradientPaint(new Point(center.x + quartDim.width, center.y + quartDim.height), Color.green.darker(), center, Color.green));
        g2D.fill(        new Rectangle(new Point(center.x                 , center.y                  ), quartDim));

        // * Diamantes
        Polygon diamond = new Polygon();
        int diamX = playfield.x, diamY = rectSubBorder.y/2;
        int diamWidth = playfield.y/8;
        diamond.addPoint(diamX, diamY - diamWidth); //Norte
        diamond.addPoint(diamX - diamWidth, diamY); //Oeste
        diamond.addPoint(diamX, diamY + diamWidth); //Este
        diamond.addPoint(diamX + diamWidth, diamY); //Sur

        // Vertical
        int deltaX1 = playfield.width/8;
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
        diamond.translate((playfield.x-rectSubBorder.x)+(rectSubBorder.x/2), (rectSubBorder.y/2)+(playfield.y-rectSubBorder.y));
        int deltaY2 = playfield.height/4;
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
    private void paintGame(Graphics2D g2D) {
        // * Bolas
        for (Ball ball : arrayBalls) {
            Circle shadow = new Circle(round(ball.x+3.5f), round(ball.y+3.5f), ball.getRadius());
            shadow.fillCircle(g2D, Color.gray.darker());
        }
        for (Ball ball : arrayBalls) ball.paint(g2D);

        // * Taco
        if (!this.hasMovement() && cueBall != null) {
            cue.paint(g2D);
        }
    }
    public boolean isPaused() {
        return paused;
    }
    public void setPaused(boolean pause) {
        this.paused = pause;
    }
}
