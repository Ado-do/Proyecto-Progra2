package inputs;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import progra2.poolgame.PoolTable;
import progra2.poolgame.PoolBall;

import geometricas.Vector2D;

public class PoolHandler implements MouseInputListener, KeyListener {
    private PoolTable table;
    private PoolBall blanca;

    private Vector2D hold, release;

    public PoolHandler(PoolTable table) {
        this.table = table;
        this.blanca = table.getBlanca();

        this.hold = new Vector2D();
        this.release = new Vector2D();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (!table.hasMovement()) { 
            hold.x = e.getX();
            hold.y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!table.hasMovement()) {
            release.x = e.getX();
            release.y = e.getY();

            Vector2D vel = new Vector2D(release.x - hold.x, release.y - hold.y);
            vel.escale(-0.25f);

            blanca.setSpeedVector(vel);
                    
            hold.escale(0);
            release.escale(0);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!table.hasMovement()) {
            release.x = e.getX();
            release.y = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        table.sendMousePosition(e.getPoint().getLocation());
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
