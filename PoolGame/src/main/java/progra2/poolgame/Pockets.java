package progra2.poolgame;

import java.util.ArrayList;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Color;

import geometricas.Angular;
import geometricas.Circle;

/**
 * Clase que representa las troneras de la mesa
 * 
 * @author Alonso Bustos
 */
public class Pockets {
    private ArrayList<Circle> arrayPockets;
    private ArrayList<Ball> ballsInHole;

    /**
     * Constructor de la clase que inicializa arraylists y posiciona las troneras en la mesa
     * 
     * @param main      Rectángulo que representa el área de la mesa junto sus bordes
     * @param playfield Rectángulo que representa el área de juego
     */
    public Pockets(Rectangle main, Rectangle playfield) {
        this.arrayPockets = new ArrayList<Circle>();
        this.ballsInHole = new ArrayList<Ball>();

        int pocketRadius = Math.round((main.width * 0.06f) / 2);
        int offsetX = (main.width/2) - playfield.x;
        float correctionMid = 0.2f;

        for (int i = 0; i < 3; i++) { // Top
            int posY = (i != 1) ? (playfield.y) : (playfield.y - Math.round(pocketRadius*correctionMid));
            arrayPockets.add(new Circle(playfield.x + (offsetX*i), posY, pocketRadius));
        }
        for (int i = 0; i < 3; i++) { // Bottom
            int posY = (i != 1) ? (main.height - playfield.y) : ((main.height - playfield.y) + Math.round(pocketRadius*correctionMid));
            arrayPockets.add(new Circle(playfield.x + (offsetX*i), posY, pocketRadius));
        }
    }

    /**
     * Método que verifica si una bola está dentro de una tronera
     * 
     * @param ball Bola a verificar
     * @return true si la bola está dentro de una tronera, false en caso contrario
     */
    public boolean isPocketed(Ball ball) {
        Point pBall = ball.getLocation();
        Point pPocket;
        float dist;

        for (Circle pocket : arrayPockets) {
            pPocket = pocket.getLocation();
            dist = (float)Angular.distEntre2Puntos(pBall, pPocket);
            if (dist < pocket.getRadius()) 
                return true;
        }
        return false;
    }

    /**
     * Método que recibe una bola y la agrega a la lista de bolas en tronera
     * 
     * @param ball Bola a agregar
     */
    public void receive(Ball ball) {
        ballsInHole.add(ball);
    }
    
    /**
     * Método que retorna una lista de bolas de un tipo específico
     * 
     * @param type Tipo de bola a buscar
     * @return Lista de bolas del tipo especificado
     */
    public ArrayList<Ball> getBallsOfType(BallType type) {
        ArrayList<Ball> balls = new ArrayList<Ball>();
        for (Ball ball : ballsInHole) {
            if (ball.getBallType() == type) {
                balls.add(ball);
            }
        }
        for (Ball ball : ballsInHole) {
            if (ball.getBallType() == type) {
                ballsInHole.remove(ball);
            }
        }
        return balls;
    }
    
    /**
     * Método que retorna una lista de todas las bolas en tronera
     * 
     * @return ArrayList de bolas en tronera
     */
    public ArrayList<Ball> getAllBalls() {
        ArrayList<Ball> balls = new ArrayList<Ball>();
        balls.addAll(ballsInHole); // Pasar bolas a arreglo que se retornará
        ballsInHole.clear(); // Limpiar arreglo de bolas
        return balls;
    }

    /**
     * Método que dibuja las troneras en la mesa
     * 
     * @param g2D Objeto Graphics2D para dibujar
     */
    public void paint(Graphics2D g2D) {
        for (Circle pocket : arrayPockets) {
            pocket.fillCircle(g2D, Color.darkGray);
            pocket.drawCircle(g2D, Color.black);
        }
    }
}
