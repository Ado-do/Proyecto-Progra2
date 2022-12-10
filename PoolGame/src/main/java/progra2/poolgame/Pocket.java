package progra2.poolgame;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
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
            Random rand = new Random();

            int borderWid = table.BORDER_WIDTH,  wid = table.WIDTH;
            int borderLen = table.BORDER_LENGHT, len = table.LENGTH;

            int Xmax = wid-borderWid-ball.getRadius(), Xmin = borderWid + ball.getRadius();
            int Ymax = len-borderLen-ball.getRadius(), Ymin = borderLen + ball.getRadius();
            ball.setLocation(rand.nextInt(Xmax - Xmin) + Xmin, rand.nextInt(Ymax - Ymin) + Ymin);
            // ball.getVel().escale(0);
        } else {
            //TODO Contar puntos
            Random rand = new Random();

            int borderWid = table.BORDER_WIDTH,  wid = table.WIDTH;
            int borderLen = table.BORDER_LENGHT, len = table.LENGTH;

            int Xmax = wid-borderWid-ball.getRadius(), Xmin = borderWid + ball.getRadius();
            int Ymax = len-borderLen-ball.getRadius(), Ymin = borderLen + ball.getRadius();
            ball.setLocation(rand.nextInt(Xmax - Xmin) + Xmin, rand.nextInt(Ymax - Ymin) + Ymin);
            // ball.getVel().escale(0);

            // array.remove(ball);
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
