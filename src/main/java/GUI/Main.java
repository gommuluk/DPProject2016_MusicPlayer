package GUI;

import FileIO.FileIO;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,800, 350);
        FileIO.makeDirectory(System.getProperty("user.home") + "/Desktop/" + "music-info");
        PlayerTab playerPanel = new PlayerTab();                        // Player Panel
        MusicList musicList = new MusicList(playerPanel);               // MP3Music List Panel
        Tab tabPanel = new Tab();                                       // Button Tab Panel

        playerPanel.connectPanels(tabPanel);                            // Connect Panels
        tabPanel.connectPanels(playerPanel, musicList);                            // Connect Panels

        root.setPrefSize(800, 600);

        root.setTop(new Toolbar(musicList));

        root.setLeft(tabPanel);
        root.setCenter(musicList.getPane());
        root.setRight(playerPanel);

        root.setVisible(true);
        primaryStage.setTitle("title");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
