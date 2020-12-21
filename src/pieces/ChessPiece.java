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
                    }
                } else if (color == Color.BLACK) {
                    if (squares[x][y].getCurrentPiece().getColor() == Color.WHITE) {
                        validMoves.add(squares[x][y]);
                    }
                }
            }
        }
    }

    public void verifyDiagonals() {
        verifyMove(row + 1, col + 1);
        verifyMove(row + 1, col - 1);
        verifyMove(row - 1, col + 1);
        verifyMove(row - 1, col - 1);
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j < 8; j++) {
                if (i == 0) {
                    if ((row + j - 1) <= 7 && (col + j - 1) <= 7) {
                        if (validMoves.contains(squares[row + j - 1][col + j - 1])) {
                            verifyMove(row + j, col + j);
                        }
                    }
                } else if (i == 1) {
                    if ((row + j - 1) <= 7 && (col - j + 1) >= 0) {
                        if (validMoves.contains(squares[row + j - 1][col - j + 1])) {
                            verifyMove(row + j, col - j);
                        }
                    }
                } else if (i == 2) {
                    if ((row - j + 1) >= 0 && (col + j - 1) <= 7) {
                        if (validMoves.contains(squares[row - j + 1][col + j - 1])) {
                            verifyMove(row - j, col + j);
                        }
                    }
                } else {
                    if ((row - j + 1) >= 0 && (col - j + 1) >= 0) {
                        if (validMoves.contains(squares[row - j + 1][col - j + 1])) {
                            verifyMove(row - j, col - j);
                        }
                    }
                }

            }
        }
    }

    public void verifyStraights() {
        verifyMove(row, col + 1);
        verifyMove(row + 1, col);
        verifyMove(row, col - 1);
        verifyMove(row - 1, col);
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j < 8; j++) {
                if (i == 0) {
                    if ((col + j - 1) <= 7) {
                        if (validMoves.contains(squares[row][col + j - 1])) {
                            verifyMove(row, col + j);
                        }
                    }
                } else if (i == 1) {
                    if ((row + j - 1) <= 7) {
                        if (validMoves.contains(squares[row + j - 1][col])) {
                            verifyMove(row + j, col);
                        }
                    }
                } else if (i == 2) {
                    if ((col - j + 1) >= 0) {
                        if (validMoves.contains(squares[row][col - j + 1])) {
                            verifyMove(row, col - j);
                        }
                    }
                } else {
                    if ((row - j + 1) >= 0) {
                        if (validMoves.contains(squares[row - j + 1][col])) {
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

    public ArrayList<Square> getValidMoves() {
        return validMoves;
    }

    public Square getCurrentSquare() {
        return currentSquare;
    }
    public void setCurrentSquare(Square square) {
        currentSquare = square;
        row = square.getRow();
        col = square.getCol();
    }

}
