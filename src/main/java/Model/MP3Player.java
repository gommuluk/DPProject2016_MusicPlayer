package Model;

import FileIO.FilePathParser;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Iterator;
import java.util.Optional;

/**
 * Created by Elliad on 2016-11-25.
 */
public class MP3Player implements PlayerBehavior, Iterator<Music> {
    private Optional<MediaPlayer> mediaPlayerOptional;

    public MP3Player() {
        mediaPlayerOptional = CurrentMusic.getInstance().mediaPlayerOptional;
    }

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
    private void set() {
        //if music is playing, stop playing.
        if(mediaPlayerOptional.isPresent()) {
            if(mediaPlayerOptional.get().getStatus() == MediaPlayer.Status.PLAYING) stop();
        }

        File file = new File(CurrentMusic.getInstance().getMusic().getFileAddress());
        if (file.isFile()) {
            mediaPlayerOptional = Optional.of(new MediaPlayer(new Media(file.toURI().toString())));
            mediaPlayerOptional.get().setOnEndOfMedia(() -> CurrentMusic.getInstance().setMedia(next().getFileAddress()));

        }
    }

    /*PlayerBehavior*/
    @Override
    public boolean play() {
        set();
        if(isPlayable()) {
            mediaPlayerOptional.ifPresent(MediaPlayer::play);
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
        System.out.println(mediaPlayerOptional.get().getStatus());
        if(mediaPlayerOptional.isPresent()) mediaPlayerOptional.ifPresent(MediaPlayer::stop);
    }





    /*Iteration Behavior*/
    @Override
    public boolean hasNext() {
        Media media = mediaPlayerOptional.get().getMedia();
        int i = MusicListManager.getInstance().findIndex(FilePathParser.parseSeparator(media.getSource()));
        if(CurrentMusic.playMode == 1 && i > MusicListManager.getInstance().getCurrentList().size() - 1)
            return false;
        else return true;
    }
    @Override
    public Music next() {
        if(hasNext()) {
            Media media = mediaPlayerOptional.get().getMedia();
            int i = MusicListManager.getInstance().findIndex(FilePathParser.parseSeparator(media.getSource()));
            switch(CurrentMusic.playMode) {
                case 0 :
                    i++;
                    i %= MusicListManager.getInstance().getCurrentList().size() - 1;
                    break;
                case 1 :
                    i++;
                    break;
                default :
                    break;
            }
            return MusicListManager.getInstance().getCurrentList().at(i);
        }
        return null;
    }
}
