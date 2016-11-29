package Model;

import Model.Iterator.MusicListIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MusicList extends Observable implements Iterable<Music> {
    List<Music> list;

    @Override
    public MusicListIterator iterator() {
        return new MusicListIterator(this);
    }

    public MusicList() {
        this.list = new ArrayList<>();
    }

    public MusicList(List<Music> list) {
        this.list = list;
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
