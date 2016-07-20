package service;

import android.content.Context;

import po.paintInfoPO;

public interface paintDataService {
	 boolean saveData(String paintName,paintInfoPO paintpo,Context context);//存储数据，参数是图片名和根结点
     paintInfoPO  getData(String paintName,Context context);//获取数据，得到一个PO     	
     boolean deleteData(String paintName,Context context);//删除数据
     
}
