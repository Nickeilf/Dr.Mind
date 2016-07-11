package ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;

/**
 * @auther:Liu 
 * @date:2016.7.8
 * @description:绘制sin曲线部分，后接一段直线
 * 重写View类的方法
 */

public class SinGraph extends View {

    private Paint paint;
    private int sum;
    private int singleRec = 20;//每相邻两个文本垂直方向上的距离
    float x_start = 50;//起始像素点
    float y_start = 200;

    public SinGraph(Context context) {
        super(context);
        this.sum = 0;
    }

    //sum表示总分支数
    public SinGraph(Context context, int sum) {
        super(context);
        this.sum = sum;
    }

//    @Override
//        protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.rgb(131, 175, 155));
        paint = new Paint();
        paint.setColor(Color.rgb(205, 243, 246));
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    public static int getDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    private void mydraw(Canvas canvas) {

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
                            Ai = singleRec * i;
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
                            Ai = singleRec * i;
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
