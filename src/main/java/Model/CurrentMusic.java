package Model;

import FileIO.FilePathParser;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

public class CurrentMusic {
    public static int playMode = 0;
    private static volatile CurrentMusic uniqueInstance;
    Optional<MediaPlayer> mediaPlayerOptional;
    private Music music;

    private CurrentMusic() {
        this.mediaPlayerOptional = Optional.empty();

    }


    public static CurrentMusic getInstance() {
        if (uniqueInstance == null) {
            synchronized (CurrentMusic.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new CurrentMusic();
                }
            }
        }
        return uniqueInstance;
    }

    public boolean setVolume(float volume) {
        if (volume >= 0.0 && volume <= 1.0) {
            mediaPlayerOptional.ifPresent(mediaPlayer -> {
                mediaPlayer.setVolume(volume);
            });
            return true;
        }
        return false;
    }

    public boolean setMedia(String filePath) {
        if (mediaPlayerOptional.isPresent()) {
            System.out.println(mediaPlayerOptional.get().getStatus());
            if (mediaPlayerOptional.get().getStatus() == MediaPlayer.Status.PLAYING)
                mediaPlayerOptional.get().stop();
        }

        File file = new File(filePath);
        if (file.isFile()) {
            mediaPlayerOptional = Optional.of(
                new MediaPlayer(
                    new Media(file.toURI().toString())));
            filePath = mediaPlayerOptional.get().getMedia().getSource();
            filePath = FilePathParser.parseSeparator(filePath);
            music = MusicListManager.getInstance().find(filePath);
            return true;
        }
        return false;
    }

    public Optional<Duration> getCurrentTime() {
        return mediaPlayerOptional.map(MediaPlayer::getCurrentTime);
    }

    public Optional<Duration> getTotalTime() {
        return mediaPlayerOptional.map(MediaPlayer::getTotalDuration);
    }

    public void seekNext() {
        seek(10);
    }

    public void seekPrevious() {
        seek(-10);
    }

    public void seek(float percent) {
        if (percent >= 0 && percent <= 1 && mediaPlayerOptional.isPresent()) {
            MediaPlayer mediaPlayer = mediaPlayerOptional.get();
            mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(percent));
        }
    }

    public Music getMusic() {
        return music;
    }

    public <T> void addChangeTimeEvent(T t, Consumer<T> func) {
        mediaPlayerOptional.ifPresent(mediaPlayer -> mediaPlayer.currentTimeProperty().addListener(observable -> {
            func.accept(t);
        }));
    }

    /*private*/
    private boolean isPlayable() {
        if (mediaPlayerOptional.isPresent()) {
            MediaPlayer.Status status = mediaPlayerOptional.get().getStatus();
            return (status == Status.READY
                || status == Status.PAUSED
                || status == Status.STOPPED
                || status == Status.UNKNOWN
            );
        }

        return false;
    }

    private void seek(int second) {
        if (mediaPlayerOptional.isPresent()) {
            MediaPlayer mediaPlayer = mediaPlayerOptional.get();
            Duration duration = mediaPlayer.getCurrentTime();
            Duration delta = new Duration(second * 1000);
            mediaPlayer.seek(duration.add(delta));
        }
    }

    public boolean play() { // 버튼을 누르면 이것이 실행됨.
        if (isPlayable() && music != null) {
            music.performPlay();
            return true;
        }
        return false;
    }

    public void pause() {
        if (music != null) music.performPause();
    }

    public void stop() {
        if (music != null) music.performStop();
    }

    public String getFileName() {
        return music.toString();
    }

}
