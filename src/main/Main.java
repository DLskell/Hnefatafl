package main;

import javax.swing.*;

public class Main extends JFrame{
    

    public static void main(String[]args){
        
        JFrame fenetre  = new JFrame("Hnefatafl");
        
        fenetre.setSize(1920, 1080);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BG_Board bg = new BG_Board();
        JPanel back = bg.getBoard();
        
        fenetre.add(back);
        fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fenetre.setUndecorated(true);
        //fenetre.pack();
        fenetre.setVisible(true);
    }
}