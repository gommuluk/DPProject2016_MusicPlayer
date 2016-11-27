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
            mediaPlayerOptional.get().setOnEndOfMedia(() -> {
                Media media = mediaPlayerOptional.get().getMedia();
                int i = MusicListManager.getInstance().findIndex(FilePathParser.parseSeparator(media.getSource()));

                switch (CurrentMusic.playMode) {
                    case 0:
                        if (i == MusicListManager.getInstance().getCurrentList().size() - 1) i = 0;
                        else i++;
                        break;
                    case 1:
                        if (!(i > MusicListManager.getInstance().getCurrentList().size() - 1)) i++;
                        break;
                    case 2:
                        break;
                }
                this.mediaPlayerOptional = Optional.of(
                    new MediaPlayer(
                        new Media(MusicListManager.getInstance().at(i).toString())));
            });
            return true;
        }
        return false;
    }

    @Override
    public void pause() {
        mediaPlayerOptional.ifPresent(MediaPlayer::pause);
    }

    @Override
    public void stop() {
        mediaPlayerOptional.ifPresent(MediaPlayer::stop);
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
