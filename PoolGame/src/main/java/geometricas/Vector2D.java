package geometricas;

import java.awt.Point;

/**
 * Clase que representa un vector en un espacio 2D, para que 
 * el calculo de velocidades y posiciones sea más sencillo
 */
public class Vector2D {
    public float x, y;
    
    /**
     * Constructor que inicializa el vector a 0
     */
    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }
    
    /**
     * Constructor que inicializa el vector con
     * componentes especificas
     * 
     * @param x posicion x del vector
     * @param y posicion y del vector
     */
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor que inicializa el vector creando una copia de otro
     * 
     * @param vec vector al cual copiar
     */
    public Vector2D(Vector2D vec) {
        setVector(vec);
    }

    /**
     * Constructor de la clase
     * @param p
     */
    public Vector2D(Point p) {
        this.x = (float)p.getX();
        this.y = (float)p.getY();
    }

    /**
     * Método para asignar X e Y diferentes al vector 
     * @param x el componente x para asignar
     * @param y el componente y para asignar
     */
    public void setVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Método para asignar posición nueva con otro vector
     * @param vec el vector que reemplazara los componentes
     */
    public void setVector(Vector2D vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    /**
     * Método para generar la magnitud del vector 
     * @return la magnitud del vector
     */
    public float getMagnitude() {
        return (float)(Math.sqrt((x * x) + (y * y)));
    }

    /**
     *  Método para convertir en vector unitario
     */
    public void toUnitVector() { // Convertir en vector unitario (señala direcciones)
        float mag = getMagnitude();
        setVector(x / mag, y / mag);
    }
    
    /**
     * Método para escalar el vector por un coeficiente escalar
     * 
     * @param esc el coeficiente escalar
     */
    public void escale(float esc) {
        setVector(x * esc, y *esc);
    }

    /**
     * Método para sumar este vector con otro vector
     * 
     * @param vec vector que se le va a sumar
     */
    public void addVector(Vector2D vec) {
        x += vec.x;
        y += vec.y;
    }

    /**
     * Método para restar este vector con otro vector
     * 
     * @param vec vector que se le va a restar
     */
    public void subtractVector(Vector2D vec) {
        x -= vec.x;
        y -= vec.y;
    }

    /**
     * Método para calcular el producto punto punto entre dos vectores,
     * se utiliza para calcular coseno entre ambos vectores
     * 
     * @param v2 vector junto con el que se va a calcular el producto punto
     * @return el producto punto entre los dos vectores (coseno)
     */
    public float dotProduct(Vector2D v2) {
        return ((this.x * v2.x) + (this.y * v2.y));
    }
}
