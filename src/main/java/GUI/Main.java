package GUI;

import FileIO.FileIO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,800, 350);
        FileIO.makeDirectory(System.getProperty("user.home") + "/Desktop/" + "music-info");

        root.setPrefSize(800, 600);

        FXMLLoader fxmlToolbarLoader = new FXMLLoader(getClass().getResource("fxml/Toolbar.fxml"));
        root.setTop(fxmlToolbarLoader.<MenuBar>load());

        FXMLLoader fxmlPlayerTabLoader = new FXMLLoader(getClass().getResource("fxml/PlayerTab.fxml"));
        root.setRight(fxmlPlayerTabLoader.<VBox>load());

        FXMLLoader fxmlMusicListLoader = new FXMLLoader(getClass().getResource("fxml/MusicList.fxml"));
        root.setLeft(fxmlMusicListLoader.<HBox>load());

        root.setVisible(true);
        primaryStage.setTitle("title");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
