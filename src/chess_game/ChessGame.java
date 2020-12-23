package chess_game;

import gui.ChessGUI;
import pieces.*;

import java.util.ArrayList;

/**
 * ChessGame acts as the model in the Model-View-Controller architecture. Holds information about the
 * game including the game state and board state. Runs the main game loop.
 * @author Riley Muessig
 */
public class ChessGame {

    /**
     * Keeps track of which player's turn it is and the state of their turn.
     */
    public enum GameState {
        WHITE_SELECT_PIECE, // White selects a piece to move.
        WHITE_SELECT_MOVE, // White selects where to move their selected piece.

        BLACK_SELECT_PIECE, // Black selects a piece to move.
        BLACK_SELECT_MOVE // Black selects where to move their selected piece.
    }

    /**
     * The chess board.
     */
    private ChessBoard board;

    /**
     * The GUI that is observing this chess game.
     */
    private ChessGUI observer;

    /**
     * The game state which keeps track of player turns.
     */
    private GameState gameState;

    /**
     * The player that controls the white pieces.
     */
    private Player player;

    /**
     * The player that controls the black pieces.
     */
    private Player player2;

    /**
     * The current selected piece by either player.
     */
    private ChessPiece selectedPiece;

    /**
     * Constructor for ChessGame. Creates the board and players.
     */
    public ChessGame() {
        player = new Player(Color.WHITE);
        player2 = new Player(Color.BLACK);
        board = new ChessBoard(player, player2);
        gameState = GameState.WHITE_SELECT_PIECE;
        ArrayList<ChessPiece> whitePieces = player.getPieces();
        ArrayList<ChessPiece> blackPieces = player2.getPieces();
        for (int i = 0; i < 16; i++) {
            whitePieces.get(i).findAllMoves();
            blackPieces.get(i).findAllMoves();
        }
    }

    public void update(int row, int col) {
        if (gameState == GameState.WHITE_SELECT_PIECE) {
            if (!board.getSquares()[row][col].isOccupied()) {
                observer.updateLabel(board.getSquares()[row][col].toString() + " is an empty square! Select" +
                        " a valid piece.");
            } else {
                if (board.getSquares()[row][col].getCurrentPiece().getColor() != Color.WHITE) {
                    observer.updateLabel(board.getSquares()[row][col].toString() + " contains a black piece." +
                            "Please select a white piece.");
                } else {
                    observer.updateLabel(board.getSquares()[row][col].toString() + " selected.");
                    selectedPiece = board.getSquares()[row][col].getCurrentPiece();
                    selectedPiece.findAllMoves();
                    gameState = GameState.WHITE_SELECT_MOVE;
                }
            }
        }

        //
        else if (gameState == GameState.WHITE_SELECT_MOVE) {
            selectMove(row, col, player, GameState.BLACK_SELECT_PIECE);
        }

        else if (gameState == GameState.BLACK_SELECT_PIECE) {
            if (!board.getSquares()[row][col].isOccupied()) {
                observer.updateLabel(board.getSquares()[row][col].toString() + " is an empty square! Select" +
                        " a valid piece.");
            } else {
                if (board.getSquares()[row][col].getCurrentPiece().getColor() != Color.BLACK) {
                    observer.updateLabel(board.getSquares()[row][col].toString() + " contains a white piece." +
                            "Please select a black piece.");
                } else {
                    observer.updateLabel(board.getSquares()[row][col].toString() + " selected.");
                    selectedPiece = board.getSquares()[row][col].getCurrentPiece();
                    selectedPiece.findAllMoves();
                    gameState = GameState.BLACK_SELECT_MOVE;
                }
            }
        }

        else if (gameState == GameState.BLACK_SELECT_MOVE) {
            selectMove(row, col, player2, GameState.WHITE_SELECT_PIECE);
        }
    }

    public void selectMove(int row, int col, Player player, GameState newGameState) {
        if (board.getSquares()[row][col].isOccupied()) {
            if (board.getSquares()[row][col].getCurrentPiece().getColor() == selectedPiece.getColor()) {
                 observer.updateLabel(board.getSquares()[row][col].toString() + " selected");
                 selectedPiece = board.getSquares()[row][col].getCurrentPiece();
                 selectedPiece.findAllMoves();
            } else if (selectedPiece.getValidMoves().contains(board.getSquares()[row][col])) {
                ChessPiece attackedPiece = board.getSquares()[row][col].getCurrentPiece();
                if (replacePieces(row, col, player.getColor(), newGameState)) {
                    player.removePiece(attackedPiece);
                }
            }
        } else if (selectedPiece.getValidMoves().contains(board.getSquares()[row][col])) {
            replacePieces(row, col, player.getColor(), newGameState);
        }
    }

    public boolean replacePieces(int row, int col, Color playerColor, GameState newGameState) {
        if (isKingSafe(row, col, playerColor)) {
            Square originalSquare = selectedPiece.getCurrentSquare();
            originalSquare.setOccupiedFalse();
            board.getSquares()[row][col].setCurrentPiece(selectedPiece);
            selectedPiece.setCurrentSquare(board.getSquares()[row][col]);
            observer.updateButton(originalSquare);
            observer.updateButton(board.getSquares()[row][col]);
            observer.updateLabel(originalSquare.toString() + " -> " + board.getSquares()[row][col].toString() +
                    " selected.");
            if (selectedPiece instanceof Pawn) {
                ((Pawn) selectedPiece).setFirstMove();
            }
            this.gameState = newGameState;
            return true;
        }
        return false;
    }

    /**
     * Further verifies the legality of a move by checking if the player's King is being attacked
     * or not after the valid move is completed. If the King is being attacked, the move cannot
     * be executed.
     *
     * Does this by simulating the move of the selected piece and then assessing the board by replacing
     * the King with a Rook, Bishop, Knight, and Pawn to check if any of these pieces are attacking a respective
     * piece of the opposing color. If they are attacking a respective piece of the opposing color, the King
     * is not safe.
     *
     * @return true if King is safe, false if King is not safe.
     */
    public boolean isKingSafe(int row, int col, Color kingColor) {
        King king;
        if (kingColor == player.getColor()) {
            king = player.getKing();
        } else {
            king = player2.getKing();
        }
        Square originalSquare = selectedPiece.getCurrentSquare();
        originalSquare.setOccupiedFalse();
        ChessPiece replacedPiece = null;
        boolean replaced = false;
        if (board.getSquares()[row][col].isOccupied()) {
            replacedPiece = board.getSquares()[row][col].getCurrentPiece();
            replaced = true;
        }
        board.getSquares()[row][col].setCurrentPiece(selectedPiece);
        selectedPiece.setCurrentSquare(board.getSquares()[row][col]);
        Square kingSquare = king.getCurrentSquare();

        kingSquare.setCurrentPiece(new Rook(board, king.getCurrentSquare(), kingColor));
        kingSquare.getCurrentPiece().findAllMoves();
        for (int i = 0; i < kingSquare.getCurrentPiece().getPiecesAttacked().size(); i++) {
            ChessPiece pieceAttacked = kingSquare.getCurrentPiece().getPiecesAttacked().get(i);
            if (pieceAttacked instanceof Rook || pieceAttacked instanceof Queen) {
                return resetSimulation(row, col, king, originalSquare, replacedPiece, replaced, kingSquare);
            }
        }

        kingSquare.setCurrentPiece(new Bishop(board, king.getCurrentSquare(), kingColor));
        kingSquare.getCurrentPiece().findAllMoves();
        for (int i = 0; i < kingSquare.getCurrentPiece().getPiecesAttacked().size(); i++) {
            ChessPiece pieceAttacked = kingSquare.getCurrentPiece().getPiecesAttacked().get(i);
            if (pieceAttacked instanceof Bishop || pieceAttacked instanceof Queen) {
                return resetSimulation(row, col, king, originalSquare, replacedPiece, replaced, kingSquare);
            }
        }

        kingSquare.setCurrentPiece(new Knight(board, king.getCurrentSquare(), kingColor));
        kingSquare.getCurrentPiece().findAllMoves();
        for (int i = 0; i < kingSquare.getCurrentPiece().getPiecesAttacked().size(); i++) {
            ChessPiece pieceAttacked = kingSquare.getCurrentPiece().getPiecesAttacked().get(i);
            if (pieceAttacked instanceof Knight) {
                return resetSimulation(row, col, king, originalSquare, replacedPiece, replaced, kingSquare);
            }
        }

        kingSquare.setCurrentPiece(new Pawn(board, king.getCurrentSquare(), kingColor));
        kingSquare.getCurrentPiece().findAllMoves();
        for (int i = 0; i < kingSquare.getCurrentPiece().getPiecesAttacked().size(); i++) {
            ChessPiece pieceAttacked = kingSquare.getCurrentPiece().getPiecesAttacked().get(i);
            if (pieceAttacked instanceof Pawn) {
                return resetSimulation(row, col, king, originalSquare, replacedPiece, replaced, kingSquare);
            }
        }

        kingSquare.setCurrentPiece(king);
        kingSquare.getCurrentPiece().findAllMoves();
        for (int i = 0; i < kingSquare.getCurrentPiece().getPiecesAttacked().size(); i++) {
            ChessPiece pieceAttacked = kingSquare.getCurrentPiece().getPiecesAttacked().get(i);
            if (pieceAttacked instanceof King) {
                return resetSimulation(row, col, king, originalSquare, replacedPiece, replaced, kingSquare);
            }
        }

        kingSquare.setCurrentPiece(king);
        if (replaced) {
            board.getSquares()[row][col].setCurrentPiece(replacedPiece);
        } else {
            board.getSquares()[row][col].setOccupiedFalse();
        }
        originalSquare.setCurrentPiece(selectedPiece);
        selectedPiece.setCurrentSquare(originalSquare);
        return true;
    }

    public boolean resetSimulation(int row, int col, King king, Square originalSquare, ChessPiece replacedPiece,
                                   boolean replaced, Square kingSquare) {
        kingSquare.setCurrentPiece(king);
        if (replaced) {
            board.getSquares()[row][col].setCurrentPiece(replacedPiece);
        } else {
            board.getSquares()[row][col].setOccupiedFalse();
        }
        originalSquare.setCurrentPiece(selectedPiece);
        selectedPiece.setCurrentSquare(originalSquare);
        return false;
    }

    /**
     * Returns the 2d array of Squares.
     * @return the board of Squares
     */
    public ChessBoard getBoard() {
        return board;
    }

    /**
     * Sets the GUI that corresponds with this game.
     * @param observer the corresponding GUI.
     */
    public void setObserver(ChessGUI observer) {
        this.observer = observer;
    }

}
