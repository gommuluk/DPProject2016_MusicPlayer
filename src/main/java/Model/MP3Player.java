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
public class MP3Player implements PlayerBehavior {
    private Optional<MediaPlayer> mediaPlayerOptional;

    public MP3Player() {
        mediaPlayerOptional = CurrentMusic.getInstance().mediaPlayerOptional;
    }

    /*private*/
    private boolean isPlayable() {
        if (mediaPlayerOptional.isPresent()) { //TODO
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
        mediaPlayerOptional = CurrentMusic.getInstance().mediaPlayerOptional;
    }

    /*PlayerBehavior*/
    @Override
    public boolean play() { //TODO
        // 플레이 버튼을 눌러서 PAUSE로 들어가는 경우, PAUSE에서 다시 플레이를 눌러서 재생하는 경우
        // 리스트를 더블 클릭해서 재생을 시작하는 경우,
        set();
        if(isPlayable()) {
            mediaPlayerOptional.ifPresent(MediaPlayer::play);
            return true;
        }
        return false;
    }
    @Override
    public void pause() {
        set();
        if(mediaPlayerOptional.isPresent())
        mediaPlayerOptional.ifPresent(MediaPlayer::pause);
    }
    @Override
    public void stop() {
        set();
        mediaPlayerOptional.ifPresent(MediaPlayer::dispose);
    }

}
