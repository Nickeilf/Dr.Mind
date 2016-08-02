package drawer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import cn.edu.cn.R;

public class DatasUtil {
	private static DatasUtil mDatas;
	private List<NavigationItem> mList;
	
	@SuppressWarnings("deprecation")
	private DatasUtil(Context context){
		mList=new ArrayList<NavigationItem>();
		mList.add(new NavigationItem("star1", context.getResources().getDrawable(R.drawable.plus ),Style.DEFAULT));
		mList.add(new NavigationItem("star2", context.getResources().getDrawable(R.drawable.plus),Style.DEFAULT));
		mList.add(new NavigationItem("star3", context.getResources().getDrawable(R.drawable.plus),Style.DEFAULT));
		mList.add(new NavigationItem("star4", context.getResources().getDrawable(R.drawable.plus),Style.DEFAULT));
		mList.add(new NavigationItem("star5", context.getResources().getDrawable(R.drawable.plus),Style.DEFAULT));
		mList.add(new NavigationItem("star6", context.getResources().getDrawable(R.drawable.plus),Style.HASLINE));
		mList.add(new NavigationItem("star7", null,Style.NO_ICON));
		mList.add(new NavigationItem("star8", null,Style.NO_ICON));
	}
	
	public static DatasUtil getInstance(Context context){
		if(mDatas==null){
			synchronized (DatasUtil.class) {
				if(mDatas==null){
					mDatas=new DatasUtil(context);
				}
			}
		}
		return mDatas;
		
	}
	
	
	public  List<NavigationItem> getMenu(){
		 return new ArrayList<NavigationItem>(mList);
	}

}
