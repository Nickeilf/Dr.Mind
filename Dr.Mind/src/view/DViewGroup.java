package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

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
	private boolean first;

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
		first = true;
		// refresh();

		myAddView();// Test
		sGestureDetector = new ScaleGestureDetector(this.getContext(), new MyScaleGestureListener());
	}

	public DViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void refresh() {
		Node root = paintInfo.getbTreeRoot().getRoot();

		// 根据树形结构画图
		removeViews(1, getChildCount() - 1);
		System.out.println("s刷新重新建图");

		DEditTextView editText = (DEditTextView) getChildAt(0);
		drawTree(editText, editText.getRight(), editText.getBottom() - editText.getMeasuredHeight() / 2);

	}

	/**
	 * 
	 * @param view
	 * @param x
	 *            是指组件最右边位置
	 * @param y
	 *            是指组件y轴最下面位置
	 */
	private void drawTree(DEditTextView view, int x, int y) {
		// 如果没有儿子就结束
		Node node = view.getNode();
		if (node.getLeftChild() == null) {
			System.out.println("没有儿砸");
		} else {
			Node p = node.getLeftChild();
			// 找出一层所有子节点
			List<Node> nodeList = new ArrayList<Node>();
			nodeList.add(p);
			while (p.getRightChild() != null) {
				p = p.getRightChild();
				nodeList.add(p);
			}
			List<Integer> weight = new ArrayList<Integer>();
			for (Node nod : nodeList) {
				weight.add(paintService.numNode(nod));
			}
			// new SinGraph
			int level = nodeList.get(0).getLevel();
			System.out.println("level is" + level);
			HeightCompute cal = new HeightCompute(weight);
			int ys = y - cal.computeHeight() / 2 - 6;
			SinGraph sin = new SinGraph(getContext(), weight, new MyPoint(x, ys), level);
			addView(sin);
			sin.layout(x, ys, x + sin.getSinWidth(), ys + sin.getSinHeight());
			// 添加一组DEditText
			// TODO Node内容
			List<MyPoint> points = sin.getPointList();
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa" + nodeList.size());
			for (int i = 0; i < nodeList.size(); i++) {
				DEditTextView text = new DEditTextView(getContext());
				MyPoint point = points.get(i);
				text.setNode(nodeList.get(i));
				text.setIncludeFontPadding(false);
				text.setOnTouchListener(new TextOnTouchListener());
				addView(text);
				text.measure(0, 0);
				int t_x = (int) (x + point.getX());
				int t_y = (int) (ys + point.getY()) - text.getMeasuredHeight() + 1;
				text.setxPos(t_x);
				text.setyPos(t_y);
				drawTree(text, t_x + text.getMeasuredWidth(), t_y + text.getMeasuredHeight());
			}
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
		// listOfWeight.add(1);
		// listOfWeight.add(1);
		//
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
		if (first) {
			refresh();
			first = false;
		}
		for (int i = 1; i < getChildCount(); i++) {
			View ins = getChildAt(i);
			if (ins instanceof DEditTextView) {
				DEditTextView view = (DEditTextView) ins;
				int x = view.getxPos();
				int y = view.getyPos();
				view.layout(x, y, x + view.getMeasuredWidth(), y + view.getMeasuredHeight());
			}
		}
		// int sin_height = sin.getSinHeight();
		// int sin_width = sin.getSinWeight();
		// sin.layout(s_x + a.getMeasuredWidth(), s_y + a.getMeasuredHeight() /
		// 2 - sin_height / 2,
		// s_x + a.getMeasuredWidth() + sin_width, s_y + a.getMeasuredHeight() /
		// 2 + sin_height / 2);
		// SinGraph sin = (SinGraph) getChildAt(1);
		// int sin_height = sin.getSinHeight();
		// int sin_width = sin.getSinWidth();
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