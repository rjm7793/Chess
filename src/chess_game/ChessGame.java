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
    }

    // TODO: ADD PRECONDITIONS AND POSTCONDITIONS FOR EACH OF THESE FUNCTIONS IN THEIR DOCUMENTATION.

    /**
     * Updates the game based on the current GameState given the index of a square selected by one of the players.
     *
     * GameState.WHITE_SELECT_PIECE: Checks if the selected square occupies a valid white piece. If valid, finds all
     * legal moves for the selected piece.
     * GameState.WHITE_SELECT_MOVE: Verifies if the selected move is a legal move for the selected white piece, then
     * tries to execute the move on the board.
     *
     * GameState.BLACK_SELECT_PIECE: Checks if the selected square occupies a valid black piece. If valid, finds all
     * legal moves for the selected piece.
     * GameState.BLACK_SELECT_MOVE: Verifies if the selected move is a legal move for the selected black piece, then
     * tries to execute the move on the board.
     *
     * @param row the row selected by the player
     * @param col the column selected by the player
     */
    public void updateGame(int row, int col) {
        // Verifies if a selected square contains a white piece.
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

        // Verifies the validity of a move selected by the player controlling the white pieces. If valid,
        // executes the move if it does not leave the player in check. Once the move is executed, sees if
        // the player controlling the black pieces is in checkmate.
        else if (gameState == GameState.WHITE_SELECT_MOVE) {
            if (verifySelection(row, col, player, GameState.BLACK_SELECT_PIECE)) {
                if (checkmate(player2)) {
                    observer.updateLabel("Checkmate! White wins!");
                }
            }
        }

        // Verifies if a selected square contains a black piece.
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

        // Verifies the validity of a move selected by the player controlling the black pieces. If valid,
        // executes the move if it does not leave the player in check. Once the move is executed, sees if
        // the player controlling the white pieces is in checkmate.
        else if (gameState == GameState.BLACK_SELECT_MOVE) {
            if (verifySelection(row, col, player2, GameState.WHITE_SELECT_PIECE)) {
                if (checkmate(player)) {
                    observer.updateLabel("Checkmate! Black wins!");
                }
            }
        }
    }

    /**
     * Checks if a given player is in checkmate by iterating through all of their remaining pieces
     * and seeing if there exists any valid moves that don't leave the player's King in check.
     *
     * If the player's remaining pieces have no valid moves that don't leave the King in check,
     * the player is found to be in checkmate.
     *
     * @param player the player that will be inspected for being in checkmate
     * @return true if the player is in checkmate, false if not
     */
    public boolean checkmate(Player player) {
        for (int i = 0; i < player.getPieces().size(); i++) {
            player.getPieces().get(i).findAllMoves();
            selectedPiece = player.getPieces().get(i);
            for (int j = 0; j < player.getPieces().get(i).getValidMoves().size(); j++) {
                Square validMove = player.getPieces().get(i).getValidMoves().get(j);
                if (isKingSafe(validMove.getRow(), validMove.getCol(), player)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if a square selected by a player is occupied, then tries to move the selected piece to that square if it
     * is a valid move.
     *
     * If the player selects a square that occupies a piece of their color, will update the selected piece
     * to the piece on the new square instead of trying to select that square as a move.
     *
     * @param row The row of the selected move.
     * @param col The column of the selected move.
     * @param player The player that selected the move.
     * @param newGameState new GameState to be switched to if move is executed.
     * @return true if a move if executed, false if another piece is selected instead.
     */
    public boolean verifySelection(int row, int col, Player player, GameState newGameState) {
        if (board.getSquares()[row][col].isOccupied()) {
            if (board.getSquares()[row][col].getCurrentPiece().getColor() == selectedPiece.getColor()) {
                // If the selected square occupies the same color piece as the player, re-selects the piece
                // to be moved and does not execute any move.
                 observer.updateLabel(board.getSquares()[row][col].toString() + " selected");
                 selectedPiece = board.getSquares()[row][col].getCurrentPiece();
                 selectedPiece.findAllMoves();
                 return false;
            } else if (selectedPiece.getValidMoves().contains(board.getSquares()[row][col])) {
                // Tries to execute the selected move. If successful, removes the attacked piece from piece list
                // of the player that did not move this turn.
                ChessPiece attackedPiece = board.getSquares()[row][col].getCurrentPiece();
                if (executeMove(row, col, player, newGameState)) {
                    if (player.getColor() == Color.WHITE) {
                        this.player.removePiece(attackedPiece);
                    } else if (player.getColor() == Color.BLACK) {
                        player2.removePiece(attackedPiece);
                    }
                    return true;
                }
            }
        } else if (selectedPiece.getValidMoves().contains(board.getSquares()[row][col])) {
            // Tries to execute the selected move to the empty square.
            executeMove(row, col, player, newGameState);
            return true;
        }
        return true;
    }

    /**
     * Executes a given move if the player's King is not in check after the move is executed. Then,
     * calls for the GUI to update its Button images and game message to reflect the new board state. Finally, it
     * updates the game state to show that it is the next player's turn. Only updates the GUI and GameState
     * if the move is executed.
     *
     * @param row the row that the selected piece will be moved to.
     * @param col the column that the selected piece will be moved to.
     * @param player the player that is moving the selected piece.
     * @param newGameState new GameState to be switched to if move is executed.
     * @return true if move is executed, false if not.
     */
    public boolean executeMove(int row, int col, Player player, GameState newGameState) {
        // Simulates the move to see if the current player's King is in check after the move takes place.
        if (isKingSafe(row, col, player)) {
            Square originalSquare = selectedPiece.getCurrentSquare();
            originalSquare.setOccupiedFalse();
            board.getSquares()[row][col].setCurrentPiece(selectedPiece);
            selectedPiece.setCurrentSquare(board.getSquares()[row][col]);
            observer.updateButton(originalSquare);
            observer.updateButton(board.getSquares()[row][col]);
            observer.updateLabel(originalSquare.toString() + " -> " + board.getSquares()[row][col].toString() +
                    " selected.");
            if (selectedPiece instanceof Pawn) { // Indicates that it is no longer the Pawn's first move.
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
     * @param row the row that the selected piece will be moved to.
     * @param col the column that the selected piece will be moved to.
     * @param player the player that is moving the selected piece.
     * @return true if King is safe, false if King is not safe.
     */
    public boolean isKingSafe(int row, int col, Player player) {
        King king;
        king = player.getKing();
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

        kingSquare.setCurrentPiece(new Rook(board, king.getCurrentSquare(), player.getColor()));
        kingSquare.getCurrentPiece().findAllMoves();
        for (int i = 0; i < kingSquare.getCurrentPiece().getPiecesAttacked().size(); i++) {
            ChessPiece pieceAttacked = kingSquare.getCurrentPiece().getPiecesAttacked().get(i);
            if (pieceAttacked instanceof Rook || pieceAttacked instanceof Queen) {
                resetSimulation(row, col, king, originalSquare, replacedPiece, replaced, kingSquare);
                return false;
            }
        }

        kingSquare.setCurrentPiece(new Bishop(board, king.getCurrentSquare(), player.getColor()));
        kingSquare.getCurrentPiece().findAllMoves();
        for (int i = 0; i < kingSquare.getCurrentPiece().getPiecesAttacked().size(); i++) {
            ChessPiece pieceAttacked = kingSquare.getCurrentPiece().getPiecesAttacked().get(i);
            if (pieceAttacked instanceof Bishop || pieceAttacked instanceof Queen) {
                resetSimulation(row, col, king, originalSquare, replacedPiece, replaced, kingSquare);
                return false;
            }
        }

        kingSquare.setCurrentPiece(new Knight(board, king.getCurrentSquare(), player.getColor()));
        kingSquare.getCurrentPiece().findAllMoves();
        for (int i = 0; i < kingSquare.getCurrentPiece().getPiecesAttacked().size(); i++) {
            ChessPiece pieceAttacked = kingSquare.getCurrentPiece().getPiecesAttacked().get(i);
            if (pieceAttacked instanceof Knight) {
                resetSimulation(row, col, king, originalSquare, replacedPiece, replaced, kingSquare);
                return false;
            }
        }

        kingSquare.setCurrentPiece(new Pawn(board, king.getCurrentSquare(), player.getColor()));
        kingSquare.getCurrentPiece().findAllMoves();
        for (int i = 0; i < kingSquare.getCurrentPiece().getPiecesAttacked().size(); i++) {
            ChessPiece pieceAttacked = kingSquare.getCurrentPiece().getPiecesAttacked().get(i);
            if (pieceAttacked instanceof Pawn) {
                resetSimulation(row, col, king, originalSquare, replacedPiece, replaced, kingSquare);
                return false;
            }
        }

        kingSquare.setCurrentPiece(king);
        kingSquare.getCurrentPiece().findAllMoves();
        for (int i = 0; i < kingSquare.getCurrentPiece().getPiecesAttacked().size(); i++) {
            ChessPiece pieceAttacked = kingSquare.getCurrentPiece().getPiecesAttacked().get(i);
            if (pieceAttacked instanceof King) {
                resetSimulation(row, col, king, originalSquare, replacedPiece, replaced, kingSquare);
                return false;
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

    /**
     * Resets the board to what it was normally before a simulated move.
     *
     * @param row the row that the selected piece was simulated to move to.
     * @param col the column that the selected piece was simulated to move to.
     * @param king the King of the player controlling the pieces.
     * @param originalSquare the Square that the piece that was moved in the simulation used to be on.
     * @param replacedPiece a captured piece during the simulated move, if applicable
     * @param replaced indicates whether a piece was captured during the simulated move.
     * @param kingSquare the Square that the King originally resided on before the simulation.
     */
    public void resetSimulation(int row, int col, King king, Square originalSquare, ChessPiece replacedPiece,
                                   boolean replaced, Square kingSquare) {
        kingSquare.setCurrentPiece(king);
        // If the Square that the selected piece was simulated to move to used to be occupied, returns the original
        // piece to that Square. Otherwise, makes the Square empty again.
        if (replaced) {
            board.getSquares()[row][col].setCurrentPiece(replacedPiece);
        } else {
            board.getSquares()[row][col].setOccupiedFalse();
        }
        originalSquare.setCurrentPiece(selectedPiece);
        selectedPiece.setCurrentSquare(originalSquare);
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
