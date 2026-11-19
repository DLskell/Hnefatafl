package ui;

import javax.swing.*;
import java.awt.*;
import main.Main;

public class ChoicePanel extends JPanel{

    private final Image fond = new ImageIcon("resources/background1.jpg").getImage();

    public ChoicePanel(Main window) {
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 0, 10, 0);

        add(bouton("IA", () -> window.showScreen("jeu-ia")), c);
        add(bouton("PVP", () -> window.showScreen("jeu-pvp")), c);
        add(bouton("Retour", () -> window.showScreen("menu")), c);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fond, 0, 0, getWidth(), getHeight(), this);
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
