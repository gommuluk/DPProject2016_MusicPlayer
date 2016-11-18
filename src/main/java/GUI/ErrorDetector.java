package GUI;

import javafx.scene.control.TitledPane;
import javafx.scene.control.Label;


/**
 * Created by Elliad on 2015-12-08.
 * This Class can make new Frame
 * When Error Detected.
 */
public class ErrorDetector {
    public ErrorDetector() {
    	
    	TitledPane errorFrame = new TitledPane();
    	errorFrame.setText("Error");
    	errorFrame.getChildrenUnmodifiable().add(new Label("\tCannot Find chosen File"));
    	errorFrame.setPrefSize(200, 50);
        errorFrame.setVisible(true);
    	
//    	BorderPane errorFrame = new BorderPane ("Error");
//        errorFrame.add(new Label("\tCannot Find chosen File"));     

    }
}