package Model.Iterator;

import Model.Music;
import Model.MusicList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Yong Woon Jang on 2016-11-29.
 */
public class MusicListShuffleIterator extends MusicListIterator {
    int shuffle[];
    int shuffleidx;
    final int size;

    public MusicListShuffleIterator(MusicList m) {
        super(m);
        size = m.size();

        shuffle = new int[size];
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i=0; i<size; i++)
            arrayList.add(i);
        Collections.shuffle(arrayList);
        for (int i=0; i<size; i++)
            shuffle[i] = arrayList.get(i);

        shuffleidx = 0;
    }

    @Override
    public Music next() {
        return musicList.at(shuffle[shuffleidx++]);
    }

    @Override
    public boolean hasNext() {
        if (shuffleidx < size) return true;
        return false;
    }
}
