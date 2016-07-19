package impl;

import android.R.drawable;
import android.provider.ContactsContract.CommonDataKinds.Note;
import data.paintDao;
import po.paintInfoPO;
import service.paintDataService;
import vo.paintInfoVo;

public class paintDataServiceImpl implements paintDataService{
	

	public paintInfoPO getData(String paintName) {
		// TODO Auto-generated method stub
		paintInfoPO po =new paintInfoPO();
	    	
//		try{
//			po=
//		}
		return po;
	}

	public boolean saveData(String paintName, paintInfoVo paintvo) {
		// TODO Auto-generated method stub
		paintInfoPO po = new paintInfoPO();
		po.setbTreeRoot(paintvo.getbTreeRoot());
		po.setPaintName(paintName);
		
		return false;
	}

}
