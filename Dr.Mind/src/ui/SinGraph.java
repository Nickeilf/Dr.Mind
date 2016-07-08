package ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

/*
 * @auther:Liu 
 * @date:2016.7.8
 * @description:画出连接节点的曲线：sin为主体，后接一段直线
 */

public class SinGraph extends View {

	private Paint paint;

	public SinGraph(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.rgb(131,175,155));
		paint = new Paint();
		paint.setColor(Color.rgb(205, 243, 246));
		setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		mydraw(canvas);
		layTextView(canvas);
	}

	private void mydraw(Canvas canvas) {
		int height = getHeight();
		int width = getWidth();
		float x = 0;
		float y1 = 0;
		float y2 = 0;
		float y3 = 0;
		float y4 = 0;
		for (int i = 0; i < 180; i++) {
			x = i;
			double sinValue = Math.sin((x + 90) / 180.0 * Math.PI);
			y1 = (float) (60 * sinValue + height / 2);
			y2 = (float) (40 * sinValue + height / 2);
			y3 = (float) (20 * sinValue + height / 2);
			y4 = (float) (0 * sinValue + height / 2);
			canvas.drawPoint(x, y1, paint);
			y2 += 20;
			y3 += 40;
			y4 += 60;
			canvas.drawPoint(x, y2, paint);
			canvas.drawPoint(x, y3, paint);
			canvas.drawPoint(x, y4, paint);
		}
		canvas.drawLine(x, y1, width, y1, paint);
		canvas.drawLine(x, y2, width, y2, paint);
		canvas.drawLine(x, y3, width, y3, paint);
		canvas.drawLine(x, y4, width, y4, paint);
	}

	private void layTextView(Canvas canvas){
		TextView textView=new TextView(getContext());
		textView.setX( 10);
		textView.setY(10);
		
	}
}
