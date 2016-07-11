package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ui.SinGraph;

public class DViewGroup extends ViewGroup {
	private ScaleGestureDetector sGestureDetector;

	private Paint paint;
	private float posX = this.getX();
	private float posY = this.getY();
	private float startX;
	private float startY;
	private int screenWidth;
	private int screenHeight;

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	/**
	 * 构造函数，继承父类
	 * 
	 * @param context
	 */
	public DViewGroup(Context context) {
		super(context);
	}

	public DViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		myAddView();// Test
		sGestureDetector = new ScaleGestureDetector(this.getContext(), new MyScaleGestureListener());
	}

	public DViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 添加View的方法
	 */
	public void myAddView() {

		SinGraph sin = new SinGraph(this.getContext(), 6);
		addView(sin);

		TextView mIcon = new TextView(this.getContext());
		mIcon.setText("a");
		mIcon.setTextColor(Color.BLACK);
		mIcon.setVisibility(VISIBLE);
		mIcon.setGravity(Gravity.CENTER);
		addView(mIcon);
	}

	/*
	 * 可划动，设定边界
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getPointerCount() == 1) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = event.getX();
				startY = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				float stopX = event.getX();
				float stopY = event.getY();
				Log.e("TAG", "onTouchEvent-ACTION_MOVE\nstartX is " + startX + " startY is " + startY + " stopX is "
						+ stopX + " stopY is " + stopY);
				posX += stopX - startX;
				posY += stopY - startY;
				posX = posX > 0 ? 0 : posX;
				posY = posY > 0 ? 0 : posY;
				posX = posX < -2 * screenWidth ? -2 * screenWidth : posX;
				posY = posY < -2 * screenHeight ? -2 * screenHeight : posY;
				this.setX(posX);
				this.setY(posY);
				invalidate();// call onDraw()
				break;
			}
			return true;
		} else {
			sGestureDetector.onTouchEvent(event);
			return true;
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		View a = getChildAt(0);
		a.layout(0, 0, 500, 500);
		// View ba = getChildAt(1);
		// ba.layout(0, 0, 500, 500);
	}

	private class MyScaleGestureListener extends SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			Log.e("view-缩放", "onScale，" + detector.getScaleFactor());
			return super.onScale(detector);
		}

		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			Log.e("view-缩放", "onScaleBegin");
			return super.onScaleBegin(detector);
		}

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {
			Log.e("view-缩放", "onScaleEnd");
		}
	}
}
