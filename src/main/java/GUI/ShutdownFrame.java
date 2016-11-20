package GUI;

import javax.swing.*;

import Alarm.AutomaticShutdown;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

class ShutdownFrame extends JFrame{	// shutdown frame extends jframe to make shutdonw setting window
	private JFXPanel fxPanel;
	private ComboBox<String> selectTimeComboBox;
	private Label selectTimeLabel;
	private Label textLabel;
	private Button okButton;
    private Group root;
	ShutdownFrame(){	// make shutdown frame
        super("AutomaticShutdown Window");
        this.setSize(250,150);
        this.setLocation(400,200);
        addJFXPanel();

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


    private void addTextLabel(){	// add text label
		textLabel = new Label("몇 분후 종료하시겠습니까?");
        textLabel.setLayoutX(20);
        textLabel.setLayoutY(15);


		root.getChildren().add(textLabel);
	}
	private void addSelectTimeComboBox(){	// add select time
		selectTimeComboBox = new ComboBox<>();
        selectTimeComboBox.getItems().addAll("1", "2", "3", "4", "5", "10", "15", "30", "45", "60");

        selectTimeComboBox.setLayoutX(40);
        selectTimeComboBox.setLayoutY(55);

		selectTimeLabel = new Label("분");
        selectTimeLabel.setLayoutX(110);
        selectTimeLabel.setLayoutY(50);
		root.getChildren().add(selectTimeLabel);
		root.getChildren().add(selectTimeComboBox);
	}
	private void addOkButton(){	// add ok button
		okButton = new Button("OK");
        okButton.setLayoutX(160);
        okButton.setLayoutY(50);
        root.getChildren().add(okButton);
        okButton.setOnAction(e -> {	// when ok button is clicked, make automatic shutdown
            this.setVisible(false);

            String selectMinute = selectTimeComboBox.getValue();
            selectMinute = selectMinute.trim();
            AutomaticShutdown.getInstance().setShutdown(selectMinute);
            if(AutomaticShutdown.getInstance().getStatus())	// if already running, change to later thing
                AutomaticShutdown.getInstance().start();
        });

	}
    private void addJFXPanel() {
        fxPanel = new JFXPanel();
        this.add(fxPanel);
        Platform.runLater(() -> initFX(fxPanel));
    }

    private void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private Scene createScene() {
        root = new Group();
        addTextLabel();
        addSelectTimeComboBox();
        addOkButton();

        return (new Scene(root, Color.GREENYELLOW));
    }

}
