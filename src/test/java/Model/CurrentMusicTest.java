package Model;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

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
    public void TestMediaSettingSuccess() {
        //createMock(MP3Music.class);
    }
}
