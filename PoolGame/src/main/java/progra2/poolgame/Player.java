package progra2.poolgame;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
    private Cue Taco;
    
    private ArrayList<Ball> ballInHole;
    private BallType ballType;

    public Player() {
        ballInHole = new ArrayList<Ball>();
        Taco = new Cue(null);
    }
    public Player(Color color) {
        ballInHole = new ArrayList<Ball>();
        Taco = new Cue(color);
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
        } else
        if (ballSolid < ballStripe) {
            ballType = BallType.STRIPE;
        } else {
            //! HACER WEAS
        }
    }

    // * Setters
    public void setBallType(BallType ballType) {
        this.ballType = ballType;
    }
}