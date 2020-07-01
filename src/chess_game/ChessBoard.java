package chess_game;

import pieces.*;

public class ChessBoard {

    private Square[][] board;

    public ChessBoard() {
        board = new Square[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = new Square(row, col);

                // Creates new ChessPieces and sets up the chess board by putting each piece in its proper position
                switch (col) {
                    // handles the placement of rooks and their piece color
                    case 0:
                    case 7:
                        if (row == 0) {
                            board[row][col].setCurrentPiece(new Rook(this, board[row][col], ChessPiece.PieceColor.BLACK));
                        } else if (row == 7) {
                            board[row][col].setCurrentPiece(new Rook(this, board[row][col], ChessPiece.PieceColor.WHITE));
                        }
                        break;

                    // handles the placement of knights and their piece color
                    case 1:
                    case 6:
                        if (row == 0) {
                            board[row][col].setCurrentPiece(new Knight(this, board[row][col], ChessPiece.PieceColor.BLACK));
                        } else if (row == 7) {
                            board[row][col].setCurrentPiece(new Knight(this, board[row][col], ChessPiece.PieceColor.WHITE));
                        }
                        break;

                    // handles the placement of bishops and their piece color
                    case 2:
                    case 5:
                        if (row == 0) {
                            board[row][col].setCurrentPiece(new Bishop(this, board[row][col], ChessPiece.PieceColor.BLACK));
                        } else if (row == 7) {
                            board[row][col].setCurrentPiece(new Bishop(this, board[row][col], ChessPiece.PieceColor.WHITE));
                        }
                        break;

                    // handles the placement of queens and their piece color
                    case 3:
                        if (row == 0) {
                            board[row][col].setCurrentPiece(new Queen(this, board[row][col], ChessPiece.PieceColor.BLACK));
                        } else if (row == 7) {
                            board[row][col].setCurrentPiece(new Queen(this, board[row][col], ChessPiece.PieceColor.WHITE));
                        }
                        break;

                    // handles the placement of the kings and their piece color
                    case 4:
                        if (row == 0) {
                            board[row][col].setCurrentPiece(new King(this, board[row][col], ChessPiece.PieceColor.BLACK));
                        } else if (row == 7) {
                            board[row][col].setCurrentPiece(new King(this, board[row][col], ChessPiece.PieceColor.WHITE));
                        }
                        break;
                }
            }
        }
        // sets up pawns for both sides
        for (int col = 0; col < 8; col++) {
            board[1][col].setCurrentPiece(new Pawn(this, board[1][col], ChessPiece.PieceColor.BLACK));
            board[6][col].setCurrentPiece(new Pawn(this, board[1][col], ChessPiece.PieceColor.WHITE));
        }
    }

    public void displayBoard() {

    }

    public void flipBoard() {

    }
}
