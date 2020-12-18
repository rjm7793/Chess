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
     * True if this king is in check, false if not.
     */
    private boolean check;

    /**
     * Opposing pieces that are attacking this king. Used to determine valid moves
     * in check situations.
     */
    private ArrayList<ChessPiece> beingAttackedBy;

    /**
     * Constructor for a King
     * @param board the board this piece belongs to
     * @param square the square this piece is on
     * @param color the color of this piece
     */
    public King(ChessBoard board, Square square, Color color) {
        super(board, square, color);
        check = false;
    }

    public void addAttacker(ChessPiece newAttacker) {
        beingAttackedBy.add(newAttacker);
    }

    public void removeAttacker(ChessPiece removedAttacker) {
        beingAttackedBy.remove(removedAttacker);
    }

    // TODO: For the check system and verifying moves, maybe iterate through all of the opposing
    //  player's remaining pieces and find all their moves and see if any of them attack a space
    //  this King wants to move to.
    /**
     * Checks if a move is in the list of valid moves for this piece, then returns
     * a boolean to signify if it is a valid move.
     *
     * @param x x value of the proposed move
     * @param y y value of the proposed move
     */
    @Override
    public void verifyMove(int x, int y) {
        super.verifyMove(x, y);
    }

    /**
     * Adds to the list of valid moves this piece can make by finding every valid move
     * this piece can make in the current turn.
     */
    public void findAllMoves() {
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
