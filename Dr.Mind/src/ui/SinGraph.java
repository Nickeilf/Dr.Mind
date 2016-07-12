package ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;

/**
 * @auther:Liu 
 * @date:2016.7.8
 * @description:绘制sin曲线部分，后接一段直线
 * 重写View类的方法
 */

public class SinGraph extends ViewGroup {

    private Paint paint;
    private int singleRec = 20;//每相邻两个文本垂直方向上的距离

    public SinGraph(Context context) {
        super(context);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.rgb(131, 175, 155));
        paint = new Paint();
        paint.setColor(Color.rgb(205, 243, 246));
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        mydraw(canvas,8,0,200);
    }

    private void mydraw(Canvas canvas,int sum,int x_start,int y_start) {
        int type = sum % 2;

        switch (type) {
            case 0:
                for (int i = 1; i <= sum; i++) {
                    float x_value = x_start;
                    float y_value = y_start;

                    for (int j = 0; j < 90; j++) {
                        x_value = x_value + 1;
                        float Ai;
                        if (i <= sum / 2) {
                            int weight = 0;
                            if (i == 1)
                                weight = 1;
                            if (i == 2)
                                weight = 2;
                            if (i == 3)
                                weight = 5;
                            Ai = singleRec * weight;
                            y_value = (float) (Ai * Math.sin(Math.PI * (j + 45) / 90) + y_start - Ai);
                        } else {
                            Ai = singleRec * (i - sum / 2);
                            y_value = (float) (-Ai * Math.sin(Math.PI * (j + 45) / 90) + y_start + Ai);
                        }
                        canvas.drawPoint(x_value, y_value, paint);
                    }
                }
                break;
            case 1:
                for (int i = 1; i <= sum; i++) {
                    float x_value = x_start;
                    float y_value = y_start;

                    for (int j = 0; j < 90; j++) {
                        x_value = x_value + 1;
                        float Ai;
                        if (i <= sum / 2) {
                            int weight = 0;
                            if (i == 1)
                                weight = 1;
                            if (i == 2)
                                weight = 2;
                            if (i == 3)
                                weight = 5;
                            Ai = singleRec * weight;
                            y_value = (float) (Ai * Math.sin(Math.PI * (j + 45) / 90) + y_start - Ai);
                        } else if (i == (sum + 1) / 2) {
                            y_value = y_start;
                        } else {
                            Ai = singleRec * (i - (sum + 1) / 2);
                            y_value = (float) (-Ai * Math.sin(Math.PI * (j + 45) / 90) + y_start + Ai);
                        }
                        canvas.drawPoint(x_value, y_value, paint);
                    }
                }
                break;
        }
    }

}
