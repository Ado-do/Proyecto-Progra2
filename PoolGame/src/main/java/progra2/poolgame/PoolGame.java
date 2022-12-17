package progra2.poolgame;

import java.awt.GraphicsEnvironment;


enum GameModes { STANDARD, RANDOM, STANDARD_MULTIPLAYER, RANDOM_MULTIPLAYER }

//! Patron de diseño "Singleton"
public class PoolGame implements Runnable {
    private static PoolGame game;

    //TODO Acceder a este campo desde cualquier clase para consultar el modo de juego actual
    public static GameModes gameMode; 

    private final int FPS_SET;
    private final int UPS_SET = 120;

    private Integer fps;
    private Integer ups;

    private GameWindow  gameWindow;
    private GamePanel   gamePanel;
    private Thread      gameThread;

    private PoolGame() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int hz = ge.getDefaultScreenDevice().getDisplayMode().getRefreshRate();
        FPS_SET = (hz > 120) ? 120 : hz;

        fps = FPS_SET;
        ups = UPS_SET;

        new MenuWindow();
    }

    public static PoolGame getInstance() {
        if (game == null) {
            game = new PoolGame();
        }
        return game;
    }

    public void startGame(GameModes gameMode, int ballsNum) {
        gameWindow = new GameWindow(gameMode, ballsNum);
        gamePanel = gameWindow.getPanel();
        gamePanel.requestFocusInWindow();

        this.startGameLoop();
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

        //! Patron de diseño "Game loop"
        /* //* GAME LOOP
         * El game loop es el bucle principal del juego, en el cual se ejecutan los métodos de renderizado y actualización
         * del juego, ademas de controlar el tiempo de ejecución de estos métodos. El game loop se ejecuta en un hilo
         * aparte del hilo principal de la aplicación, por lo que no afecta el rendimiento de la interfaz gráfica.
         * Este controla el tiempo de ejecución de los métodos de renderizado y actualización del juego, para asegurar 
         * que se ejecuten en el tiempo establecido, y no se ejecuten mas veces de lo necesario (si no se limitara, 
         * se ejecutarían tantas veces como el procesador pudiera, lo que afectaría el rendimiento del juego, haciéndolo menos estable)
         */
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

            // Intervalo de tiempo en que se muestran FPS (1000mills = 1s)
            if (currentTimeMilli - timeLastCheck >= 1000) {
                timeLastCheck = System.currentTimeMillis();

                fps = frames;
                ups = updates;
                
                frames = 0;
                updates = 0;
            }
        }
    }
}