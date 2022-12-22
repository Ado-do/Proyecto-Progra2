package progra2.poolgame;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Esta clase crea los jugadores para multiplayer
 * 
 * @author Cristóbal Figueroa
 */
public class Player {
    private Cue cue;
    
    private ArrayList<Ball> myBalls;
    public BallType myBallType;

    /**
     * Método constructor para el jugador que recibe el color del taco
     * 
     * @param color Color que tendrá el taco
     */
    public Player(Color color) {
        myBalls = new ArrayList<Ball>();
        cue = new Cue(color);
    }

    /**
     * Método constructor para el jugador que recibe el taco
     * 
     * @param cue Taco que tendrá el jugador
     */
    public Player(Cue cue) {
        myBalls = new ArrayList<Ball>();
        this.cue = cue;
    }

    /**
     * Método que elige el tipo de bolas con las que jugara
     * el jugador durante la partida actual
     * 
     * @return true si se pudo elegir el tipo de bolas, false si no se pudo
     */
    public boolean chooseBallType() {
        int ballSolid = 0, ballStripe = 0;

        for (Ball ball : myBalls) {
            if (ball.getNumber() > 0 && ball.getNumber() < 8)
                ballSolid++;
            else
                ballStripe++;
        }

        if (ballSolid > ballStripe) {
            myBallType = BallType.SOLID;
            return true;
        } else
        if (ballSolid < ballStripe) {
            myBallType = BallType.STRIPE;       
            return true;
        } else
            return false;
    }

    /**
     * Método que recibe las bolas entronadas por el jugador 
     * tras haber hecho su tiro
     * 
     * @param balls Lista de bolas que recibe el jugador
     */
    public void receiveBalls(ArrayList<Ball> balls) {
        myBalls.addAll(balls);
    }

    /**
     * Método que dá las bolas que no son del tipo de bolas
     * que le corresponden al jugador
     * 
     * @param player2 Jugador al que se le darán las bolas
     */
    public void giveDifferentBalls(Player player2) {
        ArrayList<Ball> givenBalls = new ArrayList<Ball>();

        for (Ball ball : myBalls) {
            if (ball.getBallType() != myBallType)
                givenBalls.add(ball);
        }

        // Quita las bolas dadas de la lista de bolas del jugador si cumple la condición en paréntesis
        myBalls.removeIf(ball -> (ball.getBallType() != myBallType));

        player2.receiveBalls(givenBalls);
    }

    /**
     * Método que asigna el tipo de bolas que le corresponde al jugador
     * 
     * @param ballType Tipo de bolas que le corresponde al jugador
     */
    public void setMyBallType(BallType ballType) {
        this.myBallType = ballType;
    }

    /**
     * Método que retorna el tipo de bolas que le corresponde al jugador
     * 
     * @return el tipo de bolas que le corresponde al jugador
     */
    public BallType getBalltype() {
        return myBallType;
    }

    /**
     * Método que retorna la lista de bolas que tiene el jugador
     * 
     * @return lista de bolas que tiene el jugador
     */
    public Cue getCue() {
        return cue;
    }
}