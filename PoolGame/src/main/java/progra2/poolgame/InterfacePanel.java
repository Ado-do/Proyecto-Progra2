package progra2.poolgame;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

public class InterfacePanel extends JPanel {
    protected JLabel scoreLabel;

    public InterfacePanel() {
        super();

        // * CONFIGURAR PANEL
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(0, 100));

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        scoreLabel.setForeground(Color.white);

        // * Agregar componentes
        this.addComponents();
    }

    protected void addComponents() {
        // * Oeste
        JButton pausa = new JButton("PAUSA");
        // pausa.setPreferredSize(new Dimension(tableWidth/6, 0));
        pausa.setFont(new Font("Arial", Font.PLAIN, 16));
        pausa.setFocusable(false);
        // pausa.addActionListener(e -> pauseGame());
        this.add(pausa, BorderLayout.WEST);

        // * Este
        JButton restart = new JButton("REINICIAR");
        restart.setFont(new Font("Arial", Font.PLAIN, 16));
        // restart.setPreferredSize(new Dimension(tableWidth/6, 0));
        restart.setFocusable(false);
        // restart.addActionListener(e -> restartGame());
        this.add(restart, BorderLayout.EAST);

        // * Centro
        JPanel center = new JPanel();
        center.setBackground(new Color(90, 55, 25));
        this.add(center, BorderLayout.CENTER);

        // * Sur
        JPanel bottom = new JPanel();
        bottom.setPreferredSize(new Dimension(0, 60));
        bottom.setBackground(new Color(90, 55, 25).brighter());
        this.add(bottom, BorderLayout.SOUTH);

        bottom.add(scoreLabel);
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
    }
}
