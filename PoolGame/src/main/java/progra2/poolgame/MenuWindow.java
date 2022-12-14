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
import java.awt.GridBagConstraints;
import java.awt.Insets;

import progra2.poolgame.PoolGame.Modes;
import progra2.poolgame.PoolGame.Players;

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
        MenuPanel mp = new MenuPanel(this);
        this.add(mp);
        this.pack();
        this.getRootPane().setDefaultButton(mp.start);

        // * DESPUÉS DE CONFIGURAR, HACER VISIBLE LA VENTANA
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    } 

    public void exitMenu(Players players, Modes mode, int numBalls) {
        PoolGame.getInstance().startGame(players, mode, numBalls);

        this.setVisible(false);
        this.dispose();
    }

    private class MenuPanel extends JPanel {
        private MenuWindow mw;

        private Players players;
        private Modes mode;

        private ButtonGroup playersGroup, modeGroup;
        private JToggleButton onePlayer, twoPlayer;
        private JToggleButton standartMode, randomMode;
        private JSpinner ballsNum;
        private JButton start;

        private int auxBallsNum;

        public MenuPanel(MenuWindow mw) {
            super(new GridBagLayout());
            this.mw = mw;
            // this.setPreferredSize(new Dimension(400, 300));

            auxBallsNum = 16;

            initComponents();
            addActionListeners();
        }
            
        private void initComponents() {
            GridBagConstraints gbc;
    
            // * TÍTULO
            JLabel bienvenida = new JLabel("Bienvenido!", SwingConstants.CENTER);
            gbc = new GridBagConstraints();
            gbc.gridy = 0; gbc.gridx = 0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.anchor = GridBagConstraints.PAGE_START;
            // gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(20, 0, 20, 0);
            this.add(bienvenida, gbc);
    
            // * MODO SINGLEPLAYER/MULTIPLAYER
            JLabel textPlayers = new JLabel("Número de jugadores:", SwingConstants.RIGHT);
            gbc = new GridBagConstraints();
            gbc.gridy = 1; gbc.gridx = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);
            this.add(textPlayers, gbc);

            playersGroup = new ButtonGroup();
            onePlayer = new JToggleButton("Un jugador");
            gbc = new GridBagConstraints();
            gbc.gridy = 1; gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 3, 0, 3);
            playersGroup.add(onePlayer);
            this.add(onePlayer, gbc);

            twoPlayer = new JToggleButton("Dos jugadores");
            twoPlayer.setEnabled(false); //TODO Implementar multiplayer
            gbc = new GridBagConstraints();
            gbc.gridy = 1; gbc.gridx = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 3, 0, 3);
            playersGroup.add(twoPlayer);
            this.add(twoPlayer, gbc);

            // * MODO ESTÁNDAR/MODO ALEATORIO
            JLabel textMode = new JLabel("Modo de juego:", SwingConstants.RIGHT);
            gbc = new GridBagConstraints();
            gbc.gridy = 2; gbc.gridx = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);
            this.add(textMode, gbc);

            modeGroup = new ButtonGroup();
            standartMode = new JToggleButton("Modo estandar");
            gbc = new GridBagConstraints();
            gbc.gridy = 2; gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 3, 0, 3);
            modeGroup.add(standartMode);
            this.add(standartMode, gbc);

            randomMode = new JToggleButton("Modo aleatorio");
            gbc = new GridBagConstraints();
            gbc.gridy = 2; gbc.gridx = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 3, 0, 3);
            modeGroup.add(randomMode);
            this.add(randomMode, gbc);

            // * CANTIDAD DE BOLAS
            JLabel ballsText = new JLabel("Cantidad de bolas:", SwingConstants.RIGHT);
            gbc = new GridBagConstraints();
            gbc.gridy = 3; gbc.gridx = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);
            this.add(ballsText, gbc);

            ballsNum = new JSpinner(new SpinnerNumberModel(auxBallsNum, 1, 32, 1));
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
                if (standartMode.isSelected() || randomMode.isSelected()) {
                    start.setEnabled(true);
                }
                players = Players.ONE;
            });
            twoPlayer.addActionListener(e -> {
                System.out.println("Multiplayer");
                if (standartMode.isSelected() || randomMode.isSelected()) {
                    start.setEnabled(true);
                }
                players = Players.TWO;
            });

            standartMode.addActionListener(a -> {
                System.out.println("Modo de juego estandar");
                if (onePlayer.isSelected() || twoPlayer.isSelected()) {
                    start.setEnabled(true);
                }
                if (ballsNum.isEnabled()) {
                    auxBallsNum = (int) ballsNum.getValue();
                    ballsNum.setValue(16);
                    ballsNum.setEnabled(false);
                }
                
                mode = Modes.STANDARD;
            });

            randomMode.addActionListener(e -> {
                System.out.println("Modo de juego aleatorio");
                if (onePlayer.isSelected() || twoPlayer.isSelected()) {
                    start.setEnabled(true);
                }
                if (!ballsNum.isEnabled()) {
                    ballsNum.setValue(auxBallsNum);
                    ballsNum.setEnabled(true);
                }
                mode = Modes.RANDOM;
            });

            start.addActionListener(e -> {
                System.out.println("start");
                mw.exitMenu(players, mode, (int)ballsNum.getValue());
            });
        }
    }
}