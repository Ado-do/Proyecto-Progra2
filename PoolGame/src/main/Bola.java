package main;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;

public class Bola extends JComponent {
    private int x, y;
    private Color color;
    private final int radio; // Constante
    
    public Bola(Color color, int posX, int posY) {
        super();

        this.color = color;
        this.x = posX;
        this.y = posY;
        this.radio = 30;

        // this.setBounds(posX, posY, radio, radio);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, radio, radio);

        super.paint(g);
    }
}
