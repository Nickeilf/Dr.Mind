package util;

import java.util.ArrayList;
import java.util.List;

import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class TextOnTouchListener implements OnTouchListener {
	// 存储时间的数组
	private GestureDetector mGesture;

	public TextOnTouchListener() {
		mGesture = new GestureDetector(new gestureListener());
		mGesture.setOnDoubleTapListener(new doubleTapListener());
	}

	public boolean onTouch(View v, MotionEvent event) {
		mGesture.onTouchEvent(event);
		return true;
	}

	private class gestureListener implements GestureDetector.OnGestureListener {

		public boolean onDown(MotionEvent e) {
			Log.i("MyGesture", "onDown");
			return false;
		}

		public void onShowPress(MotionEvent e) {
			Log.i("MyGesture", "onShowPress");
		}

		public boolean onSingleTapUp(MotionEvent e) {
			Log.i("MyGesture", "onSingleTapUp");
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

		public boolean onSingleTapConfirmed(MotionEvent e) {
			Log.i("MyGesture", "onSingleTapConfirmed");
			return true;
		}

		public boolean onDoubleTap(MotionEvent e) {
			Log.i("MyGesture", "onDoubleTap");
			System.out.println("双击产生");
			return true;
		}

		public boolean onDoubleTapEvent(MotionEvent e) {
			Log.i("MyGesture", "onDoubleTapEvent");

			return true;
		}
	};
}
