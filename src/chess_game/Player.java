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

    // TODO: Comment everything in Player and ChessBoard regarding the piece list.

    /**
     * Color of the pieces this player can control
     */
    private Color color;

    private ArrayList<ChessPiece> pieces;

    private King king;

    /**
     * The number of pieces remaining for this player.
     */
    private int numPieces;

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
        if (piece instanceof King) {
            king = (King) piece;
        }
        numPieces++;
    }

    public void removePiece(ChessPiece piece) {
        pieces.remove(piece);
        numPieces--;
    }

    public int getNumPieces() {
        return numPieces;
    }

    public King getKing() {
        return king;
    }

    public ArrayList<ChessPiece> getPieces() {
        return pieces;
    }

    public Color getColor() {
        return color;
    }
}
