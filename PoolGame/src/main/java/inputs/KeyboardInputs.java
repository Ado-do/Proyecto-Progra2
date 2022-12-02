package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import progra2.poolgame.GamePanel;

public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                gamePanel.getWindow().setVisible(false);
                gamePanel.getWindow().dispose();
                System.exit(0);
                break;
            case KeyEvent.VK_H:
                System.out.println("HOLAAAAA");
                break;
            default: break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }    
}
