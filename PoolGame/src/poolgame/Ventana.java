package poolgame;

import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame {
    private Gameplay pp;
    
    public Ventana() {

        // Configurar ventana (JFrame)
        super("TEST");
        this.setIconImage(new ImageIcon(getClass().getResource("/img/icon.png")).getImage());
        this.setSize(1280 + 15, 720 + 40); //? Lo sumado corresponde a los margenes de la ventana
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);

        // Agregar pantallas del juego (menus y gameplay)
        this.pp = new Gameplay();
        this.add(pp, BorderLayout.CENTER);
        
        // Despues de configurar todo hacer visible la ventana
        this.setVisible(true);
    }
}
