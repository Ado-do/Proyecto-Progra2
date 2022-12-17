package inputs;

import javax.swing.event.MouseInputListener;

import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Point;

import progra2.poolgame.GamePanel;
import progra2.poolgame.Table;

import geometricas.Angular;
import geometricas.Vector2D;

//TODO Separar en PoolMouseHandler y PoolKeyboardListener

public class PoolInputHandler implements MouseInputListener, KeyListener {
    private GamePanel gamePanel;
    private Table table;
    
    private final int maxHitForce;
    private final float angleDelta = 0.0015f;
    private Point pDirection;
    private Vector2D pForce;
    private float cueAngle;

    public PoolInputHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.table = gamePanel.getTable();
        
        this.maxHitForce = Math.round(table.main.width * 0.075f);

        this.pDirection = new Point();
        this.pForce = new Vector2D();

        this.cueAngle = 1f;
    }

    //* Eventos
    //TODO Bugfix de cuando no se arrastra el mouse (bola ultrarapida)
    @Override
    public void mousePressed(MouseEvent e) {
        if (table.isInGame() && !table.hasMovement() && !table.isPaused()) {
            pDirection = e.getPoint();
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        if (table.isInGame() && !table.hasMovement() && !table.isPaused()) {
            float dist = (float) Angular.distEntre2Puntos(pDirection, e.getPoint());

            if (dist < maxHitForce) {
                pForce.setVector((float)e.getX(), (float)e.getY());
                cueAngle = Angular.anguloPI(table.getCueBall().getLocation(), pDirection);

                table.updateCue(cueAngle, dist);
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) { //! BUG
        if (table.isInGame() && !table.hasMovement() && !table.isPaused()) {
            if (pForce.getMagnitude() == 0) {
                pForce.setVector((float)pDirection.getX(), (float)pDirection.getY()); // Bugfix de cuando no se arrastra el mouse (sino la bola se vuelve loca)
            }
            float angle = Angular.anguloPI(table.getCueBall().getLocation(), pDirection);

            Vector2D dragVec = new Vector2D(pForce.x - pDirection.x, pForce.y - pDirection.y);
            Point forceDirection = Angular.generaPunto(pDirection, dragVec.getMagnitude(), angle);

            Vector2D vel = new Vector2D((float)(forceDirection.getX() - pDirection.getX()), (float)(forceDirection.getY() - pDirection.getY()));
            System.out.println("Vel: "+vel.getMagnitude());

            table.getCue().hitBall(vel);
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        if (table.isInGame() && !table.hasMovement() && !table.isPaused()) {
            cueAngle = Angular.anguloPI(table.getCueBall().getLocation(), e.getPoint());
            table.updateCue(cueAngle, 0);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (table.isInGame() && !table.hasMovement() && !table.isPaused()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A     -> cueAngle += angleDelta;
                case KeyEvent.VK_LEFT  -> cueAngle += angleDelta;
                case KeyEvent.VK_D     -> cueAngle -= angleDelta;
                case KeyEvent.VK_RIGHT -> cueAngle -= angleDelta;
                case KeyEvent.VK_SPACE -> {
                    float x = (float)(maxHitForce*Math.cos(cueAngle*Math.PI));
                    float y = (float)(maxHitForce*Math.sin(((cueAngle*Math.PI)+Math.PI)));
                    table.getCue().hitBall(new Vector2D(x, y));
                }
                case KeyEvent.VK_R -> gamePanel.restartGame();
                case KeyEvent.VK_P -> gamePanel.pauseGame();
            }
            table.updateCue(cueAngle, 0);
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
