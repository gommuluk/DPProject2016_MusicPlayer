package Model;

import FileIO.FileIO;
import FileIO.FilePathParser;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import java.io.File;
import java.util.ArrayList;


public class WAVMusic extends Music {


    private String[] musicInfo;

    public WAVMusic(String musicFileName, String musicFileAddress) {

        //FieldKey.TITLE   FieldKey.ARTIST   FieldKey.ALBUM   FieldKey.YEAR   FieldKey.GENRE
        String fileName = musicFileAddress + File.separatorChar + musicFileName + ".wav";
        this.filePath = fileName;
        isFavorite = false;    // default setting - favorite is false
        this.fileName = musicFileName;
        this.fileAddress = musicFileAddress;
        this.lyricsFileName = "null";
        this.lyricsFileAddress = "null";
    }

    public WAVMusic(File file) {
        boolean check = true;
        String path = file.getAbsolutePath();
        this.fileName = FilePathParser.getFileName(path);
        this.fileAddress = FilePathParser.getPath(path);
        this.lyricsFileName = "null";
        this.lyricsFileAddress = "null";
    }

    @Override
    public Music clone() {
        return new WAVMusic(fileName, fileAddress);
    }

    @Override
    public String getFileAddress() {
        return fileAddress
            + File.separatorChar
            + fileName
            + ".wav";
    }
}
