package view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ui.SinGraph;

public class DViewGroup extends ViewGroup {

	private float posX = this.getX();
	private float posY = this.getY();
	private float startX;
	private float startY;

	public DViewGroup(Context context) {
		super(context);
	}

	public DViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		myAddView();
	}

	public DViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 添加View的方法
	 */
	public void myAddView() {

		SinGraph sin=new SinGraph(getContext(),6);
		addView(sin);

		TextView mIcon = new TextView(this.getContext());
		mIcon.setText("a");
		mIcon.setTextColor(Color.BLACK);
		mIcon.setVisibility(VISIBLE);
		mIcon.setGravity(Gravity.CENTER);
		addView(mIcon);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			startY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			float stopX = event.getX();
			float stopY = event.getY();
			Log.e("TAG", "onTouchEvent-ACTION_MOVE\nstartX is " + startX + " startY is " + startY + " stopX is " + stopX
					+ " stopY is " + stopY);
			posX += stopX - startX;
			posY += stopY - startY;
			this.setX(posX);
			this.setY(posY);
			invalidate();// call onDraw()
			break;
		}
		return true;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		View a = getChildAt(0);
		a.layout(300 - l, t, r, b);
	}

}