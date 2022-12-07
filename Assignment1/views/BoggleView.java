package views;

import boggle.BoggleModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Text;

//new
public class BoggleView{
    BoggleModel model; //reference to model
    Stage stage;

    Scene boardChoice, lettersChoice, gameScene; //scenes to switch to

    Button addWord, endGame, newRound, gridSmall, gridLarge, randomizeBoardLetters, InputBoardLetters; //buttons for functions
    Label scoreLabel = new Label("");
    Label boardChoiceLabel = new Label("");
    Label letterChoiceLabel = new Label("");

    TextField letterInput, wordInput;

    BorderPane borderPane1, borderPane2, borderPane3;
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

        this.width = this.model.getWidth() + 10;
        this.height = this.model.getHeight() + 10;

        //setting up all the border panes
        borderPane1 = new BorderPane();
        borderPane1.setStyle("-fx-background-color: #4BEDE5;");
        borderPane2 = new BorderPane();
        borderPane2.setStyle("-fx-background-color: #4BEDE5;");
        borderPane3 = new BorderPane();
        borderPane3.setStyle("-fx-background-color: #4BEDE5;");

        //labels
        scoreLabel.setId("ScoreLabel");
        boardChoiceLabel.setId("GridSizeLabel");
        letterChoiceLabel.setId("LettersChoiceLabel");

        boardChoiceLabel.setText("Click 5*5 to play on a big (5x5) grid; \n 4*4 to play on a small (4x4) one:");
        boardChoiceLabel.setFont(new Font(14));
        boardChoiceLabel.setStyle("-fx-text-fill: #232E94");
        boardChoiceLabel.setAlignment(Pos.CENTER);
        letterChoiceLabel.setText("Click \"randomize letters\" to randomize letters in the grid;\n " +
                "\t\"Input Letters\" to choose the letters yourself");
        letterChoiceLabel.setFont(new Font(14));
        letterChoiceLabel.setStyle("-fx-text-fill: #232E94");
        letterChoiceLabel.setAlignment(Pos.CENTER);

        //adding buttons for first screen
        gridSmall = new Button("4*4");
        gridSmall.setId("Grid Size 4*4");
        gridSmall.setPrefSize(100, 30);
        gridSmall.setFont(new Font(12));
        gridSmall.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        gridLarge = new Button("5*5");
        gridLarge.setId("Grid Size 5*5");
        gridLarge.setPrefSize(100, 30);
        gridLarge.setFont(new Font(12));
        gridLarge.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        //adding buttons for second screen
        randomizeBoardLetters = new Button("Randomize \n    Letters");
        randomizeBoardLetters.setId("Randomize Letters");
        randomizeBoardLetters.setPrefSize(100, 50 );
        randomizeBoardLetters.setFont(new Font(12));
        randomizeBoardLetters.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        InputBoardLetters = new Button(" Input \nLetters");
        InputBoardLetters.setId("Input Letters");
        InputBoardLetters.setPrefSize(100, 50);
        InputBoardLetters.setFont(new Font(12));
        InputBoardLetters.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        //text field for second screen
        letterInput = new TextField();
        letterInput.setPromptText("Input your letter choice");
        letterInput.setPrefColumnCount(this.model.boardSize*this.model.boardSize);


        //layout for scene 1 buttons
        HBox leftMenu1 = new HBox();
        leftMenu1.getChildren().addAll(gridSmall, gridLarge);
        leftMenu1.setPadding((new Insets(40, 20, 20, 20)));
        leftMenu1.setAlignment(Pos.CENTER);
        leftMenu1.setSpacing(150);

        //layout for scene 2 buttons
        HBox leftMenu2 = new HBox();
        leftMenu2.getChildren().addAll(randomizeBoardLetters,  InputBoardLetters);
        leftMenu2.setPadding((new Insets(40, 20, 20, 20)));
        leftMenu2.setAlignment(Pos.CENTER);
        leftMenu2.setSpacing(150);

        //layout for scene 1 label
        VBox topMenu1 = new VBox();
        topMenu1.getChildren().addAll(boardChoiceLabel);
        topMenu1.setAlignment(Pos.CENTER);
        //layout for scene 2 label
        VBox topMenu2 = new VBox();
        topMenu2.getChildren().addAll(letterChoiceLabel);
        topMenu2.setAlignment(Pos.CENTER);

        //setting up first scene
        borderPane1.setTop(topMenu1);
        borderPane1.setLeft(leftMenu1);
        boardChoice = new Scene(borderPane1, 400, 400);

        //setting up second scene
        borderPane2.setTop(topMenu2);
        borderPane2.setLeft(leftMenu2);
        borderPane2.setBottom(letterInput);
        lettersChoice = new Scene(borderPane2, 400, 400);

        //actions for choosing grid size
        gridSmall.setOnAction(e -> {
            stage.setScene(lettersChoice);
            this.model.boardSize = 4;
        });
        gridLarge.setOnAction(e -> {
            stage.setScene(lettersChoice);
            this.model.boardSize = 5;
        });
        //actions for choosing letters
        randomizeBoardLetters.setOnAction(e -> {
            this.model.randomizeLetters();
            gameUI();
        });
        InputBoardLetters.setOnAction(e -> {
            String letters = letterInput.getText();
            this.model.setGame(this.model.boardSize, letters);
            gameUI();
        });

        this.stage.setScene(boardChoice);
        this.stage.setTitle("CSC207 Boggle");
        this.stage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        this.stage.show();

    }

    private void gameUI(){
        //adding buttons for third screen
        GridPane grid = new GridPane();
        for (int r = 0; r < this.model.boardSize; r++) {
            for (int c = 0; c < this.model.boardSize; c++) {
                Button button = new Button(Character.toString(this.model.grid.getCharAt(r, c)));
                button.setPrefSize(40,40);
                grid.add(button, c, r);
            }
        }
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        addWord = new Button("Add Word");
        addWord.setId("Add Word");
        addWord.setPrefSize(100, 50);
        addWord.setFont(new Font(12));
        addWord.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        endGame = new Button("End Game");
        endGame.setId("End Game");
        endGame.setPrefSize(100, 50);
        endGame.setFont(new Font(12));
        endGame.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        newRound = new Button("New Round");
        newRound.setId("new Round");
        newRound.setPrefSize(100, 50);
        newRound.setFont(new Font(12));
        newRound.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        //text field for game screen
        wordInput = new TextField();
        wordInput.setPromptText("Enter a word");
        wordInput.setPrefColumnCount(10);

        //layout for game buttons
        HBox leftMenu3 = new HBox();
        leftMenu3.getChildren().addAll(addWord,  wordInput);
        leftMenu3.setPadding((new Insets(40, 20, 20, 20)));
        leftMenu3.setAlignment(Pos.CENTER);
        leftMenu3.setSpacing(20);

        HBox rightMenu = new HBox();
        rightMenu.getChildren().addAll(endGame,  newRound);
        rightMenu.setPadding((new Insets(40, 20, 20, 20)));
        rightMenu.setAlignment(Pos.CENTER);
        rightMenu.setSpacing(100);

        //setting up game scene
        borderPane3.setTop(leftMenu3);
        borderPane3.setCenter(grid);
        borderPane3.setBottom(rightMenu);
        gameScene = new Scene(borderPane3, 800, 800);
        stage.setScene(gameScene);

        //actions for the buttons
        addWord.setOnAction(e -> {
            String word = wordInput.getText().toUpperCase();
            this.model.checkWord(word);
            wordInput.clear();
            wordInput.setPromptText("Enter a word");
        });
        newRound.setOnAction(e -> {
            this.stage.setScene(boardChoice);
            this.model.endRound();
            this.model.startGame();
            initUI();
        });
        endGame.setOnAction(e -> {
            this.model.endGame();
            closeProgram();
        });

    }

    private void closeProgram() {
        this.stage.close();
    }

}