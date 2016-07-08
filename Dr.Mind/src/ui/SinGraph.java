package ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

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
		paint = new Paint();
		paint.setColor(Color.rgb(205, 243, 246));
		setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		mydraw(canvas);
	}

	private void mydraw(Canvas canvas) {
		int height = getHeight();
		int width = getWidth();
		float x=0;
		float y=0;
		for (int i = 0; i < width / 2; i++) {
			x = i;
			y = (float) (60 * (Math.sin((x+90) / 180.0 * Math.PI)) + height / 2);
//			System.out.println("x=" + x);
//			System.out.println("y=" + y);
			canvas.drawPoint(x, y, paint);
		}
		canvas.drawLine(x, y, width, y, paint);
	}

}
