package poolgame;

import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

public class Mesa extends JPanel {
    private final int borde = 35;
    private int x, y, width, length;

    private ArrayList<Bola> bolas;

    public Mesa(int x, int y, int width, int length) {
        //* Inicializar
        super();
        bolas = new ArrayList<Bola>();

        //* Settear propiedades
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;

        //* Configurar JPanel
        this.setLayout(null);
        // this.setBounds(x, y, width, length);
        this.setSize(width, length);
        this.setPreferredSize(new Dimension(width, length));
        this.setBackground(Color.YELLOW);

        System.out.println(this.getBounds());

        //* Agregar componentes
        bolas.add(new Bola(Color.white, 300, 150));
        bolas.add(new Bola(Color.red, 405, 190));
        bolas.add(new Bola(Color.orange, 420, 230));
        bolas.add(new Bola(Color.blue, 455, 240));
        bolas.add(new Bola(Color.black, 480, 210));
        bolas.add(new Bola(Color.gray, 430, 270));
        bolas.add(new Bola(Color.yellow, 445, 200));

        for (Bola bola : bolas) this.add(bola);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //* Borde
        g.setColor(new Color(153, 102, 0));
        g.fillRect(0, 0, width, length);
        
        //* Mesa
        // g.setColor(Color.green);
        g.setColor(new Color(0, 200, 0));
        g.fillRect(x + borde, y + borde, width-(borde * 2), length-(borde * 2));
        
        //* Bolas
        for (Bola bola : bolas) bola.paint(g);
        
    }
}

