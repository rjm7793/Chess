package chess_game;

import pieces.ChessPiece;
import pieces.King;

import java.util.ArrayList;

/**
 * Represents a Chess player. A player can only control the pieces that
 * have a corresponding Color.
 * @author Riley Muessig
 */
public class Player {

    // TODO: Add a timer to Player class after finishing the game.

    /**
     * Color of the pieces this player can control
     */
    private Color color;

    /**
     * A list of all pieces that are active from this player.
     */
    private ArrayList<ChessPiece> pieces;

    /**
     * This player's King piece.
     */
    private King king;

    /**
     * Constructor for Player.
     * @param color color of this player's pieces
     */
    public Player(Color color) {
        this.color = color;
        pieces = new ArrayList<>();
    }

    /**
     * Adds a piece to this player's piece list.
     * If the piece is a King, will store the piece in a separate field.
     * @param piece the piece to be added
     */
    public void addPiece(ChessPiece piece) {
        pieces.add(piece);
        if (piece instanceof King) {
            king = (King) piece;
        }
    }

    /**
     * Removes a piece from this player's piece list due to it being captured.
     * @param piece the piece that was captured
     */
    public void removePiece(ChessPiece piece) {
        pieces.remove(piece);
    }

    /**
     * Returns this player's King
     * @return ChessPiece that is an instance of a King.
     */
    public King getKing() {
        return king;
    }

    public ArrayList<ChessPiece> getPieces() {
        return pieces;
    }

    /**
     * Returns this player's piece color.
     * @return the color of this player's pieces.
     */
    public Color getColor() {
        return color;
    }
}
