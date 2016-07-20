package activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import bl.paintblImpl;
import cn.edu.cn.R;
import data.SqliteDBHelper;
import data.paintDao;
import service.paintService;
import ui.ColorPickerDialog;
import util.Constant;
import view.DEditTextView;
import view.DViewGroup;
import vo.Node;
import vo.paintInfoVo;

public class MindActivity extends Activity {
	public static MindActivity a;
	private Button btnColorPicker;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		paintDao a=new paintDao(this);
//        a.insert(19, "dad", -1,3,3, "dd",8);
//		
//        System.out.println(a.execQuery("dad"));
//		paintService PaintblImpl=new paintblImpl();
//		paintInfoVo vo=PaintblImpl.createPaint();
//		Node node=PaintblImpl.InsertNode(vo.getbTreeRoot().getRoot().get(0));
//		vo.getbTreeRoot().getRoot().get(0).setTextValue("ppppppp");
//		node.setTextValue("moximoxi");
//		PaintblImpl.SavePaint("sssqs", vo, this);
//		paintInfoVo vo2=PaintblImpl.OpenPaint("sssqs", this);
//		System.out.println(vo2.getbTreeRoot().getRoot().get(0).getId());
//		System.out.println(vo2.getbTreeRoot().getRoot().get(0).getLeftChild().getTextValue());
		// 全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		init();
	}

	@Override
	protected void onStart() {
		super.onStart();
		a = this;
	}

	/**
	 * 初始化：设定viewGroup大小为3*3倍屏幕大小
	 */
	private void init() {
		WindowManager wm = this.getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		// 设定常量
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

	@Override
	protected void onResume() {
		// if(getRequestedOrientation() !=
		// ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// }
		super.onResume();
	}

	// 获取点击事件
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View view = getCurrentFocus();
			if (isHideInput(view, ev)) {
				HideSoftInput(view.getWindowToken());
				view.clearFocus();
				DViewGroup parent = (DViewGroup) view.getParent();
				parent.refresh();
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	// 判定是否需要隐藏
	private boolean isHideInput(View v, MotionEvent ev) {
		if (v != null && (v instanceof DEditTextView)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
			if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	// 隐藏软键盘
	private void HideSoftInput(IBinder token) {
		if (token != null) {
			InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

}