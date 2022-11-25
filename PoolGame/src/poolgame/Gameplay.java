package poolgame;

import javax.swing.*;
import java.awt.*;

public class Gameplay extends JPanel {

    public Gameplay() {
        super(new BorderLayout());
        this.setBackground(Color.BLACK);

        this.add(new JPanel(), BorderLayout.NORTH);
        this.add(new JPanel(), BorderLayout.SOUTH);
        this.add(new JPanel(), BorderLayout.WEST);
        this.add(new JPanel(), BorderLayout.EAST);
        this.add(new JButton(), BorderLayout.CENTER);
    }
}
