package gui;

import chess_game.ChessGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChessGUI extends Application {

    /**
     * The model in the MVC architecture
     */
    private ChessGame chessGame;

    /**
     * The BorderPane that holds and aligns each aspect of the GUI
     */
    private BorderPane borderPane;

    /**
     * The GridPane that holds the Buttons that hold the squares on a chess board
     */
    private GridPane gridPane;

    /**
     * The vertical box of Labels that mark each row on the chess board
     */
    private VBox vBox;

    /**
     * The horizontal box of Labels that mark each column on the chess board
     */
    private HBox hBox;

    /**
     * A message displayed at the top of the GUI
     */
    private Label message;

    /**
     * A 2d array of Buttons that represent the squares on the chess board
     */
    private Button[][] buttonGrid;

    /**
     * The scene to be contained within the Stage
     */
    private Scene scene;

    @Override
    public void init() {
        chessGame = new ChessGame();
        chessGame.setObserver(this);
    }

    @Override
    public void start(Stage stage) {
        buttonGrid = new Button[8][8];
        gridPane = new GridPane();

        vBox = new VBox();
        for (int i = 1; i < 9; i++) {
            Label rowName = new Label(String.valueOf(i));
            vBox.getChildren().add(rowName);
        }

        hBox = new HBox();
        for (int i = 1; i < 9; i++) {
            Label colName = new Label(String.valueOf((char)(i + 96)));
            hBox.getChildren().add(colName);
        }

        message = new Label("");

        borderPane = new BorderPane();
    }

    /**
     * Populates the gridPane with
     */
    public void populateGridPane() {

    }

}
