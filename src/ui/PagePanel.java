package ui;

import javax.swing.*;
import java.awt.*;
import main.Main;

public class PagePanel extends JPanel {

    public PagePanel(Main window, String titre, String contenu) {
        setLayout(new BorderLayout(10, 10));

        JLabel t = new JLabel(titre, SwingConstants.CENTER);
        t.setFont(new Font("Serif", Font.BOLD, 40));
        add(t, BorderLayout.NORTH);

        JTextArea texte = new JTextArea(contenu);
        texte.setEditable(false);
        texte.setLineWrap(true);
        texte.setWrapStyleWord(true);
        texte.setFont(new Font("SansSerif", Font.PLAIN, 20));
        add(new JScrollPane(texte), BorderLayout.CENTER);

        JButton retour = new JButton("← Retour au menu");
        retour.addActionListener(e -> window.showScreen("menu"));
        add(retour, BorderLayout.SOUTH);
    }
}