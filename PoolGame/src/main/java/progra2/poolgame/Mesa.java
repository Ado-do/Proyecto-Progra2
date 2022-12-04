package progra2.poolgame;

import geometricas.Angular;
import geometricas.Circle;
import geometricas.PVector;

import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.*;

public class Mesa extends JPanel {
    public static final int BORDE = 35;
    public final int width, length;
    
    private ArrayList<Bola> bolas;
    private Bola blanca;
    private Taco taco;
    
    private Point mousePosition;

    //? TEST
    private Circle circleTest1;
    public static boolean moving = false;

    public PVector hold, release;

    public Mesa(int width, int length) {
        // * Inicializar
        super(true);
        initClasses();

        // * Settear propiedades
        this.width = width;
        this.length = length;

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

    public void update() {
        if (moving) {
            int x = Math.round(blanca.getX());
            // while (0 < x || x < width) {
                blanca.move();
            // }
        }
	}

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        //! Dibujos
        super.paintComponent(g2D);
        dibujarMesa(g2D); // * Mesa
        for (Bola bola : bolas) bola.paint(g2D); // * Bolas
        
        //? TEST ---------------------------------------------------------------------------
        // Si el mouse esta cerca de la bola blanca
        if (Angular.distEntre2Puntos(mousePosition.getLocation(), blanca.getLocation()) <= (Bola.RADIUS + Taco.DISTANCE + Taco.LENGTH)) {
            // Punto central bola blanca
            g2D.setColor(Color.RED);
            g2D.fillOval(blanca.getLocation().x - 2, blanca.getLocation().y - 2, 4, 4);

            //! Dibujar taco
            taco.sendMousePos(mousePosition);
            taco.paint(g2D);
            
            // Generar circulo que se mueve según mouse alrededor de bola blanca
            float angle = Angular.anguloPI(blanca.getLocation(), mousePosition.getLocation()); // * Calcular angulo que se forma entre posición de mouse y el centro de la bola blanca

            circleTest1.setLocation(Angular.generaPunto(blanca.getLocation(), Bola.RADIUS + Taco.DISTANCE, angle));
            circleTest1.paint(g2D); 

            // Dibujar linea de trayectoria
            float opositeAngle = angle + 1f; // * Calcular angulo opuesto para la linea que dibuja la dirección de la bola (sumarle 180° (1pi))

            Point pInicioTrayectoria = Angular.generaPunto(blanca.getLocation(), Bola.RADIUS + Taco.DISTANCE, opositeAngle);
            Point pFinalTrayectoria = Angular.generaPunto(pInicioTrayectoria, 600, opositeAngle);
            
            float[] patron = {10f, 4f};
            Stroke stroke = new BasicStroke(4f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, patron, 0.0f);
            Stroke defaultStroke = g2D.getStroke(); //Guardamos la linea predeterminada
            
            g2D.setStroke(stroke);
            g2D.setColor(Color.WHITE);
            g2D.drawLine(pInicioTrayectoria.x, pInicioTrayectoria.y, pFinalTrayectoria.x, pFinalTrayectoria.y);
            g2D.setStroke(defaultStroke); // Regresamos a la linea
        }

        //! Dibujar circunferencia formada por taco
        int radioTotal = Bola.RADIUS + Taco.DISTANCE + Taco.LENGTH;

        g2D.setColor(Color.YELLOW);
        g2D.drawOval(blanca.getLocation().x - radioTotal, blanca.getLocation().y - radioTotal, radioTotal*2, radioTotal*2);
    }

    public Bola getBlanca() {
        return blanca;
    }

    //! Subfunciones
    private void initClasses() {
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

        circleTest1 = new Circle(640 - Bola.RADIUS, 300, 10); //? TEST
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
