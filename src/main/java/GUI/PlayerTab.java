package GUI;

import FileIO.FilePathParser;
import Music.CurrentMusic;
import Music.Lyric_Repeat;
import Music.Music;
import Music.MusicListManager;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.omg.CORBA.Current;

import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public class PlayerTab extends JFXPanel {

    private VBox root = new VBox();

    public static Label lyric;

    /* Music info -> Image, name */
    private BorderPane musicInfoPanel;
    private Label musicName;
    private Image musicImage;
    private ImageView musicImageView;

    /* Buttons */
    private TilePane buttonPanel;
    private Button playButton;
    private Button seekNextButton;
    private Button seekPreviousButton;
    private Button stopButton;
    private Button playModeButton;
    private Button starButton; // for favorite
    private Slider volumeSlider;
    private Slider currentTimeSlider;
    private Tab tabPanel;

    public PlayerTab() {
        this.setPreferredSize(new Dimension(240, 300));
        this.setVisible(true);

        Platform.runLater(() -> {
            initFX(this);
        });
    }

    // can play media using javafx scene and mediaplayer
    private void initFX(JFXPanel fxPanel) {
        Scene scene = initScene();
        fxPanel.setScene(scene);
    }

    private Scene initScene() {
        addImageLabel();

        addCurrentTimeSlider();

        buttonPanel = new TilePane();
        addSeekPreviousButton();
        addPlayButton();
        addSeekNextButton();

        addPlayModeButton();
        addStopButton();
        addStarButton();

        root.getChildren().add(buttonPanel);
        addVolumeSlider();
//        //TODO!!!!!
//        addLyric();

        return (new Scene(root, Color.GREENYELLOW));
    }

    private void addImageLabel() {
        if (musicImage == null) {
            musicImage = new Image(getClass().getResourceAsStream("defaultImage.jpg"), 200, 200, true, true);
        }

        musicImageView = new ImageView(musicImage);

        musicInfoPanel = new BorderPane();
        musicInfoPanel.setCenter(musicImageView);

        musicName = new Label("Ready");
        musicName.setTextFill(Color.WHITE);
        musicName.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        musicInfoPanel.setPrefSize(30, 30);
        musicInfoPanel.setBottom(musicName);

        root.getChildren().add(musicInfoPanel);
    }

    private void addCurrentTimeSlider() {
        // TODO
        // horizontal plz
        currentTimeSlider = new Slider();
        currentTimeSlider.setValueChanging(true);
        currentTimeSlider.setMax(100);
        currentTimeSlider.setMin(100);
        CurrentMusic currentMusic = CurrentMusic.getInstance();

        currentTimeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double percent = (newValue.floatValue() - currentTimeSlider.getMin()) / (currentTimeSlider.getMax() - currentTimeSlider.getMin());
            currentMusic.seek((float)percent);
        });

        currentTimeSlider.setValue(currentTimeSlider.getMin());

        currentMusic.addChangeTimeEvent(currentTimeSlider, (Slider jSlider) -> {
            Platform.runLater(() -> {
                Optional<Duration> currentTimeOptional = currentMusic.getCurrentTime();
                Optional<Duration> totalTimeOptional = currentMusic.getTotalTime();
                if (currentTimeOptional.isPresent() && totalTimeOptional.isPresent()) {
                    double percent = currentTimeOptional.get().toMillis() / totalTimeOptional.get().toMillis();
                    jSlider.setValue((int) (percent * (jSlider.getMax() - jSlider.getMin())) + jSlider.getMin());
                }
            });
        });
        root.getChildren().add(currentTimeSlider);
    }

    //add whole Buttons
    private void addButtonImage(Button button, String imageFileName) {
        Image buttonImage = new Image(getClass().getResourceAsStream(imageFileName), 20, 20, true, true);
        if (buttonImage.getWidth() != 0) {
            button.setText(null);
            button.setGraphic(new ImageView(buttonImage));
        }
    }

    private void addSeekPreviousButton() {
        seekPreviousButton = new Button("<<");

        addButtonImage(seekPreviousButton, "seek-previous.png");

        seekPreviousButton.setOnAction(event -> {
            CurrentMusic currentMusic = CurrentMusic.getInstance();
            currentMusic.seekPrevious();
        });
        buttonPanel.getChildren().add(seekPreviousButton);
    }

    private void addPlayButton() {
        playButton = new Button("▶");

        addButtonImage(playButton, "play.jpg");

        playButton.setOnAction(event -> {
            CurrentMusic currentMusic = CurrentMusic.getInstance();
            if (currentMusic.isPlayable()) {
                CurrentMusic.getInstance().play();
                playButton.setText("||");
                addButtonImage(playButton, "pause.png");
                MusicListManager.getInstance().addToRecentPlayList(CurrentMusic.getInstance().toMusic());
            } else {
                CurrentMusic.getInstance().pause();
                reset();
            }
        });
        buttonPanel.getChildren().add(playButton);
    }

    private void addSeekNextButton() {
        seekNextButton = new Button(">>");
        addButtonImage(seekNextButton, "seek-next.png");

        seekNextButton.setOnAction(event -> {
            CurrentMusic currentMusic = CurrentMusic.getInstance();
            currentMusic.seekNext();
        });
        buttonPanel.getChildren().add(seekNextButton);
    }

    private void addPlayModeButton() {
        playModeButton = new Button("A/R");
        addButtonImage(playModeButton,"loop.png");

        playModeButton.setOnAction(event -> {
            if (CurrentMusic.playMode == 0) {
                CurrentMusic.playMode++;
                playModeButton.setText("A/N");

            } else if (CurrentMusic.playMode == 1) {
                CurrentMusic.playMode++;
                playModeButton.setText("O/R");

            } else {
                CurrentMusic.playMode = 0;
                playModeButton.setText("A/R");

            }
        });
        buttonPanel.getChildren().add(playModeButton);
    }

    private void addStopButton() {
        stopButton = new Button("■");

        addButtonImage(stopButton, "stop.png");

        stopButton.setOnAction(e -> {
            CurrentMusic currentMusic = CurrentMusic.getInstance();
            currentMusic.stop();
            reset();
        });
        buttonPanel.getChildren().add(stopButton);
    }

    private void addStarButton() {
        starButton = new Button("★");
        starButton.setOnAction(e -> {
            Music temp = CurrentMusic.getInstance().toMusic();
            if (MusicList.listNum != 1) {
                MusicListManager.getInstance().addToFavoriteMusicList(temp);
            } else {
                MusicListManager.getInstance().deleteToFavoriteMusicList(temp);
                tabPanel.getFavoriteButton().doClick();
            }
        });
        buttonPanel.getChildren().add(starButton);
    }

    private void addVolumeSlider() {
        //vertical plz
        volumeSlider = new Slider();

        // TODO implements with progress bar
        volumeSlider.setMin(0);
        volumeSlider.setMin(100);

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double volume = (newValue.doubleValue() - volumeSlider.getMin()) / (volumeSlider.getMax() - volumeSlider.getMin());
            CurrentMusic.getInstance().setVolume((float)volume);
        });
        root.getChildren().add(volumeSlider);
    }

    private void replaceMusicInfo() {
        try {
            Music music = CurrentMusic.getInstance().toMusic();
            if (music.getAlbumArt() != null) {
                musicImage = new Image(new ByteArrayInputStream(music.getAlbumArt()), 200, 200, true, true);
                musicImageView.setImage(musicImage);
            }
            musicName.setText(FilePathParser.getFileName(music.getFilename()));
        } catch (Exception e) {
        }
    }

    public void doStop() {
        Platform.runLater(() -> stopButton.fire());
    }

    public void doPlay() {
        Platform.runLater(() -> {
            replaceMusicInfo();
            playButton.fire();
        });
    }

    public void reset() {
        Platform.runLater(() -> {
            starButton.setText("★");
            playButton.setText("▶");
            addButtonImage(playButton, "play.jpg");
        });
    }

    public void connectPanels(Tab tabPanel) {
        this.tabPanel = tabPanel;
    }

//    private void addLyric() {
//        lyric = new Label();
//
//        lyric.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
//        lyric.setTextFill(Color.WHITE);
//        lyric.setPrefSize(30, 30);
//
//        // new Lyric_Repeat();
//        //buttonPanel.add(text1);
//    }
}
