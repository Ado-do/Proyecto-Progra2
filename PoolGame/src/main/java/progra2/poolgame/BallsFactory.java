package progra2.poolgame;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Rectangle;

/**
 * Clase que genera bolas y las posiciona
 * Patron de diseño: Factory
 * 
 * @author Alonso Bustos
 */
public class BallsFactory {
    private final Rectangle main;
    private final Rectangle play;
    private final int radius;
    private final int diam;

    /**
     * Método constructor para la clase BallsFactory
     * 
     * @param main      Rectángulo principal de la mesa
     * @param playfield Rectángulo del campo de juego
     */
    public BallsFactory(Rectangle main, Rectangle playfield) {
        this.main = main;
        this.play = playfield;
        radius = Math.round((main.width * 0.025f) / 2);
        diam = radius * 2;
    }

    /**
     * Método que genera las bolas en posición ordenada según modalidad estándar de pool
     * y las agrega a un ArrayList de bolas
     * 
     * @param arrayBalls ArrayList de bolas el cual se limpia y se le agregarán las bolas
     */
    public void getRackedBalls(ArrayList<Ball> arrayBalls) {
        arrayBalls.clear();

        int b0X = play.x + play.width / 4;
        int b0Y = main.height / 2;
        int b1X = Math.round(main.width - (play.x + (play.width * 0.25f)));
        int b1Y = Math.round(main.height / 2f);
        // Crear y agregar bolas ordenadas
        for (int i = 0; i < 6; i++) {
            switch (i) {
            case 0: // Blanca
                arrayBalls.add(new Ball(b0X, b0Y, radius, 0));
                break;
            case 1: // Bola 1
                arrayBalls.add(new Ball(b1X, b1Y, radius, 1));
                break;
            default: // Filas de bolas aleatorias
                int deltaX = Math.round((radius * 1.8f)); // Diferencia entre coordX de bolas
                for (int j = 0; j < i; j++) {
                    int num = (i == 3 && j == 1) ? 8 : randomNum(arrayBalls); // Asegurar que bola 8 esta en medio
                    arrayBalls.add(
                            new Ball(b1X + deltaX * (i - 1), (b1Y - radius * (i - 1)) + (diam + 1) * j, radius, num));
                }
                break;
            }
        }
    }

    /**
     * Método que genera bolas aleatorias y las agrega a un ArrayList de bolas
     * 
     * @param arrayBalls ArrayList de bolas el cual se limpia y se le agregarán las bolas
     * @param n          Cantidad de bolas a generar
     */
    public void getRandomBalls(ArrayList<Ball> arrayBalls, int n) {
        arrayBalls.clear();

        // Crear y asignar random
        for (int i = 0; i < n; i++) {
            Ball b;
            if (i == 0) {
                b = new Ball(main.width / 2, main.height / 2, radius, i);
            } else {
                b = new Ball(0, 0, radius, i);
                Ball.setRandomLocation(b);
            }
            arrayBalls.add(b);
        }
    }

    private int randomNum(ArrayList<Ball> array) {
        Random rand = new Random();
        boolean repeated;
        int randNum;
        do {
            repeated = false;
            randNum = rand.nextInt(16 - 2) + 2;
            if (randNum == 8) {
                repeated = true;
            } else {
                for (Ball ball : array) {
                    if (randNum == ball.getNumber()) {
                        repeated = true;
                        break;
                    }
                }
            }
        } while (repeated);

        return randNum;
    }
}
