package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import logic.*;

public class Gui {
    private JFrame frame;
    private JPanel scorePanel;
    private JLabel scoreLabel;
    private Ploca ploca;

    static private Vector<Color> boje = new Vector<>(Arrays.asList(
        Color.decode("#CDC1B4"),
        Color.decode("#EEE4DA"),
        Color.decode("#EDE0C8"),
        Color.decode("#F2B179"),
        Color.decode("#F59563"),
        Color.decode("#F67C5F"),
        Color.decode("#F65E3B"),
        Color.decode("#EDCF72"),
        Color.decode("#EDCC61"),
        Color.decode("#EDC850"),
        Color.decode("#EDC53F"),
        Color.decode("#EDC22E")
    ));

    private Color getBoja(int x) {
        if (x < 1) {
            return boje.getFirst();
        }
        if (x < 1100) {
            return boje.get((int) (Math.log(x) / Math.log(2)));
        }
        return boje.getLast();
    }

    private void popuniFrame() {
        frame.getContentPane().removeAll();

        scoreLabel.setText("Trenutni rekord: " + ploca.getTrenutniRekord());
        frame.add(scorePanel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel(new GridLayout(ploca.getDimenzija(), ploca.getDimenzija()));
        int[][] p = ploca.getPloca();
        for (int i = 0; i < ploca.getDimenzija(); i++) {
            for (int j = 0; j < ploca.getDimenzija(); j++) {
                JPanel panel = new JPanel();
                panel.setBackground(getBoja(p[i][j]));

                JLabel label;
                if (p[i][j] == 0) {
                    label = new JLabel("");
                } else {
                    label = new JLabel(String.valueOf(p[i][j]));
                    label.setFont(new Font("Calibri", Font.BOLD, 32));
                }
                panel.add(label);
                panel.setBorder(BorderFactory.createLineBorder(Color.black));
                gamePanel.add(panel);
            }
        }

        frame.add(gamePanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public Gui(int i) {
        frame = new JFrame("2048");
        ploca = new Ploca(i);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);

        scorePanel = new JPanel();
        scoreLabel = new JLabel("Trenutni rekord: " + ploca.getTrenutniRekord());
        scoreLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        scorePanel.add(scoreLabel);
        frame.add(scorePanel, BorderLayout.NORTH);

        popuniFrame();

        frame.setVisible(true);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                    ploca.pomjeriGore();
                } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
                    ploca.pomjeriDole();
                } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
                    ploca.pomjeriLijevo();
                } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
                    ploca.pomjeriDesno();
                }

                if(ploca.getKraj()) {
                    ploca.postaviRekord();
                    int odgovor = JOptionPane.showOptionDialog(frame,
                        "Igra je gotova!\nVaš rezultat: " + ploca.getTrenutniRekord() +"\nNajbolji rekord: " +ploca.getRekord() +
                        "\nŽelite li započeti novu igru?","Kraj igre",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Nova igra", "Zatvori"},
                        "Nova igra");

                    if (odgovor == JOptionPane.YES_OPTION) {
                        ploca.restartujPlocu();
                        popuniFrame();
                    } else {
                        System.exit(0);
                    }
                }
                else {
                    popuniFrame();
				}
            }
        });
    }
}
