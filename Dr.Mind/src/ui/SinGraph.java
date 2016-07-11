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
 * @description:绘制sin曲线部分，后接一段直线
 * 重写View类的方法
 */

public class SinGraph extends View {

	private Paint paint;
	private int sum;

	public SinGraph(Context context) {
		super(context);
		this.sum=0;
	}

	//sum表示总分支数
	public SinGraph(Context context,int sum){
		super(context);
		this.sum=sum;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.rgb(131,175,155));
		paint = new Paint();
		paint.setColor(Color.rgb(205, 243, 246));
		setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		mydraw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec),
				getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec));
	}

	public static int getDefaultSize(int size,int measureSpec){
		int result=size;
		int specMode=MeasureSpec.getMode(measureSpec);
		int specSize=MeasureSpec.getSize(measureSpec);

		switch (specMode){
			case MeasureSpec.UNSPECIFIED:
				result=size;
				break;
			case MeasureSpec.AT_MOST:
			case MeasureSpec.EXACTLY:
				result=specSize;
				break;
		}
		return  result;
	}

	private void mydraw(Canvas canvas) {

		int type=sum%2;
		double T=10*Math.PI;//二分之一周期,弧度制
		float x_start=100;//起始像素点
		float y_start=100;

		switch (type){
			case 0:
				for(int i=1;i<=sum;i++){
					    float x_value;
						float y_value;

					    //y=Asin(wx+b)+c中的A
					    double theta=Math.PI/2*i/(sum/2+1);
						float Ai = (float) (T/2*Math.tan(theta));
					    System.out.println("Ai="+Ai);

						for (int j = 0; j < 180; j++) {
							x_value=x_start+j;
							double w=Math.PI/T;
							y_value = (float) (Ai * Math.sin(w*Math.PI*j/180) + y_start);
							canvas.drawPoint(x_value, y_value, paint);
						}
				}
				break;
			case 1:
			default:break;
		}


//		int height = getHeight();
//		int width = getWidth();
//		float x = 0;
//		float y1 = 0;
//		float y2 = 0;
//		float y3 = 0;
//		float y4 = 0;
//		for (int i = 0; i < 180; i++) {
//			x = i;
//			double sinValue = Math.sin((x + 90) / 180.0 * Math.PI);
//			y1 = (float) (60 * sinValue + height / 2);
//			y2 = (float) (40 * sinValue + height / 2);
//			y3 = (float) (20 * sinValue + height / 2);
//			y4 = (float) (0 * sinValue + height / 2);
//			canvas.drawPoint(x, y1, paint);
//			y2 += 20;
//			y3 += 40;
//			y4 += 60;
//			canvas.drawPoint(x, y2, paint);
//			canvas.drawPoint(x, y3, paint);
//			canvas.drawPoint(x, y4, paint);
//		}
//		canvas.drawLine(x, y1, width, y1, paint);
//		canvas.drawLine(x, y2, width, y2, paint);
//		canvas.drawLine(x, y3, width, y3, paint);
//		canvas.drawLine(x, y4, width, y4, paint);
	}
 
}
