package geometricas;

import java.awt.Point;

public class PVector {
    private Point punto; // Localización
    private float direccion; // Angulo en términos de PI 
    private double magnitud; // Distancia

    public PVector(Point p, float ang, double mag) {
        this.punto = p;
        this.direccion = ang;
        this.magnitud = mag;
    }
}
