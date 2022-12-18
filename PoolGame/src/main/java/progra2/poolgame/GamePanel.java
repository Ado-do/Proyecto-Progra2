package progra2.poolgame;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import inputs.*;

public class GamePanel extends JPanel {
    private final int tableWidth = 1500;
    private final GameModes gameMode;
    private int ballsNum;

    private Table table;
    private JLabel scoreLabel;
    // private JLabel ballsNumLabel;

    private Integer fps;
    private Integer ups;

    public boolean pause;

    //TODO GamePanel contendrá solo a Table y la dibujara

    public GamePanel(GameModes gameMode, int ballsNum) {
        // * Inicializar
        super(true);

        // * Inicializar campos
        this.gameMode = gameMode;
        this.ballsNum = ballsNum;

        this.table = new Table(tableWidth, tableWidth/2);
        this.fps = 0; 
        this.ups = 0;
        this.pause = false;

        // * Configurar JPanel
        this.setPreferredSize(new Dimension(tableWidth, tableWidth/2));
        this.addPoolListener();
        
        this.add(table);

        // * Iniciar modo de juego
        this.initGameMode();
    }

    private void addPoolListener() {
        // * Listeners
        PoolInputHandler poolInputs = new PoolInputHandler(this);
        this.addKeyListener(poolInputs);
        table.addMouseMotionListener(poolInputs);
        table.addMouseListener(poolInputs);
        table.addKeyListener(poolInputs);
    }

    public void initGameMode() {
        table.initGame(gameMode, ballsNum);
    }

    public void restartGame() {
        scoreLabel.setText("Score: 0");
        table.initGame(gameMode, ballsNum);
    }

    public void pauseGame() {
        if (!pause) {
            pause = true;
            table.setPaused(pause);
        } else {
            pause = false;
            table.setPaused(pause);
        }
    }

    public void renderGame(Integer fps) {
        this.fps = fps;
        this.repaint();
    }
    public void updateGame(Integer ups) {
        this.ups = ups;
        table.updateGame();

        //TODO updateGUI();
    }

    @Override
    public void paint(Graphics g) {
        //! Configurar Render (Graphics2D tiene métodos de dibujado mas útiles y complejos)
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        super.paint(g2D);

        // * FPS - UPS
        this.paintInfo(g2D);

        // * PAUSA
        if (pause) {
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

    // * Getters
    public Table getTable() {
        return table;
    }
    public JLabel getScoreLabel() {
        return scoreLabel;
    }
}
