/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progra2.poolgame;

import java.awt.Point;

/**
 *
 * @author crisf
 */
public class bekktor {
    private Point posicion;
    private float magnitud;
    private float angulo;
    
    public bekktor(Point posicion, float magnitud, float angulo) {
        this.posicion = posicion;
        this.magnitud = magnitud;
        this.angulo = angulo;
    
    }
    public void escalar(int x) {
        posicion.move(((int)posicion.getX())*x,((int)posicion.getY())*x);
    
    }
    public void normalizar(int x) {
        posicion.move(((int)posicion.getX())/x, ((int)posicion.getY())/x);
    
    }
    
}
