package ui;

import javax.swing.*;
import java.awt.*;
import main.Main;

public class MenuPanel extends JPanel {

    public MenuPanel(Main window) {
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 30, 40));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel titre = new JLabel("HNEFATAFL", SwingConstants.CENTER);
        titre.setFont(new Font("Serif", Font.BOLD, 64));
        titre.setForeground(Color.WHITE);
        c.insets = new Insets(0, 0, 50, 0);
        add(titre, c);

        c.insets = new Insets(10, 0, 10, 0);
        add(bouton("Jouer", () -> window.showScreen("jeu")), c);
        add(bouton("Tutoriel", () -> window.showScreen("tuto")), c);
        add(bouton("Règles", () -> window.showScreen("regles")), c);
        add(bouton("Quitter", () -> System.exit(0)), c);
    }

    private JButton bouton(String texte, Runnable action) {
        JButton b = new JButton(texte);
        b.setFont(new Font("SansSerif", Font.PLAIN, 28));
        b.setPreferredSize(new Dimension(350, 70));
        b.setFocusPainted(false);
        b.addActionListener(e -> action.run());
        return b;
    }
}