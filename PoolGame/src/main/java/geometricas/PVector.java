package geometricas;

public class PVector {
    public float x, y;
    
    public PVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void escalar(float esc) { // Prueba, con la misma intención 
        x *= esc;
        y *= esc;
    }

    public void normalizar() {
        float mag = getMagnitud();
        x /= mag;
        y /= mag;
    }

    public float getMagnitud() {
        return (float)Math.sqrt((x * x) + (y * y));
    }
}
