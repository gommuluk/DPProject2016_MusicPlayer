package Model.Iterator;

import Model.MusicList;

import java.util.Iterator;

/**
 * Created by Yong Woon Jang on 2016-11-29.
 */
public abstract class IteratorDecorator extends MusicListIterator {
    protected IteratorDecorator(MusicList m) { super(m); }
}
