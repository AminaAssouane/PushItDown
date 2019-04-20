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
    private Level l = new Level(1);
    private int niveau = 1;
    private Bille b;
    private Plateau plat;
    private JLayeredPane pa;
    private Deplacement d;
    JButton btnNext, btnRetour, btnVue;
    int indBille = 0;

    public void nextLevel() throws IOException {
        if (jf != null) {
            jf = null;
        }
        l.nextlvl();
        niveau++;
        b.efface();
        d.efface();
        pa.removeKeyListener(d);
        plat.efface();
        plat = new Plateau(pa, l, niveau, b);

        // On a besoin des 4 lignes suivantes pour mettre la bille dans la position de départ
        indBille = l.getZ(niveau) - 1;
        while (plat.getCellule(0, 0, indBille).jl.getName().equals("vide")) {
            indBille--;
        }
        b = new Bille(0, 0, indBille, pa);

        d = new Deplacement(plat, b, btnNext);
        pa.addKeyListener(d);
    }

    public void nouvellePartie() {
        f.setBounds(100, 100, 450, 300);
        pa = new JLayeredPane();
        pa.setPreferredSize(new Dimension(500, 500));
        pa.setFocusable(true);

        plat = new Plateau(pa, l, niveau, b);
        indBille = l.getZ(niveau) - 1;
        while (plat.getCellule(0, 0, indBille).jl.getName().equals("vide")) {
            indBille--;
        }
        b = new Bille(0, 0, indBille, pa);

        d = new Deplacement(plat, b, btnNext);
        pa.addKeyListener(d);

        JLabel lblJoueur = new JLabel("Joueur : ");
        lblJoueur.setBackground(new Color(154, 205, 50));
        lblJoueur.setBounds(33, 45, 46, 14);
        pa.add(lblJoueur);

        JLabel lblScore = new JLabel("Score :");
        lblScore.setBounds(348, 45, 46, 14);
        pa.add(lblScore);

        btnRetour = new JButton("RETOUR >");
        btnRetour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                plat.retour(1);
                pa.requestFocus();
            }
        });
        btnRetour.setBounds(370, 70, 100, 23);
        pa.add(btnRetour);

        btnNext = new JButton("NEXT >");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    nextLevel();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                btnNext.setVisible(false);
            }
        });

        btnNext.setBounds(370, 399, 89, 23);

        btnVue = new JButton("Vu 2D");
        pa.add(btnVue);
        btnVue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                vue(b, plat);
                pa.requestFocus();
            }
        });
        
        btnVue.setBounds(370, 250, 89, 23);

        pa.add(btnNext);

        f.setSize(500, 500);
        f.getContentPane().setBackground(new Color(0, 206, 209));
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
        JPanel pane = new JPanel(new GridLayout((largeur), (longueur)));
        jf.pane = pane;
        for (int i = 0; i < longueur * largeur; i++) {
            JPanel boite = new JPanel();
            boite.setBorder(bordure);
            pane.add(boite);
        }
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < longueur; j++) {

                if (plateau[i][j][hauteur].isArrivee()) {
                    pane.getComponent((i * largeur) + j).setBackground(Color.cyan);
                } else if (i == b.getX() && j == b.getY() && hauteur == b.getZ()) {
                    pane.getComponent((i * largeur) + j).setBackground(Color.red);
                } else if (!plateau[i][j][hauteur + 1].jl.getName().equals("vide")) {
                    pane.getComponent((i * largeur) + j).setBackground(Color.orange);
                } else if (!plateau[i][j][hauteur].jl.getName().equals("vide")) {
                    pane.getComponent((i * largeur) + j).setBackground(Color.white);
                } else if (plateau[i][j][hauteur].jl.getName().equals("vide")) {
                    pane.getComponent((i * largeur) + j).setBackground(Color.black);
                }
            }
        }
        jf.add(pane);
        pane.setVisible(true);
        jf.setVisible(true);
    }

    public static void actualiservue() {
        if (jf != null) {
            Plateau.Cellule [][][] plateau = jf.plat.getPlateau();
            int largeur = plateau.length;
            int longueur = plateau[0].length;
            int hauteur = jf.b.getZ();
            JPanel pane = jf.pane;
            for (int i = 0; i < largeur; i++) {
                for (int j = 0; j < longueur; j++) {

                    if (plateau[i][j][hauteur].isArrivee()) {
                        pane.getComponent((i * largeur) + j).setBackground(Color.cyan);
                    } else if (i == jf.b.getX() && j == jf.b.getY() && hauteur == jf.b.getZ()) {
                        pane.getComponent((i * largeur) + j).setBackground(Color.red);
                    } else if (!plateau[i][j][hauteur + 1].jl.getName().equals("vide")) {
                        pane.getComponent((i * largeur) + j).setBackground(Color.orange);
                    } else if (!plateau[i][j][hauteur].jl.getName().equals("vide")) {
                        pane.getComponent((i * largeur) + j).setBackground(Color.white);
                    } else if (plateau[i][j][hauteur].jl.getName().equals("vide")) {
                        pane.getComponent((i * largeur) + j).setBackground(Color.black);
                    }
                }
            }
        }
    }
}
