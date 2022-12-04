package progra2.poolgame;

public class PoolGame implements Runnable {
    private final int FPS_SET = 60;
    // private final int FPS_SET = 120;
    private final int UPS_SET = 60;

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;

    public PoolGame() {
        gameWindow = new GameWindow();
        gamePanel = gameWindow.getPanel();
        gamePanel.requestFocusInWindow();

        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void update() {
        gamePanel.updateGame();
    }

    private void render() {
        gamePanel.repaint();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET; // En nanoseg, para mas precision (1 millseg = 1.000.000.000 nanosegs)
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previusTime = System.nanoTime();
     
        int frames = 0;
        int updates = 0;
        long timeLastCheck = System.currentTimeMillis();

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

            // * Frame
            if (deltaF >= 1) {
                render();

                frames++;
                deltaF--;
            }

            // * Revisar intervalo de print de FPS
            long currentTimeMilli = System.currentTimeMillis();
            if (currentTimeMilli - timeLastCheck >= 1000) { // * Intervalo de tiempo en que se muestran FPS (1000mills = 1s)
                timeLastCheck = System.currentTimeMillis();

                System.out.println("FPS: " + frames + " | UPS: " + updates);

                frames = 0;
                updates = 0;
            }
        }
    }
}