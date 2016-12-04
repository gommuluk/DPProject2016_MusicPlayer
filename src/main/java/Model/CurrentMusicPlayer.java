package Model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

import java.io.File;
import java.util.Observable;
import java.util.Optional;
import java.util.function.Consumer;

public class CurrentMusicPlayer extends Observable {
    private static volatile CurrentMusicPlayer uniqueInstance;
    public Optional<MediaPlayer> mediaPlayerOptional;
    private Music music;

    private CurrentMusicPlayer() {
        mediaPlayerOptional = Optional.empty();
    }

    public static CurrentMusicPlayer getInstance() {
        if (uniqueInstance == null) {
            synchronized (CurrentMusicPlayer.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new CurrentMusicPlayer();
                }
            }
        }
        return uniqueInstance;
    }

    public boolean setVolume(double volume) {
        if (volume >= 0.0 && volume <= 1.0) {
            mediaPlayerOptional.ifPresent(mediaPlayer -> mediaPlayer.setVolume(volume));
            return true;
        }
        return false;
    }

    public boolean setCurrentMusic(Music music) {
        File file = new File(music.getFileAddress());
        if (file.isFile()) {
            dispose();
            this.music = music;
            setMedia(new Media(file.toURI().toString()));
            setChanged();
            notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    public boolean setMedia(Media media) {
        mediaPlayerOptional = Optional.of(new MediaPlayer(media));
        mediaPlayerOptional.ifPresent(mediaPlayer -> mediaPlayer.setAutoPlay(true));
        mediaPlayerOptional.ifPresent(mediaPlayer -> mediaPlayer.setOnEndOfMedia(() -> CurrentMusicPlayer.getInstance().setCurrentMusic(MusicListManager.getInstance().getCurrentList().decoratedIterator().next())));
        mediaPlayerOptional.ifPresent(mediaPlayer -> {
            mediaPlayer.setOnReady(() -> notifyStatus());
        });
        mediaPlayerOptional.ifPresent(mediaPlayer -> {
            mediaPlayer.setOnPlaying(() -> notifyStatus());
        });
        mediaPlayerOptional.ifPresent(mediaPlayer -> {
            mediaPlayer.setOnPaused(() -> notifyStatus());
        });
        mediaPlayerOptional.ifPresent(mediaPlayer -> {
            mediaPlayer.setOnStopped(() -> notifyStatus());
        });
        return true;
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

    public void seek(double percent) {
        if (percent >= 0 && percent <= 1 && mediaPlayerOptional.isPresent()) {
            MediaPlayer mediaPlayer = mediaPlayerOptional.get();
            mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(percent));
        }
    }

    public Music getMusic() {
        return music;
    }

    public void play() { // 버튼을 누르면 이것이 실행됨.
        mediaPlayerOptional.ifPresent(MediaPlayer::play);
    }

    public void pause() {
        mediaPlayerOptional.ifPresent(MediaPlayer::pause);
    }

    public void stop() {
        mediaPlayerOptional.ifPresent(MediaPlayer::stop);
    }

    public String getFileName() {
        return music.toString();
    }

    public Status getStatus() {
        return this.mediaPlayerOptional.get().getStatus();
    }

    public <T> void addChangeTimeEvent(T t, Consumer<T> func) {
        mediaPlayerOptional.ifPresent(mediaPlayer -> mediaPlayer.currentTimeProperty().addListener(observable -> {
            func.accept(t);
        }));
    }

    private void seek(int second) {
        if (mediaPlayerOptional.isPresent()) {
            MediaPlayer mediaPlayer = mediaPlayerOptional.get();
            Duration duration = mediaPlayer.getCurrentTime();
            Duration delta = new Duration(second * 1000);
            mediaPlayer.seek(duration.add(delta));
        }
    }

    private void dispose() {
        mediaPlayerOptional.ifPresent(MediaPlayer::dispose);
    }

    private void notifyStatus() {
        if (mediaPlayerOptional.isPresent()) {
            setChanged();
            Status status = mediaPlayerOptional.get().getStatus();
            notifyObservers(status);
        }
    }
}
