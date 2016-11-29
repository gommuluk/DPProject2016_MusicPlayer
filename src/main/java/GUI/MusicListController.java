package GUI;

import Model.CurrentMusic;
import Model.Music;
import Model.MusicListManager;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;

public class MusicListController implements Initializable {
    @FXML
    private Button bnPlaylist;
    @FXML
    private Button bnFavoritePlaylist;
    @FXML
    private Button bnRecentPlaylist;
    @FXML
    private ListView<Music> musicListView;

    // Music list to be drawn
    private ObservableList<Music> musicList;

    @FXML
    private void changeMusic(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Music music = musicListView.getSelectionModel().getSelectedItem();

            CurrentMusic.getInstance().setMedia(music.getFileName());
            MusicListManager.getInstance().addToRecentPlayList(CurrentMusic.getInstance().getMusic());
        }
    }

    @FXML
    private void showPlaylist(ActionEvent event) {

    }

    @FXML
    private void showFavoritePlaylist(ActionEvent event) {

    }

    @FXML
    private void showRecentPlaylist(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.musicList = new SimpleListProperty<>(FXCollections.observableArrayList());
        musicListView.setItems(musicList);

        MusicListManager.getInstance().addRecentPlaylistObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {

            }
        }).addPlaylistObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {

            }
        }).addFavoritePlaylistObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {

            }
        });
    }
}
