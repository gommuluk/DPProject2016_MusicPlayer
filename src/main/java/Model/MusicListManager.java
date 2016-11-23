package Model;

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
    private final ArrayList<Music> musicList = new ArrayList<>();	// musiclist that has all music object
    private final ArrayList<Music> recentPlayList = new ArrayList<>();	// save recent played music object
    private final ArrayList<Music> favoriteMusicList = new ArrayList<>();	// save music object that is setted to favorite

    public  int   currentList = 0;

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
                musicList.add(new MP3Music(fileName, fileAddress, getMusicInfoFile(fileName, fileAddress)));
            } catch (Exception e) {
                new ErrorDetector();
            }
        }
        ArrayList<String> infoFileInfo = musicList.stream().map(iter -> iter.getFileInformationData()).collect(Collectors.toCollection(ArrayList::new));

        FileIO.writeTextFile(FILE_INFO_ADDRESS, FILE_INFO_NAME, infoFileInfo);

    }

    public Music find(String filePath) {	// find music file
        try {
            Music temp = getCurrentList().get(findIndex(filePath));
            if (temp != null) return temp;
            else return null;
        } catch (Exception e) {
            new ErrorDetector();
            return null;
        }
    }

    public int findIndex(String filePath){	// find music file's index
        for(Music iter : getCurrentList()){
            if(iter.getFileName().replaceAll("[+]", " ").equals(filePath)){
                return getCurrentList().indexOf(iter);
            }
        }
        return -1;
    }

    public Music at(int i) {	// return music object
       return getCurrentList().get(i);
    }

    public void addToRecentPlayList(Music music) {	// add to recent play list
        int temp = currentList;
        currentList = 2;
        if (isExist(music)) recentPlayList.remove(find(music.getFileName())); //TODO
        currentList = temp;
        recentPlayList.add(music);
    }

    public void addToFavoriteMusicList(Music music) {	// add to favorite MP3Music list
        int temp = currentList;
        currentList = 1;
        if(!isExist(music)) {
            currentList = temp;
            favoriteMusicList.add(music.clone());
        }
        else {
            currentList = temp;
        }
    }

    public boolean deleteToFavoriteMusicList(Music music) {	// delete MP3Music object in favoite MP3Music list
        if(isExist(music)) {
            for(int i = 0; i < getCurrentList().size() ; i++) {
                if(getCurrentList().get(i).getFileName().equals(music.getFileName())) {
                    getCurrentList().remove(i);
                    break;
                }
            }
            return true;
        }
        else return false;

    }



    public ArrayList<Music> getMusicList() {	// return music list
        return musicList;
    }
    public ArrayList<Music> getCurrentList() {	// return using list
        switch(currentList) {
            case 0 :
                return musicList;
            case 1 :
                return favoriteMusicList;
            case 2 :
                return recentPlayList;
        }
        return null;
    }
    private boolean isExist(Music music) {	// check MP3Music object exist
        return MusicListManager.getInstance().findIndex(music.getFileName()) != -1;
    }
}
