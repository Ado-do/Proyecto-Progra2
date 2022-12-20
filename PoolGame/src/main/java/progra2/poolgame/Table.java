package progra2.poolgame;

import static java.lang.Math.round;

import java.util.ArrayList;

// import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Color;
// import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Polygon;
import java.awt.GradientPaint;

import geometricas.Circle;

public class Table {
    public final Rectangle main, playfield;
    private final float friction;

    private BallsFactory factory; // Para inicializar arreglo de bolas

    private ArrayList<Ball> arrayBalls; // Arreglo de bolas
    private Ball cueBall; // Bola blanca
    private Pockets pockets; // Troneras
    private Cue cue; // Taco

    private Player player1;
    private Player player2;

    private Player currentPlayer;

    //? Debería estar en taco
    private float cueAngle; // Angulo del taco
    private float cueDistance; // Distancia del taco a la bola blanca
    
    //? Debería estar en PoolGame
    private int score; // Puntaje
    private boolean cueBallPocketed; // Bola blanca en tronera
    private int countBallsPocketed; // Cantidad de bolas en tronera
    
    private boolean chosenBall;

    public Table(int width) {
        // * PROPIEDADES
        int height = width/2;
        this.main = new Rectangle(width, height);

        Point pPlay = new Point(round((width - (width * 0.9f))/2), round((height - (height * 0.8f))/2));
        Dimension dimPlay = new Dimension(main.width-(pPlay.x * 2), main.height-(pPlay.y * 2));
        this.playfield = new Rectangle(pPlay, dimPlay);
        this.friction = (main.width * 1e-5f); // 1e-5f == 0.00001f
        this.score = 0;
        this.countBallsPocketed = 0;
        this.cueBallPocketed = false;
        this.chosenBall = false;

        // * INICIALIZAR
        this.initTable();
    }
    private void initTable() {
        // * Troneras
        pockets = new Pockets(main, playfield);
        
        // * Bolas array y factory
        arrayBalls = new ArrayList<Ball>();
        factory = new BallsFactory(main, playfield);
        

        // * Angulo inicial del taco
        cueAngle = 1f;
    }

    public void startGame(int numBalls) {
        // * Ubicar bolas en mesa
        this.rackBalls(PoolGame.mode, numBalls);

        // * Taco
        player1 = new Player();
        player2 = new Player(new Color(255, 0, 0));
        currentPlayer = player1;
        cue = currentPlayer.getCue();
        

        score = 0;
        countBallsPocketed = 0;
        cueBallPocketed = false;
    }
    public void clean() {
        arrayBalls.clear();
        cue = null;

        score = 0;
        countBallsPocketed = 0;
        cueBallPocketed = false;
    }

    public void update() {
        cue.update(cueAngle, cueDistance);

        // * Mesa en movimiento
        if (hasMovement()) {
            for (int i = 0; i < arrayBalls.size(); i++) {
                Ball currentBall = arrayBalls.get(i);

                // 1) Mover bola
                currentBall.move(friction);

                // 2) Revisar rebotes con paredes
                currentBall.checkBounces(main, playfield);

                // 3) Revisar colisiones entre bolas
                for (int j = 0; j < arrayBalls.size(); j++) {
                    if (i == j) 
                        continue;
                    Ball nextBall = arrayBalls.get(j);
                    
                    if (currentBall.intersecs(nextBall)) 
                        currentBall.collide(nextBall, friction);
                }

                // 4) Revisar si entro en tronera
                if (pockets.isPocketed(currentBall)) {
                    if (currentBall == cueBall) {
                        cueBallPocketed = true;                    
                    }
                    pockets.receive(currentBall);
                    arrayBalls.remove(currentBall);

                    countBallsPocketed++;
                }

                // if (currentPlayer.getBalltype() == currentBall.getBallType() && pockets.isPocketed(currentBall)) {
                //     currentPlayer = player1;
                // } else if (currentBall.getBallType() != currentBall.getBallType() && pockets.isPocketed(currentBall)){
                //     currentPlayer = player2;
                // }
                // System.out.println(player1.ballType);
                // cue = currentPlayer.getCue();
            }

        // * Mesa sin movimiento
        } else {

            // * Si alguna bola cayo en troneras
            if (countBallsPocketed != 0) {

                // * Players reciben bolas de tronera
                // Si aun no se han asignado tipo de bola
                if (!chosenBall) {
                    currentPlayer.receiveBalls(pockets.getAllBalls()); // Recibir bolas de tronera
                    chosenBall = currentPlayer.chooseBallType();

                    // Si se eligió bola, dar bola del tipo diferente al otro jugador
                    if (chosenBall) { 
                        if (currentPlayer == player1)
                            currentPlayer.giveDifferentBalls(player2);
                        else 
                            currentPlayer.giveDifferentBalls(player1);
                    }
                // Si ya se eligió tipo de bola
                } else {
                    currentPlayer.receiveBalls(pockets.getAllBalls()); // Recibir bolas de tronera

                    if (currentPlayer == player1)
                        currentPlayer.giveDifferentBalls(player2);
                    else 
                        currentPlayer.giveDifferentBalls(player1);
                }

                // Contar puntaje
                checkScore();
                countBallsPocketed = 0;

                // Si la bola blanca cayo en tronera
                if (cueBallPocketed) {
                    cueBall.getVel().escale(0); // Detener bola blanca
                    Ball.setRandomLocation(cueBall); // Ubicar bola blanca en mesa
                    arrayBalls.add(0, cueBall); // Agregar bola blanca al arreglo
                    cueBallPocketed = false;
                    if(!IsCurrentPlayer(player1)) { // Si se mete la bola a la tronera se cambia de player
                        currentPlayer = player1; 
                        System.out.println("soy player1");
                    }
                    else if(!IsCurrentPlayer(player2)) {
                        currentPlayer = player2;
                        System.out.println("soy player2");
                    }
                     
                }
            }
        }
	}
    public void updateCue(float cueAngle, float cueDistance) {
        this.cueAngle = cueAngle;
        this.cueDistance = cueDistance;
    }

    private void checkScore() {
        if (cueBallPocketed) {
            score -= 20;
            countBallsPocketed--;
        }
        score += countBallsPocketed * 50;

        PoolGame.ip.updateScore(score);
    }

    public boolean hasMovement() {
        for (Ball ball : arrayBalls) {
            if (ball.isMoving()) 
                return true;
        }
        return false;
    }
    public boolean IsCurrentPlayer( Player player) { // FUNCION PARA SABER SI EL PLAYER QUE SE INGRESA ES EL QUE ESTA EN TURNO
        if(currentPlayer == player) {
            return true;
        } else {
            return false;
        }

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
    public Pockets getPockets() {
        return pockets;
    }
    public int getScore() {
        return score;
    }

    public void paint(Graphics2D g2D) {
        // * Mesa
        this.paintTable(g2D);
        
        // * Elementos de juego
        if (PoolGame.state == GameState.PLAYING || PoolGame.state == GameState.PAUSED)
            this.paintGame(g2D);

        // * Contorno
        g2D.setColor(Color.black);
        g2D.draw(main);
    }

    //! Subfunciones    
    // * Inicializar elementos de juego (initGame)
    private void rackBalls(GameModes gameMode, int numBalls) {
        switch (gameMode) {
            case STANDARD             -> factory.getRackedBalls(arrayBalls);
            case STANDARD_MULTIPLAYER -> factory.getRackedBalls(arrayBalls);
            case RANDOM               -> factory.getRandomBalls(arrayBalls, numBalls);
        }
        
        cueBall = arrayBalls.get(0);
    }

    // * Paint elementos de juego (paint)
    private void paintTable(Graphics2D g2D) {
        // * Borde
        g2D.setColor(new Color(184, 115, 51));
        g2D.fill(main);
        
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

        int deltaX, deltaY;
        // Vertical
        deltaX = round(playfield.width/8f);
        deltaY = rectSubBorder.height + rectSubBorder.y;
        for (int i = 0; i < 8; i++) {
            diamond.translate(deltaX, 0);
            if (i != 3 && i != 7) {
                g2D.setColor(Color.yellow); g2D.fill(diamond);
                g2D.setColor(Color.black);  g2D.draw(diamond);
            }
            diamond.translate(0, deltaY);
            if (i != 3 && i != 7) {
                g2D.setColor(Color.yellow); g2D.fill(diamond);
                g2D.setColor(Color.black);  g2D.draw(diamond);
            }
            deltaY *= -1;
        }

        // Horizontal
        diamond.translate((playfield.x-rectSubBorder.x)+round(rectSubBorder.x/2f), round(rectSubBorder.y/2f)+(playfield.y-rectSubBorder.y));
        deltaY = round(playfield.height/4f);
        deltaX = rectSubBorder.width + rectSubBorder.x;
        for (int i = 0; i < 3; i++) {
            diamond.translate(0, deltaY);
            g2D.setColor(Color.yellow); g2D.fill(diamond);
            g2D.setColor(Color.black);  g2D.draw(diamond);
            deltaX *= -1;
            diamond.translate(deltaX, 0);
            g2D.setColor(Color.yellow); g2D.fill(diamond);
            g2D.setColor(Color.black);  g2D.draw(diamond);
        }

        // * Troneras
        pockets.paint(g2D);
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
            currentPlayer.getCue().paint(g2D);
        }
    }
}
