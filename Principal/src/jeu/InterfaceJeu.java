package jeu;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import jeu.Plateau.Cellule;

public class InterfaceJeu {

    private JFrame f = new JFrame();
    private JFrame fj = new JFrame();
    private JTextField textField;
    private Level l = new Level(1);
    private int niveau = 1;
    private Bille b;
    private Plateau plat;
    private JLayeredPane pa;
    private Deplacement d;
    private JButton btnNext, btnRetour, btnVue, btnRetour2, btnRestart, btnNewPlayer;
    int indBille = 0;
    private JLabel monScore;
    private Joueur j;

    public void recommencer() {
        if (jf != null) {
            jf.setVisible(false);
            jf = null;
        }

        b.efface();
        d.efface();
        pa.removeKeyListener(d);
        plat.efface();
        j.setNbCoups(0);

        plat = new Plateau(pa, l, niveau, b, j);
        indBille = l.getZ(niveau) - 1;
        while (plat.getCellule(0, 0, indBille).jl.getName().equals("vide")) {
            indBille--;
        }
        b = new Bille(0, 0, indBille, pa);

        d = new Deplacement(plat, b, btnNext);
        pa.addKeyListener(d);
    }

    public void nextLevel() throws IOException {
        if (jf != null) {
            jf.setVisible(false);
            jf = null;
        }
        // = j.score(l.getX(niveau) + l.getY(niveau));
        //    monScore.setText(String.valueOf(j.getScore())); .nextlvl();
        niveau++;
        b.efface();
        d.efface();
        pa.removeKeyListener(d);
        plat.efface();
        plat = new Plateau(pa, l, niveau, b, j);

        // On a besoin des 4 lignes suivantes pour mettre la bille dans la position de départ
        indBille = l.getZ(niveau) - 1;
        while (plat.getCellule(0, 0, indBille).jl.getName().equals("vide")) {
            indBille--;
        }
        b = new Bille(0, 0, indBille, pa);

        d = new Deplacement(plat, b, btnNext);
        pa.addKeyListener(d);
    }

    public void nouveauJoueur() {
        fj.setBounds(100, 100, 300, 300);
        fj.getContentPane().setBackground(new Color(0, 0, 0));
        fj.getContentPane().setLayout(null);

        textField = new JTextField();
        textField.setBounds(64, 143, 152, 28);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int touche = e.getKeyCode();
                switch (touche) {
                    case KeyEvent.VK_ENTER:
                        j = new Joueur(textField.getText());
                        fj.setVisible(false);
                        nouvellePartie();
                        break;
                }
            }
        });
        fj.getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("images\\pseudo.png"));
        lblNewLabel.setBounds(39, 68, 224, 28);

        btnNewPlayer = new JButton("");
        btnNewPlayer.setBorderPainted(false);
        btnNewPlayer.setBackground(new Color(0, 0, 0));
        btnNewPlayer.setIcon(new ImageIcon("images\\go.png"));
        btnNewPlayer.setBounds(118, 198, 42, 33);
        fj.getContentPane().add(btnNewPlayer);

        btnNewPlayer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                j = new Joueur(textField.getText());
                if (j.getNom() != "") {
                    fj.setVisible(false);
                    nouvellePartie();
                }
            }
        });

        fj.getContentPane().add(lblNewLabel);
        fj.setLocationRelativeTo(null);
        fj.setVisible(true);
        fj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void nouvellePartie() {
        f.setBounds(100, 100, 700, 700);
        f.setIconImage(Toolkit.getDefaultToolkit().getImage("images\\icone.png"));
        pa = new JLayeredPane();
        pa.setPreferredSize(new Dimension(700, 700));
        pa.setFocusable(true);

        plat = new Plateau(pa, l, niveau, b, j);
        indBille = l.getZ(niveau) - 1;
        while (plat.getCellule(0, 0, indBille).jl.getName().equals("vide")) {
            indBille--;
        }
        b = new Bille(0, 0, indBille, pa);

        JLabel lblJoueur = new JLabel("");
        lblJoueur.setIcon(new ImageIcon("images\\joueur.png"));
        lblJoueur.setBackground(new Color(154, 205, 50));
        lblJoueur.setBounds(38, 38, 123, 23);
        pa.add(lblJoueur);

        JLabel lblScore = new JLabel("");
        lblScore.setIcon(new ImageIcon("images\\score.png"));
        lblScore.setBounds(492, 38, 112, 23);
        pa.add(lblScore);

        btnRetour = new JButton("");
        btnRetour.setBackground(new Color(169, 169, 169));
        btnRetour.setIcon(new ImageIcon("images\\retourbille.png"));
        btnRetour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                plat.retour(1);
                pa.requestFocus();
            }
        });
        btnRetour.setBounds(20, 432, 209, 41);
        pa.add(btnRetour);

        btnNext = new JButton("");

        btnNext.setBackground(new Color(0, 0, 0));
        btnNext.setBorderPainted(false);
        btnNext.setIcon(new ImageIcon("images\\next.png"));
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    nextLevel();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    System.out.println("Vous avez fini!");
                    //e.printStackTrace();
                }
                btnNext.setVisible(false);
            }
        });
        btnNext.setVisible(false);
        btnNext.setBounds(234, 548, 210, 81);
        pa.add(btnNext);

        btnRetour2 = new JButton("");
        btnRetour2.setBackground(new Color(105, 105, 105));
        btnRetour2.setIcon(new ImageIcon("images\\retourbloc.png"));
        btnRetour2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                plat.blocarriere();
                pa.requestFocus();
            }
        });
        btnRetour2.setBounds(20, 484, 209, 41);
        pa.add(btnRetour2);

        btnVue = new JButton("");
        btnVue.setBackground(new Color(192, 192, 192));
        btnVue.setIcon(new ImageIcon("images\\vue2d.png"));
        btnVue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                vue(b, plat);
                pa.requestFocus();
            }
        });
        btnVue.setBounds(452, 484, 208, 41);
        pa.add(btnVue);

        btnRestart = new JButton("");
        btnRestart.setBackground(new Color(128, 128, 128));
        btnRestart.setIcon(new ImageIcon("images\\recommencer.png"));

        btnRestart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                recommencer();
            }
        });
        btnRestart.setBounds(451, 432, 209, 41);
        pa.add(btnRestart);

        JLabel pseudo = new JLabel(j.getNom());
        pseudo.setForeground(new Color(255, 215, 0));
        pseudo.setFont(new Font("Gabriela", Font.BOLD, 20));
        pseudo.setBounds(165, 38, 139, 23);
        pa.add(pseudo);

        btnVue.setBounds(452, 484, 208, 41);
        

        monScore = new JLabel(String.valueOf(j.getScore()));
        monScore.setForeground(new Color(255, 215, 0));
        monScore.setFont(new Font("Gabriela", Font.BOLD, 18));
        monScore.setBounds(555, 38, 105, 23);
        pa.add(monScore);
        d = new Deplacement(plat, b, btnNext);
        pa.addKeyListener(d);

        f.getContentPane().setBackground(new Color(0, 0, 0));
        f.setLocationRelativeTo(null);
        f.getContentPane().add(pa, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    static JFrameJeu jf;

    private static class JFrameJeu extends JFrame {

        private Bille b;
        private Plateau plat;
        private JPanel pane;

        private JFrameJeu() {
            super();
        }
    }

    public static void vue(Bille b, Plateau plat) {
        if (jf != null) {
            jf.setVisible(true);
            actualiservue();
            return;
        }
        jf = new JFrameJeu();
        jf.b = b;
        jf.plat = plat;
        jf.setSize(500, 500);
        jf.setTitle("Vue du dessus");
        int hauteur = b.getZ();
        Plateau.Cellule[][][] plateau = plat.getPlateau();
        int largeur = plateau.length;
        int longueur = plateau[0].length;
        Border bordure = BorderFactory.createLineBorder(Color.gray, 1);
        JPanel pane = new JPanel(new GridLayout((longueur), (largeur)));
        jf.pane = pane;
        for (int i = 0; i < longueur * largeur; i++) {
            JPanel boite = new JPanel();
            boite.setBorder(bordure);
            pane.add(boite);
        }
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < longueur; j++) {

                if (plateau[i][j][hauteur].isArrivee()) {
                    pane.getComponent((i) + (longueur) * j).setBackground(Color.cyan);
                } else if (i == b.getX() && j == b.getY() && hauteur == b.getZ()) {
                    pane.getComponent((i) + j * longueur).setBackground(Color.red);
                } else if (!plateau[i][j][hauteur + 1].jl.getName().equals("vide")) {
                    pane.getComponent((i) + j * longueur).setBackground(Color.orange);
                } else if (!plateau[i][j][hauteur].jl.getName().equals("vide")) {
                    pane.getComponent((i) + j * longueur).setBackground(Color.white);
                } else if (plateau[i][j][hauteur].jl.getName().equals("vide")) {
                    pane.getComponent((i) + j * longueur).setBackground(Color.black);
                }
            }
        }
        jf.add(pane);
        pane.setVisible(true);
        jf.setVisible(true);
    }

    public static void actualiservue() {
        if (jf != null) {
            Plateau.Cellule[][][] plateau = jf.plat.getPlateau();
            int largeur = plateau.length;
            int longueur = plateau[0].length;
            int hauteur = jf.b.getZ();
            JPanel pane = jf.pane;
            for (int i = 0; i < largeur; i++) {
                for (int j = 0; j < longueur; j++) {
                    if (plateau[i][j][hauteur].isArrivee()) {
                        pane.getComponent((i) + (longueur) * j).setBackground(Color.cyan);
                    } else if (i == jf.b.getX() && j == jf.b.getY() && hauteur == jf.b.getZ()) {
                        pane.getComponent((i) + j * longueur).setBackground(Color.red);
                    } else if (!plateau[i][j][hauteur + 1].jl.getName().equals("vide")) {
                        pane.getComponent((i) + j * longueur).setBackground(Color.orange);
                    } else if (!plateau[i][j][hauteur].jl.getName().equals("vide")) {
                        pane.getComponent((i) + j * longueur).setBackground(Color.white);
                    } else if (plateau[i][j][hauteur].jl.getName().equals("vide")) {
                        pane.getComponent((i) + j * longueur).setBackground(Color.black);
                    }
                }
            }
        }
    }
}
