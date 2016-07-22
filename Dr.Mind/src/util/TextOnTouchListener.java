package util;

import activity.MindActivity;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import bl.paintblImpl;
import data.SqliteDBHelper;
import service.paintService;
import view.DEditTextView;
import view.DViewGroup;

public class TextOnTouchListener implements OnTouchListener {
	private GestureDetector mGesture;
	private gestureListener gl;
	private doubleTapListener dtl;
	private paintService paintService;
	private int count = 0;
	private long firClick;

	public TextOnTouchListener() {
		gl = new gestureListener();
		dtl = new doubleTapListener();
		mGesture = new GestureDetector(gl);
		mGesture.setOnDoubleTapListener(dtl);
		paintService = new paintblImpl();
	}

	public boolean onTouch(View v, MotionEvent event) {
		gl.setV(v);
		dtl.setV(v);
		if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
			System.out.println("夭寿啦！！");
		}
		v.setFocusable(true);
		v.setFocusableInTouchMode(true);
		v.requestFocus();
		if (v instanceof DEditTextView) {
			DEditTextView view = (DEditTextView) v;
			view.setSelection(view.length());
		}

		mGesture.onTouchEvent(event);
		return true;
	}

	private class gestureListener implements GestureDetector.OnGestureListener {
		private View v;

		public void setV(View v) {
			this.v = v;
		}

		public boolean onDown(MotionEvent e) {
			Log.i("MyGesture", "onDown");
			return true;
		}

		public void onShowPress(MotionEvent e) {
			Log.i("MyGesture", "onShowPress");
		}

		public boolean onSingleTapUp(MotionEvent e) {
			Log.i("MyGesture", "onSingleTapUp");
			InputMethodManager imm = (InputMethodManager) MindActivity.a.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(v, 0);
			return true;
		}

		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			Log.i("MyGesture22", "onScroll:" + (e2.getX() - e1.getX()) + "   " + distanceX);
			return true;
		}

		public void onLongPress(MotionEvent e) {
			Log.i("MyGesture", "onLongPress");
		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			Log.i("MyGesture", "onFling");
			return true;
		}
	};

	// OnDoubleTapListener监听
	private class doubleTapListener implements GestureDetector.OnDoubleTapListener {
		private View v;

		public void setV(View v) {
			this.v = v;
		}

		public boolean onSingleTapConfirmed(MotionEvent e) {
			Log.i("MyGesture", "onSingleTapConfirmed");
			return true;
		}

		public boolean onDoubleTap(MotionEvent e) {
			Log.i("MyGesture", "onDoubleTap");
			InputMethodManager imm = (InputMethodManager) MindActivity.a.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			DEditTextView editText = (DEditTextView) v;
			paintService.InsertNode(editText.getNode());
			DViewGroup parent = (DViewGroup) editText.getParent();
//			parent.refresh();
			return true;
		}

		public boolean onDoubleTapEvent(MotionEvent e) {
			Log.i("MyGesture", "onDoubleTapEvent");

			return true;
		}
	};
}