package progra2.poolgame;

import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

import geometricas.Angular;
import geometricas.Circle;

public class Pocket extends Circle {
    public Pocket(int x, int y, int radius) {
        super(x, y, radius);
    }
    public void receive(ArrayList<Ball> array, Ball ball) {
        if (ball.getColor() == Color.WHITE) {
            //TODO Descontar puntos y devolver bola a mesa
        } else {
            //TODO Contar puntos

            array.remove(ball);
        }
    }

    public boolean isPocketed(Ball ball) {
        Point pBall = ball.getLocation();
        Point pPocket = this.getLocation();
        if (Angular.distEntre2Puntos(pBall, pPocket) < this.radius) {
            System.out.println("CAYO LA WEAAAA");
            return true;
        } else {
            return false;
        }
    }

    public void paint(Graphics g) {
        this.fillCircle(g, Color.BLACK);
    }
}
