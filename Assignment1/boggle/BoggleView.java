package boggle;
import boggle.BoggleModel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
//new
public class BoggleView {
    BoggleModel model; //reference to model
    Stage stage;
    // try to fix this
    Button addWord, endRound; //buttons for functions
    Label scoreLabel = new Label("");

    BorderPane borderPane;
    Canvas canvas;
    GraphicsContext gc; //the graphics context will be linked to the canvas

    Boolean paused;
    Timeline timeline;

    private double width; //height and width of canvas
    private double height;

    /**
     * Constructor
     *
     * @param model reference to tetris model
     * @param stage application stage
     */

    public BoggleView(BoggleModel model, Stage stage) {
        this.model = model;
        this.stage = stage;
        initUI();
    }

    private void initUI() {
        this.paused = false;
        this.stage.setTitle("CSC207 Boggle");
        this.width = this.model.getWidth() + 10;
        this.height = this.model.getHeight() + 10;

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #121212;");

        //add canvas
        canvas = new Canvas(this.width, this.height);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        //labels
        scoreLabel.setId("ScoreLabel");

        final ToggleGroup toggleGroup = new ToggleGroup();

        scoreLabel.setText("Score is: 0");
        scoreLabel.setFont(new Font(20));
        scoreLabel.setStyle("-fx-text-fill: #e8e6e3");

        //add buttons
        addWord = new Button("Add Word");
        addWord.setId("Add Word");
        addWord.setPrefSize(150, 50);
        addWord.setFont(new Font(12));
        addWord.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        endRound = new Button("End Round");
        endRound.setId("End Round");
        endRound.setPrefSize(150, 50);
        endRound.setFont(new Font(12));
        endRound.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");


        HBox controls = new HBox(20, endRound, addWord);
        controls.setPadding(new Insets(20, 20, 20, 20));
        controls.setAlignment(Pos.CENTER);

        borderPane.setTop(controls);
        borderPane.setCenter(canvas);

        var scene = new Scene(borderPane, 800, 800);
        this.stage.setScene(scene);
        this.stage.show();

    }

}