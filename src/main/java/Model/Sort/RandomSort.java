package Model;

import java.util.ArrayList;
import java.util.Collections;

//뒤섞기
public class RandomSort implements SortBehavior {

	public void sort(ArrayList<Music> musicList){
		
		Collections.shuffle(musicList); 
	}
}


