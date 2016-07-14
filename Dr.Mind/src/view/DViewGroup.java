package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bl.paintblImpl;
import service.paintService;
import ui.MyPoint;
import ui.SinGraph;
import util.Constant;
import util.HeightCompute;
import util.TextOnTouchListener;
import vo.Node;
import vo.paintInfoVo;

public class DViewGroup extends ViewGroup {
	private paintService paintService;
	private paintInfoVo paintInfo;
	private ScaleGestureDetector sGestureDetector;

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
		paintService = new paintblImpl();
		paintInfo = paintService.createPaint();
		// refresh();

		myAddView();// Test
		sGestureDetector = new ScaleGestureDetector(this.getContext(), new MyScaleGestureListener());
	}

	public DViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void refresh() {
		Node root = paintInfo.getbTreeRoot().getRoot();
		System.out.println("s刷新重新建图");
		// 根据树形结构画图

		DEditTextView editText = (DEditTextView) getChildAt(0);
		drawTree(editText);

	}

	private void drawTree(DEditTextView view) {
		// 如果没有儿子就结束
		Node node = view.getNode();
		if (node.getLeftChild() == null) {
			System.out.println("没有儿砸");
			return;
		} else {
			Node p = node.getLeftChild();
			System.out.println("有儿砸啦");
			// 找出一层所有子节点
			List<Node> nodeList = new ArrayList<Node>();
			nodeList.add(p);
			while (p.getRightChild() != null) {
				nodeList.add(p);
				p = p.getRightChild();
			}
			List<Integer> weight = new ArrayList<Integer>();
			for (Node nod : nodeList) {
				weight.add(paintService.numNode(nod));
			}
			System.out.println("?????" + weight.size());
			// new SinGraph
			int x = (int) (view.getxPos() + view.getMeasuredWidth());
			System.out.println(x + "xxxxxxxxxxx");
			HeightCompute cal = new HeightCompute(weight);
			int y = (int) (view.getY() + view.getMeasuredHeight() / 2 - cal.computeHeight() / 2);
			System.out.println(y + "yyyyyyyyyyyy");
			SinGraph sin = new SinGraph(getContext(), weight, new MyPoint(x, y));
			addView(sin);
			sin.layout(0, 0, 1000, 1000);

		}
	}

	/**
	 * 添加View的方法
	 */
	public void myAddView() {
		DEditTextView editText = new DEditTextView(getContext());
		editText.setNode(paintInfo.getbTreeRoot().getRoot());
		editText.setOnTouchListener(new TextOnTouchListener());
		addView(editText);

		// ArrayList<Integer> listOfWeight = new ArrayList<Integer>();
		// listOfWeight.add(1);
		// listOfWeight.add(2);
		// listOfWeight.add(3);
		// listOfWeight.add(4);
		// listOfWeight.add(5);
		// listOfWeight.add(6);
		// listOfWeight.add(7);
		// SinGraph sin = new SinGraph(getContext(), listOfWeight, new
		// MyPoint(0, 200));
		// addView(sin);
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
		DEditTextView a = (DEditTextView) getChildAt(0);
		int s_x = 3 * screenWidth / 2 - a.getMeasuredWidth() / 2;
		int s_y = 3 * screenHeight / 2 - a.getMeasuredHeight() / 2;
		a.setxPos(s_x);
		a.setyPos(s_y);
		a.layout(s_x, s_y, s_x + a.getMeasuredWidth(), s_y + a.getMeasuredHeight());
		refresh();

		// int sin_height = sin.getSinHeight();
		// int sin_width = sin.getSinWeight();
		// sin.layout(s_x + a.getMeasuredWidth(), s_y + a.getMeasuredHeight() /
		// 2 - sin_height / 2,
		// s_x + a.getMeasuredWidth() + sin_width, s_y + a.getMeasuredHeight() /
		// 2 + sin_height / 2);
		// SinGraph sin = (SinGraph) getChildAt(1);
		// int sin_height = sin.getSinHeight();
		// int sin_width = 200;
		// sin.layout(s_x + a.getMeasuredWidth(), s_y + a.getMeasuredHeight() /
		// 2 - sin_height / 2,
		// s_x + a.getMeasuredWidth() + sin_width, s_y + a.getMeasuredHeight() /
		// 2 + sin_height / 2);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	private class MyScaleGestureListener extends SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			Log.e("view-缩放", "onScale，" + detector.getScaleFactor());
			// 缩放待实现，已检测到
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