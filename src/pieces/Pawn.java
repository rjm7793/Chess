package pieces;

import chess_game.ChessBoard;
import chess_game.Square;

public class Pawn extends ChessPiece {

    public Pawn(ChessBoard board, Square square, PieceColor color) {
        super(board, square, color);
    }

    /**
     * Checks if a move is in the list of valid moves for this piece, then returns
     * a boolean to signify if it is a valid move.
     *
     * @return true if valid, false if invalid
     */
    @Override
    public boolean verifyMove() {
        return false;
    }

    @Override
    public void findAllMoves() {

    }
}
