package pieces;

import chess_game.ChessBoard;
import chess_game.Color;
import chess_game.Square;

import java.util.ArrayList;

/**
 * ChessPiece is an abstract class that holds the basic information needed for all
 * chess pieces, regardless of their unique traits.
 * @author Riley Muessig
 */
public abstract class ChessPiece {

    /**
     * The board that this piece belongs to
     */
    protected ChessBoard board;

    protected Square[][] squares;

    /**
     * The Square that this piece is currently on
     */
    protected Square currentSquare;

    /**
     * A list of all valid moves that can be made using this piece in the current turn.
     */
    protected ArrayList<Square> validMoves;

    /**
     * A list of all pieces that are being attacked by this piece. Used to keep track of
     * check and checkmate situations.
     */
    protected ArrayList<ChessPiece> allPiecesAttacked;

    /**
     * The color of this piece
     */
    protected Color color;

    /**
     * The coordinates of the Square this piece is on
     */
    protected int row;
    protected int col;

    /**
     * Initializes the ChessPiece with relevant information about the board as well as
     * this piece's whereabouts and colors.
     * @param board the board this piece is on
     * @param square the square this piece is on
     * @param color the color of this piece
     */
    public ChessPiece(ChessBoard board, Square square, Color color) {
        this.board = board;
        this.currentSquare = square;
        this.color = color;
        validMoves = new ArrayList<>();
        allPiecesAttacked = new ArrayList<>();
        squares = board.getSquares();
        row = square.getRow();
        col = square.getCol();
    }

    /**
     * Checks if a move is in the list of valid moves for this piece, then returns
     * a boolean to signify if it is a valid move.
     *
     * @param x x value of the proposed move
     * @param y y value of the proposed move
     */
    public void verifyMove(int x, int y) {
        if (x >= 0 && x <= 7 && y >= 0 && y <= 7) {
            if (!squares[x][y].isOccupied()) {
                validMoves.add(squares[x][y]);
            } else {
                if (color == Color.WHITE) {
                    if (squares[x][y].getCurrentPiece().getColor() == Color.BLACK) {
                        validMoves.add(squares[x][y]);
                        allPiecesAttacked.add(squares[x][y].getCurrentPiece());
                    }
                } else if (color == Color.BLACK) {
                    if (squares[x][y].getCurrentPiece().getColor() == Color.WHITE) {
                        validMoves.add(squares[x][y]);
                        allPiecesAttacked.add(squares[x][y].getCurrentPiece());
                    }
                }
            }
        }
    }

    /**
     * Checks for valid moves branching diagonally from this piece.
     *
     * Used to verify all moves for Bishops and diagonal moves for Queens.
     */
    public void verifyDiagonals() {
        // Checks validity of all moves one square away from this piece.
        verifyMove(row + 1, col + 1);
        verifyMove(row + 1, col - 1);
        verifyMove(row - 1, col + 1);
        verifyMove(row - 1, col - 1);
        // Checks validity of moves in all 4 diagonal directions. Stops reading in a given direction
        // if the move one square closer to this piece is not valid. It would not be possible for any further moves
        // in that direction to be valid.
        //
        // Ex: If (row + 1, col + 1) is not valid, (row + 2, col + 2) cannot be valid and is not checked.
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j < 8; j++) {
                if (i == 0) {
                    if ((row + j - 1) <= 7 && (col + j - 1) <= 7) { // Checks moves that branch down and to the right.
                        if (validMoves.contains(squares[row + j - 1][col + j - 1])
                                && !squares[row + j - 1][col + j - 1].isOccupied()) {
                            verifyMove(row + j, col + j);
                        }
                    }
                } else if (i == 1) {
                    if ((row + j - 1) <= 7 && (col - j + 1) >= 0) { // Checks moves that branch down and to the left.
                        if (validMoves.contains(squares[row + j - 1][col - j + 1])
                                && !squares[row + j - 1][col - j + 1].isOccupied()) {
                            verifyMove(row + j, col - j);
                        }
                    }
                } else if (i == 2) {
                    if ((row - j + 1) >= 0 && (col + j - 1) <= 7) { // Checks moves that branch up and to the right.
                        if (validMoves.contains(squares[row - j + 1][col + j - 1])
                                && !squares[row - j + 1][col + j - 1].isOccupied()) {
                            verifyMove(row - j, col + j);
                        }
                    }
                } else {
                    if ((row - j + 1) >= 0 && (col - j + 1) >= 0) { // Checks moves that branch up and to the left.
                        if (validMoves.contains(squares[row - j + 1][col - j + 1])
                                && !squares[row - j + 1][col - j + 1].isOccupied()) {
                            verifyMove(row - j, col - j);
                        }
                    }
                }

            }
        }
    }

    /**
     * Checks for valid moves branching horizontally and vertically from this piece.
     *
     * Used to verify all moves for Rooks and horizontal/vertical moves for Queens.
     */
    public void verifyStraights() {
        // Checks validity of all moves one square away from this piece.
        verifyMove(row, col + 1);
        verifyMove(row + 1, col);
        verifyMove(row, col - 1);
        verifyMove(row - 1, col);
        // Checks validity of moves in all 4 horizontal and vertical directions. Stops reading in a given direction
        // if the move one square closer to this piece is not valid. It would not be possible for any further moves
        // in that direction to be valid.
        //
        // Ex: If (row + 1, col) is not valid, (row + 2, col) cannot be valid and is not checked.
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j < 8; j++) {
                if (i == 0) {
                    if ((col + j - 1) <= 7) { // Checks moves that branch to the right.
                        if (validMoves.contains(squares[row][col + j - 1]) && !squares[row][col + j - 1].isOccupied()) {
                            verifyMove(row, col + j);
                        }
                    }
                } else if (i == 1) {
                    if ((row + j - 1) <= 7) { // Checks moves that branch downward.
                        if (validMoves.contains(squares[row + j - 1][col]) && !squares[row + j - 1][col].isOccupied()) {
                            verifyMove(row + j, col);
                        }
                    }
                } else if (i == 2) {
                    if ((col - j + 1) >= 0) { // Checks moves that branch to the left.
                        if (validMoves.contains(squares[row][col - j + 1]) && !squares[row][col - j + 1].isOccupied()) {
                            verifyMove(row, col - j);
                        }
                    }
                } else {
                    if ((row - j + 1) >= 0) { // Checks moves that branch upward.
                        if (validMoves.contains(squares[row - j + 1][col]) && !squares[row - j + 1][col].isOccupied()) {
                            verifyMove(row - j, col);
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds to the list of valid moves this piece can make by finding every valid move
     * this piece can make in the current turn.
     */
    public abstract void findAllMoves();


    /**
     * Returns color of this piece. Used to check if a piece can attack another.
     * @return color of this piece
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns an ArrayList holding all the valid moves of this piece.
     * @return the ArrayList of valid moves
     */
    public ArrayList<Square> getValidMoves() {
        return validMoves;
    }

    /**
     * Returns the Square that this piece is standing on
     * @return the Square this piece is on
     */
    public Square getCurrentSquare() {
        return currentSquare;
    }

    /**
     * Returns an ArrayList holding all the pieces that this piece can attack this turn.
     * @return the ArrayList of pieces attacked by this piece.
     */
    public ArrayList<ChessPiece> getPiecesAttacked() {
        return allPiecesAttacked;
    }

    /**
     * Sets the current Square of this piece to a given square and updates the row and column of this piece.
     * @param square the Square to update this piece's current square to.
     */
    public void setCurrentSquare(Square square) {
        currentSquare = square;
        row = square.getRow();
        col = square.getCol();
    }

}
