package cn.edu.nju.drmind.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import cn.edu.nju.drmind.vo.Node;

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
	private boolean editing;
	private boolean down;
	private boolean drawable;
	private boolean folded;
	private boolean visible;
	

	boolean init = true;

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
		editing = false;
		down = false;
		drawable=true;
		visible=true;
		this.setTextSize(10);
	}

	public void setNode(Node node) {
		this.node = node;
		this.level = node.getLevel();
		paint = new Paint();
		this.getBackground().setAlpha(0);
		paint_width();
		paint_color();
		paint.setStyle(Paint.Style.STROKE);
		if (level == 0) {
			paint.setAlpha(100);
		}
		invalidate();
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		if (!focused) {
			this.clearFocusing();
			DViewGroup pa = (DViewGroup) getParent();
			pa.textMove(this);
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint_color();
		paint_width();
		float height = this.getHeight() - 5;
		if (level == 0) {
			if (focusing) {
				paint.setAlpha(200);
			} else {
				paint.setAlpha(100);
			}
			canvas.drawRoundRect(new RectF(paint.getStrokeWidth(), paint.getStrokeWidth(),
					this.getWidth() - paint.getStrokeWidth(), height - paint.getStrokeWidth()), 10, 10, paint);
		} else {
			if (focusing) {
				paint.setColor(Color.rgb(77, 123, 150));
				paint.setStrokeWidth(6);
				if (down) {
					paint.setAlpha(100);
				} else {
					paint.setAlpha(200);
				}
				canvas.drawRoundRect(
						new RectF(paint.getStrokeWidth(), paint.getStrokeWidth(),
								this.getWidth() - paint.getStrokeWidth(), height - paint.getStrokeWidth() - 5),
						10, 10, paint);
				paint.setAlpha(255);
				paint_color();
				paint_width();
			}
			canvas.drawLine(0, height, this.getWidth(), height, paint);
		}

	}

	public void clearFocusing() {
		this.setInputType(InputType.TYPE_NULL);
		focusing = false;
		down = false;
		editing = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 设置3个状态，无焦点，焦点和文本编辑状态，需要重绘处理
		if (event.getPointerCount() == 1) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				this.requestFocus();
				startX = event.getX();
				startY = event.getY();
				raw_x = xPos;
				raw_y = yPos;
				down = true;
				focusing = true;
				invalidate();
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
//				float stop_y = event.getY();
				down = false;
				invalidate();
				if (Math.abs(yPos - raw_y) > 10 || Math.abs(xPos - raw_x) > 10) {
					if (editing == false) {
						editing = true;
						this.requestFocus();
					}
				} else {
					if (editing == false) {
						editing = true;
					} else {
						this.setCursorVisible(true);
						this.setInputType(InputType.TYPE_CLASS_TEXT);
					}
				}

				if (moving) {
					DViewGroup parent = (DViewGroup) getParent();
					parent.checkMove(this, yPos + event.getY(), xPos + event.getX());
					moving = false;
				}
				raw_x = xPos;
				raw_y = yPos;
				break;
			}
		}
		return super.onTouchEvent(event);
	}

	public void levelChanged() {
		paint_color();
		paint_width();
	}

	private void paint_color() {
		int index = level % 7;
		switch (index) {
		case 0:
			paint.setColor(Color.rgb(212, 44, 103));
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

	public void setRaw_width(int raw_width) {
		this.raw_width = raw_width;
	}

	public int getRaw_width() {
		return raw_width;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public boolean isDrawable() {
		return drawable;
	}

	public void setDrawable(boolean drawable) {
		this.drawable = drawable;
	}
	
	public boolean isFolded() {
		return folded;
	}

	public void setFolded(boolean folded) {
		this.folded = folded;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
