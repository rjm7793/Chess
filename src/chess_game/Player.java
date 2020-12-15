package chess_game;

import pieces.ChessPiece;

import java.util.ArrayList;

/**
 * Represents a Chess player. A player can only control the pieces that
 * have a corresponding Color.
 * @author Riley Muessig
 */
public class Player {

    // TODO: Add a timer to Player class after finishing the game.

    // TODO: Comment everything in Player and ChessBoard regarding the piece list.

    /**
     * Color of the pieces this player can control
     */
    private Color color;

    private ArrayList<ChessPiece> pieces;

    /**
     * Constructor for Player.
     * @param color color of this player's pieces
     */
    public Player(Color color) {
        this.color = color;
        pieces = new ArrayList<>();
    }

    public void addPiece(ChessPiece piece) {
        pieces.add(piece);
    }

    public void removePiece(ChessPiece piece) {
        pieces.remove(piece);
    }
}