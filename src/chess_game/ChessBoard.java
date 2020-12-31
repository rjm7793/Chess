package chess_game;

import pieces.*;

/**
 * ChessBoard represents the chess board by using a 2d array of Squares that contain information
 * about each Chess Piece on the board.
 * @author Riley Muessig
 */
public class ChessBoard {

    /**
     * the 2d array of Squares
     */
    private Square[][] squares;

    /**
     * Both chess Players
     */
    private Player white;
    private Player black;

    /**
     * Initializes the 2d array of squares and sets up the board with each piece in its
     * proper position. Adds the pieces to their respective player's piece list.
     */
    public ChessBoard(Player player1, Player player2) {
        white = player1;
        black = player2;
        squares = new Square[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Fills the board with new Squares in such a way that follows the standard
                // chess board pattern of alternating light and dark squares.
                switch (row % 2) {
                    // Creates new squares following the correct checkerboard color pattern for even rows
                    case 0:
                        if (col % 2 == 0) {
                            squares[row][col] = new Square(row, col, Square.SquareType.LIGHT);
                        } else { 
                            squares[row][col] = new Square(row, col, Square.SquareType.DARK);
                        }
                        break;

                    // Creates new squares following the correct checkerboard color pattern for odd rows
                    case 1:
                        if (col % 2 == 0) {
                            squares[row][col] = new Square(row, col, Square.SquareType.DARK);
                        } else {
                            squares[row][col] = new Square(row, col, Square.SquareType.LIGHT);
                        }
                        break;
                }

                // Creates new ChessPieces and sets up the chess board by putting each piece in its proper position
                switch (col) {
                    // handles the placement of rooks and their piece color
                    case 0:
                    case 7:
                        if (row == 0) {
                            squares[row][col].setCurrentPiece(new Rook(this, squares[row][col], black));
                            black.addPiece(squares[row][col].getCurrentPiece());
                        } else if (row == 7) {
                            squares[row][col].setCurrentPiece(new Rook(this, squares[row][col], white));
                            white.addPiece(squares[row][col].getCurrentPiece());
                        }
                        break;

                    // handles the placement of knights and their piece color
                    case 1:
                    case 6:
                        if (row == 0) {
                            squares[row][col].setCurrentPiece(new Knight(this, squares[row][col], black));
                            black.addPiece(squares[row][col].getCurrentPiece());
                        } else if (row == 7) {
                            squares[row][col].setCurrentPiece(new Knight(this, squares[row][col], white));
                            white.addPiece(squares[row][col].getCurrentPiece());
                        }
                        break;

                    // handles the placement of bishops and their piece color
                    case 2:
                    case 5:
                        if (row == 0) {
                            squares[row][col].setCurrentPiece(new Bishop(this, squares[row][col], black));
                            black.addPiece(squares[row][col].getCurrentPiece());
                        } else if (row == 7) {
                            squares[row][col].setCurrentPiece(new Bishop(this, squares[row][col], white));
                            white.addPiece(squares[row][col].getCurrentPiece());
                        }
                        break;

                    // handles the placement of queens and their piece color
                    case 3:
                        if (row == 0) {
                            squares[row][col].setCurrentPiece(new Queen(this, squares[row][col], black));
                            black.addPiece(squares[row][col].getCurrentPiece());
                        } else if (row == 7) {
                            squares[row][col].setCurrentPiece(new Queen(this, squares[row][col], white));
                            white.addPiece(squares[row][col].getCurrentPiece());
                        }
                        break;

                    // handles the placement of the kings and their piece color
                    case 4:
                        if (row == 0) {
                            squares[row][col].setCurrentPiece(new King(this, squares[row][col], black));
                            black.addPiece(squares[row][col].getCurrentPiece());
                        } else if (row == 7) {
                            squares[row][col].setCurrentPiece(new King(this, squares[row][col], white));
                            white.addPiece(squares[row][col].getCurrentPiece());
                        }
                        break;
                }
            }
        }
        // sets up pawns for both sides
        for (int col = 0; col < 8; col++) {
            squares[1][col].setCurrentPiece(new Pawn(this, squares[1][col], black));
            black.addPiece(squares[1][col].getCurrentPiece());
            squares[6][col].setCurrentPiece(new Pawn(this, squares[6][col], white));
            white.addPiece(squares[6][col].getCurrentPiece());
        }
    }

    /**
     * Returns the 2d array of squares
     * @return the 2d array of Squares
     */
    public Square[][] getSquares() {
        return squares;
    }
}
