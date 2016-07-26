package view;

import vo.Node;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class DEditTextView extends EditText {
	private DEditTextView dad;
	private DEditTextView littleSon;

	private Node node;
	private int xPos;
	private int yPos;
	private int raw_x;
	private int raw_y;
	private int raw_width;
	
	private Paint paint;
	private int level;
	private float startX;
	private float startY;

	private boolean moving;
	private boolean focusing;

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
		this.setInputType(InputType.TYPE_NULL);
		focusing = false;
		this.getMeasuredWidth();
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

	public void clearFocusing() {
		this.setInputType(InputType.TYPE_NULL);
		focusing = false;
	}

	@Override
	protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
		String i =this.getText().toString();
		super.onTextChanged(text, start, lengthBefore, lengthAfter);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 设置3个状态，无焦点，焦点和文本编辑状态，需要重绘处理
		if (event.getPointerCount() == 1) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = event.getX();
				startY = event.getY();
				raw_x = xPos;
				raw_y = yPos;
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
			case MotionEvent.ACTION_UP:
				float stop_y = event.getY();
				if (Math.abs(yPos - raw_y) > 10 || Math.abs(xPos - raw_x) > 10) {
					if (focusing == false)
						focusing = true;
				} else {
					if (focusing == false)
						focusing = true;
					else {
						this.setCursorVisible(true);
						this.setInputType(InputType.TYPE_CLASS_TEXT);
					}
				}

				if (moving) {
					DViewGroup parent = (DViewGroup) getParent();
					parent.checkMove(this, stop_y);
					moving = false;
				}
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

	private void paint_width() {
		int width = 8 - level;
		if (width <= 0) {
			width = 1;
		}
		paint.setStrokeWidth(width);
	}

	public int getRaw_x() {
		return raw_x;
	}

	public void setRaw_x(int raw_x) {
		this.raw_x = raw_x;
	}

	public int getRaw_y() {
		return raw_y;
	}

	public void setRaw_y(int raw_y) {
		this.raw_y = raw_y;
	}
}
