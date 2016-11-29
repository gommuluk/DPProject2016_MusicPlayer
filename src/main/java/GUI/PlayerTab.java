package GUI;

import FileIO.FilePathParser;
import Model.CurrentMusic;
import Model.Music;
import Model.MusicListManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PlayerTab implements Initializable {
    public static Label lyric;

    /* MP3Music info -> Image, name */
    @FXML
    private Label musicName;
    private Image musicImage;
    @FXML
    private ImageView musicImageView;

    /* Buttons */
    @FXML
    private Button playButton;
    @FXML
    private Button seekNextButton;
    @FXML
    private Button seekPreviousButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button playModeButton;
    @FXML
    private Button starButton; // for favorite
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider currentTimeSlider;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addCurrentTimeSliderEventHandler();
        addVolumeSlider();
//        addLyric();
    }

    private void addCurrentTimeSliderEventHandler() {
        // TODO
        // horizontal plz
        CurrentMusic currentMusic = CurrentMusic.getInstance();

        currentTimeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double percent = (newValue.floatValue() - currentTimeSlider.getMin()) / (currentTimeSlider.getMax() - currentTimeSlider.getMin());
            currentMusic.seek((float)percent);
        });

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
    }

    //add whole Buttons
    private void changeButtonToImage(Button button, String imageFileName) {
        Image buttonImage = new Image(getClass().getResourceAsStream(imageFileName), 20, 20, true, true);
        if (buttonImage.getWidth() != 0) {
            button.setText(null);
            button.setGraphic(new ImageView(buttonImage));
        }
    }

    @FXML
    private void seekPrevious(ActionEvent event) {
        CurrentMusic currentMusic = CurrentMusic.getInstance();
        currentMusic.seekPrevious();
    }

    @FXML
    private void play(ActionEvent event) {
        CurrentMusic currentMusic = CurrentMusic.getInstance();
        if (currentMusic.play()) { //TODO
            playButton.setText("||");
            changeButtonToImage(playButton, "pause.png");
            MusicListManager.getInstance().addToRecentPlayList(currentMusic.getMusic());
        } else {
            currentMusic.pause();
            reset();
        }
    }

    @FXML
    private void seekNext(ActionEvent event) {
        CurrentMusic currentMusic = CurrentMusic.getInstance();
        currentMusic.seekNext();
    }

    @FXML
    private void changePlayMode(ActionEvent event) {
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
    }

    @FXML
    private void stop(ActionEvent event) {
        CurrentMusic currentMusic = CurrentMusic.getInstance();
        currentMusic.stop();
        reset();
    }

    @FXML
    private void toggleFavorite() {
        Music temp = CurrentMusic.getInstance().getMusic();
        MusicListManager musicList = MusicListManager.getInstance();
        if (musicList.currentList != 1) {
            musicList.addToFavoriteMusicList(temp);
        } else {
            musicList.deleteToFavoriteMusicList(temp);
        }
    }

    private void addVolumeSlider() {
        // TODO implements with progress bar
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            CurrentMusic.getInstance().setVolume(newValue.floatValue());
        });
    }

    private void replaceMusicInfo() {
        try {
            Music music = CurrentMusic.getInstance().getMusic();
            if (music.getAlbumArt() != null) {
                musicImage = new Image(new ByteArrayInputStream(music.getAlbumArt()), 200, 200, true, true);
                musicImageView.setImage(musicImage);
            }
            musicName.setText(FilePathParser.getFileName(music.getFileName()));
        } catch (Exception e) {
            e.printStackTrace();
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
            changeButtonToImage(playButton, "play.jpg");
        });
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
