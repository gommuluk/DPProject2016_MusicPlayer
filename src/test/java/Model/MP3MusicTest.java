package Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;

import java.io.File;

public class MP3MusicTest {
    File musicFile;
    MP3Music mp3Music;

    @Rule
    public ExternalResource resource = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            musicFile = new File(getClass().getResource("Alan Walker - Fade.mp3").toURI());
        }

        @Override
        protected void after() {
            musicFile = null;
        }
    };

    @Before
    public void setUp() throws Exception {
        mp3Music = new MP3Music(musicFile);
    }

    @After
    public void tearDown() throws Exception {
        mp3Music = null;
    }

    @Test
    public void getFileAddressTest() throws Exception {
        assertEquals(musicFile.getAbsolutePath(), mp3Music.getFileAddress());
    }

    @Test
    public void getFileInformationDataTest() throws Exception {

    }

    @Test
    public void getFileNameTest() throws Exception {
        assertTrue(musicFile.getName().startsWith(mp3Music.getFileName()));
    }

    @Test
    public void isFavoriteTest() throws Exception {
        assertEquals(false, mp3Music.isFavorite());
        mp3Music.setFavorite(true);
        assertEquals(true, mp3Music.isFavorite());
    }
}
