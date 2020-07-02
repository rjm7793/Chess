package chess_game;

import pieces.ChessPiece;

public class Square {

    private boolean occupied;

    private ChessPiece currentPiece;

    private final int row;
    private final int col;

    public Square(int row, int col) {
        occupied = false;
        currentPiece = null;
        this.row = row;
        this.col = col;
    }

    public ChessPiece getCurrentPiece() {
        return currentPiece;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setCurrentPiece(ChessPiece newPiece) {
        occupied = true;
        currentPiece = newPiece;
    }

    public void setOccupiedFalse() {
        occupied = false;
        currentPiece = null;
    }

    /**
     * Returns the position of this square in chess notation.
     * ex: the 6th row from the top and the 3rd column from the left
     * would be displayed as "c3".
     *
     * @return position of this square in chess notation
     */
    @Override
    public String toString() {
        return String.valueOf((char)(col + 97)) + (-row + 8);
    }
}
