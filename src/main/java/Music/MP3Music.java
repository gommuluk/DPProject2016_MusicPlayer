package Music;

import FileIO.FileIO;
import FileIO.FilePathParser;

import com.mpatric.mp3agic.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MP3Music extends Music {	// extends MP3File - it is in Mp3agic library

    Mp3File mp3File;
    private ID3v1 id3v1Tag;	// save id3v1tag
    private ID3v2 id3v2Tag;	// save id3v2tag
    private boolean isV1Tag = false, isV2Tag = false;	// check tag is existing

    private String[] musicInfo;	// use saving music information

    //make an object with file's name and address. infoInfo has musicfile's address when it read musicinfofile.txt's information
    public MP3Music(String musicFileName, String musicFileAddress, String[] infoInfo) throws UnsupportedTagException, InvalidDataException, IOException {
        mp3File = new Mp3File(musicFileAddress
                + File.separatorChar
                + musicFileName
                + ".mp3");	// make an object
        isFavorite = false;	// default setting - favorite is false
        if (infoInfo != null) {	// when information exist
            if (infoInfo[0] == null) infoInfo[0] = "0";
            this.playCnt = Integer.parseInt(infoInfo[0]);
            this.fileName = infoInfo[1];
            this.fileAddress = infoInfo[2];
            super.lyricsFileName = infoInfo[3];
            this.lyricsFileAddress = infoInfo[4];
        }
        if (mp3File.hasId3v1Tag()) {	// check id3v1tag exist
            isV1Tag = true;
            id3v1Tag = mp3File.getId3v1Tag();
        }
        if (mp3File.hasId3v2Tag()) {	// check id3v1tag exist
            isV2Tag = true;
            id3v2Tag = mp3File.getId3v2Tag();
        }
        this.fileAddress = musicFileAddress;
        this.fileName = musicFileName;
        this.musicInfo = infoInfo;
        setMusicInformation();

    }

    //make an object with file
    public MP3Music(File file) throws UnsupportedTagException, InvalidDataException, IOException{
    	mp3File = new Mp3File(file.getAbsolutePath());
    	boolean check = true;
    	String path = file.getAbsolutePath();
    	this.fileName = FilePathParser.getFileName(path);
    	this.fileAddress = FilePathParser.getPath(path);
    	ArrayList<String> informationString = FileIO.readTextFile(FILE_INFO_ADDRESS, FILE_INFO_NAME, ".txt");

    	//save information when info exist
        assert informationString != null;
        for (String iter : informationString) {
            musicInfo = iter.split("/");
            if (musicInfo[1].equals(fileName)) {
            	 this.playCnt = Integer.parseInt(musicInfo[0]);
                 this.fileName = musicInfo[1];
                 this.fileAddress = musicInfo[2];
                 this.lyricsFileName = musicInfo[3];
                 this.lyricsFileAddress = musicInfo[4];
            	break;
            }
        }
        this.playCnt = 0;
        this.lyricsFileName = "null";
        this.lyricsFileAddress = "null";


    }

    //set music information - 2 case
    private void setMusicInformation() {
        if (isV1Tag) {
            this.artist = id3v1Tag.getArtist();
            this.composer = null;
            this.name = id3v1Tag.getTitle();
            this.album = id3v1Tag.getAlbum();
        }
        if (isV2Tag) {
            this.artist = id3v2Tag.getArtist();
            this.composer = id3v2Tag.getComposer();
            this.name = id3v2Tag.getTitle();
            this.album = id3v2Tag.getAlbum();
            this.image = id3v2Tag.getAlbumImage();
        }
    }

    //return play count, file name, file address, lyricfile name, lyricfile address to save music information

    public MP3Music clone() {	// make clone object
        try {
            return new MP3Music(fileName, fileAddress, musicInfo);
        } catch (UnsupportedTagException | InvalidDataException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
