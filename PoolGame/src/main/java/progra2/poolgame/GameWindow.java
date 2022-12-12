package progra2.poolgame;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class GameWindow extends JFrame {
    private GamePanel gamePanel;

    public GameWindow() {
        super("PoolGame");
        this.gamePanel = new GamePanel();

        // * ICONO
        try { this.setIconImage(new ImageIcon(getClass().getResource("/resources/icon.png")).getImage());
        } catch (Exception e) { System.out.println("Exception: Error al cargar icono de la ventana"); };

        // * CONFIGURAR JFRAME (VENTANA)
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // * ASIGNAR TECLA "ESC" PARA CERRAR JUEGO
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

        // * AGREGAR JPANEL PRINCIPAL DEL JUEGO
        this.add(gamePanel);
        this.pack();
        System.out.println("Tamaño GameWindow: "+this.getWidth()+"x"+this.getHeight());
        
        // * DESPUÉS DE CONFIGURAR, HACER VISIBLE LA VENTANA
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public GamePanel getPanel() {
        return gamePanel;
    }
}
