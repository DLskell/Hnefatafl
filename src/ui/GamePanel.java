package ui;

import javax.swing.*;
import java.awt.*;
import main.Main;
import board.BG_Board;

public class GamePanel extends JPanel {

    public GamePanel(Main window) {
        setLayout(new BorderLayout());

        JButton retour = new JButton("← Menu");
        retour.addActionListener(e -> window.showScreen("menu"));
        JPanel barre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        barre.add(retour);
        add(barre, BorderLayout.NORTH);

        BG_Board bg = new BG_Board();
        add(bg.getBoard(), BorderLayout.CENTER);
    }
}