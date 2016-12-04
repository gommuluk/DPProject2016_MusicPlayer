package Model;

import FileIO.FileIO;
import GUI.ErrorDetector;
import Model.Sort.AddressSort;
import Model.Sort.SortBehavior;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observer;
import java.util.stream.Collectors;

// manage music objects and it made by singleton skill
public class MusicListManager {
    private static volatile MusicListManager uniqueInstance;

    private final String FILE_INFO_ADDRESS = System.getProperty("user.home") + "/Desktop/" + "music-info";
    private final String FILE_INFO_NAME = "MusicInfoFile";


    // All musics list
    private final MusicList playlist = new MusicList();
    // Recently played musics list
    private final MusicList recentPlaylist = new MusicList();
    // Favorite musics list
    private final MusicList favoritePlaylist = new MusicList();

    private SortBehavior sortBehavior = new AddressSort();
    public int currentList = 0;





    public void addMusic(String filepath) {    // add music file's with path, it get all music file in path and under path
        File file = new File(filepath);

        if (file.isFile()) {
            String fileName = filepath.substring(filepath.lastIndexOf(File.separatorChar) + 1,
                filepath.lastIndexOf("."));
            String fileAddress = filepath.substring(0, filepath.lastIndexOf(File.separatorChar));
            try {
                playlist.addMusic(new MP3Music(fileName, fileAddress, getMusicInfoFile(fileName, fileAddress))); //TODO
            } catch (Exception e) {
                new ErrorDetector();
            }
        }
        ArrayList<String> infoFileInfo = playlist.getMusicList().stream().map(iter -> iter.getFileInformationData()).collect(Collectors.toCollection(ArrayList::new));

        FileIO.writeTextFile(FILE_INFO_ADDRESS, FILE_INFO_NAME, infoFileInfo);

    }

    public Music find(String filePath) {    // find music file
        try {
            Music temp = getCurrentList().at(findIndex(filePath));
            if (temp != null) return temp;
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
            new ErrorDetector();
            return null;
        }
    }

    public int findIndex(String filePath) {    // find music file's index
        for (Music iter : getCurrentList()) {
            if (iter.getFileAddress().replaceAll("[+]", " ").equals(filePath)) {
                return getCurrentList().find(iter);
            }
        }
        return -1;
    }

    public Music at(int i) {    // return music object
        return getCurrentList().at(i);
    }

    public void addToRecentPlayList(Music music) {    // add to recent play list
        int temp = currentList;
        currentList = 2;
        if (isExist(music)) recentPlaylist.remove(recentPlaylist.find(music)); //TODO
        currentList = temp;
        recentPlaylist.addMusic(music);
    }

    public void addToFavoriteMusicList(Music music) {    // add to favorite MP3Music list
        int temp = currentList;
        currentList = 1;
        if (!isExist(music)) {
            currentList = temp;
            favoritePlaylist.addMusic(music);
        } else {
            currentList = temp;
        }
    }

    public boolean deleteToFavoriteMusicList(Music music) {    // delete MP3Music object in favoite MP3Music list
        if (isExist(music)) {
            for (Music iter : favoritePlaylist) {
                if (iter.getFileName().equals(music.getFileName())) {
                    favoritePlaylist.remove(favoritePlaylist.find(iter));
                    break;
                }
            }
            return true;
        } else return false;

    }

    public static MusicListManager getInstance() {    // return unique object
        if (uniqueInstance == null) {
            synchronized (MusicListManager.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new MusicListManager();
                }
            }
        }
        return uniqueInstance;
    }
    private String[] getMusicInfoFile(final String fileName, final String fileAddress) {    // read musicinfo file's information
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

    public MusicList getPlaylist() {    // return music list
        return playlist;
    }

    public MusicList getRecentPlaylist() {
        return recentPlaylist;
    }

    public MusicList getFavoritePlaylist() {
        return favoritePlaylist;
    }

    public MusicList getCurrentList() {    // return using list
        switch (currentList) {
            case 0:
                return playlist;
            case 1:
                return favoritePlaylist;
            case 2:
                return recentPlaylist;
        }
        return null;
    }

    private boolean isExist(Music music) {    // check MP3Music object exist
        return MusicListManager.getInstance().findIndex(music.getFileAddress()) != -1;
    }

    public MusicListManager addRecentPlaylistObserver(Observer o) {
        recentPlaylist.addObserver(o);
        return this;
    }

    public MusicListManager addPlaylistObserver(Observer o) {
        playlist.addObserver(o);
        return this;
    }

    public MusicListManager addFavoritePlaylistObserver(Observer o) {
        favoritePlaylist.addObserver(o);
        return this;
    }

    public boolean setCurrentList(int i) {
        if(i >= 0 && i <= 2) {
            currentList = i;
            return true;
        }
        else return false;
    }

    public void performSort() {
        sortBehavior.sort((ArrayList<Music>)this.getCurrentList().getMusicList());
    }

    public void setSortBehavior(SortBehavior sortBehavior) {
        this.sortBehavior = sortBehavior;
    }
}
