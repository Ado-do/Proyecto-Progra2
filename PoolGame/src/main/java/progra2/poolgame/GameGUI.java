package progra2.poolgame;

import javax.swing.*;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

//TODO Remplazar por InterfacePanel

// public abstract class GameGUI extends JPanel {
public class GameGUI extends JPanel {
    protected JLabel scoreLabel;

    public GameGUI() {
        super();
        this.setLayout(new BorderLayout());

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        scoreLabel.setForeground(Color.white);

        // * Agregar componentes
        this.addComponents();
    }

    protected void addComponents() {
        JPanel top = new JPanel();
        top.setBackground(new Color(90, 55, 25));
        this.add(top, BorderLayout.CENTER);

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

// class StandardGameGUI extends GameGUI {
//     public StandardGameGUI() {
//         super();
//     }

//     @Override
//     protected void addComponents() {
//         JPanel top = new JPanel();
//         top.setBackground(new Color(90, 55, 25));
//         this.add(top, BorderLayout.CENTER);

//         JPanel bottom = new JPanel();
//         bottom.setPreferredSize(new Dimension(0, 60));
//         bottom.setBackground(new Color(90, 55, 25).brighter());
//         this.add(bottom, BorderLayout.SOUTH);

//         bottom.add(scoreLabel);
//     }
// }

// class RandomGameGUI extends GameGUI {
//     public RandomGameGUI() {
//         super();
//     }

//     @Override
//     protected void addComponents() {
//         JPanel top = new JPanel();
//         top.setBackground(new Color(90, 55, 25));
//         this.add(top, BorderLayout.CENTER);

//         JPanel bottom = new JPanel();
//         bottom.setPreferredSize(new Dimension(0, 60));
//         bottom.setBackground(new Color(90, 55, 25).brighter());
//         this.add(bottom, BorderLayout.SOUTH);

//         bottom.add(scoreLabel);
//     }
// }