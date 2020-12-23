package pieces;

import chess_game.ChessBoard;
import chess_game.Color;
import chess_game.Square;

import java.util.ArrayList;

/**
 * Subclass of ChessPiece. Representation of a King.
 * @author Riley Muessig
 */
public class King extends ChessPiece {

    /**
     * Constructor for a King
     * @param board the board this piece belongs to
     * @param square the square this piece is on
     * @param color the color of this piece
     */
    public King(ChessBoard board, Square square, Color color) {
        super(board, square, color);
    }

    /**
     * Adds to the list of valid moves this piece can make by finding every valid move
     * this piece can make in the current turn.
     */
    public void findAllMoves() {
        validMoves.clear();
        allPiecesAttacked.clear();
        verifyMove(row + 1, col);
        verifyMove(row + 1, col + 1);
        verifyMove(row, col + 1);
        verifyMove(row - 1, col + 1);
        verifyMove(row - 1, col);
        verifyMove(row - 1, col - 1);
        verifyMove(row, col - 1);
        verifyMove(row + 1, col - 1);
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
            return "whiteking";
        } else {
            return "blackking";
        }
    }
}
