package inputs;

import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import progra2.poolgame.GamePanel;
import progra2.poolgame.Mesa;

public class MouseInputs implements MouseInputListener {
    private GamePanel gamePanel;
    private Mesa mesa;
    private JPanel gui;

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.mesa = gamePanel.getMesa();
        this.gui = gamePanel.getGUI();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == mesa) System.out.println("hola");
        if (e.getSource() == gui) System.out.println("CHAU");
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gamePanel.requestFocus();
        if (e.getSource() == mesa) {
            mesa.sendMousePosition(e.getPoint().getLocation());
        }
        if (e.getSource() == gui) System.out.println("CHAU");
    }
}
