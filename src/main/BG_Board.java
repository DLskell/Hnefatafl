package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BG_Board {

    
    static final int SIZE = Board.SIZE;
    static final int CELL = 75;
    static final int GRID_OFFSET = 74;
    static final int BOARD_X = 472, BOARD_Y = 40;

    
    private final Image bgimage      = load("background1.jpg");
    private final Image boardImage   = load("pixil-frame-0.png");
    private final Image attackerImg  = load("jeton-noir.png");
    private final Image defenderImg  = load("jeton_blanc.png");
    private final Image kingImg      = load("roi.png");

    
    private Board board = new Board();
    private int selR = -1, selC = -1;                 
    private List<int[]> targets = new ArrayList<>();  

    private final JPanel bg;

    public BG_Board() {
        bg = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.drawImage(bgimage, 0, 0, null);
                g2.drawImage(boardImage, BOARD_X, BOARD_Y, null);
                drawRestrictedCells(g2);
                drawSelection(g2);
                drawPieces(g2);
                drawStatus(g2);
            }
        };

        bg.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int px = e.getX() - BOARD_X - GRID_OFFSET;
                int py = e.getY() - BOARD_Y - GRID_OFFSET;
                if (px < 0 || py < 0) return;
                int col = px / CELL, row = py / CELL;
                if (col >= SIZE || row >= SIZE) return;
                onCellClicked(row, col);
            }
        });

        InputMap im = bg.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke("ESCAPE"), "quit");
        im.put(KeyStroke.getKeyStroke("R"), "restart");
        bg.getActionMap().put("quit", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { System.exit(0); }
        });
        bg.getActionMap().put("restart", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { board = new Board(); clearSelection(); bg.repaint(); }
        });
    }

    
    private void onCellClicked(int r, int c) {
        if (board.getResult() != Board.Result.ONGOING) return;

      
        if (selR >= 0 && board.isLegalMove(selR, selC, r, c)) {
            board.move(selR, selC, r, c);
            clearSelection();
        }
        
        else if (board.isCurrentPlayersPiece(r, c)) {
            selR = r; selC = c;
            targets = board.legalMovesFrom(r, c);
        }
        
        else {
            clearSelection();
        }
        bg.repaint();
    }

    private void clearSelection() { selR = selC = -1; targets = new ArrayList<>(); }

    
    private static int cellX(int c) { return BOARD_X + GRID_OFFSET + c * CELL; }
    private static int cellY(int r) { return BOARD_Y + GRID_OFFSET + r * CELL; }

    private void drawRestrictedCells(Graphics2D g2) {
        g2.setColor(new Color(120, 40, 160, 110));  
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                if (Board.isRestricted(r, c)) g2.fillRect(cellX(c), cellY(r), CELL, CELL);
    }

    private void drawSelection(Graphics2D g2) {
        if (selR < 0) return;
        g2.setColor(new Color(255, 215, 0, 140));
        g2.fillRect(cellX(selC), cellY(selR), CELL, CELL);
        g2.setColor(new Color(0, 160, 0, 150));
        for (int[] t : targets)
            g2.fillOval(cellX(t[1]) + 25, cellY(t[0]) + 25, CELL - 50, CELL - 50);
    }

    private void drawPieces(Graphics2D g2) {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                Image img = switch (board.get(r, c)) {
                    case Board.ATTACKER -> attackerImg;
                    case Board.DEFENDER -> defenderImg;
                    case Board.KING     -> kingImg;
                    default             -> null;
                };
                if (img != null) g2.drawImage(img, cellX(c), cellY(r), CELL, CELL, null);
            }
        }
    }

    private void drawStatus(Graphics2D g2) {
        String text = switch (board.getResult()) {
            case ATTACKERS_WIN -> "Victoire des attaquants !  (R = rejouer)";
            case DEFENDERS_WIN -> "Victoire des défenseurs !  (R = rejouer)";
            default -> board.isAttackersTurn() ? "Tour des attaquants (noirs)"
                                               : "Tour des défenseurs (blancs)";
        };
        g2.setFont(new Font("SansSerif", Font.BOLD, 28));
        FontMetrics fm = g2.getFontMetrics();
        int w = fm.stringWidth(text) + 40;
        g2.setColor(new Color(0, 0, 0, 170));
        g2.fillRoundRect(30, 40, w, 60, 20, 20);
        g2.setColor(Color.WHITE);
        g2.drawString(text, 50, 80);
    }

    
    private static Image load(String name) {
        String[] folders = { "resources/", "Hnefatafl/resources/", "../resources/" };
        for (String folder : folders) {
            java.io.File f = new java.io.File(folder + name);
            if (f.exists()) return new ImageIcon(f.getPath()).getImage();
        }
        System.err.println("IMAGE INTROUVABLE : " + name
                + " (dossier de travail : " + new java.io.File(".").getAbsolutePath() + ")");
        return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    }

    public JPanel getBoard() { return bg; }
}