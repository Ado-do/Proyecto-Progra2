package progra2.poolgame;

import geometricas.Angular;
import geometricas.PVector;

import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.*;

public class Mesa extends JPanel {
    public static final int BORDE = 40;
    public final int WIDH, LENGTH;
    
    private ArrayList<Bola> bolas;
    private Bola blanca;
    private Taco taco;
    
    private ArrayList<Tronera> troneras;
    
    private Point mousePosition;
    public PVector hold, release;

    //? TEST
    public boolean movement = false;

    public Mesa(int width, int length) {
        super(true);

        // * Settear propiedades
        this.WIDH = width;
        this.LENGTH = length;

        // * Inicializar
        initClasses();

        // * Configurar JPanel
        this.setPreferredSize(new Dimension(width, length));
        this.setBackground(Color.YELLOW);
        this.setOpaque(false);

        hold = new PVector(0,0);
        release = new PVector(0,0); 
    }

    public void sendMousePosition(Point position) {
        mousePosition = position;
    }

    public void updateGame() {
        movement = checkMovement();

        if (movement) {
            for (int i = 0; i < bolas.size(); i++) {
                Bola bolaActual = bolas.get(i);
                bolaActual.move();
                for (int j = 0; j < bolas.size(); j++) {
                    if (i == j) continue;
                    Bola bolaSiguente = bolas.get(j);
                    
                    if (bolaActual.hayColision(bolaSiguente)) {
                        System.out.println("Choco bola " + i + " con bola " + j);
                        // bolaActual.descolisonarBolas(bolaSiguente);
                        // bolaActual.colisionar(bolaSiguente);
                     
                    }
                     for (int k = 0; k < troneras.size(); k++) {
                            Tronera tronera = troneras.get(k);
                            if(bolaActual.hayColision(tronera)) {
                                bolas.remove(i);
                                System.out.println("caca");
                            }
                     }
                    
                }
            }
        }
	}

    private boolean checkMovement() {
        for (Bola bola : bolas) {
            if (bola.isMoving()) return true;
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        //! Dibujos
        super.paintComponent(g2D);
        dibujarMesa(g2D); // * Mesa
        for (Bola bola : bolas) bola.paint(g2D); // * Bolas
        for (Tronera tronera : troneras) tronera.paint(g2D);
        // Si el mouse esta cerca de la bola blanca
        if (Angular.distEntre2Puntos(mousePosition.getLocation(), blanca.getLocation()) <= (Bola.RADIUS + Taco.DISTANCE + Taco.LENGTH) && !movement) {
            // Punto central bola blanca
            g2D.setColor(Color.RED);
            g2D.fillOval(blanca.getLocation().x - 2, blanca.getLocation().y - 2, 4, 4);

            //! Dibujar taco
            taco.sendMousePos(mousePosition);
            taco.paint(g2D);
            
            // Generar circulo que se mueve según mouse alrededor de bola blanca
            float angle = Angular.anguloPI(blanca.getLocation(), mousePosition.getLocation()); // * Calcular angulo que se forma entre posición de mouse y el centro de la bola blanca

            // circleTest1.setLocation(Angular.generaPunto(blanca.getLocation(), Bola.RADIUS + Taco.DISTANCE, angle));
            // circleTest1.paint(g2D); 

            // Dibujar linea de trayectoria
            float opositeAngle = angle + 1f; // * Calcular angulo opuesto para la linea que dibuja la dirección de la bola (sumarle 180° (1pi))

            Point pInicioTrayectoria = Angular.generaPunto(blanca.getLocation(), Bola.RADIUS + 4, opositeAngle);
            Point pFinalTrayectoria = Angular.generaPunto(pInicioTrayectoria, 600, opositeAngle); //TODO Cambiar el 600 por la distancia con el objeto habra colision
            
            float[] patron = {10f, 4f};
            Stroke stroke = new BasicStroke(4f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, patron, 0.0f);
            Stroke defaultStroke = g2D.getStroke(); //Guardamos la borde predeterminada
            
            g2D.setStroke(stroke);
            g2D.setColor(Color.WHITE);
            g2D.drawLine(pInicioTrayectoria.x, pInicioTrayectoria.y, pFinalTrayectoria.x, pFinalTrayectoria.y);
            g2D.setStroke(defaultStroke); // Regresamos al borde normal

            //! Dibujar circunferencia formada por taco
            int radioTotal = Bola.RADIUS + Taco.DISTANCE + Taco.LENGTH;

            g2D.setColor(Color.YELLOW);
            g2D.drawOval(blanca.getLocation().x - radioTotal, blanca.getLocation().y - radioTotal, radioTotal*2, radioTotal*2);
        }
    }

    //* Getters
    public Bola getBlanca() {
        return blanca;
    }

    //! Subfunciones
    private void initClasses() {
        troneras = new ArrayList<Tronera>();
        bolas = new ArrayList<Bola>();
        blanca = new Bola(Color.white, WIDH/2, LENGTH/2);
        taco = new Taco(blanca);
        mousePosition = new Point();
        
        troneras.add(new Tronera(50,50));
        troneras.add(new Tronera(WIDH-50,LENGTH-50));
        troneras.add(new Tronera(WIDH-40,LENGTH-550));
        troneras.add(new Tronera(WIDH/2,LENGTH-570));
        troneras.add(new Tronera(WIDH/2,LENGTH-30));
        troneras.add(new Tronera(50,LENGTH-50));
        
        
        bolas.add(blanca);
        
        int offset = -600;
        bolas.add(new Bola(Color.red, 750 + offset, 300));
        bolas.add(new Bola(Color.orange, 750 + offset, 335));
        bolas.add(new Bola(Color.blue, 750 + offset, 265));
        bolas.add(new Bola(Color.black, 785 + offset, 320));
        bolas.add(new Bola(Color.gray, 785 + offset, 280));
        bolas.add(new Bola(Color.yellow, 820 + offset, 300));

        // circleTest1 = new Circle(640 - Bola.RADIUS, 300, 10); //? TEST
    }
    private void dibujarMesa(Graphics g) {
        // * Borde
        g.setColor(new Color(184, 115, 51));
        g.fillRect(0, 0, WIDH, LENGTH);
        
        int subBorde = 8;
        g.setColor(new Color(153, 102, 0));
        g.fillRect(BORDE-subBorde, BORDE-subBorde, (WIDH - (BORDE*2)) + subBorde*2, (LENGTH - (BORDE*2)) + subBorde*2);

        // * Mesa
        g.setColor(Color.green);
        g.setColor(new Color(0, 200, 0));
        g.fillRect(BORDE, BORDE, WIDH-(BORDE * 2), LENGTH-(BORDE * 2));
        
        // Borde
        // g.setColor(new Color(153, 102, 0));
        // g.setColor(new Color(160,82,45));
        // int borde2 = 8;
        // for (int i = 0; i < borde2; i++) {
        //     g.drawRect(BORDE-i, BORDE-i, (WIDH-(BORDE * 2))+i*2, (LENGTH-(BORDE * 2))+i*2);
        // }
    }
}
