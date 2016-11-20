package GUI;

import Music.MusicListManager;
import Util.RecursiveFinder;

import javax.swing.*;

import FileIO.FileIO;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Path;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.*;
import java.io.File;

class Toolbar extends MenuBar {
    /* These Menus are create into ToolBar */
	private final Menu fileMenu = new Menu("_File Path");
	private final MenuItem setMenuItem = new MenuItem("Set");

	private final Menu alarmMenu = new Menu("_Alarm");
	private final MenuItem setAlarmMenuItem = new MenuItem("Set");
    
	private final Menu automaticShutdownMenu = new Menu("_Automatic Shutdown");
	private final MenuItem setAutomaticShutdownMenuItem = new MenuItem("Set");

    /* Constructor */
    public Toolbar(MusicList musicList) {
        onCreate(musicList);
        onAlarmToolBarCreate();
    	onAutomaticShutdownToolBarCreate();
    }

    /* Constructor */
    public Toolbar(){
    	onAlarmToolBarCreate();
    	onAutomaticShutdownToolBarCreate();
    }

    private void onCreate(MusicList musicList) {
    	
        //connect musicList

        //create menu items
    	setMenuItem.setMnemonicParsing(true);

        fileMenu.getItems().add(setMenuItem);

        //add menu to this
        this.getMenus().add(fileMenu);

        //add this to the frame
        this.setVisible(true);
        
        setMenuItem.setOnAction(e -> {
            DirectoryChooser chooser = new DirectoryChooser();
            File returnVal = chooser.showDialog(null);
            if (returnVal != null) {
                try {
                    RecursiveFinder finder = new RecursiveFinder(
                            chooser.getInitialDirectory().toString());
                    String[] paths = finder.find()
                            .stream()
                            .map(Path::toAbsolutePath)
                            .map(Path::toString)
                            .toArray(String[]::new);

                    for (String path : paths) {
                        MusicListManager.getInstance().addMusic(path);
                    }
                    musicList.arrayListToListModel(MusicListManager.getInstance().getMusicList());
                    MusicList.listNum = 0;
                    musicList.getPanel().updateUI();
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
        alarmMenu.getItems().add(setAlarmMenuItem);
        
        //add menu to this
        this.getMenus().addAll(alarmMenu);
        
        //add this to the frame
        this.setVisible(true);
        
        setAlarmMenuItem.setOnAction(e -> new AlarmFrame());
    }
    /* Alarm Tools */
    private void onAutomaticShutdownToolBarCreate(){
    	
    	setAutomaticShutdownMenuItem.setMnemonicParsing(true);
        
        //add menu items to menus
        automaticShutdownMenu.getItems().add(setAutomaticShutdownMenuItem);
        
        //add menu to this
        this.getMenus().addAll(automaticShutdownMenu);
        
        //add this to the frame
        this.setVisible(true);
        
        setAutomaticShutdownMenuItem.setOnAction(e -> {
            new ShutdownFrame();
        });
    }
}
