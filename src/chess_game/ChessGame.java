package chess_game;

import gui.ChessGUI;

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
     * Constructor for ChessGame. Creates the board and players.
     */
    public ChessGame() {
        board = new ChessBoard();
        player = new Player(Color.WHITE);
        player2 = new Player(Color.BLACK);
        gameState = GameState.WHITE_SELECT_PIECE;
    }

    /**
     * The main game loop that enforces turn order and when the game ends.
     */
    public void gameLoop() {
        while (true) {

        }
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
