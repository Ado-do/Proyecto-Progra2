package progra2.poolgame;

import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;

import geometricas.Angular;
import geometricas.Circle;

public class Pocket extends Circle {
    private Table table;

    public Pocket(int x, int y, int radius, Table table) {
        super(x, y, radius);
        this.table = table;
    }
    public void receive(ArrayList<Ball> array, Ball ball) {
        if (ball.getColor() == Color.WHITE) {
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
        this.fillCircle(g2D, Color.BLACK);
    }
}
