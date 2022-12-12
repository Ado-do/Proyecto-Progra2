package inputs;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Point;

import progra2.poolgame.Table;
import progra2.poolgame.Cue;
import progra2.poolgame.Ball;
import geometricas.Angular;
import geometricas.Vector2D;

public class PoolInputHandler implements MouseInputListener, KeyListener {
    private Table table;
    private Cue cue;
    private Ball blanca;

    private Point pDirection;
    private Vector2D force;

    public PoolInputHandler(Table table) {
        this.table = table;
        this.cue = table.getCue();
        this.blanca = table.getBlanca();

        this.force = new Vector2D();
        this.pDirection = new Point();
    }

    //* Eventos
    @Override
    public void mousePressed(MouseEvent e) {
        if (!table.hasMovement()) {
            pDirection = e.getPoint();
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        if (!table.hasMovement()) {
            float dist = (float) Angular.distEntre2Puntos(pDirection, e.getPoint());
            int hitArea = 100;

            if (dist < hitArea) {
                force.setVector(e.getX(), e.getY());
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (!table.hasMovement()) {
            if (force.getMagnitude() == 0) {
                force.setVector((float)pDirection.getX(), (float)pDirection.getY()); // Bugfix de cuando no se arrastra el mouse (sino la bola se vuelve loca)
            }
            // System.out.println("release: "+force.getMagnitude());
            // System.out.println("hold: "+(new Vector2D(pDirection)).getMagnitude());
            // System.out.println("("+release.x+" - "+hold.x+", "+release.y+" - "+hold.y+")");
            // System.out.println("("+(release.x - hold.x)+", "+(release.y - hold.y)+")");

            Vector2D dragVec = new Vector2D(force.x - pDirection.x, force.y - pDirection.y);
            float angle = Angular.anguloPI(blanca.getLocation(), pDirection);
            Point forceDirection = Angular.generaPunto(pDirection, dragVec.getMagnitude(), angle);

            Vector2D vel = new Vector2D((float)(forceDirection.getX() - pDirection.getX()), (float)(forceDirection.getY() - pDirection.getY()));
            System.out.println("vel: "+vel.getMagnitude());

            cue.hitBall(vel);
                    
            force.escale(0);
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        table.sendMouseInfo(e);
    }
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                break;
            case KeyEvent.VK_D:
                break;
            case KeyEvent.VK_SPACE:
                break;
            default: break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
