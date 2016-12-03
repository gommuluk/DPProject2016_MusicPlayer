
package Model;

import java.util.Comparator;

public class AddressCompare implements Comparator<Music> {

	@Override
	public int compare(Music arg0, Music arg1) {
		return arg0.getFileAddress().compareToIgnoreCase(arg1.getFileAddress());
	}
}






		


