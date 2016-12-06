package Model;

import GUI.MusicListController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertEquals;

public class MusicListManagerTests {
    private MusicListManager musicListManager;
    private Music music;
    @Before
    public void setUp() throws Exception {
        musicListManager = MusicListManager.getInstance();
        music = new MP3Music(new File(getClass().getResource("Alan Walker - Fade.mp3").toURI()));
    }

    @Test
    public void TestSingleton() {
        assertEquals(MusicListManager.getInstance().toString(), musicListManager.toString());
    }

    @Test
    public void TestAddToMusicList() {
        musicListManager.addToFavoriteMusicList(music);
        musicListManager.addToRecentPlayList(music);
        assertEquals(musicListManager.getFavoritePlaylist().size(), 1);
        assertEquals(musicListManager.getRecentPlaylist().size(), 1);
        musicListManager.addMusic(music.getFileAddress());
        assertEquals(musicListManager.getPlaylist().size(), 1);
    }

    @Test
    public void TestDeleteToMusicList() {
        musicListManager.deleteToFavoriteMusicList(music);
        assertEquals(musicListManager.getFavoritePlaylist().size(), 0);
    }

    @After
    public void tearDown() throws Exception {

    }
}
