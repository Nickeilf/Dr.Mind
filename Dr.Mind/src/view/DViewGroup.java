package view;

import java.util.ArrayList;
import java.util.HashMap;

import service.paintService;
import util.Constant;
import vo.Node;
import vo.paintInfoVo;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import bl.paintblImpl;
import data.paintDao;

public class DViewGroup extends ViewGroup {
	private paintService paintService;
	private paintInfoVo paintInfo;
	private paintDao dao;
	// 存放所有子节点
	private ArrayList<DEditTextView> editTexts;
	// 存放所有节点与node对应的hash表
	private HashMap<Node, DEditTextView> maps;

	private float posX = this.getX();
	private float posY = this.getY();
	private float startX;
	private float startY;
	private int screenWidth;
	private int screenHeight;
	private int singleRec;

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
		paintService = new paintblImpl();
		paintInfo = paintService.createPaint();
		dao =new paintDao(getContext());
		init();
	}

	public DViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		paintService = new paintblImpl();
		paintInfo = paintService.createPaint();
		dao =new paintDao(getContext());
		init();
	}

	public DViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		paintService = new paintblImpl();
		paintInfo = paintService.createPaint();
		dao =new paintDao(getContext());
		init();
	}

	public void save(){
		//TODO getName
		paintService.SavePaint("Mind1", paintInfo, dao);
	}
	
	public void TestFocus() {
		View v = getFocusedChild();
		System.out.println(v == null);
		System.out.println(v instanceof DEditTextView);
	}

	/**
	 * 初始化建立一个跟节点
	 */
	public void init() {
		WindowManager wm = ((Activity) this.getContext()).getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		screenWidth = width;
		screenHeight = height;
		singleRec = width / 10;
		editTexts = new ArrayList<DEditTextView>();
		maps = new HashMap<Node, DEditTextView>();

		DEditTextView root = new DEditTextView(getContext());
		root.setNode(paintInfo.getbTreeRoot().getRoot().get(0));
		root.setText("思维导图");
		root.setLittleSon(root);
		root.measure(0, 0);
		int s_x = 3 * width / 2 - root.getMeasuredWidth() / 2;
		int s_y = 3 * height / 2 - root.getMeasuredHeight() / 2;
		root.setxPos(s_x);
		root.setyPos(s_y);
		addView(root);
		maps.put(root.getNode(), root);

	}

	/**
	 * 读取跟节点们
	 */
	public void load(Node root) {
		// 先清空
		this.removeAllViews();
		// 读取
		DEditTextView rootText = new DEditTextView(getContext());
		rootText.setNode(root);
	}

	public void checkMove(DEditTextView view, float y_pos) {
		// TODO 动画移动效果
		if (view.getNode().getLevel() == 0) {
			// 跟节点不调整
			return;
		} else {
			int weight = paintService.numNode(view.getNode());
			ArrayList<Node> nodes = paintService.getAllSon(view.getNode().getParent());
			int new_pos = 0;
			for (; new_pos < nodes.size(); new_pos++) {
				DEditTextView v = maps.get(nodes.get(new_pos));
				if (v.getyPos() > view.getyPos()) {
					break;
				}
			}
			int position = nodes.indexOf(view.getNode());
			// 没有移动超过临界
			if (new_pos - position == 1) {
				return;
			}
			boolean up = new_pos < position;
			if (up) {
				// 上行
				for (int i = new_pos; i < position; i++) {
					DEditTextView v = maps.get(nodes.get(i));
					v.setyPos(v.getyPos() + weight * singleRec);
					move(v, 0, weight * singleRec);
				}
				Node next = nodes.get(new_pos);
				DEditTextView next_text = maps.get(next);
				float y_dis = view.getyPos();
				float x_dis = view.getxPos();
				view.setyPos(next_text.getLittleSon().getyPos() - singleRec * paintService.numNode(next)
						- (weight - 1) * singleRec / 2);
				view.setxPos(next_text.getxPos());
				y_dis = view.getyPos() - y_dis;
				x_dis = view.getxPos() - x_dis;
				move(view, x_dis, y_dis);
				requestLayout();
				// 树形结构更新
				if (new_pos == 0) {
					paintService.MoveNode(view.getNode(), view.getNode().getParent(), null);
				} else {
					paintService.MoveNode(view.getNode(), view.getNode().getParent(), nodes.get(new_pos - 1));
				}
			} else {
				// 下行
				new_pos = nodes.size() - 1;
				for (; new_pos >= 0; new_pos--) {
					DEditTextView v = maps.get(nodes.get(new_pos));
					if (v.getyPos() < view.getyPos()) {
						break;
					}
				}
				for (int i = new_pos; i > position; i--) {
					DEditTextView v = maps.get(nodes.get(i));
					v.setyPos(v.getyPos() - weight * singleRec);
					move(v, 0, -weight * singleRec);
				}
				Node last = nodes.get(new_pos);
				DEditTextView last_text = maps.get(last);
				float y_dis = view.getyPos();
				float x_dis = view.getxPos();
				view.setyPos(last_text.getLittleSon().getyPos() + singleRec + (weight - 1) * singleRec / 2);
				view.setxPos(last_text.getxPos());
				y_dis = view.getyPos() - y_dis;
				x_dis = view.getxPos() - x_dis;
				move(view, x_dis, y_dis);
				requestLayout();
				paintService.MoveNode(view.getNode(), view.getNode().getParent(), last);
			}
		}

	}

	/**
	 * 移动节点及其所有后代
	 * 
	 * @param text
	 *            节点Text
	 * @param x_dis
	 *            x方向位移
	 * @param y_dis
	 *            y方向位移
	 */
	public void move(DEditTextView text, float x_dis, float y_dis) {
		ArrayList<Node> sons = paintService.getAllChild(text.getNode());
		for (Node son : sons) {
			DEditTextView v = maps.get(son);
			int x = (int) (v.getxPos() + x_dis);
			int y = (int) (v.getyPos() + y_dis);
			v.setxPos(x);
			v.setyPos(y);
			v.layout(x, y, x + v.getMeasuredWidth(), y + v.getMeasuredHeight());
		}
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

	public void voice(String text) {
		View v = getFocusedChild();
		((DEditTextView) v).setText(text);
	}

	/**
	 * 删除节点，其他的相应上下浮动
	 */
	public void deleteNode() {
		View v = getFocusedChild();
		// TODO 考虑删除跟节点的情况
		System.out.println(v == null);
		System.out.println(v instanceof DEditTextView);
		if (v instanceof DEditTextView) {
			// 移除View
			DEditTextView text = (DEditTextView) v;
			int weight = paintService.numNode(text.getNode());
			ArrayList<Node> sons = paintService.getAllChild(text.getNode());
			for (Node node : sons) {
				DEditTextView son = maps.get(node);
				removeView(son);
				editTexts.remove(son);
				maps.remove(node);
			}
			if (text.getDad() == null) {

			} else if (text.getDad().getLittleSon() == text) {
				text.getDad().setLittleSon(null);
			}
			removeView(text);
			editTexts.remove(text);
			maps.remove(text.getNode());
			paintService.DeleteAllChild(text.getNode());
			// 其他View移动
			for (int i = 0; i < editTexts.size(); i++) {
				DEditTextView view = editTexts.get(i);
				int y = view.getyPos();
				int lowest = text.getLittleSon() == null ? text.getyPos() : text.getLittleSon().getyPos();
				if (y > lowest) {
					y -= weight * singleRec / 2;
					view.setyPos(y);
				} else {
					y += weight * singleRec / 2;
					view.setyPos(y);
				}
			}
			requestLayout();
		}

	}

	/**
	 * 插入节点，其他的相应上下浮动
	 */
	public void insertNode() {
		View v = getFocusedChild();
		if (v instanceof DEditTextView) {
			// 创建，初始化
			DEditTextView text = (DEditTextView) v;
			Node node = paintService.InsertNode(text.getNode());
			DEditTextView son = new DEditTextView(getContext());
			son.setDad(text);
			son.setNode(node);
			son.setText("新建节点");
			son.setLittleSon(son);
			// 定位
			DEditTextView little = text.getLittleSon();
			if (little == text) {
				// 第一个子节点
				son.setxPos(text.getxPos() + text.getMeasuredWidth() + Constant.SIN_WIDTH);
				son.setyPos(text.getyPos());
				addView(son);
				editTexts.add(son);
				maps.put(node, son);
				son.measure(0, 0);
				System.out.println("添加大成功");
				text.setLittleSon(son);
				requestLayout();
			} else {
				// 加到父节点的最后一个
				son.setxPos(little.getxPos());
				son.setyPos(little.getyPos() + singleRec / 2);
				addView(son);
				editTexts.add(son);
				maps.put(node, son);
				son.measure(0, 0);
				System.out.println("添加大成功");
				// TODO 移动的动画
				ArrayList<DEditTextView> moveList = new ArrayList<DEditTextView>();
				for (int i = 0; i < getChildCount(); i++) {
					if (getChildAt(i) instanceof DEditTextView) {
						moveList.add((DEditTextView) getChildAt(i));
					}
				}
				DEditTextView p = son;
				while (p.getNode() != paintInfo.getbTreeRoot().getRoot().get(0)) {
					moveList.remove(p);
					p = p.getDad();
				}
				moveList.remove(p);
				for (DEditTextView dEditTextView : moveList) {
					int pos = dEditTextView.getyPos() - son.getyPos() > 0 ? singleRec / 2 : -singleRec / 2;
					int y = dEditTextView.getyPos() + pos;
					dEditTextView.setyPos(y);
				}
				text.setLittleSon(son);
				requestLayout();
			}

		} else {
			System.out.println("MDZZ");
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	/*
	 * 可划动，设定边界
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		View view = getFocusedChild();
		if (isHideInput(view, event)) {
			view.clearFocus();
		}
		if (event.getPointerCount() == 1) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = event.getX();
				startY = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				float stopX = event.getX();
				float stopY = event.getY();
				Log.e("pos", startX + "," + startY);
				posX += stopX - startX;
				posY += stopY - startY;
				posX = posX > 0 ? 0 : posX;
				posY = posY > 0 ? 0 : posY;
				posX = posX < -2 * screenWidth ? -2 * screenWidth : posX;
				posY = posY < -2 * screenHeight ? -2 * screenHeight : posY;
				this.setX(posX);
				this.setY(posY);
				requestLayout();
				invalidate();// call onDraw()
				break;
			}
		}
		return true;
	}

	/**
	 * 绘图
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < editTexts.size(); i++) {
			DEditTextView view = editTexts.get(i);
			DEditTextView pa = view.getDad();
			myDraw(pa.getRight(), pa.getBottom(), view.getLeft(), view.getBottom(), canvas);
		}
	}

	private void myDraw(int x_start, int y_start, int x_end, int y_end, Canvas canvas) {
		Paint paint = new Paint();
		paint.setStrokeWidth(5);
		paint.setColor(Color.BLUE);
		int A = (y_end - y_start) / 2;
		int T = Math.abs(x_start - x_end) * 2;
		float w = (float) (Math.PI * 2 / T);
		float x_value;
		float y_value;
		float x_newvalue;
		float y_newvalue;
		for (int i = 0; i < T / 2; i++) {
			x_value = i + x_start;
			y_value = (float) (-A * Math.sin(w * i + Math.PI / 2) + A + y_start);
			x_newvalue = i + x_start + 1;
			int j = i + 1;
			y_newvalue = (float) (-A * Math.sin(w * j + Math.PI / 2) + A + y_start);
			canvas.drawLine(x_value, y_value, x_newvalue, y_newvalue, paint);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (int i = 0; i < getChildCount(); i++) {
			View v = getChildAt(i);
			if (v instanceof DEditTextView) {
				DEditTextView view = (DEditTextView) v;
				view.measure(0, 0);
				view.layout(view.getxPos(), view.getyPos(), view.getxPos() + view.getMeasuredWidth(),
						view.getyPos() + view.getMeasuredHeight());
			}
		}
		invalidate();
	}

}