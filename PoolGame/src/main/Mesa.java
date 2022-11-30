package main;

import java.util.ArrayList;
import geometricas.Angular;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class Mesa extends JPanel {
    private final int BORDE = 35;
    private final int x, y, width, length;

    private ArrayList<Bola> bolas;
    private Bola blanca;
    private Taco taco;

//? TEST
    private Circle circleTest1;
    private Circle circleTest2;

    public Mesa(int x, int y, int width, int length) {
    //* Inicializar
        super();

        bolas = new ArrayList<Bola>();
        blanca = new Bola(Color.white, 640 - 30/2, 300);
        taco = new Taco(blanca);

    //? TEST
        circleTest1 = new Circle(640 - Bola.RADIUS, 300, 10);
        circleTest2 = new Circle(640 - Bola.RADIUS, 300, 10); 

    //* Settear propiedades
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;

    //* Configurar JPanel
        this.setLayout(null);
        this.setPreferredSize(new Dimension(width, length));
        this.setBackground(Color.YELLOW);
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                //TODO Esconder circleTest y Taco cuando sale el cursor
                // taco.setVisible(false);
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                circleTest1.setLocation(e.getPoint().getLocation());
                
                repaint();
            }
        });

    //* Agregar componentes
        int offset = 200;

        bolas.add(blanca);

        bolas.add(new Bola(Color.red, 750 + offset, 300));
        bolas.add(new Bola(Color.orange, 750 + offset, 335));
        bolas.add(new Bola(Color.blue, 750 + offset, 265));

        bolas.add(new Bola(Color.black, 785 + offset, 320));
        bolas.add(new Bola(Color.gray, 785 + offset, 280));
        
        bolas.add(new Bola(Color.yellow, 820 + offset, 300));
    }

    @Override
    public void paintComponent(Graphics g) {
    //! Configurar Render (Graphics2D tiene métodos de dibujado mas útiles y complejos)
        Graphics2D g2D = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHints(rh);

        super.paintComponent(g2D);

    //* Borde
        g2D.setColor(new Color(184, 115, 51));
        g2D.fillRect(0, 0, width, length);
        g2D.setColor(Color.BLACK);
        int borde1 = 10;
        for (int i = 0; i < borde1; i++) {
            g2D.drawRect(i, i, width-i*2, length-i*2);
        }

        
    //* Mesa
        g2D.setColor(Color.green);
        g2D.setColor(new Color(0, 200, 0));
        g2D.fillRect(x + BORDE, y + BORDE, width-(BORDE * 2), length-(BORDE * 2));

        // g2D.setColor(new Color(153, 102, 0));
        g2D.setColor(new Color(160,82,45));
        int borde2 = 8;
        for (int i = 0; i < borde2; i++) {
            g2D.drawRect((x + BORDE)+i, (y + BORDE)+i, (width-(BORDE * 2))-i*2, (length-(BORDE * 2))-i*2);
        }

    //* Bolas
        for (Bola bola : bolas) bola.paint(g2D);
        
    //? TEST ---------------------------------------------------------------------------
        int distancia = 15;
        int distAjustada = Bola.RADIUS + distancia;

        int largoTaco = 150;

        // Si el mouse esta cerca de la bola blanca
        if (Angular.distEntre2Puntos(circleTest1.getLocation(), blanca.getLocation()) <= distAjustada + largoTaco) {

        //! Punto central bola blanca
            g2D.setColor(Color.RED);
            g2D.fillOval((int)blanca.getLocation().getX() - 2, (int)blanca.getLocation().getY() - 2, 4, 4);

        //! Generar circulo que se mueve según mouse alrededor de bola blanca
            
            // Generar punto a un cierta distancia fija, respecto al angulo que se formo desde el mouse y la bola blanca
            float angulo = Angular.anguloPI(blanca.getLocation(), circleTest1.getLocation()); //! Da error si remplazo por aux (wat?? tiene que ver con race condition?)
            
            circleTest1.setLocation(Angular.generaPunto(blanca.getLocation(), distAjustada, angulo));

            // Dibujar resultado
            circleTest1.paint(g2D);

        //! Generar linea recta que simula taco
            // Punto mas cercano a bola blanca (generado anteriormente)
            Point taco1 = circleTest1.getLocation();
            int taco1X = (int)taco1.getX();
            int taco1Y = (int)taco1.getY();

            // Punto lejano (generar ahora con ayuda de angulo obtenido)
            Point taco2 = Angular.generaPunto(taco1, largoTaco, angulo);
            int taco2X = (int)taco2.getX();
            int taco2Y = (int)taco2.getY();
            g2D.setColor(Color.BLACK);
            g2D.drawLine(taco1X, taco1Y, taco2X, taco2Y);

        //! Dibujar linea de trayectoria
            float anguloOpuesto = angulo + 1f; // Generar Angulo opuesto para la linea que dibuja la dirección de la bola
            circleTest2.setLocation(Angular.generaPunto(blanca.getLocation(), distAjustada, anguloOpuesto));

            // Generar linea recta que muestra el camino que seguirá la bola
            Point linea1 = circleTest2.getLocation();
            int linea1X = (int)linea1.getX();
            int linea1Y = (int)linea1.getY();
        
            Point lineaPrediccion = Angular.generaPunto(linea1, 600, anguloOpuesto);
            int lineaPrediccX = (int)lineaPrediccion.getX();
            int lineaPrediccY = (int)lineaPrediccion.getY();
            g2D.setColor(Color.WHITE);
            g2D.drawLine(linea1X, linea1Y, lineaPrediccX, lineaPrediccY);

        }
    //! Dibujar circunferencia formada por taco
        int radioTotal = distAjustada + largoTaco;

        g2D.setColor(Color.YELLOW);
        //TODO Quizás hacer una subclase de Graphics2D, configurarle el antialiasing y ademas agregar esta función "drawCircle()" (?
        g2D.drawOval((int)blanca.getLocation().getX() - radioTotal, (int)blanca.getLocation().getY() - radioTotal, radioTotal*2, radioTotal*2);
    }
}

