package boggle;


import boggle.BoggleModel;
import boggle.BoggleView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A Boggle Application, in JavaFX
 */
// new

public class BoggleController extends Application{
    BoggleModel model;
    BoggleView view;


    /**
     * Main method
     *
     * @param args agument, if any
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start method.  Control of application flow is dictated by JavaFX framework
     *
     * @param primaryStage stage upon which to load GUI elements
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.model = new BoggleModel();
        this.model.startGame();
        this.view = new BoggleView(model, primaryStage);
        //this.model.playGame();
    }

}