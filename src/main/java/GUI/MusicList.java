package GUI;

import Model.CurrentMusic;
import Model.Music;
import Model.MusicListManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.omg.CORBA.Current;

import java.util.ArrayList;

public class MusicList {

    private ListView<Music> musicList;
    private BorderPane musicListPane = new BorderPane();
    private VBox vBox;

    public MusicList(PlayerTab playerTab) {
    	musicList = new ListView<>();
        musicList.setOrientation(Orientation.VERTICAL);

        musicList.setPrefHeight(100);
        musicList.setMaxWidth(100);
        musicList.setOnMouseClicked(click -> {

            if (click.getClickCount() == 2) {
                Music currentMusic = musicList.getSelectionModel().getSelectedItem();
                CurrentMusic.getInstance().stop();
                if(CurrentMusic.getInstance().setMedia(currentMusic.getFileAddress())) {
                    CurrentMusic.getInstance().play();
                    MusicListManager.getInstance().addToRecentPlayList(CurrentMusic.getInstance().getMusic());
                }
            }
        });

        vBox = new VBox();

        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        updateVBox();

        musicListPane.setCenter(vBox);
        musicList.setVisible(true);
        musicListPane.setVisible(true);

    }

    public BorderPane getPane() {
        return musicListPane;
    }

    public void setMusicList(ArrayList<Music> arrMusic) {

        ObservableList<Music> items = FXCollections.observableArrayList(arrMusic);
        musicList.setItems(items);
        updateVBox();
    }

    public void updateVBox() {
        vBox.getChildren().clear();
        vBox.getChildren().add(musicList);
    }

}
