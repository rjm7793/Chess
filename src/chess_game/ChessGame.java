package chess_game;

import gui.ChessGUI;
import pieces.ChessPiece;

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

        else if (gameState == GameState.WHITE_SELECT_MOVE) {
            selectMove(row, col, player2);
            gameState = GameState.BLACK_SELECT_PIECE;

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
            selectMove(row, col, player);
            gameState = GameState.WHITE_SELECT_PIECE;
        }
    }

    public void selectMove(int row, int col, Player player) {
        if (board.getSquares()[row][col].isOccupied()) {
            if (board.getSquares()[row][col].getCurrentPiece().getColor() == selectedPiece.getColor()) {
                 observer.updateLabel(board.getSquares()[row][col].toString() + " selected");
                 board.getSquares()[row][col].getCurrentPiece().findAllMoves();
            } else if (selectedPiece.getValidMoves().contains(board.getSquares()[row][col])) {
                player.removePiece(board.getSquares()[row][col].getCurrentPiece());
                replacePieces(row, col);
            }
        } else if (selectedPiece.getValidMoves().contains(board.getSquares()[row][col])) {
            replacePieces(row, col);
        }
    }

    public void replacePieces(int row, int col) {
        Square originalSquare = selectedPiece.getCurrentSquare();
        originalSquare.setOccupiedFalse();
        board.getSquares()[row][col].setCurrentPiece(selectedPiece);
        observer.updateButton(originalSquare);
        observer.updateButton(board.getSquares()[row][col]);
        observer.updateLabel(originalSquare.toString() + " -> " + board.getSquares()[row][col].toString() +
                " selected.");
    }

    /**
     * Returns the 2d array of Squares.
     * @return the board of Squares
     */
    public ChessBoard getBoard() {
        return board;
    }

    /**
     * Returns the GameState.
     * @return the game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Sets this game's GameState.
     * @param state the game state to set the game to
     */
    public void setGameState(GameState state) {
        gameState = state;
    }

    /**
     * Sets the GUI that corresponds with this game.
     * @param observer the corresponding GUI.
     */
    public void setObserver(ChessGUI observer) {
        this.observer = observer;
    }

}
