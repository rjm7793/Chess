package gui;

import chess_game.ChessGame;
import chess_game.Square;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ChessGUI extends Application {

    /**
     * The model in the MVC architecture
     */
    private ChessGame model;

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
    private Button[][] buttons;

    /**
     * The scene to be contained within the Stage
     */
    private Scene scene;

    @Override
    public void init() {
        model = new ChessGame();
        model.setObserver(this);
    }

    @Override
    public void start(Stage stage) {
        buttons = new Button[8][8];
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

    public void setButtonBackgroundImage(Button button, Square square) {

    }

    /**
     * Populates the gridPane with buttons
     */
    public void populateGridPane() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button button = new Button();
                button.setMaxWidth(80);
                button.setMaxHeight(80);

                Square square = model.getBoard().getSquares()[row][col];
                // Checks if the square has a piece on it. If occupied, it will update the
                // background image accordingly.
                if (square.isOccupied()) {
                    setButtonBackgroundImage(button, square);
                } else {
                    // Checks the color of the current square and updates the button's
                    // background color accordingly.
                    if (square.getSquareType() == Square.SquareType.DARK) {
                        button.setBackground(new Background(
                                new BackgroundFill(Color.PERU, new CornerRadii(0), Insets.EMPTY)));
                    } else {
                        button.setBackground(new Background(
                                new BackgroundFill(Color.NAVAJOWHITE, new CornerRadii(0), Insets.EMPTY)));
                    }
                }
                gridPane.add(button, col, row);
                buttons[row][col] = button;
            }
        }
    }

}
