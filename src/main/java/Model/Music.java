package Model;

import java.io.File;

/**
 * Created by Elliad on 2016-11-22.
 */
public abstract class Music implements Cloneable{


    protected final String FILE_INFO_NAME = "MusicInfoFile";
    protected final String FILE_INFO_ADDRESS = System.getProperty("user.home")
        + File.separatorChar
        + "music-info"
        + File.separatorChar;	// music info file's address
    protected String fileName, fileAddress;
    protected String artist, composer, name, album;
    protected byte[] image;
    protected boolean isFavorite;
    protected int playCnt;
    protected Lyric lyrics;	// lyric object
    protected String lyricsFileAddress, lyricsFileName;	// save lyric file's address and name

    public PlayerBehavior playerBehavior;

    public abstract Music clone();

    public String getFileInformationData() {
        return Integer.toString(this.playCnt) + "/" + this.fileName + "/" +
            this.fileAddress + "/" + this.lyricsFileName + "/" + this.lyricsFileAddress + "\n";
    }
    public void setLyrics(Lyric lyrics, String lyricsFileName, String lyricsFileAddress) { //set lyric
        this.lyricsFileName = lyricsFileName;
        this.lyricsFileAddress = lyricsFileAddress;
    }

    public byte[] getAlbumArt(){	//return album art
        return this.image;
    }
    public boolean getFavorite() { return this.isFavorite; }	// return favorite status

    public void setFavorite() { this.isFavorite = !this.isFavorite; }	// toggle favorite status
    public String getFileName() {     //return file name
        return this.fileName;
    }
    public String getFileAddress() { return this.fileAddress;}
    public boolean performPlay() { return playerBehavior.play();}
    public void performPause() { playerBehavior.pause();}
    public void performStop() { playerBehavior.stop();}
}
