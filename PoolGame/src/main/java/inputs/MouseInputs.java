package inputs;

import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import geometricas.PVector;
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
        // if (e.getSource() == mesa) {
        //     PVector vel = new PVector(-40, 0);
        //     vel.escalar(-0.25f);
        //     mesa.getBlanca().setSpeed(vel);
        //     mesa.moving = true;
        // }
        // if (e.getSource() == gui) System.out.println("CHAU");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mesa.hold.x = e.getX();
        mesa.hold.y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mesa.release.x = e.getX();
        mesa.release.y = e.getY();
        PVector vel = new PVector(mesa.release.x - mesa.hold.x, mesa.release.y - mesa.hold.y);
        vel.escalar(-0.25f);


        mesa.getBlanca().setSpeed(vel);

        mesa.moving = true;
                
        mesa.hold.escalar(0);
        mesa.release.escalar(0);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mesa.release.x = e.getX();
        mesa.release.y = e.getY();
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
