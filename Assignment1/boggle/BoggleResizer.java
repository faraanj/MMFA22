package boggle;
import javafx.scene.control.*;
import javafx.scene.text.Font;

/**
 * A BoggleResizer which adjusts the size of buttons on the GUI
 *
 */
public class BoggleResizer {
    int numResized;

    public BoggleResizer(){
        int numResized = 0;
    }

    public void reSizeUp(Button button){
        if(button.getPrefHeight() < 76){
            double inch = button.getFont().getSize() + 1;
            button.setFont(new Font(inch));
            double height = button.getPrefHeight() + 4;
            double width = button.getPrefWidth() + 4;
            button.setPrefSize(width, height);
        }
    }

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
