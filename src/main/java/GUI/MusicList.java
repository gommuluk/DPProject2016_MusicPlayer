package GUI;

import Music.CurrentMusic;
import Music.Music;
import Music.MusicListManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class MusicList {
    private ListView<Music> musicList; // Store Music List
    public static int listNum = 0;                                                  // Static Variable ; that can recognize list that you are playing now

    public MusicList(PlayerTab playerTab) {
    	musicList = new ListView<Music>();
        musicList.setOrientation(Orientation.VERTICAL);

        //musicList.setPrefWidth(100);
        //musicList.setPrefHeight(70);
        musicList.setOnMouseClicked(new EventHandler<MouseEvent>() {

             @Override
             public void handle(MouseEvent click) {

                 if (click.getClickCount() == 2) {
                     Music currentMusic = musicList.getSelectionModel().getSelectedItem();

                     playerTab.doStop();
                     CurrentMusic.getInstance().setMedia(musicList.getSelectionModel().getSelectedItems().get(0).getFilename());
                     MusicListManager.getInstance().addToRecentPlayList(CurrentMusic.getInstance().toMusic());
                     playerTab.doPlay();
                     listNum = Tab.listNum;
                 }
             }
         });

    }

    public Pane getPane() {
        return new Pane();
    }

    public void setMusicList(ArrayList<Music> arrMusic) {
        ObservableList<Music> items = FXCollections.observableArrayList(arrMusic);
        musicList.setItems(items);
    }

}


