package progra2.poolgame;

import java.awt.GraphicsEnvironment;

import javax.swing.JButton;

/*
 * STANDARD: Modo de juego con pool standard (16 bolas)
 *     - Singleplayer: Ganas el juego al meter todas las bolas antes de los 90 segundos.
 *                     Ganas puntaje al meter bolas, teniendo bonus por meter bolas seguidas.
 *                     +50 pts y +5seg por bola metida.
 *     - Multiplayer: El jugador 1 parte lanzado la bola blanco, se define si juega con lisas o rayadas dependiendo
 *                    de la cantidad de bolas que meta al principio (Si juega y mete una rayada juega con las rayadas, y asco
 *                    con las lisas), El jugador 2 tiene su turno cuando el jugador 1, meta la  blanca, no meta ninguna bola,
 *                    el jugador pierde cuando; El rival mete todas sus bolas o él mete la bola negra (La bola negra se mete
 *                    al final).
 * RANDOM: Modo de juego con pool random (bolas aleatorias)
 *     - Singleplayer: Ganas el juego al meter todas las bolas antes de los 90 segundos.
 *                     Ganas puntaje al meter bolas, teniendo bonus por meter bolas seguidas.
 *                    +50 pts y +5seg por bola metida.
 *     - Multiplayer: *Sin modalidad multiplayer*
 */

enum GameModes { STANDARD, STANDARD_MULTIPLAYER, RANDOM }

//! Patron de diseño "Singleton"
public class PoolGame implements Runnable {
    private static PoolGame game;

    private final int FPS_SET;
    private final int UPS_SET = 120;
    private Integer fps;
    private Integer ups;

    public static GameModes mode;
    public static GameState state;
    
    public static GamePanel gp;
    public static InterfacePanel ip;
    
    private Thread gameThread;
    
    // * Variables de juego
    private final int tableWidth = 1400;
    public static Table table;

    // private Player player1;
    // private Player player2;

    private PoolGame() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int hz = ge.getDefaultScreenDevice().getDisplayMode().getRefreshRate();
        FPS_SET = (hz > 120) ? 120 : hz;
        fps = FPS_SET;
        ups = UPS_SET;

        table = new Table(tableWidth);
        
        GameWindow gameWindow = new GameWindow();
        gp = gameWindow.getGamePanel();
        ip = gameWindow.getInterfacePanel();
        
        new MenuWindow();
        PoolGame.state = GameState.MENU;

        this.startGameLoop();
    }
    public static PoolGame getInstance() {
        if (game == null) {
            game = new PoolGame();
        }
        return game;
    }

    // * Métodos de juego
    public void openMenu() {
        new MenuWindow();
        PoolGame.state = GameState.MENU;
    }

    public void startGame(int ballsNum) {
        // * Inicializar jugadores dependiendo de modo de juego
        // player1 = new Player(null);
        // player2 = new Player(new Color(255, 0, 0));
        
        table.startGame(ballsNum);
        gp.requestFocusInWindow();
        PoolGame.state = GameState.PLAYING;
    }
    public void pauseGame() {
        JButton bPause = ip.getPauseButton();
        if (state == GameState.PLAYING) {
            this.pauseGame();
            bPause.setText("REANUDAR");
        } else if (state == GameState.PAUSED) {
            this.resumeGame();
            bPause.setText("PAUSA");
        }
    }
    public void resumeGame() {
        PoolGame.state = GameState.PLAYING;
    }
    public void restartGame() {
        PoolGame.state = GameState.MENU;
        table.clean();
        new MenuWindow();
    }
    
    // * Métodos de loop
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
        System.out.println("Game loop iniciado");
    }

    private void render() {
        gp.render(fps);
    }
    private void update() {
        gp.update(ups);

        // * Revisar termino de juego
        // if (gamePanel.isGameOver()) {
        //     this.state = GameState.GAME_OVER;
        //     gameWindow.gameOver();
        // }
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