package main;

import javax.swing.*;
import java.awt.*;
import ui.GamePanel;
import ui.MenuPanel;
import ui.PagePanel;

public class Main extends JFrame{
    
    private final CardLayout cards  = new CardLayout();
    private final JPanel root = new JPanel(cards);

    public Main(){
        super("Hnefatafl");
        setSize(1920, 1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        root.add(new MenuPanel(this), "menu");
        root.add(new GamePanel(this), "jeu");
        root.add(new PagePanel(this, "Règles", "Écris ici les règles du Hnefatafl..."), "regles");
        root.add(new PagePanel(this, "Tutoriel", "Écris ici le tutoriel..."), "tuto");

        add(root);
        setVisible(true);
    }

    public void showScreen(String name) {
        cards.show(root, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

}