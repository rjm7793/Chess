package chess_game;

import pieces.ChessPiece;

public class Square {

    private boolean occupied;

    private ChessPiece currentPiece;

    private int row;
    private int col;

    public Square(int row, int col) {
        occupied = false;
        currentPiece = null;
        this.row = row;
        this.col = col;
    }

    public void setCurrentPiece(ChessPiece newPiece) {
        this.currentPiece = newPiece;
    }
}
