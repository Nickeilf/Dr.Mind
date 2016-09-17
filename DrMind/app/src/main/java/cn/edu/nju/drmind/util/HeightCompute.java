package cn.edu.nju.drmind.util;

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
		int singleRec = Constant.getScreenWidth()/5;
		for (int i = 0; i < listOfWeight.size(); i++) {
			height += listOfWeight.get(i);
		}
		height--;
		if(height==0){
			height=1;
		}
		height *= singleRec;
		return height+10;
	}
}
