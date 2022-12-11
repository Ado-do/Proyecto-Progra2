package progra2.poolgame;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;

public class SubGUI extends JPanel {
    
    public SubGUI() {
        super(new BorderLayout());

        this.setPreferredSize(new Dimension(0, 100));

        // * Centro
        JPanel subPanelCentro = new JPanel(new GridLayout(2, 0));
        JLabel score = new JLabel("SCORE: 100000", SwingConstants.CENTER);
        JLabel escape = new JLabel("Presionar ESC para cerrar", SwingConstants.CENTER);
        JLabel instruc = new JLabel("Presionar y arrastrar para golpear bola", SwingConstants.CENTER);
        subPanelCentro.add(score);
        subPanelCentro.add(escape);
        subPanelCentro.add(instruc);

        this.add(subPanelCentro);

        // * Este
        JButton reiniciar = new JButton("REINICIAR");
        reiniciar.setFocusable(false);
        
        this.add(reiniciar, BorderLayout.EAST);

        // * Oeste
        JPanel subPanelOeste = new JPanel(new GridLayout(2, 0));
        
        JButton sumarBola = new JButton("AGREGAR BOLA");
        sumarBola.setFocusable(false);
        JButton restarBola = new JButton("QUTAR BOLA");
        restarBola.setFocusable(false);

        subPanelOeste.add(sumarBola);
        subPanelOeste.add(restarBola);

        this.add(subPanelOeste, BorderLayout.WEST);
    }
}
