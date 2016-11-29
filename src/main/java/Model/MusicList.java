package Model;

import Model.Iterator.CyclicIterator;
import Model.Iterator.MusicListIterator;
import Model.Iterator.MusicListShuffleIterator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class MusicList extends Observable implements Iterable<Music> {
    List<Music> list;
    PlayMode playMode;

    @Override
    public MusicListIterator iterator() {
        MusicListIterator iterator;
        switch(playMode) {
            case CYCLIC_WHOLE :
                iterator = new CyclicIterator(new MusicListIterator(this));
                break;
            case ONE_REPEAT :
                ArrayList<Music> oneElementList = new ArrayList<Music>();
                oneElementList.add(CurrentMusic.getInstance().getMusic());
                iterator = new MusicListIterator(new MusicList(oneElementList));
                break;
            case SHUFFLE :
                iterator = new MusicListShuffleIterator(this);
                break;
            default :  // WHOLE CASE
                iterator = new MusicListIterator(this);
                break;
        }
        return iterator;
    }

    public MusicList() {
        this.list = new ArrayList<>();
        this.playMode = PlayMode.CYCLIC_WHOLE;
    }

    public MusicList(List<Music> list) {
        this.list = list;
        this.playMode = PlayMode.CYCLIC_WHOLE;
    }

    public List<Music> getMusicList() {
        return java.util.Collections.unmodifiableList(this.list);
    }

    public boolean addMusic(Music music) {
        boolean ret = this.list.add(music);
        setChanged();
        notifyObservers(list);
        return ret;
    }

    public int find(Music music) {
        return this.list.indexOf(music);
    }

    public Music at(int index) throws IndexOutOfBoundsException {
        return this.list.get(index);
    }

    public void remove(int index) throws IndexOutOfBoundsException {
        this.list.remove(index);
    }

    public int size() {
        return list.size();
    }
}
