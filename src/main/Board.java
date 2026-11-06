package main;

import java.util.ArrayList;
import java.util.List;


public class Board {

    public static final int SIZE = 11;
    public static final int CENTER = SIZE / 2;
    public static final char EMPTY = '.', ATTACKER = 'a', DEFENDER = 'D', KING = 'K';

    public enum Result { ONGOING, ATTACKERS_WIN, DEFENDERS_WIN }

    private static final int[] DR = {-1, 1, 0, 0};
    private static final int[] DC = {0, 0, -1, 1};

    private final char[][] grid = new char[SIZE][SIZE];
    private boolean attackersTurn = true;     
    private Result result = Result.ONGOING;

    public Board() {
        this(new String[] {
            "...aaaaa...",  
            ".....a.....",  
            "...........",  
            "a....D....a",  
            "a...DDD...a",  
            "aa.DDKDD.aa",  
            "a...DDD...a",  
            "a....D....a",  
            "...........",  
            ".....a.....",  
            "...aaaaa..."   
        }, true);
    }

    
    public Board(String[] rows, boolean attackersTurn) {
        for (int r = 0; r < SIZE; r++) grid[r] = rows[r].toCharArray();
        this.attackersTurn = attackersTurn;
    }

    public char get(int r, int c)          { return grid[r][c]; }
    public boolean isAttackersTurn()       { return attackersTurn; }
    public Result getResult()              { return result; }

    public static boolean inBounds(int r, int c) { return r >= 0 && r < SIZE && c >= 0 && c < SIZE; }
    public static boolean isCorner(int r, int c) {
        return (r == 0 || r == SIZE - 1) && (c == 0 || c == SIZE - 1);
    }
    public static boolean isThrone(int r, int c)     { return r == CENTER && c == CENTER; }
    public static boolean isRestricted(int r, int c) { return isCorner(r, c) || isThrone(r, c); }

    private static boolean isAttacker(char p) { return p == ATTACKER; }
    private static boolean sameSide(char a, char b) { return isAttacker(a) == isAttacker(b); }

    public boolean isCurrentPlayersPiece(int r, int c) {
        char p = grid[r][c];
        if (p == EMPTY) return false;
        return attackersTurn == isAttacker(p);
    }

    
    public List<int[]> legalMovesFrom(int r, int c) {
        List<int[]> moves = new ArrayList<>();
        if (result != Result.ONGOING || !inBounds(r, c) || !isCurrentPlayersPiece(r, c)) return moves;
        char piece = grid[r][c];
        for (int d = 0; d < 4; d++) {
            int nr = r + DR[d], nc = c + DC[d];
            while (inBounds(nr, nc) && grid[nr][nc] == EMPTY) {
                if (!isRestricted(nr, nc) || piece == KING) moves.add(new int[] {nr, nc});
                nr += DR[d]; nc += DC[d];
            }
        }
        return moves;
    }

    public boolean isLegalMove(int fr, int fc, int tr, int tc) {
        for (int[] m : legalMovesFrom(fr, fc))
            if (m[0] == tr && m[1] == tc) return true;
        return false;
    }

    public boolean move(int fr, int fc, int tr, int tc) {
        if (!isLegalMove(fr, fc, tr, tc)) return false;

        char piece = grid[fr][fc];
        grid[fr][fc] = EMPTY;
        grid[tr][tc] = piece;

        applyCaptures(tr, tc);

        
        if (piece == KING && isCorner(tr, tc)) {
            result = Result.DEFENDERS_WIN;
        } else if (isAttacker(piece) && kingCaptured()) {
            result = Result.ATTACKERS_WIN;
        } else {
            attackersTurn = !attackersTurn;
            if (!hasLegalMove(attackersTurn))   
                result = attackersTurn ? Result.DEFENDERS_WIN : Result.ATTACKERS_WIN;
        }
        return true;
    }

    private boolean hasLegalMove(boolean attackers) {
        boolean saved = attackersTurn;
        attackersTurn = attackers;
        boolean found = false;
        for (int r = 0; r < SIZE && !found; r++)
            for (int c = 0; c < SIZE && !found; c++)
                if (isCurrentPlayersPiece(r, c) && !legalMovesFrom(r, c).isEmpty()) found = true;
        attackersTurn = saved;
        return found;
    }

    
    private void applyCaptures(int r, int c) {
        char mover = grid[r][c];
        List<int[]> victims = new ArrayList<>();
        for (int d = 0; d < 4; d++) {
            int vr = r + DR[d], vc = c + DC[d];          
            int br = vr + DR[d], bc = vc + DC[d];        
            if (!inBounds(vr, vc) || !inBounds(br, bc)) continue;
            char victim = grid[vr][vc];
            if (victim == EMPTY || victim == KING || sameSide(victim, mover)) continue;
            if (isHostileTo(victim, br, bc)) victims.add(new int[] {vr, vc});
        }
        for (int[] v : victims) grid[v[0]][v[1]] = EMPTY;   
    }

    
    private boolean isHostileTo(char victim, int r, int c) {
        if (isCorner(r, c)) return true;                          
        char p = grid[r][c];
        if (isThrone(r, c) && p == EMPTY) return true;            
        return p != EMPTY && !sameSide(p, victim);
    }

    
    private boolean kingCaptured() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (grid[r][c] != KING) continue;
                if (r == 0 || c == 0 || r == SIZE - 1 || c == SIZE - 1) return false;
                for (int d = 0; d < 4; d++) {
                    int nr = r + DR[d], nc = c + DC[d];
                    boolean hostile = grid[nr][nc] == ATTACKER
                                   || (isThrone(nr, nc) && grid[nr][nc] == EMPTY);
                    if (!hostile) return false;
                }
                return true;
            }
        }
        return false;
    }
}