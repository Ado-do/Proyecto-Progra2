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
    private JButton pauseButton;
    private JLabel scoreLabel;

    public InterfacePanel() {
        super(true);

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
        int tableWidth = PoolGame.table.main.width;

        // * Oeste
        pauseButton = new JButton("PAUSA");
        pauseButton.setPreferredSize(new Dimension(tableWidth/8, 0));
        pauseButton.setFont(new Font("Arial", Font.PLAIN, 16));
        pauseButton.setFocusable(false);
        pauseButton.addActionListener(e -> PoolGame.getInstance().pauseGame());
        this.add(pauseButton, BorderLayout.WEST);

        // * Este
        JButton restartButton = new JButton("REINICIAR");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 16));
        restartButton.setPreferredSize(new Dimension(tableWidth/8, 0));
        restartButton.setFocusable(false);
        restartButton.addActionListener(e -> PoolGame.getInstance().restartGame());
        this.add(restartButton, BorderLayout.EAST);

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

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
    }

    // * Getters
    public JButton getPauseButton() {
        return pauseButton;
    }
}
