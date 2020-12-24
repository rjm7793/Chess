package pieces;

import chess_game.ChessBoard;
import chess_game.Color;
import chess_game.Square;

/**
 * Subclass of ChessPiece. Representation of a Pawn.
 * @author Riley Muessig
 */
public class Pawn extends ChessPiece {

    /**
     * Boolean for if it is the pawn's first move. Used to check if this pawn
     * can move two spaces instead of one.
     */
    private boolean firstMove;

    /**
     * Constructor for a Pawn
     * @param board the board this piece belongs to
     * @param square the square this piece is on
     * @param color the color of this piece
     */
    public Pawn(ChessBoard board, Square square, Color color) {
        super(board, square, color);
        firstMove = true;
    }

    /**
     * Checks if a move is in the list of valid moves for this piece, then returns
     * a boolean to signify if it is a valid move.
     *
     * @param x x value of the proposed move
     * @param y y value of the proposed move
     */
    @Override
    public void verifyMove(int x, int y) {
        // must be within confines of the chess board
        if (x >= 0 && x <= 7 && y >= 0 && y <= 7) {
            // verifies moves where the pawn does not take any pieces
            if (!squares[x][y].isOccupied()) {
                if (y == col) {
                    // Checks if the pawn is trying to move by two squares, then checks
                    // if the square in between the starting point and destination is
                    // occupied. Then checks if it is the pawn's first move, in which
                    // case the move will be validated.
                    if (x == row + 2 || x == row - 2) {
                        if (color == Color.WHITE) {
                            if (!squares[x + 1][y].isOccupied()) {
                                if (firstMove) {
                                    validMoves.add(squares[x][y]);
                                }
                            }
                        }
                        if (color == Color.BLACK) {
                            if (!squares[x - 1][y].isOccupied()) {
                                if (firstMove) {
                                    validMoves.add(squares[x][y]);
                                }
                            }
                        }
                    }
                    else {
                        validMoves.add(squares[x][y]);
                    }
                }
            }
            // verifies moves where the pawn takes a piece by moving on a diagonal
            else {
                // Checks if the destination square is on a diagonal to this pawn.
                if (y != col) {
                    // Checks if the piece on the diagonal is of the opposing color before validating the move.
                    if (color != squares[x][y].getCurrentPiece().getColor()) {
                        validMoves.add(squares[x][y]);
                        allPiecesAttacked.add(squares[x][y].getCurrentPiece());
                    }
                }
            }
        }
    }

    /**
     * Adds to the list of valid moves this piece can make by finding every valid move
     * this piece can make in the current turn.
     */
    public void findAllMoves() {
        validMoves.clear();
        allPiecesAttacked.clear();
        // Finds all potential moves for white pawns
        if (color == Color.WHITE) {
            verifyMove(row - 1, col);
            verifyMove(row - 1, col + 1);
            verifyMove(row - 1, col - 1);
            verifyMove(row - 2, col);
        }
        // Finds all potential moves for black pawns
        else {
            verifyMove(row + 1, col);
            verifyMove(row + 1, col + 1);
            verifyMove(row + 1, col - 1);
            verifyMove(row + 2, col);
        }
    }

    /**
     * Indicates that it is no longer this Pawn's first move.
     */
    public void setFirstMove() {
        firstMove = false;
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
            return "whitepawn";
        } else {
            return "blackpawn";
        }
    }
}
