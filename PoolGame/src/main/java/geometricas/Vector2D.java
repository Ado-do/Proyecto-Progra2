package geometricas;

import java.awt.Point;

public class Vector2D {
    public float x, y;
    
    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Vector2D(Vector2D vec) {
        setVector(vec);
    }
    public Vector2D(Point p) {
        this.x = (float)p.getX();
        this.y = (float)p.getY();
    }

    public void setVector(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public void setVector(Vector2D vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public float getMagnitude() {
        return (float)(Math.sqrt((x * x) + (y * y)));
    }
    public void toUnitVector() { // Convertir en vector unitario (señala direcciones)
        float mag = getMagnitude();
        setVector(x / mag, y / mag);
    }
    public void escale(float esc) {
        setVector(x * esc, y *esc);
    }

    public void addVector(Vector2D vec) {
        x += vec.x;
        y += vec.y;
    }
    public void subtractVector(Vector2D vec) {
        x -= vec.x;
        y -= vec.y;
    }

    // Para calcular coseno de vectores unitarios
    public float dotProduct(Vector2D v2) {
        return ((this.x * v2.x) + (this.y * v2.y));
    }
}
