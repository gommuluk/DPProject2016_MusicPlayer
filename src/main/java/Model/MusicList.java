package Model;

import Model.Iterator.CyclicIterator;
import Model.Iterator.MusicListIterator;
import Model.Iterator.MusicListShuffleIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class MusicList extends Observable implements Iterable<Music> {
    List<Music> list;
    public static PlayMode playMode = PlayMode.CYCLIC_WHOLE;

    @Override
    public MusicListIterator iterator() {
        return new MusicListIterator(this);
    }

    public MusicListIterator decoratedIterator() {
        MusicListIterator iterator = iterator();
        switch(playMode) {
            case CYCLIC_WHOLE :
                iterator = new CyclicIterator(iterator);
                break;
            case ONE_REPEAT :
                ArrayList<Music> oneElementList = new ArrayList<Music>();
                oneElementList.add(CurrentMusic.getInstance().getMusic());
                iterator = new MusicListIterator(new MusicList(oneElementList));
                break;
            case SHUFFLE :
                iterator = new MusicListShuffleIterator(new MusicListIterator(this));
                break;
            default :  // WHOLE CASE
                break;
        }
        return iterator;
    }

    public MusicList() {
        this.list = new ArrayList<>();
    }

    public MusicList(List<Music> list) {
        this.list = list;
    }

    public List<Music> getMusicList() {
        return this.list;
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
