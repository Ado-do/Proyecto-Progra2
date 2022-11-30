package main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

//TODO Agregar lógica creada en el paint de Mesa para generar taco

public class Taco {
    private final int WIDTH = 6;

    private Point p1, p2;
    private Bola blanca;
    
    public Taco(Bola bolaBlanca) {
        this.blanca = bolaBlanca;
    }

    // public

    public void paint(Graphics g) {

    }
}
