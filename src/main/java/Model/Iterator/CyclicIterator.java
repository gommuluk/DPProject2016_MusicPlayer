package Model.Iterator;

import Model.Music;

/**
 * Created by Yong Woon Jang on 2016-11-29.
 */
public class CyclicIterator extends IteratorDecorator {
    private MusicListIterator iterator;

    public CyclicIterator(MusicListIterator it) {
        super(null);
        iterator = it;
    }

    @Override
    public Music next() {
        if (!iterator.hasNext()) {
            iterator.reset();
        }

        return iterator.next();
    }

    @Override
    public boolean hasNext() {
        return true;
    }
}
