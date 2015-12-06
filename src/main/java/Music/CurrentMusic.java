package Music;

import FileIO.FilePathParser;
import GUI.PlayerTab;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

import java.io.File;
import java.util.Optional;

public class CurrentMusic {
    private static CurrentMusic uniqueInstance;
    private Optional<MediaPlayer> mediaPlayerOptional;
    private PlayerTab playerTab;
    private Music thisMusic;
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

    public boolean isPlayable() {
        if (mediaPlayerOptional.isPresent()) {
            Status status = mediaPlayerOptional.get().getStatus();
            return (status == Status.READY
                    || status == Status.PAUSED
                    || status == Status.STOPPED);
        }

        return false;
    }

    public void play() {
        mediaPlayerOptional.ifPresent(mediaPlayer -> {
            if (isPlayable()) {
                mediaPlayer.play();
                MusicListManager.getInstance().getRecentPlayList().add(this.toMusic());
            }
        });
        mediaPlayerOptional.get().setOnEndOfMedia(new Runnable() {
            public void run() {
                Media media = mediaPlayerOptional.get().getMedia();
                int i = MusicListManager.getInstance().findIndex(media.getSource());
                i++;
                playerTab.doStop();
                setMedia(MusicListManager.getInstance().at(i).getFilename());
                playerTab.doPlay();
                playerTab.updateUI();
            }
        });

    }

    public void pause() {
        mediaPlayerOptional.ifPresent(MediaPlayer::pause);
    }

    public void stop() {
        mediaPlayerOptional.ifPresent(MediaPlayer::stop);
    }

    public boolean setMedia(File file) {
        if (file.isFile()) {
            mediaPlayerOptional = Optional.of(
                    new MediaPlayer(
                            new Media(file.toURI().toString())));
            return true;
        }
        return false;
    }

    public boolean setVolume(float volume) {
        if (volume >= 0.0 && volume <= 1.0) {
            mediaPlayerOptional.ifPresent(mediaPlayer -> setVolume(volume));
            return true;
        }
        return false;

    }

    public boolean setMedia(String filePath) {
        return setMedia(new File(filePath));
    }

    public Optional<Duration> getCurrentTime() {
        return mediaPlayerOptional.map(MediaPlayer::getCurrentTime);
    }

    private void seek(int second) {
        if (mediaPlayerOptional.isPresent()) {
            MediaPlayer mediaPlayer = mediaPlayerOptional.get();
            Duration duration = mediaPlayer.getCurrentTime();
            Duration delta = new Duration(second * 1000);
            mediaPlayer.seek(duration.add(delta));
        }
    }

    public void seekNext() {
        seek(10);
    }

    public void seekPrevious() {
        seek(-10);
    }

    public Music toMusic() {
    	String filePath = mediaPlayerOptional.get().getMedia().getSource();
        filePath = FilePathParser.parseSeparator(filePath);
        thisMusic = MusicListManager.getInstance().find(filePath);
        return thisMusic;
    }

    public Status getStatus() {
        return mediaPlayerOptional.get().getStatus();
    }

    public void setPlayerTab(PlayerTab playerTab) {
        this.playerTab = playerTab;
    }
}
