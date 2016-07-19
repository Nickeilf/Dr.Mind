package service;

import java.util.ArrayList;

import android.provider.ContactsContract.CommonDataKinds.Note;
import po.paintInfoPO;
import vo.paintInfoVo;

public interface paintDataService {
	 boolean saveData(String paintName,paintInfoVo paintvo);//存储数据，参数是图片名和根结点
     paintInfoPO  getData(String paintName);//获取数据，得到一个PO     	

}
