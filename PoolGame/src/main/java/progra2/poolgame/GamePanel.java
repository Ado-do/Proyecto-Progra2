package progra2.poolgame;

import javax.swing.*;
import java.awt.*;

import inputs.*;

public class GamePanel extends JPanel {
    private GameWindow gameWindow;

    private PoolTable table;
    private SubGUI gui;

    private Integer fps;
    private Integer ups;

    public GamePanel(GameWindow gameWindow) {
        // * Inicializar
        super(true);
        this.gameWindow = gameWindow;

        int tableWidth = 1400;
        int tableLength = tableWidth/2;
        table = new PoolTable(tableWidth, tableLength);
        gui = new SubGUI();

        //* Listeners
        PoolInputHandler poolInputs = new PoolInputHandler(table);
        table.addMouseMotionListener(poolInputs);
        table.addMouseListener(poolInputs);
        table.addKeyListener(poolInputs);

        fps = 0;
        ups = 0;

        // * Configurar JPanel principal de juego
        this.setSize(1280, 720);
        this.setLayout(new BorderLayout());
        
        this.add(gui, BorderLayout.SOUTH);
        this.add(table, BorderLayout.CENTER);
        
        // System.out.println("Tamaño GamePanel: "+this.getWidth()+"x"+this.getHeight()); //? TEST
    }

    public void render(Integer fps) {
        this.fps = fps;

        this.repaint();
    }

    public void updateGame(Integer ups) {
        this.ups = ups;

        table.updateGame();
    }
    
    @Override
    public void paint(Graphics g) {
        //! Configurar Render (Graphics2D tiene métodos de dibujado mas útiles y complejos)
        Graphics2D g2D = (Graphics2D) g;

        // Para hacer los bordes de los dibujos mas suaves
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHints(rh);

        //* Dibujar
        super.paint(g2D);

        g2D.setColor(Color.BLACK);
        g2D.setFont(new Font("Arial", Font.BOLD, 16));
        String contFPS = "FPS: " + fps.toString();
        g2D.drawString(contFPS, 5, g2D.getFontMetrics().getHeight());
        String contUPS = "UPS: " + ups.toString();
        g2D.drawString(contUPS, this.getWidth() - g2D.getFontMetrics().stringWidth(contUPS) - 5, g2D.getFontMetrics().getHeight());

        Toolkit.getDefaultToolkit().sync(); //* Para solucionar problemas de fluidez
    }
    
    //* Getters
    public PoolTable getTable() {
        return table;
    }
    public JPanel getSubGUI() {
        return gui;
    }
    public GameWindow getWindow() {
        return gameWindow;
    }
}
