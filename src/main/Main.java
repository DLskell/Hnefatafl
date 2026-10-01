package main;

import javax.swing.*;

import java.awt.*;


/*
mport java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Random;
*/


public class Main extends JFrame{
    

    public static void main(String[]args){
        
        JFrame fenetre  = new JFrame("Hnefatafl");
        
        fenetre.setSize(1200, 1200);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel bg = new JLabel(new ImageIcon("src/main/background1.jpg"));
        bg.setBounds(0,0,1200,1200);        
        fenetre.add(bg);
        
 

        
        JPanel panel = new Board().panel;
        Dimension size = new Dimension(825, 825);
        panel.setPreferredSize(size);
        panel.setMinimumSize(size);
        panel.setMaximumSize(size);
               
        panel.setBackground(Color.BLACK);

        Box box = new Box(BoxLayout.Y_AXIS);
        
        box.add(Box.createVerticalGlue());
        box.add(panel);
        box.add(Box.createVerticalGlue());

        fenetre.add(box);
        
        fenetre.setVisible(true);
    }
}