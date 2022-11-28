package main;

public class PoolGame {
    private GameWindow gameWindow;
    private GamePanel gamePanel;

    public PoolGame() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
    }
}
