package activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import cn.edu.cn.R;
import ui.SinGraph;
import util.Constant;
import view.DViewGroup;

public class MindActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);
		init();
	}

	/**
	 * 初始化：设定viewGroup大小为3*3倍屏幕大小
	 */
	private void init() {
		WindowManager wm = this.getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		//设定常量
		Constant.setScreenHeight(height);
		Constant.setScreenWidth(width);
		DViewGroup dView = (DViewGroup) findViewById(R.id.viewgroup);
		LinearLayout.LayoutParams lay = (LayoutParams) findViewById(R.id.viewgroup).getLayoutParams();

		lay.height = 3 * height;
		lay.width = 3 * width;

		dView.setScreenWidth(width);
		dView.setScreenHeight(height);
		findViewById(R.id.viewgroup).setX(0);
		findViewById(R.id.viewgroup).setY(-height);
		findViewById(R.id.viewgroup).setLayoutParams(lay);
	}
}