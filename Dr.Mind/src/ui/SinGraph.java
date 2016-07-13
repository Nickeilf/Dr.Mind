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
 * @description:绘制sin曲线部分
 * 重写View类的方法
 */

public class SinGraph extends View{

    private Paint paint;
    private int sum;
    private int singleRec = Constant.getSingleRec();//每相邻两个文本垂直方向上的距离
    private int x_start;
    private int y_start;
    private List<Integer> weightList;
    private List<MyPoint> pointList;


    //构造方法
    //@param:上下文，节点的权重列表,当前结点所在的x、y坐标
    public SinGraph(Context context,List<Integer> weightList,int x_stat,int y_start) {
        super(context);
        this.weightList=weightList;
        this.sum=weightList.size();
        this.x_start=x_stat;
        this.y_start=y_start;
        paint = new Paint();
        paint.setColor(Color.rgb(205, 243, 246));
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        pointList=new ArrayList<MyPoint>(sum);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.rgb(131, 175, 155));
        mydraw(canvas);
    }

    public void setWeightList(List<Integer> weightList){
        this.weightList=weightList;
        this.sum=weightList.size();
        invalidate();
    }

    public List getWeightList(){
        return weightList;
    }

    //获取SinGragh的纵向高度
    public int getSinHeight(){
        int height=0;
        for(int i=0;i<weightList.size();i++){
            int thisWeight=weightList.get(i);
            height+=thisWeight;
            if(thisWeight%2 == 0){
                 height++;
             }
        }
        height*=singleRec;
        return height;
    }

    public int getSinWeight(){
        return 200;
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
                    pointList.add(new MyPoint(x_value,y_value));
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
                    pointList.add(new MyPoint(x_value,y_value));
                }
                break;
        }
    }

}
