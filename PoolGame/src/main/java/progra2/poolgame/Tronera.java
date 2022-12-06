package progra2.poolgame;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Math.round;

public class Tronera {
    public float x, y;
    private static final int RADIUS = 30;
    public Tronera(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(round(x - RADIUS), round(y - RADIUS), RADIUS*2, RADIUS*2);
        g.setColor(Color.RED);
        g.drawOval(round(x - RADIUS), round(y - RADIUS), RADIUS*2, RADIUS*2);
    }
}
