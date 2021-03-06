package pieces;

import chess_game.ChessBoard;
import chess_game.Color;
import chess_game.Player;
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
     * Signifies if this Pawn can be captured by an en passant move from another pawn.
     */
    private boolean enPassantable;

    /**
     * Constructor for a Pawn
     * @param board the board this piece belongs to
     * @param square the square this piece is on
     * @param player the player that owns this piece
     */
    public Pawn(ChessBoard board, Square square, Player player) {
        super(board, square, player);
        firstMove = true;
        enPassantable = false;
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

                // Checks for en passant moves on opposing Pawns that have just moved 2 pieces forward.
                else {
                    if (col + 1 <= 7) {
                        if (squares[row][col + 1].isOccupied()) {
                            if (squares[row][col + 1].getCurrentPiece() instanceof Pawn) {
                                if (((Pawn) squares[row][col + 1].getCurrentPiece()).isEnPassantable()) {
                                    validMoves.add(squares[x][y]);
                                    allPiecesAttacked.add(squares[row][col + 1].getCurrentPiece());
                                }
                            }
                        }
                    }

                    if (col - 1 >= 0) {
                        if (squares[row][col - 1].isOccupied()) {
                            if (squares[row][col - 1].getCurrentPiece() instanceof Pawn) {
                                if (((Pawn) squares[row][col - 1].getCurrentPiece()).isEnPassantable()) {
                                    validMoves.add(squares[x][y]);
                                    allPiecesAttacked.add(squares[row][col - 1].getCurrentPiece());
                                }
                            }
                        }
                    }
                }
            }
            // verifies moves where the pawn takes a piece directly (not en passant) by moving on a diagonal
            else {
                // Checks if the destination square is on a diagonal to this pawn.
                if (y != col) {
                    // Checks if the piece on the diagonal is of the opposing color before validating the move.
                    if (color != squares[x][y].getCurrentPiece().getColor()) {
                        if (squares[x][y].getCurrentPiece() instanceof King) {
                            ((King) squares[x][y].getCurrentPiece()).setCheck(true);
                        }
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
     * Returns whether or not this pawn can be captured in an en passant move the next turn.
     *
     * @return true if can be captured by en passant, false if not.
     */
    public boolean isEnPassantable() {
        return enPassantable;
    }

    /**
     * Indicates whether or not this piece can be captured in an en passant move by an opponent's
     * pawn the next turn.
     *
     * @param newRow the new row of the Pawn after it moves
     * @param newCol the new column of the Pawn after it moves
     */
    public void setEnPassant(int newRow, int newCol) {
        if (color == Color.BLACK) {
            enPassantable = (newRow == row + 2 && newCol == col);
        } else if (color == Color.WHITE) {
            enPassantable = (newRow == row - 2 && newCol == col);
        }
    }

    /**
     * Indicates that it is no longer this Pawn's first move.
     */
    public void setFirstMove() {
        firstMove = false;
    }

    /**
     * Sets the current Square of this piece to a given square and updates the row and column of this piece.
     *
     * If this Pawn reaches the other side of the board, it is promoted to a new piece and the player's piece
     * list is updated.
     *
     * @param square the Square to update this piece's current square to.
     */
    @Override
    public void setCurrentSquare(Square square) {
        currentSquare = square;
        row = square.getRow();
        col = square.getCol();
        if ((color == Color.BLACK && row == 7) || (color == Color.WHITE && row == 0)) {
            currentSquare.setCurrentPiece(new Queen(board, currentSquare, player));
            player.addPiece(squares[row][col].getCurrentPiece());
            player.removePiece(this);
        }
    }

    /**
     * Sets the current Square of this piece to a given square and updates the row and column of this piece.
     *
     * Used to simulate a pawn move without promoting it if it reaches the other side of the board, in order
     * to avoid duplication.
     *
     * @param square the Square to update this piece's current square to
     */
    public void setSquareWithoutPromotion(Square square) {
        super.setCurrentSquare(square);
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
