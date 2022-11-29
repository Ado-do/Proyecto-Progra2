package main;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;

//TODO Agregar logica creada en el paint de Mesa para generar taco

public class Taco extends JComponent {
    private final int ancho = 6;
    
    public Taco() {
        this.setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.isVisible()) {
            g.setColor(Color.black);
            for (int i = 1; i <= ancho; i++) {
            g.drawLine(350, 310 + i, 520, 310 + i);
        }
        }
    }
}
