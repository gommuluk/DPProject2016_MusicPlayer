package Model;

import java.io.File;
import java.nio.file.Path;

public abstract class Music implements Cloneable {
    protected String filePath;
    protected String fileName, fileAddress;
    protected String artist, composer, name, album;
    protected byte[] image;
    protected boolean isFavorite;
    protected String lyricsFileAddress, lyricsFileName;    // save lyric file's address and name

    public abstract Music clone();

    public String getFileInformationData() {
        return this.filePath + ":" + this.lyricsFileName + ":" + this.lyricsFileAddress + "\n";
    }

    public byte[] getAlbumArt() {    //return album art
        return this.image;
    }

    public String getFileName() {     //return file name
        return this.fileName;
    }

    public abstract String getFileAddress();

    public String toString() {
        return getFileName();
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean flag) {
        this.isFavorite = flag;
    }
}
