package progra2.poolgame;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
    public Cue cue;
    
    private ArrayList<Ball> ballInHole;
    public BallType ballType;

    public Player() {
        ballInHole = new ArrayList<Ball>();
        cue = new Cue(null);
    }
    public Player(Color color) {
        ballInHole = new ArrayList<Ball>();
        cue = new Cue(color);
    }
    public Player(Cue cue) {
        ballInHole = new ArrayList<Ball>();
        this.cue = cue;
    }


    public void saveBall(Ball ballPocketed) {
        ballInHole.add(ballPocketed);
    }
    
    public void chooseBallType() {
        int ballSolid = 0, ballStripe = 0;

        for (Ball ball : ballInHole) {
            if (ball.getNumber() >= 1 && ball.getNumber() <= 7)
                ballSolid++;
            else
                ballStripe++;
        }

        if (ballSolid > ballStripe) {
            ballType = BallType.SOLID;
        } if (ballSolid < ballStripe) {
            ballType = BallType.STRIPE;
       
    }
}

    // * Setters
    public void setBallType(BallType ballType) {
        this.ballType = ballType;
    }
    // * getters
    public Cue getCuePlayer() {
        return this.cue;
    } 
}