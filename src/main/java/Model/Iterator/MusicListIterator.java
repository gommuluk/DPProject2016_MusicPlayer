package Model.Iterator;

import Model.Music;
import Model.MusicList;

import java.util.Iterator;

/**
 * Created by Yong Woon Jang on 2016-11-29.
 */
public class MusicListIterator implements Iterator {
    protected MusicList musicList;
    private int idx;

    public MusicListIterator(MusicList m) {
        musicList = m;
        idx = 0;
    }

    @Override
    public Music next() {
        return musicList.at(idx++);
    }

    @Override
    public boolean hasNext() {
        if (idx < musicList.size())
            return true;
        return false;
    }

    public void reset() {
        idx = 0;
    }

    public int size() {
        return musicList.size();
    }
}
