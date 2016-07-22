package activity;

import ui.ViewToPicture;
import util.Constant;
import view.DEditTextView;
import view.DViewGroup;
import voice.VoiceToWord;
import FAB.FloatingActionButton;
import FAB.FloatingActionMenu;
import FAB.SubActionButton;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import cn.edu.cn.R;

public class MindActivity extends Activity {
	public static MindActivity a;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		 * paintDao a=new paintDao(this); a.insert(19, "dad", 0,3,3, "dd",8);
		 * 
		 * System.out.println(a.execQuery("dad"));
		 */
		// paintService PaintblImpl=new paintblImpl();
		// paintInfoVo vo=PaintblImpl.createPaint();
		// Node node=PaintblImpl.InsertNode(vo.getbTreeRoot().getRoot().get(0));
		// vo.getbTreeRoot().getRoot().get(0).setTextValue("ppppppp");
		// node.setTextValue("moximoxi");
		// PaintblImpl.SavePaint("sssqs", vo, this);
		// paintInfoVo vo2=PaintblImpl.OpenPaint("sssqs", this);
		// System.out.println(vo2.getbTreeRoot().getRoot().get(0).getId());
		// System.out.println(vo2.getbTreeRoot().getRoot().get(0).getLeftChild().getTextValue());

		// 全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		init();

		// 中心图标
		ImageView icon = new ImageView(this); // Create an icon
		icon.setImageDrawable(this.getResources()
				.getDrawable(R.drawable.ic_add));
		FloatingActionButton actionButton = new FloatingActionButton.Builder(
				this).setContentView(icon).build();

		// 分散式图标
		SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
		ImageView itemIcon1 = new ImageView(this);
		itemIcon1.setImageDrawable(this.getResources().getDrawable(
				R.drawable.voice));
		SubActionButton button1 = itemBuilder.setContentView(itemIcon1).build();
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				DViewGroup group = (DViewGroup) findViewById(R.id.viewgroup);

				VoiceToWord voice = new VoiceToWord(MindActivity.this,
						"534e3fe2", group);
				voice.GetWordFromVoice();
			}
		});

		ImageView itemIcon2 = new ImageView(this);
		itemIcon2.setImageDrawable(this.getResources().getDrawable(
				R.drawable.delete));
		SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				DViewGroup group = (DViewGroup) findViewById(R.id.viewgroup);
				group.deleteNode();
			}
		});

		ImageView itemIcon3 = new ImageView(this);
		itemIcon3.setImageDrawable(this.getResources().getDrawable(
				R.drawable.plus));
		SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();
		button3.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				DViewGroup group = (DViewGroup) findViewById(R.id.viewgroup);
				group.insertNode();
			}
		});

		ImageView itemIcon4 = new ImageView(this);
		itemIcon4.setImageDrawable(this.getResources().getDrawable(
				R.drawable.picture));
		SubActionButton button4 = itemBuilder.setContentView(itemIcon4).build();
		button4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				View view = findViewById(R.id.viewgroup);
				if (view != null) {
					ViewToPicture viewToPic = new ViewToPicture();
					viewToPic.save(view, "Liu");
				} else {
					System.out.println("bitmap null");
				}
			}
		});
		
		ImageView itemIcon5 = new ImageView(this);
		itemIcon5.setImageDrawable(this.getResources().getDrawable(
				R.drawable.success2));
		SubActionButton button5 = itemBuilder.setContentView(itemIcon5).build();
		

		// 整合在一起
		FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
				.addSubActionView(button1).addSubActionView(button2)
				.addSubActionView(button3).addSubActionView(button4)
				.addSubActionView(button5)
				.attachTo(actionButton).build();

	}

	@Override
	protected void onStart() {
		super.onStart();
		a = this;
	}

	/**
	 * 初始化：设定viewGroup大小为3*3倍屏幕大小
	 */
	@SuppressWarnings("deprecation")
	private void init() {
		WindowManager wm = this.getWindowManager();
		@SuppressWarnings("deprecation")
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		// 设定常量
		Constant.setScreenHeight(height);
		Constant.setScreenWidth(width);
		DViewGroup dView = (DViewGroup) findViewById(R.id.viewgroup);
		LinearLayout.LayoutParams lay = (LayoutParams) findViewById(
				R.id.viewgroup).getLayoutParams();

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
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	// 判定是否需要隐藏
	private boolean isHideInput(View v, MotionEvent ev) {
		if (v != null && (v instanceof DEditTextView)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
					+ v.getWidth();
			if (ev.getX() > left && ev.getX() < right && ev.getY() > top
					&& ev.getY() < bottom) {
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
			manager.hideSoftInputFromWindow(token,
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

}