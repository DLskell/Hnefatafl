package main;

import java.awt.*;
import javax.swing.*;

public class Board {

    final int MAX_COL = 12;
    final int MAX_ROW = 11;

    public static final int SQUARE_SIZE = 75;
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE/2;

    public void draw(Graphics2D g2){
        int c = 0;

        for (int row = 0; row < MAX_ROW; row++) {
            for (int col = 0; col < MAX_COL; col++) {

                if (c == 0) {
                    g2.setColor(new Color(0, 0, 0));
                    c = 1;
                }else {
                    g2.setColor(new Color(255, 255, 255));
                    c = 0;
                }

                g2.fillRect((col-1)*SQUARE_SIZE, row*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }

            if (c == 0) c = 1; else{ c = 0; }

        }
    }

    JPanel panel = new JPanel(){
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            draw(g2);
        }
    };
    
}
