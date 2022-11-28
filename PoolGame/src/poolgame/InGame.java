package poolgame;

import javax.swing.*;
import java.awt.*;

public class InGame extends JPanel {
    // private int x, y;

    private Mesa mesa;
    private JPanel panelInferior;

    public InGame() {
        //* Config this
        super();
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        
        //* Config JComponents
        mesa = new Mesa(0, 0, 1280, 600);

        panelInferior = new JPanel(new BorderLayout());
        panelInferior.setPreferredSize(new Dimension(1280, 120));

        JButton bSur = new JButton("SCORE Y BOTONESSS");
        // bSur.setPreferredSize(new Dimension(1280, 120));

        panelInferior.add(bSur);

        //* Agregar JComponents
        this.add(mesa, BorderLayout.CENTER);
        // this.add(bSur, BorderLayout.SOUTH);
        this.add(panelInferior, BorderLayout.SOUTH);
    }
    
    @Override
    public void paint(Graphics g) {
        //* Configurar Render (Graphics2D tiene metodos de dibujado mas utiles y complejos) */
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        //* Fondo
        super.paint(g2);
        
        //* Mesa y bolas
        mesa.paint(g2);

        //* Taco
        g2.setColor(Color.black);
        int anchoTaco = 6;
        for (int i = 1; i <= anchoTaco; i++) {
            g2.drawLine(400, 310 + i, 590, 310 + i);
        }

        //? TEST
        // g2.setColor(Color.white);
        // g2.fill3DRect(10, 10, 100, 100, false);
    }
}
