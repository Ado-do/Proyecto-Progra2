package main;

import java.util.ArrayList;
import javax.swing.JPanel;

import geometricas.Angular;

import java.awt.*;
import java.awt.event.*;
import java.nio.channels.AcceptPendingException;

public class Mesa extends JPanel {
    private final int BORDE = 35;
    private int x, y, width, length;

    private ArrayList<Bola> bolas;
    private Bola blanca;
    private Taco taco;

    //? TEST
    private CircleTest circleTest;

    public Mesa(int x, int y, int width, int length) {
        //* Inicializar
        super();
        bolas = new ArrayList<Bola>();
        taco = new Taco();

        circleTest = new CircleTest(640 - 30/2, 300, 10); //? TEST

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
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                circleTest.setCenterLocation(e.getPoint().getLocation());
                repaint();
            }
        });

        //* Agregar componentes
        int offset = 80;
        blanca = new Bola(Color.white, 640 - 30/2, 300);

        bolas.add(blanca);

        bolas.add(new Bola(Color.red, 750 + offset, 300));
        bolas.add(new Bola(Color.orange, 750 + offset, 335));
        bolas.add(new Bola(Color.blue, 750 + offset, 265));

        bolas.add(new Bola(Color.black, 785 + offset, 320));
        bolas.add(new Bola(Color.gray, 785 + offset, 280));
        
        bolas.add(new Bola(Color.yellow, 820 + offset, 300));

        for (Bola bola : bolas) this.add(bola);
    }

    @Override
    public void paintComponent(Graphics g) {
        //! Configurar Render (Graphics2D tiene metodos de dibujado mas utiles y complejos)
        Graphics2D g2D = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHints(rh);

        super.paintComponent(g2D);

        //* Borde
        g2D.setColor(new Color(153, 102, 0));
        g2D.fillRect(0, 0, width, length);
        
        //* Mesa
        g2D.setColor(Color.green);
        g2D.setColor(new Color(0, 200, 0));
        g2D.fillRect(x + BORDE, y + BORDE, width-(BORDE * 2), length-(BORDE * 2));
        
        //* Bolas
        for (Bola bola : bolas) bola.paintComponent(g2D);
        
        //? TEST ---------------------------------------------------------------------------
        //! Punto central bola blanca
        g2D.setColor(Color.RED);
        g2D.fillOval((int)blanca.getCenterLocation().getX() - 2, (int)blanca.getCenterLocation().getY() - 2, 4, 4);

        //! Generar circulo que se mueve segun mouse al rededor de bola blanca
        
        // Generar punto a un cierta distancia fija, respecto al angulo que se formo desde el mouse y la bola blanca
        int distancia = 15;
        float angulo = Angular.anguloPI(blanca.getCenterLocation(), circleTest.getCenterLocation()); //! Da error si remplazo por aux (wat?? tiene que ver con race condition?)
        
        int distAjustada = Bola.radius + distancia; 
        circleTest.setCenterLocation(Angular.generaPunto(blanca.getCenterLocation(), distAjustada, angulo));

        // Dibujar resultado
        circleTest.paintComponent(g2D);

        //! Generar linea recta que simula taco
        // Punto mas cercano a bola blanca (generado anteriormente)
        Point taco1 = circleTest.getCenterLocation();
        int taco1X = (int)taco1.getX();
        int taco1Y = (int)taco1.getY();

        // Punto lejano (generar ahora con ayuda de angulo obtenido)
        int largoTaco = 150;
        Point taco2 = Angular.generaPunto(taco1, largoTaco, angulo);
        int taco2X = (int)taco2.getX();
        int taco2Y = (int)taco2.getY();
        g2D.setColor(Color.BLACK);
        g2D.drawLine(taco1X, taco1Y, taco2X, taco2Y);

        //! Dibujar circurferencia formada por taco
        int radioTotal = distAjustada + largoTaco;

        g2D.setColor(Color.YELLOW);
        //TODO Quizas hacer una subclase de Graphics2D, configurarle el antialiasing y ademas agregar esta funcion "drawCircle()" (?  
        g2D.drawOval((int)blanca.getCenterLocation().getX() - radioTotal, (int)blanca.getCenterLocation().getY() - radioTotal, radioTotal*2, radioTotal*2);
    }
}

