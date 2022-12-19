package inputs;

import javax.swing.JButton;
import javax.swing.event.MouseInputListener;

import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Point;

import progra2.poolgame.GameState;
import progra2.poolgame.PoolGame;
import progra2.poolgame.Table;

import geometricas.Angular;
import geometricas.Vector2D;

//! Patron de diseño: Observer
public class PoolInputHandler implements MouseInputListener, KeyListener {
    private Table table;
    
    private final int maxHitForce;
    private final float angleDelta = 0.0075f;
    private Point pDirection;
    private Vector2D vForce;
    private float cueAngle;

    private boolean mouseDragged;

    public PoolInputHandler() {
        this.table = PoolGame.table;
        
        this.maxHitForce = Math.round(table.main.width * 0.1f);

        this.pDirection = new Point();
        this.vForce = new Vector2D();

        this.cueAngle = 1f;
    }

    //* Eventos
    @Override
    public void mousePressed(MouseEvent e) {
        if (PoolGame.state == GameState.PLAYING && !table.hasMovement() ) {
            pDirection = e.getPoint();
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        if (PoolGame.state == GameState.PLAYING && !table.hasMovement()) {
            mouseDragged = true;

            float dist = (float) Angular.distEntre2Puntos(pDirection, e.getPoint());

            if (dist < maxHitForce) {
                vForce.setVector((float)e.getX(), (float)e.getY());
                cueAngle = Angular.anguloPI(table.getCueBall().getLocation(), pDirection);

                table.updateCue(cueAngle, dist);
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (PoolGame.state == GameState.PLAYING && !table.hasMovement() && mouseDragged) {
            if (vForce.getMagnitude() == 0) {
                vForce.setVector((float)pDirection.getX(), (float)pDirection.getY()); // Bugfix de cuando no se arrastra el mouse (sino la bola se vuelve loca)
            }
            float angle = Angular.anguloPI(table.getCueBall().getLocation(), pDirection);
            
            Vector2D dragVec = new Vector2D(vForce.x - pDirection.x, vForce.y - pDirection.y);
            Point forceDirection = Angular.generaPunto(pDirection, dragVec.getMagnitude(), angle);
            
            Vector2D vel = new Vector2D((float)(forceDirection.getX() - pDirection.getX()), (float)(forceDirection.getY() - pDirection.getY()));
            System.out.println("Vel: "+vel.getMagnitude());
            
            table.getCue().shotBall(vel);
            table.updateCue(angle, 0);

            mouseDragged = false;
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        if (PoolGame.state == GameState.PLAYING && !table.hasMovement()) {
            cueAngle = Angular.anguloPI(table.getCueBall().getLocation(), e.getPoint());
            table.updateCue(cueAngle, 0);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (PoolGame.state == GameState.PLAYING || PoolGame.state == GameState.PAUSED) {
            if (!table.hasMovement() && PoolGame.state == GameState.PLAYING) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W     -> cueAngle -= 0.5f;
                    case KeyEvent.VK_UP    -> cueAngle -= 0.5f;
                    case KeyEvent.VK_S     -> cueAngle += 0.5f;
                    case KeyEvent.VK_DOWN  -> cueAngle += 0.5f;
                    case KeyEvent.VK_A     -> cueAngle += angleDelta;
                    case KeyEvent.VK_LEFT  -> cueAngle += angleDelta;
                    case KeyEvent.VK_D     -> cueAngle -= angleDelta;
                    case KeyEvent.VK_RIGHT -> cueAngle -= angleDelta;
                    case KeyEvent.VK_SPACE -> {
                        float x = (float)((maxHitForce*0.9f)*(Math.cos(cueAngle*Math.PI)));
                        float y = (float)((maxHitForce*0.9f)*(Math.sin(((cueAngle*Math.PI)+Math.PI))));
                        table.getCue().shotBall(new Vector2D(x, y));
                    }
                }
                table.updateCue(cueAngle, 0);
            }
            switch (e.getKeyCode()) {
                case KeyEvent.VK_R -> {
                    cueAngle = 1f;
                    PoolGame.getInstance().restartGame();
                }
                //? Hacer click en el botón de pausa de ip o pausar el juego con método de PoolGame
                // case KeyEvent.VK_P -> PoolGame.ip.getPauseButton().doClick();
                case KeyEvent.VK_P -> PoolGame.getInstance().pauseGame();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
