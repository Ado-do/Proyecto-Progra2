package progra2.poolgame;

import java.util.ArrayList;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;

import geometricas.Angular;
import geometricas.Circle;

public class Pocket extends Circle {

    public Pocket(int x, int y, int radius) {
        super(x, y, radius);
    }
    public void receive(ArrayList<Ball> array, Ball ball) {
        if (ball.getNumber() == 0) {
            //TODO Descontar puntos y devolver bola a mesa
            ball.getVel().escale(0);
            array.remove(ball);
        } else {
            //TODO Contar puntos
            array.remove(ball);
        }
    }

    public boolean isPocketed(Ball ball) {
        Point pBall = ball.getLocation();
        Point pPocket = this.getLocation();
        return (Angular.distEntre2Puntos(pBall, pPocket) < this.radius) ? true : false;
    }

    public void paint(Graphics2D g2D) {
        this.fillCircle(g2D, Color.darkGray);
        this.drawCircle(g2D, Color.black);
    }
}
