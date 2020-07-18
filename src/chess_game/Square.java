package chess_game;

import pieces.ChessPiece;

/**
 * Square represents a single square on the chess board. Contains information about
 * the square color, whether it is occupied or not, and if it is occupied, the piece
 * that it contains.
 * @author Riley Muessig
 */
public class Square {

    /**
     * SquareType enum keeps track of the color of this square.
     * Used by ChessGUI to determine the background color of
     * each corresponding button.
     */
    public enum SquareType {
        LIGHT,
        DARK
    }

    /**
     * Keeps track of whether this square is occupied by a chess piece or not.
     */
    private boolean occupied;

    /**
     * The chess piece that this square holds, if any
     */
    private ChessPiece currentPiece;

    /**
     * Coordinates of this square on the 8x8 chess board
     */
    private final int row;
    private final int col;

    /**
     * The color of this square
     */
    private SquareType squareType;

    /**
     * Constructor for a square. Holds no chess piece by default until one is added.
     * @param row row of this square
     * @param col column of this square
     * @param type color of this square
     */
    public Square(int row, int col, SquareType type) {
        occupied = false;
        currentPiece = null;
        this.row = row;
        this.col = col;
        squareType = type;
    }

    /**
     * Returns true if occupied by a chess piece, false if not occupied.
     * @return boolean pertaining to whether ot not this square is occupied.
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Returns the square color held by this square's SquareType
     * @return this square's SquareType
     */
    public SquareType getSquareType() {
        return squareType;
    }

    /**
     * Returns the chess piece occupying this square, if any.
     * @return this square's piece. null if unoccupied.
     */
    public ChessPiece getCurrentPiece() {
        return currentPiece;
    }

    /**
     * Returns the row coordinate of this square.
     * @return the integer value of the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column coordinate of this square.
     * @return the integer value of the column
     */
    public int getCol() {
        return col;
    }

    /**
     * Replaces the previous chess piece of this square (if any) with a new chess piece.
     * Also occupies this square.
     * @param newPiece the new piece to set this square's current piece to
     */
    public void setCurrentPiece(ChessPiece newPiece) {
        occupied = true;
        currentPiece = newPiece;
    }

    /**
     * Removes the chess piece of this square and does not replace it.
     * De-occupies the square.
     */
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
