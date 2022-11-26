package poolgame;

import javax.swing.*;
import java.awt.*;

public class Gameplay extends JPanel {
    // private int x, y;

    private Mesa mesa;

    public Gameplay() {
        //* Config this
        super();
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        
        //* Config JComponents
        mesa = new Mesa(0, 0, 1280, 600);

        // JPanel panelInferior = new JPanel();
        // panelInferior.setBounds(720, 0, 10, 10);
        // panelInferior.setSize(10, 10);
        // panelInferior.setBackground(Color.BLACK);

        JButton bSur = new JButton("SCORE Y BOTONESSS");
        bSur.setPreferredSize(new Dimension(1280, 120));

        // panelInferior.add(bSur);

        //* Agregar JComponents
        this.add(mesa, BorderLayout.CENTER);
        this.add(bSur, BorderLayout.SOUTH);
        // this.add(panelInferior, BorderLayout.SOUTH);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        //* Borde mesa
        //// g.setColor(new Color(153, 102, 0));
        //// g.fillRect(0, 0, 750, 575);
        
        //* Mesa
        ///// g.setColor(Color.green);
        //// g.fillRect(20, 20, 700, 525);
        //// g.fillRect(this.x + 20, this.y + 20, 700, 525);

        //* Taco
        //// g.setColor(Color.black);
        //// g.drawLine(40, 40, 300, 150);
        g.setColor(Color.black);
        for (int i = 1; i <= 4; i++) {
            g.drawLine(40, 40 + i, 300, 150 + i);
        }

        //* Bolas
        //// g.setColor(Color.white);
        //// g.fillOval(300, 150, 30, 30);
        //// g.setColor(Color.red);
        //// g.fillOval(405, 190, 30, 30);
        //// g.setColor(Color.orange);
        //// g.fillOval(420, 230, 30, 30);
        //// g.setColor(Color.blue);
        //// g.fillOval(455, 240, 30, 30);
        //// g.setColor(Color.black);
        //// g.fillOval(480, 210, 30, 30);
        //// g.setColor(Color.gray);
        //// g.fillOval(430, 270, 30, 30);
        //// g.setColor(Color.white);
        //// g.fillOval(445, 200, 30, 30);

        mesa.paint(g);
    }
}
