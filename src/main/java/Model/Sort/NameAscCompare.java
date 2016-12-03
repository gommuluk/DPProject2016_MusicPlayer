package Model.Sort;

import Model.Music;

import java.util.Comparator;


//오름차순(ASC)
public class NameAscCompare implements Comparator<Music> {

	@Override
	public int compare(Music arg0, Music arg1) {
		return arg0.getFileName().compareToIgnoreCase(arg1.getFileName());
	}
}
