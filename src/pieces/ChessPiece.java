package pieces;

import chess_game.ChessBoard;
import chess_game.Square;

import java.util.ArrayList;

/**
 * ChessPiece
 * @author Riley Muessig
 */
public abstract class ChessPiece {

    /**
     * The board that this piece belongs to
     */
    protected ChessBoard board;

    protected Square currentSquare;

    protected ArrayList<Square> validMoves;

    protected ArrayList<ChessPiece> allPiecesAttacked;

    protected String name;

    protected int row;
    protected int col;

    public ChessPiece(ChessBoard board, Square square) {
        this.board = board;
        this.currentSquare = square;
        row = square.getRow();
        col = square.getCol();
    }

    /**
     * Checks if a move is in the list of valid moves for this piece, then returns
     * a boolean to signify if it is a valid move.
     *
     * @return true if valid, false if invalid
     */
    public abstract boolean verifyMove();

    public abstract void findAllMoves();

    @Override
    public String toString() {
        return name;
    }
}
