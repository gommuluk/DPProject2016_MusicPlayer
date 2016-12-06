package Model;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static javafx.scene.media.MediaPlayer.Status.PAUSED;
import static javafx.scene.media.MediaPlayer.Status.PLAYING;
import static javafx.scene.media.MediaPlayer.Status.STOPPED;
import static junit.framework.TestCase.assertEquals;
import static org.easymock.EasyMock.createMock;

public class CurrentMusicTest {
    @Test
    public void TestSingleton() {
        assertEquals(CurrentMusicPlayer.getInstance().toString(), CurrentMusicPlayer.getInstance().toString());
    }

    @Test
    public void TestMediaSettingFail() throws InvalidDataException, IOException, UnsupportedTagException {
        CurrentMusicPlayer currentMusicPlayer = CurrentMusicPlayer.getInstance();
        boolean result;
        try {
            result = currentMusicPlayer.setCurrentMusic(new MP3Music(new File("non_exist_file.file")));
        } catch (IOException e) {
            result = false;
        }
        assertEquals(result, false);
    }

    @Test
    public void TestMediaSettingSuccess() throws InvalidDataException, UnsupportedTagException, URISyntaxException {
        boolean result;
        new JFXPanel();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                boolean result;
                CurrentMusicPlayer currentMusicPlayer = CurrentMusicPlayer.getInstance();
                try {
                    result = currentMusicPlayer.setCurrentMusic(new MP3Music(new File(getClass().getResource("Alan Walker - Fade.mp3").toURI())));
                } catch (UnsupportedTagException | InvalidDataException | URISyntaxException | IOException e) {
                    result = false;
                }
                assertEquals(result, true);
            }
        });
    }

    @Test
    public void TestPlay() throws URISyntaxException, InvalidDataException, UnsupportedTagException {

        boolean result;
        new JFXPanel();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    boolean result;
                    CurrentMusicPlayer currentMusicPlayer = CurrentMusicPlayer.getInstance();
                    try {
                        result = currentMusicPlayer.setCurrentMusic(new MP3Music(new File(getClass().getResource("Alan Walker - Fade.mp3").toURI())));
                    } catch (UnsupportedTagException | InvalidDataException | URISyntaxException | IOException e) {
                        result = false;
                    }
                    assertEquals(result, true);
                    currentMusicPlayer.play();
                    assertEquals(currentMusicPlayer.getStatus(), PLAYING);
                }
            });
    }

    @Test
    public void TestPause() {
        boolean result;
        new JFXPanel();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                boolean result;
                CurrentMusicPlayer currentMusicPlayer = CurrentMusicPlayer.getInstance();
                try {
                    result = currentMusicPlayer.setCurrentMusic(new MP3Music(new File(getClass().getResource("Alan Walker - Fade.mp3").toURI())));
                } catch (UnsupportedTagException | InvalidDataException | URISyntaxException | IOException e) {
                    result = false;
                }
                assertEquals(result, true);
                currentMusicPlayer.play();
                assertEquals(currentMusicPlayer.getStatus(), PLAYING);
                currentMusicPlayer.pause();
                assertEquals(currentMusicPlayer.getStatus(), PAUSED);
            }
        });
    }

    @Test
    public void TestStop() {
        boolean result;
        new JFXPanel();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                boolean result;
                CurrentMusicPlayer currentMusicPlayer = CurrentMusicPlayer.getInstance();
                try {
                    result = currentMusicPlayer.setCurrentMusic(new MP3Music(new File(getClass().getResource("Alan Walker - Fade.mp3").toURI())));
                } catch (UnsupportedTagException | InvalidDataException | URISyntaxException | IOException e) {
                    result = false;
                }
                assertEquals(result, true);
                currentMusicPlayer.play();
                assertEquals(currentMusicPlayer.getStatus(), PLAYING);
                currentMusicPlayer.stop();
                assertEquals(currentMusicPlayer.getStatus(), STOPPED);
            }
        });
    }

}

