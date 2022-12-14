package progra2.poolgame;

import java.awt.GraphicsEnvironment;

//! Patron de diseño "Singleton"

public class PoolGame implements Runnable {
    private static PoolGame game;

    public enum Players { ONE, TWO };
    public enum Modes { STANDARD, RANDOM };

    public Players players;
    public Modes mode;

    private final int FPS_SET;
    private final int UPS_SET = 120;

    private Integer fps;
    private Integer ups;

    private MenuWindow  menuWindow;
    private GameWindow  gameWindow;
    private GamePanel   gamePanel;
    private Thread      gameThread;

    private PoolGame() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int hz = ge.getDefaultScreenDevice().getDisplayMode().getRefreshRate();
        FPS_SET = (hz > 120) ? 120 : hz;

        fps = FPS_SET;
        ups = UPS_SET;

        menuWindow = new MenuWindow();
    }

    public static PoolGame getInstance() {
        if (game == null) {
            game = new PoolGame();
        }
        return game;
    }

    public void startGame(Players players, Modes mode, int ballsNum) {
        this.players = players;
        this.mode = mode;

        gameWindow = new GameWindow(players, mode, ballsNum);
        gamePanel = gameWindow.getPanel();
        gamePanel.requestFocusInWindow();

        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
        System.out.println("Game loop iniciado");
    }

    private void render() {
        gamePanel.renderGame(fps);
    }
    private void update() {
        gamePanel.updateGame(ups);
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET; // En nanoseg, para mas precision (1 millseg = 1.000.000.000 nanosegs)
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previusTime = System.nanoTime();
        long timeLastCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        double deltaU = 0;
        double deltaF = 0;

        boolean running = true;
        // * GAMELOOP
        while (running) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previusTime) / timePerUpdate;
            deltaF += (currentTime - previusTime) / timePerFrame;
            previusTime = currentTime;

            // * Update
            if (deltaU >= 1) {
                update();

                updates++;
                deltaU--;
            }

            // * Render
            if (deltaF >= 1) {
                render();

                frames++;
                deltaF--;
            }

            // * Revisar intervalo de print de FPS
            long currentTimeMilli = System.currentTimeMillis();
            if (currentTimeMilli - timeLastCheck >= 1000) { // * Intervalo de tiempo en que se muestran FPS (1000mills = 1s)
                timeLastCheck = System.currentTimeMillis();

                fps = frames;
                ups = updates;
                
                // System.out.println("FPS: " + frames + " | UPS: " + updates);

                frames = 0;
                updates = 0;
            }
        }
    }
}