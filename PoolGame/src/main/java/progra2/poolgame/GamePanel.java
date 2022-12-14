package progra2.poolgame;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import inputs.*;

/**
 * Clase que crea el panel donde se dibujará la mesa y las bolas
 * @author Alonso Bustos
 */
public class GamePanel extends JPanel {
    private Table table;

    private Integer fps;
    private Integer ups;
    
    /**
     * Constructor del panel de juego, inicializa los campos y configura el panel
     */
    public GamePanel() {
        // * Inicializar
        super(true);

        // * Inicializar campos
        this.table = PoolGame.table;
        this.fps = 0;
        this.ups = 0;

        // * Configurar JPanel
        this.setPreferredSize(new Dimension(table.main.width, table.main.height));
        this.addPoolListener();
    }

    private void addPoolListener() {
        // * Listeners
        PoolInputHandler poolInputs = new PoolInputHandler();
        this.addMouseMotionListener(poolInputs);
        this.addMouseListener(poolInputs);
        this.addKeyListener(poolInputs);
    }

    /**
     * Método que dibuja frame de la mesa
     * 
     * @param fps Frames por segundo
     */
    public void render(Integer fps) {
        this.fps = fps;
        this.repaint();
    }

    /**
     * Método que actualiza lógica de pool
     * 
     * @param ups Actualizaciones por segundo
     */
    public void update(Integer ups) {
        this.ups = ups;

        if (PoolGame.state == GameState.PLAYING) {
            table.update();
        }
    }

    /**
     * Método que dibuja el frame (mesa y información de monitorio)
     * 
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        //! Configurar Render (Graphics2D tiene métodos de dibujado mas útiles y complejos)
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        // * Dibujar mesa
        table.paint(g2D);

        // * FPS - UPS
        this.paintInfo(g2D);

        // * PAUSA
        if (PoolGame.state == GameState.PAUSED) {
            g2D.setColor(Color.WHITE);
            g2D.setFont(new Font("Arial", Font.BOLD, 50));
            g2D.drawString("PAUSA", this.getWidth()/2 - g2D.getFontMetrics().stringWidth("PAUSA")/2, this.getHeight()/2);
        }

        Toolkit.getDefaultToolkit().sync(); // Para solucionar problemas de fluidez
    }
    private void paintInfo(Graphics2D g2D) {
        // * Info de monitorio (fps - ups)
        g2D.setColor(Color.black);
        g2D.setFont(new Font("Arial", Font.BOLD, 16));
        String contFPS = "FPS: " + fps.toString();
        g2D.drawString(contFPS, 5, g2D.getFontMetrics().getHeight()+2);
        String contUPS = "UPS: " + ups.toString();
        g2D.drawString(contUPS, this.getWidth() - g2D.getFontMetrics().stringWidth(contUPS) - 5, g2D.getFontMetrics().getHeight()+2);
    }
}
