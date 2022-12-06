package inputs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class GUIHandler implements ActionListener {
    private JPanel gui;

    public GUIHandler(JPanel gui) {
        this.gui = gui;
    }

    //TODO Configurar acciones de botones de gui

    @Override
    public void actionPerformed(ActionEvent e) {}
}
