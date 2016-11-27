package Model;

import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Created by Elliad on 2016-11-25.
 */
public class WAVPlayer implements PlayerBehavior {
    @Override
    public boolean play() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(CurrentMusic.getInstance().getFileName()).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            return true;
        } catch(Exception ex) {
            return false;
        }
    }

    @Override
    public void pause() {
        /*mediaPlayerOptional.ifPresent(MediaPlayer::pause);*/
    }

    @Override
    public void stop() {
        /*mediaPlayerOptional.ifPresent(MediaPlayer::stop);*/
    }
}
