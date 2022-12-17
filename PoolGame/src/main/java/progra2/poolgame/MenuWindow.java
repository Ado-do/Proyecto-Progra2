package progra2.poolgame;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MenuWindow extends JFrame {
    
    public MenuWindow() {
        super("PoolGame");
        
        // * ICONO
        try { this.setIconImage(new ImageIcon(getClass().getResource("/resources/icon.png")).getImage());
        } catch (Exception e) { System.out.println("Exception: Error al cargar icono de la ventana"); };

        // * CONFIGURAR JFRAME (VENTANA)
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // * AGREGAR COMPONENTS
        MenuPanel mp = new MenuPanel();
        this.add(mp);
        this.pack();
        this.getRootPane().setDefaultButton(mp.start);

        // * DESPUÉS DE CONFIGURAR, HACER VISIBLE LA VENTANA
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    } 

    public void exitMenu(GameModes gameMode, int numBalls) {
        PoolGame.getInstance().startGame(gameMode, numBalls);

        this.setVisible(false);
        this.dispose();
    }

    private class MenuPanel extends JPanel {
        private ButtonGroup playersGroup, modeGroup;
        private JToggleButton onePlayer, twoPlayer;
        private JToggleButton standardMode, randomMode;
        private JSpinner ballsNum;
        private JButton start;

        private int holdBallsNum;

        public MenuPanel() {
            super(new GridBagLayout());
            // this.setPreferredSize(new Dimension(400, 300));

            holdBallsNum = 16;

            addComponents();
            addActionListeners();
        }
            
        private void addComponents() {
            GridBagConstraints gbc;
    
            // * TÍTULO
            JLabel bienvenida = new JLabel("Bienvenido a nuestro proyecto \"PoolGame en Java\"!", SwingConstants.CENTER);
            bienvenida.setFont(new Font("Arial", Font.BOLD, 15));
            gbc = new GridBagConstraints();
            gbc.gridy = 0; gbc.gridx = 0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.anchor = GridBagConstraints.PAGE_START;
            // gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(20, 0, 20, 0);
            this.add(bienvenida, gbc);
    
            // * MODO ESTÁNDAR/MODO ALEATORIO
            JLabel textMode = new JLabel("Modo de juego:", SwingConstants.RIGHT);
            gbc = new GridBagConstraints();
            gbc.gridy = 1; gbc.gridx = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);
            this.add(textMode, gbc);

            modeGroup = new ButtonGroup();
            standardMode = new JToggleButton("Modo estandar");
            gbc = new GridBagConstraints();
            gbc.gridy = 1; gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 3, 0, 3);
            modeGroup.add(standardMode);
            this.add(standardMode, gbc);

            randomMode = new JToggleButton("Modo aleatorio");
            gbc = new GridBagConstraints();
            gbc.gridy = 1; gbc.gridx = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 3, 0, 3);
            modeGroup.add(randomMode);
            this.add(randomMode, gbc);

            // * MODO SINGLEPLAYER/MULTIPLAYER
            JLabel textPlayers = new JLabel("Número de jugadores:", SwingConstants.RIGHT);
            gbc = new GridBagConstraints();
            gbc.gridy = 2; gbc.gridx = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);
            this.add(textPlayers, gbc);

            playersGroup = new ButtonGroup();
            onePlayer = new JToggleButton("Un jugador");
            gbc = new GridBagConstraints();
            gbc.gridy = 2; gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 3, 0, 3);
            playersGroup.add(onePlayer);
            this.add(onePlayer, gbc);

            twoPlayer = new JToggleButton("Dos jugadores");
            twoPlayer.setEnabled(false); //TODO Implementar multiplayer
            gbc = new GridBagConstraints();
            gbc.gridy = 2; gbc.gridx = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 3, 0, 3);
            playersGroup.add(twoPlayer);
            this.add(twoPlayer, gbc);

            // * CANTIDAD DE BOLAS
            JLabel ballsText = new JLabel("Cantidad de bolas:", SwingConstants.RIGHT);
            gbc = new GridBagConstraints();
            gbc.gridy = 3; gbc.gridx = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);
            this.add(ballsText, gbc);

            ballsNum = new JSpinner(new SpinnerNumberModel(holdBallsNum, 1, 32, 1));
            ballsNum.setEnabled(false);
            gbc = new GridBagConstraints();
            gbc.gridy = 3; gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            gbc.insets = new Insets(0, 3, 0, 3);
            this.add(ballsNum, gbc);

            // * BOTÓN DE JUGAR
            start = new JButton("Jugar!");
            start.setEnabled(false);
            gbc = new GridBagConstraints();
            gbc.gridy = 4; gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(10, 10, 10, 10);
            // gbc.fill = GridBagConstraints.HORIZONTAL;
            this.add(start, gbc);
        }

        private void addActionListeners() {
            onePlayer.addActionListener(a -> {
                System.out.println("Singleplayer");
                if (standardMode.isSelected() || randomMode.isSelected()) {
                    start.setEnabled(true);
                }
            });
            twoPlayer.addActionListener(e -> {
                System.out.println("Multiplayer");
                if (standardMode.isSelected() || randomMode.isSelected()) {
                    start.setEnabled(true);
                }
            });

            standardMode.addActionListener(a -> {
                System.out.println("Modo de juego estandar");
                if (onePlayer.isSelected() || twoPlayer.isSelected()) {
                    start.setEnabled(true);
                }
                if (ballsNum.isEnabled()) {
                    holdBallsNum = (int) ballsNum.getValue();
                    ballsNum.setValue(16);
                    ballsNum.setEnabled(false);
                }
            });

            randomMode.addActionListener(e -> {
                System.out.println("Modo de juego aleatorio");
                if (onePlayer.isSelected() || twoPlayer.isSelected()) {
                    start.setEnabled(true);
                }
                if (!ballsNum.isEnabled()) {
                    ballsNum.setValue(holdBallsNum);
                    ballsNum.setEnabled(true);
                }
            });

            start.addActionListener(e -> {
                System.out.println("Start game!");
                GameModes mode;
                
                if (onePlayer.isSelected()) {
                    if (standardMode.isSelected()) {
                        mode = GameModes.STANDARD;
                    } else {
                        mode = GameModes.RANDOM;
                    }
                } else {
                    mode = GameModes.STANDARD_MULTIPLAYER;
                }
                exitMenu(mode, (int)ballsNum.getValue());
            });
        }
    }
}