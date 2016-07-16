package ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Button;

/**
 * Created by liu on 2016/7/16.
 */
public class MyButton extends Button {
    public MyButton(Context context){super(context);};

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        int height=this.getHeight();
        int width=this.getWidth();
        int paint_width=6;
        paint.setStrokeWidth(6);
        paint.setColor(Color.BLUE);
        canvas.drawLine(0, height / 2, width, height / 2, paint);
        canvas.drawLine(width/2,0,width/2,height,paint);
    }
}
