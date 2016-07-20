package view;

import activity.MindActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import util.Constant;
import vo.Node;

public class DEditTextView extends EditText {
	private DEditTextView dad;
	private DEditTextView littleSon;

	private Node node;
	private int xPos;
	private int yPos;
	private Paint paint;
	private int level;
	private float startX;
	private float startY;

	private boolean moving;

	public DEditTextView getLittleSon() {
		return littleSon;
	}

	public void setLittleSon(DEditTextView littleSon) {
		this.littleSon = littleSon;
	}

	public DEditTextView getDad() {
		return dad;
	}

	public void setDad(DEditTextView dad) {
		this.dad = dad;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public DEditTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public DEditTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DEditTextView(Context context) {
		super(context);
		init();
	}

	public Node getNode() {
		return node;
	}

	private void init() {
	}

	public void setNode(Node node) {
		this.node = node;
		this.level = node.getLevel();
		paint = new Paint();
		if (level > 0) {
			this.getBackground().setAlpha(0);
			paint_width();
			paint_color();
		}
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		float height = this.getHeight() - paint.getStrokeWidth() / 2 - 1;
		if (level != 0)
			canvas.drawLine(0, height, this.getWidth(), height, paint);
	}

	private void paint_width() {
		int width = 8 - level;
		if (width <= 0) {
			width = 1;
		}
		paint.setStrokeWidth(width);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 设置3个状态，无焦点，焦点和文本编辑状态，需要重绘处理
		if (event.getPointerCount() == 1) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = event.getX();
				startY = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				moving = true;
				float stopX = event.getX();
				float stopY = event.getY();
				xPos += stopX - startX;
				yPos += stopY - startY;
				this.setxPos(xPos);
				this.setyPos(yPos);
				this.measure(0, 0);
				this.layout(this.getxPos(), this.getyPos(), this.getxPos() + this.getMeasuredWidth(),
						this.getyPos() + this.getMeasuredHeight());
				DViewGroup group = (DViewGroup) getParent();
				group.move(this, stopX - startX, stopY - startY);
				break;
			}
		}
		return super.onTouchEvent(event);
	}

	private void paint_color() {
		int index = level % 7;
		switch (index) {
		case 0:
			paint.setColor(Color.rgb(3, 22, 52));
			break;
		case 1:
			paint.setColor(Color.rgb(131, 175, 155));
			break;
		case 2:
			paint.setColor(Color.rgb(118, 77, 57));
			break;
		case 3:
			paint.setColor(Color.rgb(248, 147, 29));
			break;
		case 4:
			paint.setColor(Color.rgb(56, 13, 49));
			break;
		case 5:
			paint.setColor(Color.rgb(107, 194, 53));
			break;
		case 6:
			paint.setColor(Color.rgb(137, 157, 192));
			break;
		}
	}
}
