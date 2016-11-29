package Model.Iterator;

import Model.Music;
import Model.MusicList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Yong Woon Jang on 2016-11-29.
 */
public class MusicListShuffleIterator extends IteratorDecorator {
    private MusicListIterator iterator;

    private int shuffle[];
    private int shuffleidx;
    private final int range;

    public MusicListShuffleIterator(MusicListIterator it) {
        super(null);
        iterator = it;
        range = it.size();

        shuffle = new int[range];
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i=0; i<range; i++)
            arrayList.add(i);
        Collections.shuffle(arrayList);
        for (int i=0; i<range; i++)
            shuffle[i] = arrayList.get(i);

        shuffleidx = 0;
    }

    @Override
    public Music next() {
        return iterator.musicList.at(shuffle[shuffleidx++]);
    }

    @Override
    public boolean hasNext() {
        if (shuffleidx < range) return true;
        return false;
    }
}
