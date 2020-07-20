package chess_game;

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
     * Constructor for Player.
     * @param color color of this player's pieces
     */
    public Player(Color color) {
        this.color = color;
    }
}
