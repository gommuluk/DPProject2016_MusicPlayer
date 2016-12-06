package Model;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static javafx.scene.media.MediaPlayer.Status.PLAYING;
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
        CurrentMusicPlayer currentMusicPlayer = CurrentMusicPlayer.getInstance();
        boolean result;
        try {
            result = currentMusicPlayer.setCurrentMusic(new MP3Music(new File(getClass().getResource("Alan Walker - Fade.mp3").toURI())));
        } catch (IOException e) {
            result = false;
        }
        assertEquals(result, true);
    }

    @Test
    public void TestPlay() throws URISyntaxException, InvalidDataException, UnsupportedTagException {
        CurrentMusicPlayer currentMusicPlayer = CurrentMusicPlayer.getInstance();
        boolean result;
        try {
            result = currentMusicPlayer.setCurrentMusic(new MP3Music(new File(getClass().getResource("Alan Walker - Fade.mp3").toURI())));
        } catch (IOException e) {
            result = false;
        }
        assertEquals(result, true);
        currentMusicPlayer.play();
        assertEquals(currentMusicPlayer.getStatus(), PLAYING);

    }

}
