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

//TODO URGENTEEEE Mejorar precision de tiros
//! Bloque dirección de tiro cuando presiona mouse y modificar magnitud según distancia de arrastrado de mouse

public class PoolInputHandler implements MouseInputListener, KeyListener {
    private final float forceCorrection = (-1 * 0.15f); // Corregir e invertir dirección de fuerza del golpe a la bola
    private Table table;
    private Ball blanca;

    // private Vector2D hold, release;
    private boolean ballWasPressed;

    private Point hold, release;

    public PoolInputHandler(Table table) {
        this.table = table;
        this.blanca = table.getBlanca();

        // this.hold = new Vector2D();
        // this.release = new Vector2D();
        
        this.hold = new Point();
        this.release = new Point();

        ballWasPressed = false;
    }

    private void hitBall(Vector2D hitVel) {
        hitVel.escale(forceCorrection);
        blanca.setVel(hitVel);
    }

    //* Eventos
    @Override
    public void mousePressed(MouseEvent e) {
        // if (!table.hasMovement() && blanca.isPressed(e.getPoint())) {
        if (blanca.isPressed(e.getPoint())) {
            ballWasPressed = true;

            // hold.setVector(e.getX(), e.getY());
            hold = e.getPoint();
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        //TODO Mejorar¿
        table.sendMouseInfo(e);

        if (ballWasPressed) {
        // if (!table.hasMovement() && ballWasPressed) {
            // Point pHold = new Point(Math.round(hold.x), Math.round(hold.y));

            // float dist = (float) Angular.distEntre2Puntos(pHold, e.getPoint());
            float dist = (float) Angular.distEntre2Puntos(hold, e.getPoint());
            float hitArea = blanca.getRadius() + Cue.DISTANCE + Cue.LENGTH;

            if (dist < hitArea) {
                // release.setVector(e.getX(), e.getY());
                release = e.getPoint();
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (ballWasPressed) {
        // if (!table.hasMovement() && ballWasPressed) {
            // if (release.getMagnitude() == 0) {
            if (release == null) {
                // release.setVector(hold); // Bugfix de cuando no se arrastra el mouse (sino la bola se vuelve loca)
                release.setLocation(hold);
            }
            System.out.println("("+release.x+" - "+hold.x+", "+release.y+" - "+hold.y+")");
            System.out.println("("+(release.x - hold.x)+", "+(release.y - hold.y)+")");

            Vector2D vel = new Vector2D(release.x - hold.x, release.y - hold.y);
            
            // float angle = Angular.anguloPI(blanca.getLocation(), hold);
            // Point pVel = Angular.generaPunto(hold, release.getMagnitude(), angle);
            // Vector2D vel = new Vector2D((float)(pVel.getX() - hold.getX()), (float)(pVel.getY() - hold.getY()));

            System.out.println("vel: "+vel.getMagnitude());
            hitBall(vel);
                    
            // hold.escale(0);
            // release.escale(0);

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
        table.sendMouseInfo(e);
    }

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
