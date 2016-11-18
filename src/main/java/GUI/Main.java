package GUI;

import FileIO.FileIO;
import Music.CurrentMusic;

import javafx.scene.control.MenuBar;;
import javafx.scene.layout.BorderPane;;
import javafx.embed.swing.*;


class Main {
    private final BorderPane mainPane = new BorderPane ();

    public static void main(String[] args) {
        Main main = new Main();
        FileIO.makeDirectory(System.getProperty("user.home") + "/Desktop/" + "music-info");
        main.init();
    }

    private void init() {
        PlayerTab playerPanel = new PlayerTab();                        // Player Panel
        MusicList musicList = new MusicList(playerPanel);               // Music List Panel
        Tab tabPanel = new Tab();                                       // Button Tab Panel

        playerPanel.connectPanels(tabPanel);                            // Connect Panels
        tabPanel.connectPanels(playerPanel, musicList);                            // Connect Panels

        CurrentMusic.getInstance().setPlayerTab(playerPanel);           // SetUp Panels

        this.mainPane.setPrefSize(800, 500);

        this.mainPane.setTop(new Toolbar(musicList));
//        addStackPane(hbox);
        this.mainPane.setLeft(tabPanel.createTab(musicList));
        this.mainPane.setCenter(new ScrollBar(musicList.getPanel()));
        this.mainPane.setRight(playerPanel);

        this.mainPane.setVisible(true);                                                        //Setting Layout and add Panels end
    }
}
