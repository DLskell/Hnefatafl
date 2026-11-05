package board;

import javax.swing.*;
import java.awt.*;

public class BG_Board {

    ImageIcon bgimage = new ImageIcon("resources/background1.jpg");
    ImageIcon boardImage = new ImageIcon("resources/pixil-frame-0.png");
    private JPanel bg;
    
    public BG_Board(){
        bg = new JPanel(){
            @Override
            protected void paintComponent(Graphics g)
                {
                    super.paintComponent(g);
                    g.drawImage(bgimage.getImage(), 0, 0, null);
                    g.drawImage(boardImage.getImage(), 472, 40, null);
                };
        };
    };
    
    public JPanel getBoard(){
        return bg;
    };
};
