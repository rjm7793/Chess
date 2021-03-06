package pieces;

import chess_game.ChessBoard;
import chess_game.Color;
import chess_game.Player;
import chess_game.Square;

/**
 * Subclass of ChessPiece. Representation of a Bishop.
 * @author Riley Muessig
 */
public class Bishop extends ChessPiece {

    /**
     * Constructor for a Bishop
     * @param board the board this piece belongs to
     * @param square the square this piece is on
     * @param player the player that owns this piece
     */
    public Bishop(ChessBoard board, Square square, Player player) {
        super(board, square, player);
    }

    /**
     * Adds to the list of valid moves this piece can make by finding every valid move
     * this piece can make in the current turn.
     */
    public void findAllMoves() {
        validMoves.clear();
        allPiecesAttacked.clear();
        verifyDiagonals();
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
            return "whitebishop";
        } else {
            return "blackbishop";
        }
    }
}
