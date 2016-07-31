package service;

import java.util.ArrayList;

import po.paintInfoPO;
import data.paintDao;

public interface paintDataService{
	
	 boolean saveData(String paintName,paintInfoPO paintpo,paintDao dao);//存储数据，参数是图片名和根结点
     paintInfoPO  getData(String paintName,paintDao dao);//获取数据，得到一个PO     	
     boolean deleteData(String paintName,paintDao dao);//删除数据
     ArrayList<String> getAllPaintName(paintDao dao);//获取所有数据库中图表的名字
}
