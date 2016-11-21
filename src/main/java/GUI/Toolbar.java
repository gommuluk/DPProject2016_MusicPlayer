package GUI;

import Music.MusicListManager;
import Util.RecursiveFinder;

import java.io.IOException;
import java.nio.file.Path;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.File;

public class Toolbar implements Initializable {
    /* These Menus are create into ToolBar */
	/*private final Menu fileMenu = new Menu("_File Path");
	private final MenuItem setMenuItem = new MenuItem("Set");

	private final Menu alarmMenu = new Menu("_Alarm");
	private final MenuItem setAlarmMenuItem = new MenuItem("Set");

	private final Menu automaticShutdownMenu = new Menu("_Automatic Shutdown");
	private final MenuItem setAutomaticShutdownMenuItem = new MenuItem("Set");*/
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

    /* Constructor */
    public Toolbar(MusicList musicList) {
        onCreate(musicList);
        onAlarmToolBarCreate();
    	onAutomaticShutdownToolBarCreate();
    }

    public Toolbar() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public MenuBar getMenuBar(){
    	return menuBar;
    }

    private void onCreate(MusicList musicList) {

        //connect musicList

        //create menu items
    	setMenuItem.setMnemonicParsing(true);

        //fileMenu.getItems().add(setMenuItem);

        //add menu to this
        //menuBar.getMenus().add(fileMenu);

        //add this to the frame
        menuBar.setVisible(true);

        setMenuItem.setOnAction(e -> {
            DirectoryChooser chooser = new DirectoryChooser();
            File returnVal = chooser.showDialog(null);
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
                    musicList.setMusicList(MusicListManager.getInstance().getMP3MusicList());
                    MusicList.listNum = 0;
                    musicList.getPane().requestLayout();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            });
    }

    /* Alarm Tools */
    private void onAlarmToolBarCreate(){

    	setAlarmMenuItem.setMnemonicParsing(true);

        //add menu items to menus
        //alarmMenu.getItems().add(setAlarmMenuItem);

        //add menu to this
        //menuBar.getMenus().addAll(alarmMenu);

        //add this to the frame
        menuBar.setVisible(true);

        setAlarmMenuItem.setOnAction(e -> new AlarmFrame());
    }
    /* Alarm Tools */
    private void onAutomaticShutdownToolBarCreate(){

    	setAutomaticShutdownMenuItem.setMnemonicParsing(true);

        //add menu items to menus
        //automaticShutdownMenu.getItems().add(setAutomaticShutdownMenuItem);

        //add menu to this
        //menuBar.getMenus().addAll(automaticShutdownMenu);

        //add this to the frame
        menuBar.setVisible(true);

        setAutomaticShutdownMenuItem.setOnAction(e -> {
            new ShutdownFrame();
        });
    }
}
