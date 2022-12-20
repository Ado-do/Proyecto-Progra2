package progra2.poolgame;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
    private Cue cue;
    
    private ArrayList<Ball> myBalls;
    public BallType myBallType;

    public Player() {
        myBalls = new ArrayList<Ball>();
        cue = new Cue(null);
    }
    public Player(Color color) {
        myBalls = new ArrayList<Ball>();
        cue = new Cue(color);
    }
    public Player(Cue cue) {
        myBalls = new ArrayList<Ball>();
        this.cue = cue;
    }

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

    public void receiveBalls(ArrayList<Ball> balls) {
        myBalls.addAll(balls);
    }

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

    // * Setters
    public void setMyBallType(BallType ballType) {
        this.myBallType = ballType;
    }

    // * Getters
    public BallType getBalltype() {
        return myBallType;
    }
    public Cue getCue() {
        return cue;
    }
}