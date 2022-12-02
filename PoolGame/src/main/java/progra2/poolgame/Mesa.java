package progra2.poolgame;

import geometricas.Angular;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.*;

public class Mesa extends JPanel {
    private final int BORDE = 35;
    private final int width, length;
    // private final int x, y;
    
    private ArrayList<Bola> bolas;
    private Bola blanca;
    private Taco taco;
    
    private Point mousePosition;

    private Circle circleTest1, circleTest2; //? TEST

    // public Mesa(int x, int y, int width, int length) {
    public Mesa(int width, int length) {
        // * Inicializar
        super(true);

        bolas = new ArrayList<Bola>();
        blanca = new Bola(Color.white, 640 - 30/2, 300);
        taco = new Taco(blanca);
        mousePosition = new Point();
        
        bolas.add(blanca);
        
        int offset = -600;
        bolas.add(new Bola(Color.red, 750 + offset, 300));
        bolas.add(new Bola(Color.orange, 750 + offset, 335));
        bolas.add(new Bola(Color.blue, 750 + offset, 265));
        bolas.add(new Bola(Color.black, 785 + offset, 320));
        bolas.add(new Bola(Color.gray, 785 + offset, 280));
        bolas.add(new Bola(Color.yellow, 820 + offset, 300));

        //? TEST
        circleTest1 = new Circle(640 - Bola.RADIUS, 300, 10);
        circleTest2 = new Circle(640 - Bola.RADIUS, 300, 10); 

        // * Settear propiedades
        // this.x = x;
        // this.y = y;
        this.width = width;
        this.length = length;

        // * Configurar JPanel
        this.setPreferredSize(new Dimension(width, length));
        this.setBackground(Color.YELLOW);
        this.setOpaque(false);
        // this.addMouseMotionListener(new MouseAdapter() {
            // @Override
            // public void mouseMoved(MouseEvent e) {
            //     mousePosition = e.getPoint().getLocation();
                
            //     repaint();
            // }
        // });
    }

    public void sendMousePosition(Point position) {
        mousePosition = position;
        // repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        //! Configurar Render (Graphics2D tiene métodos de dibujado mas útiles y complejos)
        Graphics2D g2D = (Graphics2D) g;
        // * Para hacer los bordes de los dibujos mas suaves
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHints(rh);

        //! Dibujos
        super.paintComponent(g2D);

        // * Mesa
        dibujarMesa(g2D);

        // * Bolas
        for (Bola bola : bolas) bola.paint(g2D);
        
        //? TEST ---------------------------------------------------------------------------
        // Si el mouse esta cerca de la bola blanca
        if (Angular.distEntre2Puntos(mousePosition.getLocation(), blanca.getLocation()) <= (Bola.RADIUS + Taco.DISTANCE + Taco.LENGTH)) {

            //? Punto central bola blanca
            g2D.setColor(Color.RED);
            g2D.fillOval((int)Math.round(blanca.getLocation().getX()) - 2, (int)Math.round(blanca.getLocation().getY()) - 2, 4, 4);

            //! Dibujar taco
            taco.sendMousePos(mousePosition);
            taco.paint(g2D);
            
            //? Generar circulo que se mueve según mouse alrededor de bola blanca
            float angle = Angular.anguloPI(blanca.getLocation(), mousePosition.getLocation()); // * Calcular angulo que se forma entre posición de mouse y el centro de la bola blanca

            circleTest1.setLocation(Angular.generaPunto(blanca.getLocation(), Bola.RADIUS + Taco.DISTANCE, angle));
            circleTest1.paint(g2D); 

            //! Dibujar linea de trayectoria
            float oppAngle = angle + 1f; // * Calcular angulo opuesto para la linea que dibuja la dirección de la bola (sumarle 180° (1pi))
            circleTest2.setLocation(Angular.generaPunto(blanca.getLocation(), Bola.RADIUS + Taco.DISTANCE, oppAngle));

            Point line1 = circleTest2.getLocation();
            int line1X = (int)Math.round(line1.getX());
            int line1Y = (int)Math.round(line1.getY());
        
            Point lineaPrediccion = Angular.generaPunto(line1, 600, oppAngle);
            int lineaPrediccX = (int)Math.round(lineaPrediccion.getX());
            int lineaPrediccY = (int)Math.round(lineaPrediccion.getY());
            g2D.setColor(Color.WHITE);
            g2D.drawLine(line1X, line1Y, lineaPrediccX, lineaPrediccY);
        }

        //? Dibujar circunferencia formada por taco
        int radioTotal = Bola.RADIUS + Taco.DISTANCE + Taco.LENGTH;

        g2D.setColor(Color.YELLOW);
        //TODO Quizás hacer una subclase de Graphics2D, configurarle el antialiasing y ademas agregar esta función "drawCircle()" (?
        g2D.drawOval((int)Math.round(blanca.getLocation().getX()) - radioTotal, (int)Math.round(blanca.getLocation().getY()) - radioTotal, radioTotal*2, radioTotal*2);
    }

    private void dibujarMesa(Graphics g) {
        // * Borde
        g.setColor(new Color(184, 115, 51));
        g.fillRect(0, 0, width, length);

        int borde1 = 10;
        // g.setColor(Color.BLACK);
        g.setColor(Color.WHITE);
        for (int i = 0; i < borde1; i++) {
            g.drawRect(i, i, width-i*2, length-i*2);
        }
        
        // * Mesa
        g.setColor(Color.green);
        g.setColor(new Color(0, 200, 0));
        // g.fillRect(x + BORDE, y + BORDE, width-(BORDE * 2), length-(BORDE * 2));
        g.fillRect(BORDE, BORDE, width-(BORDE * 2), length-(BORDE * 2));
        
        // Borde
        // g.setColor(new Color(153, 102, 0));
        int borde2 = 8;
        g.setColor(new Color(160,82,45));
        for (int i = 0; i < borde2; i++) {
            // g.drawRect((x + BORDE)+i, (y + BORDE)+i, (width-(BORDE * 2))-i*2, (length-(BORDE * 2))-i*2);
            g.drawRect(BORDE+i, BORDE+i, (width-(BORDE * 2))-i*2, (length-(BORDE * 2))-i*2);
        }
    }
}

