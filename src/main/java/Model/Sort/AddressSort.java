
package Model.Sort
    ;

import Model.Music;

import java.util.ArrayList;
import java.util.Collections;


//경로 + 파일 이름으로 정렬
public class AddressSort implements SortBehavior {

	public void sort(ArrayList<Music> musicList){

		Collections.sort(musicList, new Model.AddressCompare());
	}
}



