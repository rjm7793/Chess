package chess_game;

import gui.ChessGUI;

public class ChessGame {

    private ChessBoard board;

    private ChessGUI observer;

    public ChessGame() {
        board = new ChessBoard();
    }

    public ChessBoard getBoard() {
        return board;
    }

    public void setObserver(ChessGUI observer) {
        this.observer = observer;
    }

}
