package progra2.poolgame;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class GameWindow extends JFrame {
    private GamePanel gamePanel;
    private InterfacePanel interfacePanel;

    public GameWindow(GameModes gameMode, int ballsNum) {
        super("PoolGame");
        
        // * CONFIGURAR JFRAME (VENTANA)
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIcon(); // Icono de la ventana
        this.asiggnESC(); // Asignar tecla "ESC" para cerrar juego
        
        // * AGREGAR JPANELS PRINCIPALES
        JPanel containerPanel = new JPanel();
        
        this.gamePanel = new GamePanel(gameMode, ballsNum);
        // this.interfacePanel = new InterfacePanel(this.gamePanel);
        containerPanel.add(gamePanel);
        // containerPanel.add(interfacePanel);

        this.add(containerPanel);
        this.pack();
        
        // * DESPUÉS DE CONFIGURAR, HACER VISIBLE LA VENTANA
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    
    public GamePanel getPanel() {
        return gamePanel;
    }

    private void setIcon() {
        try { 
            this.setIconImage(new ImageIcon(getClass().getResource("/resources/icon.png")).getImage());
        } catch (Exception e) { 
            System.out.println("Exception: Error al cargar icono de la ventana"); 
        }
    }

    private void asiggnESC() {
        KeyStroke esc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(esc, "ESC");
        this.getRootPane().getActionMap().put("ESC", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                setVisible(false);
                dispose();
                System.exit(0);
            }
        });
    }
}
