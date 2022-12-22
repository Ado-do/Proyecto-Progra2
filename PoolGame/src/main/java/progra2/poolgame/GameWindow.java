package progra2.poolgame;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

/**
 * Clase que representa la ventana principal del juego
 * @author Alonso Bustos
 */
public class GameWindow extends JFrame {
    private GamePanel gamePanel;
    private InterfacePanel interfacePanel;

    /**
     * Constructor de la ventana principal del juego, configura la ventana y agrega los paneles principales
     */
    public GameWindow() {
        super("PoolGame");
        
        // * CONFIGURAR JFRAME (VENTANA)
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIcon(); // Icono de la ventana
        this.asiggnESC(); // Asignar tecla "ESC" para cerrar juego

        // * AGREGAR JPANELS PRINCIPALES
        this.gamePanel = new GamePanel();
        this.interfacePanel = new InterfacePanel();
        
        this.add(gamePanel, BorderLayout.CENTER);
        this.add(interfacePanel, BorderLayout.SOUTH);
        this.pack();

        // * DESPUÉS DE CONFIGURAR, HACER VISIBLE LA VENTANA
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    /**
     * Método que retorna el panel de juego
     * 
     * @return Panel de juego que contiene la mesa y las bolas
     */
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    /**
     * Método que retorna el panel de interfaz
     * 
     * @return Panel de interfaz que contiene los botones y la información del juego
     */
    public InterfacePanel getInterfacePanel() {
		return interfacePanel;
	}

    private void setIcon() {
        try { this.setIconImage(new ImageIcon(getClass().getResource("/resources/icon.png")).getImage());
        } catch (Exception e) { System.out.println(e + ": Error al cargar icono de la ventana"); }
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
