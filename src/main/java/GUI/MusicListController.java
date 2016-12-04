package GUI;

import Model.CurrentMusic;
import Model.Music;
import Model.MusicList;
import Model.MusicListManager;
import Model.Sort.AddressSort;
import Model.Sort.NameAscSort;
import Model.Sort.NameDescSort;
import Model.Sort.RandomSort;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import sun.security.pkcs11.wrapper.CK_SSL3_RANDOM_DATA;

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
    @FXML
    private ComboBox<String> sortMode;

    private int pressedBtn;
    private enum Now {
        Playlist,
        FavoritePlaylist,
        RecentPlaylist
    }

    Now playinglist;

    // Music list to be drawn
    private ObservableList<Music> musicList;

    @FXML
    private void changeMusic(MouseEvent event) {
        if (event.getClickCount() == 2) {
            MusicListManager.getInstance().setCurrentList(pressedBtn);
            Music music = musicListView.getSelectionModel().getSelectedItem();
            CurrentMusic.getInstance().stop();
            if(CurrentMusic.getInstance().setMedia(music.getFileAddress())) {
                CurrentMusic.getInstance().play();
                MusicListManager.getInstance().addToRecentPlayList(CurrentMusic.getInstance().getMusic());
            }
        }
    }

    @FXML
    private void changeSortMode(ActionEvent event) {
        String mode = sortMode.getValue();
        switch(mode) {
            case "제목 순" :
                MusicListManager.getInstance().setSortBehavior(new NameAscSort());
                break;
            case "제목 역순" :
                MusicListManager.getInstance().setSortBehavior(new NameDescSort());
                break;
            case "섞기" :
                MusicListManager.getInstance().setSortBehavior(new RandomSort());
                break;
            default : //경로 + 제목순
                MusicListManager.getInstance().setSortBehavior(new AddressSort());
                break;

        }
        MusicListManager.getInstance().performSort();
        redrawPlaylist(MusicListManager.getInstance().getCurrentList());
    }

    private void redrawPlaylist(MusicList drawlist) {
        musicList.setAll(drawlist.getMusicList());
        musicListView.setItems(musicList);
    }

    @FXML
    private void showPlaylist(ActionEvent event) {
        pressedBtn = 0;
        redrawPlaylist(MusicListManager.getInstance().getPlaylist());
    }

    @FXML
    private void showFavoritePlaylist(ActionEvent event) {
        pressedBtn = 1;
        redrawPlaylist(MusicListManager.getInstance().getFavoritePlaylist());
    }

    @FXML
    private void showRecentPlaylist(ActionEvent event) {
        pressedBtn = 2;
        redrawPlaylist(MusicListManager.getInstance().getRecentPlaylist());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.musicList = new SimpleListProperty<>(FXCollections.observableArrayList());
        musicListView.setItems(musicList);
        pressedBtn = 0;
        playinglist = null;

        MusicListManager.getInstance().addRecentPlaylistObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (playinglist == Now.RecentPlaylist) {
                    redrawPlaylist(MusicListManager.getInstance().getRecentPlaylist());
                }
            }
        }).addPlaylistObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (playinglist == Now.Playlist) {
                    redrawPlaylist(MusicListManager.getInstance().getPlaylist());
                }
            }
        }).addFavoritePlaylistObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (playinglist == Now.FavoritePlaylist) {
                    redrawPlaylist(MusicListManager.getInstance().getFavoritePlaylist());
                }
            }
        });
    }
}
