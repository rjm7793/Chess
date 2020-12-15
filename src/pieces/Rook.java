package pieces;

import chess_game.ChessBoard;
import chess_game.Color;
import chess_game.Square;

/**
 * Subclass of ChessPiece. Representation of a Rook.
 * @author Riley Muessig
 */
public class Rook extends ChessPiece {

    /**
     * Constructor for a Rook
     * @param board the board this piece belongs to
     * @param square the square this piece is on
     * @param color the color of this piece
     */
    public Rook(ChessBoard board, Square square, Color color) {
        super(board, square, color);
    }

    /**
     * Checks if a move is in the list of valid moves for this piece, then returns
     * a boolean to signify if it is a valid move.
     *
     * @param x x value of the proposed move
     * @param y y value of the proposed move
     */
    public void verifyMove(int x, int y) {

    }

    /**
     * Adds to the list of valid moves this piece can make by finding every valid move
     * this piece can make in the current turn.
     */
    @Override
    public void findAllMoves() {

    }

    /**
     * Returns a String based on the color of this piece. Allows ChessGUI to
     * efficiently access an image whose name corresponds to the color and type of
     * this piece. Used in populateGridPane() in ChessGUI.
     * @return String representation of this piece
     */
    @Override
    public String toString() {
        if (color == Color.WHITE) {
            return "whiterook";
        } else {
            return "blackrook";
        }
    }
}
