package poolgame;

import java.awt.*;
import javax.swing.*;

public class Ventana extends JFrame {

    public Ventana() {
        setTitle("Pool");
        setLayout(new BorderLayout());
        Ambiente a = new Ambiente();
        add(a, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 900);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
