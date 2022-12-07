package boggle;
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

import java.util.ArrayList;

//new
public class BoggleView{

    BoggleResizer boggleResizer; //Boggle Resizer for Accessibility feature
    BoggleModel model; //reference to model
    Stage stage;

    Scene boardChoice, lettersChoice, gameScene; //scenes to switch to

    Button addWord, endGame, newRound, gridSmall, gridLarge, randomizeBoardLetters, InputBoardLetters, Big1, Big2, Big3, Small1, Small2, Small3; //buttons for functions
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
        this.boggleResizer = new BoggleResizer();
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

        boardChoiceLabel.setText("Click 5*5 to play on a big (5x5) grid; \n 4*4 to play on a small (4x4) one:\n Click +/- to adjust button size");
        boardChoiceLabel.setFont(new Font(14));
        boardChoiceLabel.setStyle("-fx-text-fill: #232E94");
        boardChoiceLabel.setAlignment(Pos.CENTER);
        letterChoiceLabel.setText("Click \"randomize letters\" to randomize letters in the grid;\n " +
                "\"Input Letters\" to choose the letters yourself\n Click +/- to adjust button size");
        letterChoiceLabel.setFont(new Font(14));
        letterChoiceLabel.setStyle("-fx-text-fill: #232E94");
        letterChoiceLabel.setAlignment(Pos.CENTER);

        //adding buttons for first screen
        gridSmall = new Button("4*4");
        gridSmall.setId("Grid Size 4*4");
        gridSmall.setPrefSize(100, 40);
        gridSmall.setFont(new Font(12));
        gridSmall.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        //Button to make content bigger for the first scene
        Big2 = new Button("+");
        Big2.setId("+");
        Big2.setPrefSize(100, 40);
        Big2.setFont(new Font(20));
        Big2.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        //Button to make content smaller for the first scene
        Small2 = new Button("-");
        Small2.setId("-");
        Small2.setPrefSize(100, 40);
        Small2.setFont(new Font(20));
        Small2.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        gridLarge = new Button("5*5");
        gridLarge.setId("Grid Size 5*5");
        gridLarge.setPrefSize(100, 40);
        gridLarge.setFont(new Font(12));
        gridLarge.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        //adding buttons for second screen
        randomizeBoardLetters = new Button("Randomize \n    Letters");
        randomizeBoardLetters.setId("Randomize Letters");
        randomizeBoardLetters.setPrefSize(100, 40 );
        randomizeBoardLetters.setFont(new Font(12));
        randomizeBoardLetters.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        InputBoardLetters = new Button(" Input \nLetters");
        InputBoardLetters.setId("Input Letters");
        InputBoardLetters.setPrefSize(100, 40);
        InputBoardLetters.setFont(new Font(12));
        InputBoardLetters.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        //Button to make content bigger for the second scene
        Big3 = new Button("+");
        Big3.setId("+");
        Big3.setPrefSize(100, 40);
        Big3.setFont(new Font(20));
        Big3.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        //Button to make content smaller for the second scene
        Small3 = new Button("-");
        Small3.setId("-");
        Small3.setPrefSize(100, 40);
        Small3.setFont(new Font(20));
        Small3.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        //text field for second screen
        letterInput = new TextField();
        letterInput.setPromptText("Input your letter choice");
        letterInput.setPrefColumnCount(this.model.boardSize*this.model.boardSize);


        //layout for scene 1 buttons
        HBox leftMenu1 = new HBox();
        leftMenu1.getChildren().addAll(gridSmall, gridLarge, Big2, Small2);
        leftMenu1.setPadding((new Insets(40, 20, 20, 20)));
        leftMenu1.setAlignment(Pos.CENTER);
        leftMenu1.setSpacing(55);

        //layout for scene 2 buttons
        HBox leftMenu2 = new HBox();
        leftMenu2.getChildren().addAll(randomizeBoardLetters,  InputBoardLetters, Big3, Small3);
        leftMenu2.setPadding((new Insets(40, 20, 20, 20)));
        leftMenu2.setAlignment(Pos.CENTER);
        leftMenu2.setSpacing(45);

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
        borderPane1.setCenter(leftMenu1);
        boardChoice = new Scene(borderPane1, 800, 700);

        //setting up second scene
        borderPane2.setTop(topMenu2);
        borderPane2.setCenter(leftMenu2);
        borderPane2.setBottom(letterInput);
        lettersChoice = new Scene(borderPane2, 800, 700);

        //actions for choosing grid size
        gridSmall.setOnAction(e -> {
            stage.setScene(lettersChoice);
            this.model.boardSize = 4;
        });
        gridLarge.setOnAction(e -> {
            stage.setScene(lettersChoice);
            this.model.boardSize = 5;
        });

        Big2.setOnAction(e -> {
            boggleResizer.reSizeUp(Big2);
            boggleResizer.reSizeUp(Small2);
            boggleResizer.reSizeUp(gridLarge);
            boggleResizer.reSizeUp(gridSmall);
        });

        Small2.setOnAction(e -> {
            boggleResizer.reSizeDown(Big2);
            boggleResizer.reSizeDown(Small2);
            boggleResizer.reSizeDown(gridLarge);
            boggleResizer.reSizeDown(gridSmall);
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

        Big3.setOnAction(e -> {
            boggleResizer.reSizeUp(Big3);
            boggleResizer.reSizeUp(Small3);
            boggleResizer.reSizeUp(randomizeBoardLetters);
            boggleResizer.reSizeUp(InputBoardLetters);
        });

        Small3.setOnAction(e -> {
            boggleResizer.reSizeDown(Big3);
            boggleResizer.reSizeDown(Small3);
            boggleResizer.reSizeDown(randomizeBoardLetters);
            boggleResizer.reSizeDown(InputBoardLetters);
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
        ArrayList<Button> buttonArrayList = new ArrayList<>();
        GridPane grid = new GridPane();
        for (int r = 0; r < this.model.boardSize; r++) {
            for (int c = 0; c < this.model.boardSize; c++) {
                Button button = new Button(Character.toString(this.model.grid.getCharAt(r, c)));
                button.setPrefSize(40,40);
                buttonArrayList.add(button);
                grid.add(button, c, r);
                // Allows the buttons to be clicked, adds the clicked character to the textField
                int finalR = r;
                int finalC = c;
                button.setOnAction(e -> {
                    String currWord = wordInput.getText();
                    String newWord = currWord + Character.toString(this.model.grid.getCharAt(finalR, finalC));
                    wordInput.setText(newWord);
                });
            }
        }
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        addWord = new Button("Add Word");
        addWord.setId("Add Word");
        addWord.setPrefSize(120, 60);
        addWord.setFont(new Font(14));
        addWord.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        //Button to make content bigger for the third scene
        Big1 = new Button("+");
        Big1.setId("+");
        Big1.setPrefSize(120, 60);
        Big1.setFont(new Font(30));
        Big1.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        //Button to make content smaller for the third scene
        Small1 = new Button("-");
        Small1.setId("-");
        Small1.setPrefSize(120, 60);
        Small1.setFont(new Font(30));
        Small1.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        endGame = new Button("End Game");
        endGame.setId("End Game");
        endGame.setPrefSize(120, 60);
        endGame.setFont(new Font(14));
        endGame.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        newRound = new Button("New Round");
        newRound.setId("new Round");
        newRound.setPrefSize(120, 60);
        newRound.setFont(new Font(14));
        newRound.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        //text field for game screen
        wordInput = new TextField();
        wordInput.setPromptText("Enter a word");
        wordInput.setPrefColumnCount(10);

        //layout for game buttons
        HBox leftMenu3 = new HBox();
        leftMenu3.getChildren().addAll(addWord,  wordInput, Big1, Small1);
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
        gameScene = new Scene(borderPane3, 800, 700);
        stage.setScene(gameScene);

        //actions for the buttons
        addWord.setOnAction(e -> {
            String word = wordInput.getText().toUpperCase();
            this.model.checkWord(word);
            wordInput.clear();
            wordInput.setPromptText("Enter a word");
        });
        Big1.setOnAction(e -> {
            for (Button butt : buttonArrayList) {
                boggleResizer.reSizeUp(butt);
            }
        });

        Small1.setOnAction(e -> {
            for (Button butt : buttonArrayList) {
                boggleResizer.reSizeDown(butt);
            }
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
