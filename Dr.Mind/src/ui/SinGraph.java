package ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import util.Constant;

/**
 * @auther:Liu
 * @date:2016.7.8
 * @description:绘制sin曲线部分 重写View类的方法
 */

public class SinGraph extends View {

	private Paint paint;
	private int sum;
	private List<Integer> weightList;
	private List<MyPoint> pointList;
	private MyPoint start_point;
	private int level;

	// 构造方法
	// @param:上下文，结点的权重列表,view放置的坐标,结点层级
	public SinGraph(Context context, List<Integer> weightList, MyPoint point,int level) {
		super(context);
		this.weightList = weightList;
		this.sum = weightList.size();
		this.start_point = point;
		this.level=level;

		paint = new Paint();
		paint_color();
		paint_width();
		paint.setAntiAlias(true);
		pointList = new ArrayList<MyPoint>(sum);
	}

	private void paint_width(){
        int width=8-level;
		if(width<=0){
			width=1;
		}
		paint.setStrokeWidth(width);
	}

	private void paint_color() {
		int index=level&7;
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

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// canvas.drawColor(Color.rgb(131, 175, 155));
		mydraw(canvas);
	}

	public void setWeightList(List<Integer> weightList) {
		this.weightList = weightList;
		this.sum = weightList.size();
		invalidate();
	}

	public List getWeightList() {
		return weightList;
	}

	// 获取SinGragh的纵向高度
	public int getSinHeight() {
		int height = 0;
		for (int i = 0; i < weightList.size(); i++) {
			height += weightList.get(i);
		}
		height--;

		if (height == 0) {
			height = 1;
		}

		height *= (Constant.getScreenWidth() / 5);
		return (int)(height+10+paint.getStrokeWidth());
	}

	public int getSinWidth() {
		return 180;
	}

	public List<MyPoint> getPointList() {
		int weightSum = 0;
		for (int i = 0; i < weightList.size(); i++) {
			weightSum += weightList.get(i);
		}
		int type = sum % 2;
		int singleRec = Constant.getScreenWidth() / 10;
		int x_start = 0;
		int y_start = this.getSinHeight() / 2;

		for (int i = 0; i < sum; i++) {
			float x_value = x_start;
			float y_value = y_start;
			float y_next_value = y_start;

			float Ai = 0;
			float weight = 0;
			weight += weightList.get(i) / 2.0;
			for (int k = 0; k < i; k++) {
				weight += weightList.get(k);
			}
			if (i < sum / 2) {
				weight = weightSum / (float) 2 - weight;
			} else {
				weight = weight - weightSum / (float) 2;
			}
			Ai = weight * singleRec;

			for (int j = 0; j < 180; j++) {
				if (i < sum / 2) {
					y_value = (float) (Ai * Math.sin(Math.PI * (j + 90) / 180) + y_start - Ai);
				} else {
					y_value = (float) (-Ai * Math.sin(Math.PI * (j + 90) / 180) + y_start + Ai);
				}
				x_value = x_value + 1;
			}
			pointList.add(new MyPoint(x_value, y_value+5));
		}

		return pointList;
	}

	private void mydraw(Canvas canvas) {
		int weightSum = 0;
		for (int i = 0; i < weightList.size(); i++) {
			weightSum += weightList.get(i);
		}
		int singleRec = Constant.getScreenWidth() / 10;
		int x_start = 0;
		int y_start = this.getSinHeight() / 2;

		for (int i = 0; i < sum; i++) {
			float x_value = x_start;
			float y_value ;
			float y_next_value;

			float Ai = 0;
			float weight = 0;
			weight += weightList.get(i) / 2.0;
			for (int k = 0; k < i; k++) {
				weight += weightList.get(k);
			}
			if (i < sum / 2) {
				weight = weightSum / (float) 2 - weight;
			} else {
				weight = weight - weightSum / (float) 2;
			}
			Ai = weight * singleRec;

			for (int j = 0; j < 180; j++) {
				if (i < sum / 2) {
					y_value = (float) (Ai * Math.sin(Math.PI * (j + 90) / 180) + y_start - Ai)+5;
					y_next_value = (float) (Ai * Math.sin(Math.PI * (j + 1 + 90) / 180) + y_start - Ai)+5;
				} else {
					y_value = (float) (-Ai * Math.sin(Math.PI * (j + 90) / 180) + y_start + Ai)+5;
					y_next_value = (float) (-Ai * Math.sin(Math.PI * (j + 1 + 90) / 180) + y_start + Ai)+5;
				}
				canvas.drawLine(x_value, y_value, x_value + 1, y_next_value, paint);
//				canvas.drawPoint(x_value,y_value,paint);
				x_value = x_value + 1;
			}
		}

	}
}