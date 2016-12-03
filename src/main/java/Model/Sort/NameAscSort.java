package Model.Sort;

import Model.Music;

import java.util.ArrayList;
import java.util.Collections;

//제목 오름차순으로 정렬
public class NameAscSort implements SortBehavior {

	public void sort(ArrayList<Music> musicList){

		Collections.sort(musicList, new NameAscCompare());
	}
}
