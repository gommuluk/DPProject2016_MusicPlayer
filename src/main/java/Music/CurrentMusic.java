package Music;

import FileIO.FilePathParser;
import GUI.PlayerTab;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class CurrentMusic {
    public static int playMode = 0;
    private static CurrentMusic uniqueInstance;
    private Optional<MediaPlayer> mediaPlayerOptional;
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

    public boolean setVolume(float volume) {
        if (volume >= 0.0 && volume <= 1.0) {
            mediaPlayerOptional.ifPresent(mediaPlayer -> mediaPlayer.setVolume(volume));
            return true;
        }
        return false;
    } // 빼야 함.

    public boolean setMedia(String filePath) {
        File file = new File(filePath);
        if (file.isFile()) {
            mediaPlayerOptional = Optional.of(
                    new MediaPlayer(
                            new Media(file.toURI().toString())));
            return true;
        }
        return false;
    } // 빼야 함.

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

    public Music toMusic() {
        try {
            String filePath = mediaPlayerOptional.get().getMedia().getSource();
            filePath = FilePathParser.parseSeparator(filePath);
            thisMusic = MusicListManager.getInstance().find(filePath);
            return thisMusic;
        } catch (Exception e1) {
            return null;
        }
    }

    public Status getStatus() {
        return mediaPlayerOptional.get().getStatus();
    }


    public <T> void addChangeTimeEvent(T t, Consumer<T> func) {
        mediaPlayerOptional.ifPresent(mediaPlayer -> mediaPlayer.currentTimeProperty().addListener(observable -> {
            func.accept(t);
        }));
    }




    /*private*/
    private boolean isPlayable() {
        if (mediaPlayerOptional.isPresent()) {
            Status status = mediaPlayerOptional.get().getStatus();
            return (status == Status.READY
                    || status == Status.PAUSED
                    || status == Status.STOPPED
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
/*
    public boolean play() {
        if(isPlayable()) {
            mediaPlayerOptional.ifPresent(mediaPlayer -> {
                mediaPlayer.play();
                MusicListManager.getInstance().addToRecentPlayList(this.toMusic());
            });
            mediaPlayerOptional.get().setOnEndOfMedia(() -> {
                Media media = mediaPlayerOptional.get().getMedia();
                int i = MusicListManager.getInstance().findIndex(FilePathParser.parseSeparator(media.getSource()));

                switch (playMode) {
                    case 0:
                        if (i == MusicListManager.getInstance().currentList().size() - 1) i = 0;
                        else i++;
                        break;
                    case 1:
                        if (!(i > MusicListManager.getInstance().currentList().size() - 1)) i++;
                        break;
                    case 2:
                        break;
                }
                playerTab.doStop();
                setMedia(MusicListManager.getInstance().at(i).getFilename());
                playerTab.doPlay();
            });
            return true;
        }
        return false;
    }

    public void pause() {
        mediaPlayerOptional.ifPresent(MediaPlayer::pause);
    }

    public void stop() {
        mediaPlayerOptional.ifPresent(MediaPlayer::stop);
    }
*/

}