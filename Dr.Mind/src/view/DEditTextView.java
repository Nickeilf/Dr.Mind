package view;

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
import android.widget.EditText;

import util.Constant;
import vo.Node;

public class DEditTextView extends EditText {
	private Node node;
	private int xPos;
	private int yPos;
	private Paint paint;
	private int level;

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

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("哇我被调用了");
		return super.onTouchEvent(event);
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
		this.setOnFocusChangeListener(new OnFocusChangeListener() {

			public void onFocusChange(View v, boolean hasFocus) {
				DEditTextView f = (DEditTextView) v;
				if (!hasFocus) {
					f.node.setTextValue(f.getText().toString());
				}
			}
		});
		this.addTextChangedListener(new MyTextWatcher(this));
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
		float height = this.getHeight() - paint.getStrokeWidth() / 2-1;
		canvas.drawLine(0, height, this.getWidth(), height, paint);
	}

	private void paint_width() {
		int width = 8 - level;
		if (width <= 0) {
			width = 1;
		}
		paint.setStrokeWidth(width);
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

	private class MyTextWatcher implements TextWatcher {
		DEditTextView v;

		public MyTextWatcher(DEditTextView v) {
			this.v = v;
		}

		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub

		}

		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		public void afterTextChanged(Editable s) {
		}

	}
}
