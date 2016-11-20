package Music;

import FileIO.FileIO;

import GUI.ErrorDetector;
import GUI.MusicList;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MusicListManager {	// manage music objects and it made by singleton skill
    private static MusicListManager uniqueInstance;		// unique object to make sigleton

    private final String FILE_INFO_ADDRESS = System.getProperty("user.home") + "/Desktop/"+"music-info";	// file address
    private final String FILE_INFO_NAME = "MusicInfoFile";	// music information name
    private final ArrayList<MP3Music> MP3MusicList = new ArrayList<>();	// musiclist that has all music object
    private final ArrayList<MP3Music> recentPlayList = new ArrayList<>();	// save recent played music object
    private final ArrayList<MP3Music> favoriteMP3MusicList = new ArrayList<>();	// save music object that is setted to favorite

    public static MusicListManager getInstance() {	// return unique object
        if (uniqueInstance == null) {
            synchronized (MusicListManager.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new MusicListManager();
                }
            }
        }
        return uniqueInstance;
    }

    private String[] getMusicInfoFile(final String fileName, final String fileAddress) {	// read musicinfo file's information
        ArrayList<String> informationString = FileIO.readTextFile(FILE_INFO_ADDRESS, FILE_INFO_NAME, ".txt");
        String[] information = new String[5];

        assert informationString != null;
        for (String iter : informationString) {
            information = iter.split("/");
            if (information[1].equals(fileName)) {
                return information;
            }
        }
        information[0] = "0";
        information[1] = fileName;
        information[2] = fileAddress;
        information[3] = "null";
        information[4] = "null";
        return information;
    }

    public void addMusic(String filepath) {	// add music file's with path, it get all music file in path and under path
        File file = new File(filepath);

        if (file.isFile()) {
            String fileName = filepath.substring(filepath.lastIndexOf(File.separatorChar) + 1,
                    filepath.lastIndexOf("."));
            String fileAddress = filepath.substring(0, filepath.lastIndexOf(File.separatorChar));
            try {
                MP3MusicList.add(new MP3Music(fileName, fileAddress, getMusicInfoFile(fileName, fileAddress)));
            } catch (Exception e) {
                new ErrorDetector();
            }
        }
        ArrayList<String> infoFileInfo = MP3MusicList.stream().map(iter -> iter.getSaveInfo()).collect(Collectors.toCollection(ArrayList::new));

        FileIO.writeTextFile(FILE_INFO_ADDRESS, FILE_INFO_NAME, infoFileInfo);

    }

    public MP3Music find(String filePath) {	// find music file
        try {
            MP3Music temp = currentList().get(findIndex(filePath));
            if (temp != null) return temp;
            else return null;
        } catch (Exception e) {
            new ErrorDetector();
            return null;
        }
    }

    public int findIndex(String filePath){	// find music file's index
        for(MP3Music iter : currentList()){
            if(iter.getFilename().replaceAll("[+]", " ").equals(filePath)){
                return currentList().indexOf(iter);
            }
        }
        return -1;
    }

    public MP3Music at(int i) {	// return music object
       return currentList().get(i);
    }

    public void addToRecentPlayList(MP3Music MP3Music) {	// add to recent play list
        int temp = MusicList.listNum;
        MusicList.listNum = 2;
        if (isExist(MP3Music)) recentPlayList.remove(find(MP3Music.getFilename()));
        MusicList.listNum = temp;
        recentPlayList.add(MP3Music);
    }

    public void addToFavoriteMusicList(MP3Music MP3Music) {	// add to favorite MP3Music list
        int temp = MusicList.listNum;
        MusicList.listNum = 1;
        if(!isExist(MP3Music)) {
            MusicList.listNum = temp;
            favoriteMP3MusicList.add(MP3Music.clone());
        }
        else {
            MusicList.listNum = temp;
        }
    }

    public boolean deleteToFavoriteMusicList(MP3Music MP3Music) {	// delete MP3Music object in favoite MP3Music list
        if(isExist(MP3Music)) {
            for(int i = 0; i < currentList().size() ; i++) {
                if(currentList().get(i).getFilename().equals(MP3Music.getFilename())) {
                    currentList().remove(i);
                    break;
                }
            }
            return true;
        }
        else return false;

    }



    public ArrayList<MP3Music> getMP3MusicList() {	// return music list
        return MP3MusicList;
    }
    public ArrayList<MP3Music> currentList() {	// return using list
        switch(MusicList.listNum) {
            case 0 :
                return MP3MusicList;
            case 1 :
                return favoriteMP3MusicList;
            case 2 :
                return recentPlayList;
        }
        return null;
    }
    private boolean isExist(MP3Music MP3Music) {	// check MP3Music object exist
        return MusicListManager.getInstance().findIndex(MP3Music.getFilename()) != -1;
    }
}
