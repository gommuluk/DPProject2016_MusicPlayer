package Model.Sort;

import Model.Music;

import java.util.Comparator;


//내림차순(DESC)
public class NameDescCompare implements Comparator<Music> {

	@Override
	public int compare(Music arg0, Music arg1) {
		return (-1)*(arg0.getFileName().compareToIgnoreCase(arg1.getFileName()));
	}
}

