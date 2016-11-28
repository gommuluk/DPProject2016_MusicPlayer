package Model;

import FileIO.FilePathParser;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Optional;

/**
 * Created by Elliad on 2016-11-25.
 */
public class MP3Player implements PlayerBehavior {

    private Optional<MediaPlayer> mediaPlayerOptional;


    /*private*/
    private boolean isPlayable() {
        if (mediaPlayerOptional.isPresent()) {
            MediaPlayer.Status status = mediaPlayerOptional.get().getStatus();
            return (status == MediaPlayer.Status.READY
                || status == MediaPlayer.Status.PAUSED
                || status == MediaPlayer.Status.STOPPED
                || status == MediaPlayer.Status.UNKNOWN
            );
        }

        return false;
    }

    @Override
    public boolean play() {
        set();
        if(isPlayable()) {
            mediaPlayerOptional.ifPresent(MediaPlayer::play);
            MusicListManager.getInstance().addToRecentPlayList(CurrentMusic.getInstance().getMusic());
            return true;
        }
        return false;
    }

    @Override
    public void pause() {
        if(mediaPlayerOptional.isPresent())
        mediaPlayerOptional.ifPresent(MediaPlayer::pause);
    }

    @Override
    public void stop() {
        if(mediaPlayerOptional.isPresent()) mediaPlayerOptional.ifPresent(MediaPlayer::stop);
    }

    private void set() {
        File file = new File(CurrentMusic.getInstance().getMusic().getFileAddress());
        if (file.isFile()) {
            mediaPlayerOptional = Optional.of(
                new MediaPlayer(
                    new Media(file.toURI().toString())));
        }

    }
}
