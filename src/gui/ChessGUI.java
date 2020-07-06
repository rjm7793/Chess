package gui;

import chess_game.ChessGame;
import chess_game.Square;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pieces.ChessPiece;

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
        gridPane.setAlignment(Pos.CENTER);
        populateGridPane();

        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setTranslateX(630);
        for (int i = 8; i > 0; i--) {
            Label rowLabel = new Label(String.valueOf(i));
            rowLabel.setFont(new Font("Arial", 60));
            vBox.getChildren().add(rowLabel);
        }


        hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setTranslateY(-150);
        for (int i = 1; i < 9; i++) {
            Label colLabel = new Label(String.valueOf((char)(i + 96)));
            colLabel.setFont(new Font("Arial", 70));
            hBox.getChildren().add(colLabel);
        }

        message = new Label("ches s");
        message.setFont(new Font("Arial", 80));

        borderPane = new BorderPane();
        borderPane.setBottom(hBox);
        borderPane.setLeft(vBox);
        borderPane.setTop(message);
        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(message, Pos.CENTER);

        scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("ChessGUI");
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
    }

    public void setButtonBackgroundImage(Button button, Square square) {
        ChessPiece piece = square.getCurrentPiece();
        BackgroundFill backgroundFill;
        if (square.getSquareType() == Square.SquareType.DARK) {
            backgroundFill = new BackgroundFill(Color.PERU, new CornerRadii(0), Insets.EMPTY);
        } else {
            backgroundFill = new BackgroundFill(Color.NAVAJOWHITE, new CornerRadii(0), Insets.EMPTY);
        }
        // The constructor for Background requires the BackgroundFill and BackgroundImage to be in
        // a List or an array, which is why the singular fill and image is put in an array.
        BackgroundFill[] fills = new BackgroundFill[1];
        fills[0] = backgroundFill;

        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("images/" + piece.toString() + ".png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(50, 50, true, true, true, false));
        BackgroundImage[] images = new BackgroundImage[1];
        images[0] = backgroundImage;

        Background background = new Background(fills, images);
        button.setBackground(background);
    }

    /**
     * Populates the gridPane with buttons
     */
    public void populateGridPane() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button button = new Button();
                button.setMaxSize(80, 80);
                button.setMinSize(10, 10);
                button.setPrefSize(70, 70);

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
