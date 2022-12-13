package progra2.poolgame;

import javax.swing.JPanel;

import java.awt.*;

import inputs.*;

public class GamePanel extends JPanel {
    private Table table;
    private SubGUI gui;

    private Integer fps;
    private Integer ups;

    public GamePanel() {
        // * Inicializar
        super(true);

        int tableWidth = 1400;
        int tableHeight = tableWidth/2;
        table = new Table(tableWidth, tableHeight);
        gui = new SubGUI();

        fps = 0;
        ups = 0;

        // * Listeners
        PoolInputHandler poolInputs = new PoolInputHandler(table);
        table.addMouseMotionListener(poolInputs);
        table.addMouseListener(poolInputs);
        table.addKeyListener(poolInputs);
        GUIHandler guiHandler = new GUIHandler(gui);
        //TODO Agregar listener a gui

        // * Configurar JPanel principal de juego
        this.setSize(1280, 720);
        this.setLayout(new BorderLayout());
        
        this.add(gui, BorderLayout.SOUTH);
        this.add(table, BorderLayout.CENTER);
        
        // System.out.println("Tamaño GamePanel: "+this.getWidth()+"x"+this.getHeight()); //? TEST
    }

    public void renderGame(Integer fps) {
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

        // * Dibujar todo
        super.paint(g2D);

        // * Info de monitorio (fps - ups)
        g2D.setColor(Color.GREEN);
        g2D.setFont(new Font("Arial", Font.BOLD, 16));
        String contFPS = "FPS: " + fps.toString();
        g2D.drawString(contFPS, 5, g2D.getFontMetrics().getHeight());
        String contUPS = "UPS: " + ups.toString();
        g2D.drawString(contUPS, this.getWidth() - g2D.getFontMetrics().stringWidth(contUPS) - 5, g2D.getFontMetrics().getHeight());

        Toolkit.getDefaultToolkit().sync(); // * Para solucionar problemas de fluidez
    }
    
    // * Getters
    public Table getTable() {
        return table;
    }
    public JPanel getSubGUI() {
        return gui;
    }
}
