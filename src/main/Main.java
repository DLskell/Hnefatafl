package main;

import javax.swing.*;


/*
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Random;
*/


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