package boggle;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A Boggle Application, in JavaFX
 */

public class BoggleController extends Application{
    private BoggleModel model;
    private BoggleView view;


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
    }

}