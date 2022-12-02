package progra2.poolgame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameWindow extends JFrame {
    private GamePanel gamePanel;

    public GameWindow() {
        super("TEST");
        gamePanel = new GamePanel(this);

        // * Icono
        try { this.setIconImage(new ImageIcon(getClass().getResource("/resources/icon.png")).getImage());
        } catch (Exception e) { System.out.println("Exception: Error al cargar icono de la ventana"); };

        // * Configurar JFrame (Ventana)
        // this.setSize(1280 + 14, 720 + 39); //? Lo sumado corresponde a los margenes de la ventana (Total: 1296x759)
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    //! Detener ejecución y cerrar ventana
                    setVisible(false);
                    dispose();
                    System.exit(0);
                }
            }
        });
        
        // * Agregar pantallas del juego (menus y gameplay)
        this.add(gamePanel);
        this.pack();
        System.out.println("Tamaño GameWindow: "+this.getWidth()+"x"+this.getHeight());
        
        // * Después de configurar todo hacer visible la ventana
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public GamePanel getPanel() {
        return gamePanel;
    }
}
