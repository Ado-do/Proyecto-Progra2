package progra2.poolgame;

import javax.swing.*;
import java.awt.*;

import inputs.*;

public class GamePanel extends JPanel {
    private GameWindow gameWindow;

    private Mesa mesa;
    private JPanel gui;

    public GamePanel(GameWindow gameWindow) {
        // * Inicializar
        super(true);
        this.gameWindow = gameWindow;
        mesa = new Mesa(1280, 600);
        initGUI();
        
        // * Configurar JPanel principal de juego
        this.setSize(1280, 720);
        this.setLayout(new BorderLayout());
        this.addListeners(); // * Agregar Listeners
        System.out.println("Tamaño GamePanel: "+this.getWidth()+"x"+this.getHeight()); //? TEST
        this.add(gui, BorderLayout.SOUTH);
        this.add(mesa, BorderLayout.CENTER);
    }

    public void updateGame() {
        mesa.update();
    }
    
    @Override
    public void paint(Graphics g) {
        //! Configurar Render (Graphics2D tiene métodos de dibujado mas útiles y complejos)
        Graphics2D g2D = (Graphics2D) g;

        // Para hacer los bordes de los dibujos mas suaves
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHints(rh);

        //* Dibujar
        // mesa.paint(g);
        super.paint(g2D);

        Toolkit.getDefaultToolkit().sync(); //* Para solucionar problemas de fluidez
    }
    
    public Mesa getMesa() {
        return mesa;
    }
    public JPanel getGUI() {
        return gui;
    }
    public GameWindow getWindow() {
        return gameWindow;
    }

    //! Subfunciones
    private void initGUI() {
        gui = new JPanel(new BorderLayout());
        gui.setPreferredSize(new Dimension(1280, 120));

        // * Centro
        JPanel subPanelCentro = new JPanel(new GridLayout(2, 0));
        JLabel score = new JLabel("SCORE: 100000", SwingConstants.CENTER);
        JLabel escape = new JLabel("Presionar ESC para cerrar", SwingConstants.CENTER);
        subPanelCentro.add(score);
        subPanelCentro.add(escape);

        gui.add(subPanelCentro);

        // * Este
        JButton reiniciar = new JButton("REINICIAR");
        reiniciar.setFocusable(false);
        
        gui.add(reiniciar, BorderLayout.EAST);

        // * Oeste
        JPanel subPanelOeste = new JPanel(new GridLayout(2, 0));
        JButton sumarBola = new JButton("AGREGAR BOLA");
        sumarBola.setFocusable(false);
        JButton restarBola = new JButton("QUTAR BOLA");
        restarBola.setFocusable(false);

        subPanelOeste.add(sumarBola);
        subPanelOeste.add(restarBola);

        gui.add(subPanelOeste, BorderLayout.WEST);
    }
    private void addListeners() {
        KeyboardInputs keyInputs = new KeyboardInputs(this);
        this.addKeyListener(keyInputs);
        
        MouseInputs mouseInputs = new MouseInputs(this);
        this.addMouseListener(mouseInputs);
        gui.addMouseListener(mouseInputs);
        mesa.addMouseListener(mouseInputs);
        
        mesa.addMouseMotionListener(mouseInputs);
        // this.addMouseMotionListener(mouseInputs);
    }
}
