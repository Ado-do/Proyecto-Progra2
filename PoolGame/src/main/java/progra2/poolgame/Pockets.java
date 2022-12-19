package progra2.poolgame;

import java.util.ArrayList;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Color;

import geometricas.Angular;
import geometricas.Circle;

public class Pockets {
    private ArrayList<Circle> arrayPockets;
    
    public Pockets(Rectangle main, Rectangle playfield) {
        this.arrayPockets = new ArrayList<Circle>();

        int pocketRadius = Math.round((main.width * 0.06f) / 2);
        int offsetX = (main.width/2) - playfield.x;
        float correctionMid = 0.2f;

        for (int i = 0; i < 3; i++) { // Top
            int posY = (i != 1) ? (playfield.y) : (playfield.y - Math.round(pocketRadius*correctionMid));
            arrayPockets.add(new Circle(playfield.x + (offsetX*i), posY, pocketRadius));
        }
        for (int i = 0; i < 3; i++) { // Bottom
            int posY = (i != 1) ? (main.height - playfield.y) : ((main.height - playfield.y) + Math.round(pocketRadius*correctionMid));
            arrayPockets.add(new Circle(playfield.x + (offsetX*i), posY, pocketRadius));
        }
    }

    public boolean isPocketed(Ball ball) {
        Point pBall = ball.getLocation();
        Point pPocket;
        float dist;

        for (Circle pocket : arrayPockets) {
            pPocket = pocket.getLocation();
            dist = (float)Angular.distEntre2Puntos(pBall, pPocket);
            if (dist < pocket.getRadius()) 
                return true;
        }
        return false;
    }

    public void receive(ArrayList<Ball> array, Ball ball) {
        array.remove(ball);
    }

    public void paint(Graphics2D g2D) {
        for (Circle pocket : arrayPockets) {
            pocket.fillCircle(g2D, Color.darkGray);
            pocket.drawCircle(g2D, Color.black);
        }
    }
}
