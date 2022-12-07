package boggle;
import javafx.scene.control.*;
import javafx.scene.text.Font;

/**
 * A BoggleResizer which adjusts the size of buttons on the GUI
 *
 */
public class BoggleResizer {

    /**
     * BoggleResizer constructor
     */
    public BoggleResizer(){
    }

    /**
     * @param button
     * Increases the size of the button as well as the size of the font within
     * if the size of the button is within the bounds
     */
    public void reSizeUp(Button button){
        if(button.getPrefHeight() < 76){
            double inch = button.getFont().getSize() + 1;
            button.setFont(new Font(inch));
            double height = button.getPrefHeight() + 4;
            double width = button.getPrefWidth() + 4;
            button.setPrefSize(width, height);
        }
    }

    /**
     * @param button
     * Decreases the size of the button as well as the size of the font within
     * if the size of the button is within the bounds
     */
    public void reSizeDown(Button button){
        if(button.getPrefHeight() > 40){
            double inch = button.getFont().getSize() - 1;
            button.setFont(new Font(inch));
            double height = button.getPrefHeight() - 4;
            double width = button.getPrefWidth() - 4;
            button.setPrefSize(width, height);
        }
    }
}
