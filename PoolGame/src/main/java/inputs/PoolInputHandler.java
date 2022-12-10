package inputs;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Point;

import progra2.poolgame.PoolTable;
import progra2.poolgame.CueStick;
import progra2.poolgame.PoolBall;
import geometricas.Angular;
import geometricas.Vector2D;

//TODO Mejorar precision de tiros

public class PoolInputHandler implements MouseInputListener, KeyListener {
    private final float forceCorrection = (-1 * 0.2f); // Corregir e invertir dirección de fuerza del golpe a la bola
    private PoolTable table;
    private PoolBall blanca;

    private Vector2D hold, release;
    private boolean ballWasPressed;

    public PoolInputHandler(PoolTable table) {
        this.table = table;
        this.blanca = table.getBlanca();

        this.hold = new Vector2D();
        this.release = new Vector2D();
        
        ballWasPressed = false;
    }

    private void hitBall(Vector2D hitVel) {
        hitVel.escale(forceCorrection);
        blanca.setVel(hitVel);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!table.hasMovement() && blanca.isPressed(e.getPoint())) {
            ballWasPressed = true;

            hold.setVector(e.getX(), e.getY());
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        //TODO Mejorar¿
        table.sendMousePosition(e.getPoint());

        if (!table.hasMovement() && ballWasPressed) {
            Point pHold = new Point(Math.round(hold.x), Math.round(hold.y));

            float dist = (float) Angular.distEntre2Puntos(pHold, e.getPoint());
            float hitArea = blanca.getRadius() + CueStick.DISTANCE + CueStick.LENGTH;

            if (dist < hitArea) {
                release.setVector(e.getX(), e.getY());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!table.hasMovement() && ballWasPressed) {
            if (release.x == 0 || release.y == 0) {
                release.setVector(hold); // Bugfix de cuando no se arrastra el mouse (sino la bola se vuelve loca)
            }
            // System.out.println("("+release.x+" - "+hold.x+",("+release.y+"-"+hold.y+")");
            // System.out.println("("+(release.x - hold.x)+", "+(release.y - hold.y)+")");

            Vector2D vel = new Vector2D(release.x - hold.x, release.y - hold.y);
            // System.out.println("vel: "+vel.getMagnitude());
            
            hitBall(vel);
                    
            hold.escale(0);
            release.escale(0);

            ballWasPressed = false;
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        table.sendMousePosition(e.getPoint());
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
