package GUI;

import Music.MusicListManager;
import Util.RecursiveFinder;

import javax.swing.*;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Path;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

class Toolbar extends JMenuBar {
    /* These Menus are create into ToolBar */
	private final MenuBar menuBar = new MenuBar();
    //private final JMenu fileMenu = new JMenu("File Path");
	private final Menu fileMenu = new Menu("_File Path");
    //private final JMenuItem setMenuItem = new JMenuItem("Set");
	private final MenuItem setMenuItem = new MenuItem("Set");

    //private final JMenu alarmMenu = new JMenu("Alarm");
	private final Menu alarmMenu = new Menu("_Alarm");
    //private final JMenuItem setAlarmMenuItem = new JMenuItem("Set");
	private final MenuItem setAlarmMenuItem = new MenuItem("Set");
    
    //private final JMenu automaticShutdownMenu = new JMenu("Automatic Shutdown");
	private final Menu automaticShutdownMenu = new Menu("_Automatic Shutdown");
    //private final JMenuItem setAutomaticShutdownMenuItem = new JMenuItem("Set");
	private final MenuItem setAutomaticShutdownMenuItem = new MenuItem("Set");

    /* Constructor */
    public Toolbar(MusicList musicList) {
        onCreate(musicList);
    }

    /* Constructor */
    public Toolbar(){
    	onAlarmToolBarCreate();
    	onAutomaticShutdownToolBarCreate();
    }

    private void onCreate(MusicList musicList) {
    	
        //connect musicList

        //create menu items
        //setMenuItem.setMnemonic(KeyEvent.VK_N);
    	setMenuItem.setMnemonicParsing(true);
        //setMenuItem.setActionCommand("Set");

        //add menu items to menus
        //fileMenu.add(setMenuItem);
        fileMenu.getItems().add(setMenuItem);

        //add menu to this
        //this.add(fileMenu);
        menuBar.getMenus().add(fileMenu);

        //add this to the frame
        menuBar.setVisible(true);

        /*setMenuItem.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    RecursiveFinder finder = new RecursiveFinder(
                            chooser.getSelectedFile().getPath());
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
        });*/
        
        setMenuItem.setOnAction(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    RecursiveFinder finder = new RecursiveFinder(
                            chooser.getSelectedFile().getPath());
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
        //setAlarmMenuItem.setActionCommand("Set");
        
        //add menu items to menus
        alarmMenu.getItems().add(setAlarmMenuItem);
        
        //add menu to this
        menuBar.getMenus().addAll(alarmMenu);
        
        //add this to the frame
        menuBar.setVisible(true);
        
        setAlarmMenuItem.setOnAction(e -> new AlarmFrame());
    }
    /* Alarm Tools */
    private void onAutomaticShutdownToolBarCreate(){
    	
    	setAutomaticShutdownMenuItem.setMnemonicParsing(true);
    	//setAutomaticShutdownMenuItem.setMnemonic(KeyEvent.VK_N);
        //setAutomaticShutdownMenuItem.setActionCommand("Set");
        
        //add menu items to menus
        automaticShutdownMenu.getItems().add(setAutomaticShutdownMenuItem);
        
        //add menu to this
        menuBar.getMenus().addAll(automaticShutdownMenu);
        
        //add this to the frame
        menuBar.setVisible(true);
        
        setAutomaticShutdownMenuItem.setOnAction(e -> {
            new ShutdownFrame();
        });
    }
}
