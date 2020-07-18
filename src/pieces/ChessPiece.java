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
        row = square.getRow();
        col = square.getCol();
    }

    /**
     * Checks if a move is in the list of valid moves for this piece, then returns
     * a boolean to signify if it is a valid move.
     *
     * @param x x value of the proposed move
     * @param y y value of the proposed move
     * @return true if valid, false if invalid
     */
    public abstract boolean verifyMove(int x, int y);

    /**
     * Adds to the list of valid moves this piece can make by finding every valid move
     * this piece can make in the current turn.
     */
    public abstract void findAllMoves();

}
