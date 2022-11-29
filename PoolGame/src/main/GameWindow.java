package main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameWindow extends JFrame {

    public GameWindow(GamePanel gamePanel) {
        // Configurar ventana (JFrame)
        super("TEST");
        this.setIconImage(new ImageIcon(getClass().getResource("/resources/icon.png")).getImage());
        this.setSize(1280 + 15, 720 + 40); //? Lo sumado corresponde a los margenes de la ventana
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Agregar pantallas del juego (menus y gameplay)
        this.add(gamePanel);

        // Despues de configurar todo hacer visible la ventana
        this.setVisible(true);
    }
}
