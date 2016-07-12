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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import ui.SinGraph;

public class DViewGroup extends ViewGroup {
	private ScaleGestureDetector sGestureDetector;

	private Paint paint;
	private int x_start=0;
	private int y_start=200;//绘图的起始坐标
	private int y_bias=0;
	int singleRec=20;


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
		EditText editText=new EditText(getContext());
		addView(editText);
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
		// 循环所有子View
		for (int i=0; i<getChildCount(); i++) {
			View child = getChildAt(i);
			// 取出当前子View长宽
			int width = child.getMeasuredWidth();
			int height = child.getMeasuredHeight();

			// 计算当前的mLeft和mTop值（r,b为传递进来的父View的mRight和mBottom值）
			int mLeft = (r - width) / 2;
			int mTop = (b - height) / 2;

			// 调用layout并传递计算过的参数为子view布局
			child.layout(mLeft, mTop, mLeft + width, mTop + height);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		measureChildren(widthMeasureSpec,heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.rgb(131, 175, 155));
		paint = new Paint();
		paint.setColor(Color.rgb(205, 243, 246));
		paint.setStrokeWidth(2);
		paint.setAntiAlias(true);
		mydraw(canvas,7);
	}

	private void mydraw(Canvas canvas,int sum ) {

		int type = sum % 2;

		switch (type) {
			case 0:
				for (int i = 1; i <= sum; i++) {
					float x_value = x_start;
					float y_value = y_start;

					for (int j = 0; j < 90; j++) {
						x_value = x_value + 1;
						float Ai;
						if (i <= sum / 2) {
							Ai = singleRec * i;
							y_value = (float) (Ai * Math.sin(Math.PI * (j + 45) / 90) + y_start - Ai);
						} else {
							Ai = singleRec * (i - sum / 2);
							y_value = (float) (-Ai * Math.sin(Math.PI * (j + 45) / 90) + y_start + Ai);
						}
						canvas.drawPoint(x_value, y_value, paint);
					}
					y_bias=(int)y_value;
//					EditText editText=new EditText(getContext());
//					addView(editText);
					myAddView();
				}
				break;
			case 1:
				for (int i = 1; i <= sum; i++) {
					float x_value = x_start;
					float y_value = y_start;

					for (int j = 0; j < 90; j++) {
						x_value = x_value + 1;
						float Ai;
						if (i <= sum / 2) {
							Ai = singleRec * i;
							y_value = (float) (Ai * Math.sin(Math.PI * (j + 45) / 90) + y_start - Ai);
						} else if (i == (sum + 1) / 2) {
							y_value = y_start;
						} else {
							Ai = singleRec * (i - (sum + 1) / 2);
							y_value = (float) (-Ai * Math.sin(Math.PI * (j + 45) / 90) + y_start + Ai);
						}
						canvas.drawPoint(x_value, y_value, paint);
					}
					y_bias=(int)y_value;
//					EditText editText=new EditText(getContext());
//					addView(editText);
					myAddView();
				}
				break;
		}
	}
}
