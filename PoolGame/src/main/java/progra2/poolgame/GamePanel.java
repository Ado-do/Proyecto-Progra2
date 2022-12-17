package progra2.poolgame;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
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
    private GameGUI gui;
    private JLabel scoreLabel;
    // private JLabel ballsNumLabel;

    private Integer fps;
    private Integer ups;

    public boolean pause;

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
        this.setLayout(new BorderLayout());
        this.addPoolListener();
        
        JPanel mainGUI = new JPanel(new BorderLayout());
        mainGUI.setPreferredSize(new Dimension(0, 100));
        this.add(table, BorderLayout.CENTER);
        this.add(mainGUI, BorderLayout.SOUTH);

        // * Agregar componentes a mainGUI
        this.addMainGUIComponents(mainGUI, gameMode);

        // * Iniciar modo de juego
        this.initGameMode();
    }
    private void addMainGUIComponents(JPanel main, GameModes gameMode) {
        // * Pausa
        JButton pausa = new JButton("PAUSA");
        pausa.setPreferredSize(new Dimension(tableWidth/6, 0));
        pausa.setFont(new Font("Arial", Font.PLAIN, Math.round(tableWidth * 0.025f)));
        pausa.setFocusable(false);
        pausa.addActionListener(e -> pauseGame());

        main.add(pausa, BorderLayout.WEST);

        // * Reiniciar
        JButton restart = new JButton("REINICIAR");
        restart.setFont(new Font("Arial", Font.PLAIN, Math.round(tableWidth * 0.025f)));
        restart.setPreferredSize(new Dimension(tableWidth/6, 0));
        restart.setFocusable(false);
        restart.addActionListener(e -> restartGame());
        main.add(restart, BorderLayout.EAST);

        // * GUI de juego
        switch (gameMode) {
            // case STANDARD -> gui = new StandardGameGUI();
            case STANDARD -> gui = new GameGUI();
            // case RANDOM   -> gui = new RandomGameGUI();
            case RANDOM   -> gui = new GameGUI();
            case RANDOM_MULTIPLAYER -> throw new UnsupportedOperationException("Unimplemented case: " + gameMode);
            case STANDARD_MULTIPLAYER -> throw new UnsupportedOperationException("Unimplemented case: " + gameMode);
            default -> throw new IllegalArgumentException("Unexpected value: " + gameMode);
        }
        main.add(gui, BorderLayout.CENTER);

        scoreLabel = gui.getScoreLabel();
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

        updateGUI();
    }
    private void updateGUI() {
        scoreLabel.setText("Score: " + table.getScore());
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
        this.paintInfo(g2D);

        if (pause) {
            g2D.setColor(Color.WHITE);
            g2D.setFont(new Font("Arial", Font.BOLD, 50));
            g2D.drawString("PAUSA", this.getWidth()/2 - g2D.getFontMetrics().stringWidth("PAUSA")/2, this.getHeight()/2);
        }

        Toolkit.getDefaultToolkit().sync(); // Para solucionar problemas de fluidez
    }
    private void paintInfo(Graphics2D g2D) {
        // Info de monitorio (fps - ups)
        g2D.setColor(Color.GREEN);
        g2D.setFont(new Font("Arial", Font.BOLD, 16));
        String contFPS = "FPS: " + fps.toString();
        g2D.drawString(contFPS, 5, g2D.getFontMetrics().getHeight());
        String contUPS = "UPS: " + ups.toString();
        g2D.drawString(contUPS, this.getWidth() - g2D.getFontMetrics().stringWidth(contUPS) - 5, g2D.getFontMetrics().getHeight());
    }

    // * Getters
    public Table getTable() {
        return table;
    }
    public JPanel getSubGUI() {
        return gui;
    }
    public JLabel getScoreLabel() {
        return scoreLabel;
    }
}
