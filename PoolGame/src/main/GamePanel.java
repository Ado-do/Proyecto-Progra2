package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Mesa mesa;
    private JPanel panelInferior;

    public GamePanel() {
        //* Configurar JPanel principal de juego
        super();
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        
        //* Configurar JComponents
        mesa = new Mesa(0, 0, 1280, 600);
        panelInferior = new JPanel(new BorderLayout());
        panelInferior.setPreferredSize(new Dimension(1280, 120));

        JLabel bSur = new JLabel("SCORE: 100000", SwingConstants.CENTER);
        JButton bEste = new JButton("REINICIAR");
        JPanel pOeste = new JPanel(new GridLayout(2,1));
        JButton sumarBola = new JButton("AGREGAR BOLA");
        JButton restarBola = new JButton("QUTAR BOLA");
        pOeste.add(sumarBola);
        pOeste.add(restarBola);

        panelInferior.add(pOeste, BorderLayout.WEST);
        panelInferior.add(bEste, BorderLayout.EAST);
        panelInferior.add(bSur);

        //* Agregar JComponents a JPanel principal
        this.add(mesa, BorderLayout.CENTER);
        this.add(panelInferior, BorderLayout.SOUTH);
    }
    
    @Override
    public void paint(Graphics g) {
        //* Fondo
        super.paint(g);
        
        //* Mesa, bolas y taco
        mesa.paintComponent(g);
        
    }
}
