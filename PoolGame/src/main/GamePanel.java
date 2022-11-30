package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Mesa mesa;
    private JPanel panelSur;

    public GamePanel() {
    //* Configurar JPanel principal de juego
        super();
        this.setSize(1280, 720);
        this.setLayout(new BorderLayout());

        System.out.println("Tamaño GamePanel: "+this.getWidth()+"x"+this.getHeight());

        
    //* Configurar JComponents
        mesa = new Mesa(0, 0, 1280, 600);
        initPanelInferior();

    //* Agregar JComponents a JPanel principal
        this.add(mesa, BorderLayout.CENTER);
        this.add(panelSur, BorderLayout.SOUTH);
    }
    
    private void initPanelInferior() {
        panelSur = new JPanel(new BorderLayout());
        panelSur.setPreferredSize(new Dimension(1280, 120));

    //* Centro
        JLabel score = new JLabel("SCORE: 100000", SwingConstants.CENTER);
        JLabel escape = new JLabel("Presionar ESC para cerrar", SwingConstants.CENTER);

        JPanel subPanelCentro = new JPanel(new GridLayout(2, 0));
        subPanelCentro.add(score);
        subPanelCentro.add(escape);

        // panelSur.add(score, BorderLayout.CENTER);
        panelSur.add(subPanelCentro);
    //* Este
        JButton reiniciar = new JButton("REINICIAR");
        reiniciar.setFocusable(false);
        
        panelSur.add(reiniciar, BorderLayout.EAST);
    //* Oeste
        JButton sumarBola = new JButton("AGREGAR BOLA");
        sumarBola.setFocusable(false);

        JButton restarBola = new JButton("QUTAR BOLA");
        restarBola.setFocusable(false);

        JPanel subPanelOeste = new JPanel(new GridLayout(2, 0));
        subPanelOeste.add(sumarBola);
        subPanelOeste.add(restarBola);

        panelSur.add(subPanelOeste, BorderLayout.WEST);
    }

    @Override
    public void paint(Graphics g) {
        //* Fondo
        super.paint(g);
        
        //* Mesa, bolas y taco
        mesa.paintComponent(g);
    }
}
