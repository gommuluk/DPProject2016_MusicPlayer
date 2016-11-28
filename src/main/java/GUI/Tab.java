package GUI;

import Model.Music;
import Model.MusicListManager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

class Tab extends Pane {
    /* whole Buttons in MP3Music List Tab */
    private final Button allMusic = new Button("전체 음악");
    private final Button favoriteMusic = new Button("즐겨찾기");
    private final Button recentMusic = new Button("최근 재생한 곡");


    private GridPane buttonPanel;
    private MusicList musicList;
    private PlayerTab playerTab;


    public Tab() {
        setActionListeners();
        buttonPanel = new GridPane();
        buttonPanel.setHgap(10);
        buttonPanel.setVgap(10);
        buttonPanel.setPadding(new Insets(25, 25, 25, 25));
        buttonPanel.add(allMusic, 0, 0);
        buttonPanel.add(favoriteMusic, 0, 1);
        buttonPanel.add(recentMusic, 0, 2);
        this.getChildren().add(buttonPanel);
        this.setVisible(true);
    }

    /* can add buttons into Panel. this Function executed when this Object initialize */
    /* add ActionListeners into Buttons */
    private void setActionListeners() {
        this.allMusic.setOnAction(e -> {
            MusicListManager.getInstance().currentList = 0;
            tabClicked();
        });

        this.favoriteMusic.setOnAction(e -> {
            MusicListManager.getInstance().currentList = 1;
            tabClicked();
        });

        this.recentMusic.setOnAction(e -> {
            MusicListManager.getInstance().currentList = 2;
            tabClicked();
        });

    }
    /* connect Buttons to Control other Class' Object */
    public void connectPanels(PlayerTab playerTab, MusicList musicList) {
        this.musicList = musicList;
        this.playerTab = playerTab;
    }
    /* refresh lists and reset whole player's buttons */
    private void tabClicked() {
        musicList.setMusicList(MusicListManager.getInstance().getCurrentList());
        musicList.getPane().requestLayout();
        playerTab.reset();
        //playerTab.updateUI();
    }
    /* can give Favorite List Button */
    public Button getFavoriteButton() {
        return this.favoriteMusic;
    }


}
