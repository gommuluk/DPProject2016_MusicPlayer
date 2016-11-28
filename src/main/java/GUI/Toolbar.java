package GUI;

import Model.Music;
import Model.MusicListManager;
import Util.RecursiveFinder;

import java.io.IOException;
import java.nio.file.Path;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.File;

public class Toolbar implements Initializable {
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu fileMenu;
    @FXML
    private MenuItem setMenuItem;
    @FXML
    private Menu alarmMenu;
    @FXML
    private MenuItem setAlarmMenuItem;
    @FXML
    private Menu automaticShutdownMenu;
    @FXML
    private MenuItem setAutomaticShutdownMenuItem;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void setDirectory() {
        DirectoryChooser chooser = new DirectoryChooser();
        File returnVal = chooser.showDialog(null);
        MusicListManager musicList = MusicListManager.getInstance();
        if (returnVal != null) {
            try {
                RecursiveFinder finder = new RecursiveFinder(
                    returnVal.getPath());
                String[] paths = finder.find()
                    .stream()
                    .map(Path::toAbsolutePath)
                    .map(Path::toString)
                    .toArray(String[]::new);

                for (String path : paths) {
                    MusicListManager.getInstance().addMusic(path);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        for(Music iter : MusicListManager.getInstance().getCurrentList()) {
            System.out.println(iter.getFileAddress());
        }//TODO//TEST
    }

    @FXML
    private void openAlarm(ActionEvent event) {
        new AlarmFrame();
    }

    @FXML
    private void openAutoShutdown(ActionEvent event) {
        new ShutdownFrame();
    }
}
