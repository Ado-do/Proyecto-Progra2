package poolgame;

import java.awt.*;
import javax.swing.JPanel;

public class Ambiente extends JPanel {

    private int x, y;

    public Ambiente() {
        this.setBackground(Color.GREEN);
    }

    public void paint(Graphics g) {

        g.setColor(new Color(153, 102, 0));
        g.fillRect(this.x, this.y, 750, 575);

        g.setColor(Color.green);
        g.fillRect(this.x + 20, this.y + 20, 700, 525);

        g.setColor(Color.black);
        g.drawLine(40, 40, 300, 150);    //se le dice al pincel pinte rectángulo
        g.setColor(Color.white);
        g.fillOval(300, 150, 30, 30); //se le dice al pincel pinte rectángulo

        g.setColor(Color.red);
        g.fillOval(405, 190, 30, 30); //se le dice al pincel pinte rectángulo
        g.setColor(Color.orange);
        g.fillOval(420, 230, 30, 30); //se le dice al pincel pinte rectángulo
        g.setColor(Color.blue);
        g.fillOval(455, 240, 30, 30); //se le dice al pincel pinte rectángulo
        g.setColor(Color.black);
        g.fillOval(480, 210, 30, 30); //se le dice al pincel pinte rectángulo
        g.setColor(Color.gray);
        g.fillOval(430, 270, 30, 30); //se le dice al pincel pinte rectángulo
        g.setColor(Color.white);
        g.fillOval(445, 200, 30, 30); //se le dice al pincel pinte rectángulo

    }
}
