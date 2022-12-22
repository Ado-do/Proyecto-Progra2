package geometricas;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Clase que representa un circulo en un espacio 2D,
 * para que el calculo de colisiones sea más sencillo
 * 
 * @author Lirayen Martinez
 */
public class Circle {
    public float x, y;
    protected int radius;
    protected int diameter;
    public Rectangle bounds;

    /**
     * Constructor que inicializa el circulo con una ubicación y radio especifica,
     * junto con su rectángulo de colisión
     * 
     * @param x      posicion x del circulo
     * @param y      posicion y del circulo
     * @param radius radio del circulo
     */
    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.diameter = radius * 2;
        this.bounds = new Rectangle(x-radius, y-radius, diameter, diameter);
    }

    /**
     * Método que sincronizar el rectángulo de colisión con la ubicación del circulo
     */
    protected void syncBounds() {
        bounds.setLocation(Math.round(x - radius), Math.round(y - radius));
    }

    /**
     * Retorna el radio de la bola
     * 
     * @return radio de la bola
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Método que calcula el area del circulo y retorna el area del circulo
     * 
     * @return area del circulo
     */
    public float getArea() {
        return (float)(Math.PI * (radius * radius));
    }

    /**
     * Método que calcula del perímetro del circulo y
     * retorna perímetro del circulo
     * 
     * @return perímetro del circulo
     */
    public float getPerimeter() {
        return (float)(2 * Math.PI * radius);
    }

    /**
     * Método que retorna la ubicación del circulo
     * 
     * @return ubicación del circulo
     */
    public Point getLocation() {
        return new Point(Math.round(x), Math.round(y));
    }

    /**
     * Método que retorna el rectángulo de colisión
     * 
     * @return rectángulo de colisión
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Método que asigna la ubicación del circulo
     * 
     * @param x posicion x del circulo
     * @param y posicion y del circulo
     */
    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
        syncBounds();
    }

    /**
     * Método que asigna la ubicación del circulo
     * 
     * @param p ubicación del circulo
     */
    public void setLocation(Point p) {
        this.x = (float)p.getX();
        this.y = (float)p.getY();
        syncBounds();
    }

    /**
     * Método que dibuja el contorno del circulo con un color especifico
     * 
     * @param c color especificado
     */
    public void drawCircle(Graphics2D g2D, Color color) {
        g2D.setColor(color);
        g2D.drawOval(Math.round(x - radius), Math.round(y - radius), diameter, diameter);
    }

    /**
     * Método que dibuja el circulo relleno con un color especifico
     * 
     * @param c color especificado
     */
    public void fillCircle(Graphics2D g2D, Color c) {
        g2D.setColor(c);
        g2D.fillOval(Math.round(x - radius), Math.round(y - radius), diameter, diameter);
    }
}
