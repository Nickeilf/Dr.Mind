package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liu on 2016/7/13.
 */
public class HeightCompute {
	List<Integer> listOfWeight;

	public HeightCompute() {
		listOfWeight = new ArrayList<Integer>();
	}

	public HeightCompute(List<Integer> list) {
		this.listOfWeight = list;
	}

	public int computeHeight() {
		int height = 0;
		int singleRec = Constant.getSingleRec();
		for (int i = 0; i < listOfWeight.size(); i++) {
			height += listOfWeight.get(i);
		}
		height--;
		height *= singleRec;
		return height;
	}
}
