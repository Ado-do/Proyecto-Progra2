package progra2.poolgame;

public class PoolGame implements Runnable {
    // private final int FPS_CAP = 60;
    private final int FPS_CAP = 120;

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;

    public PoolGame() {
        gameWindow = new GameWindow();
        gamePanel = gameWindow.getPanel();
        gamePanel.requestFocus();

        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_CAP; // En nanoseg, para mas precision (1 millseg = 1.000.000.000 nanosegs)

        long timeNowNano;
        long timeLastFrame = System.nanoTime();

        long timeNowMillis;
        long timeLastCheck = System.currentTimeMillis();
        
        int framesPerSecond = 0;
        boolean running = true;
        
        // * GAMELOOP
        while (running) {
            // * Revisar intervalo de frame
            timeNowNano = System.nanoTime();
            if (timeNowNano - timeLastFrame >= timePerFrame) {
                gamePanel.repaint(); // * Crear frame
                framesPerSecond++;
                timeLastFrame = timeNowNano;
            }
            // * Revisar intervalo de print de FPS
            timeNowMillis = System.currentTimeMillis();
            if (timeNowMillis - timeLastCheck >= 1000) { // * Intervalo de tiempo en que se muestran FPS (1000mills = 1s)
                timeLastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + framesPerSecond);
                framesPerSecond = 0;
            }
        }
    }
}