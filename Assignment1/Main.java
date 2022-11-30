import boggle.BoggleController;
import boggle.BoggleGame;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Main class for the first Assignment in CSC207, Fall 2022
 */
public class Main extends Application {
    /**
     * Main method.
     * @param args command line arguments.

    public static void main(String[] args)  {
    BoggleController b = new BoggleController();

    //BoggleGame b = new BoggleGame();
    //b.giveInstructions();
    //b.playGame();
    }
     **/
    /** Main method
     */
    public static void main(String[] args) {
        launch(args);
    }

    /** Start the visualization
     */
    public void start(Stage primaryStage) throws Exception {
        BoggleController b = new BoggleController();
        b.start(primaryStage);

        //BoggleGame b = new BoggleGame();
        //b.giveInstructions();
        //b.playGame();
    }

}
