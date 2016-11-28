package GUI;

import FileIO.FileIO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,800, 350);
        FileIO.makeDirectory(System.getProperty("user.home") + "/Desktop/" + "music-info");
        Tab tabPanel = new Tab();                                       // Button Tab Panel
        root.setLeft(tabPanel);

        root.setPrefSize(800, 600);

        FXMLLoader toolbarLoader = new FXMLLoader(getClass().getResource("fxml/Toolbar.fxml"));
        Parent toolbarNode = toolbarLoader.<MenuBar>load();
        root.setTop(toolbarNode);

        FXMLLoader playerPanel = new FXMLLoader(getClass().getResource("fxml/PlayerTab.fxml"));
        Parent playerTabNode = playerPanel.<VBox>load();
        PlayerTab playerTab = playerPanel.getController();
        root.setRight(playerTabNode);

        MusicList musicList = new MusicList(playerTab);               // MP3Music List Panel
        tabPanel.connectPanels(playerTab, musicList);
        root.setCenter(musicList.getPane());

        root.setVisible(true);
        primaryStage.setTitle("title");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
