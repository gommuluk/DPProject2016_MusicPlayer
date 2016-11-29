package Model;

import java.io.File;
import java.util.ArrayList;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.*;


import FileIO.FileIO;
import FileIO.FilePathParser;




public class WAVMusic extends Music{

	private AudioFile wavFile;
	private Tag tag;

	private String[] musicInfo;

	public WAVMusic(String musicFileName, String musicFileAddress, String[] infoInfo){

		//FieldKey.TITLE   FieldKey.ARTIST   FieldKey.ALBUM   FieldKey.YEAR   FieldKey.GENRE
		String fileName = musicFileAddress + File.separatorChar + musicFileName + ".wav";
		  if (infoInfo != null) {
			  try{
			  tag.setField(FieldKey.TITLE, infoInfo[1]);
			  } catch(FieldDataInvalidException e) {
				  e.printStackTrace();
			  }
			  this.playCnt = Integer.parseInt(infoInfo[0]);
			  this.fileName = infoInfo[1];
			  this.fileAddress = infoInfo[2];
			  this.lyricsFileName = infoInfo[3];
			  this.lyricsFileAddress = infoInfo[4];
		  }
	    wavFile = new AudioFile(fileName, null, tag);
	    isFavorite = false;	// default setting - favorite is false
	    this.playerBehavior = new WAVPlayer();

	}

	public WAVMusic(File file){
		wavFile = new AudioFile(file, null, null);

		boolean check = true;
    	String path = file.getAbsolutePath();
    	this.fileName = FilePathParser.getFileName(path);
    	this.fileAddress = FilePathParser.getPath(path);
    	ArrayList<String> informationString = FileIO.readTextFile(FILE_INFO_ADDRESS, FILE_INFO_NAME, ".txt");

        assert informationString != null;
        for (String iter : informationString) {
            musicInfo = iter.split("/");
            if (musicInfo[1].equals(fileName)) {
            	break;
            }
        }
        this.playCnt = 0;
        this.lyricsFileName = "null";
        this.lyricsFileAddress = "null";

	}

	@Override
    public Music clone() {
			return /*new WAVMusic(fileName, fileAddress, musicInfo)*/ null;
    }

	@Override
	public String getFileAddress(){
		return null;
	}
}
