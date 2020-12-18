package pieces;

import chess_game.ChessBoard;
import chess_game.Color;
import chess_game.Square;

/**
 * Subclass of ChessPiece. Representation of a Knight.
 * @author Riley Muessig
 */
public class Knight extends ChessPiece {

    /**
     * Constructor for a Knight
     * @param board the board this piece belongs to
     * @param square the square this piece is on
     * @param color the color of this piece
     */
    public Knight(ChessBoard board, Square square, Color color) {
        super(board, square, color);
    }

    /**
     * Adds to the list of valid moves this piece can make by finding every valid move
     * this piece can make in the current turn.
     */
    public void findAllMoves() {
        verifyMove(row + 2, col + 1);
        verifyMove(row + 2, col - 1);
        verifyMove(row + 1, col - 2);
        verifyMove(row - 1, col - 2);
        verifyMove(row - 2, col - 1);
        verifyMove(row - 2, col + 1);
        verifyMove(row - 1, col + 2);
        verifyMove(row + 1, col + 2);
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
            return "whiteknight";
        } else {
            return "blackknight";
        }
    }
}
