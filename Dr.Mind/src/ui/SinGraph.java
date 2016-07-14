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


    //构造方法
    //@param:上下文，结点的权重列表,view放置的坐标
    public SinGraph(Context context, List<Integer> weightList, MyPoint point) {
        super(context);
        this.weightList = weightList;
        this.sum = weightList.size();
        this.start_point = point;

        paint = new Paint();
        paint.setColor(Color.rgb(205, 243, 246));
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        pointList = new ArrayList<MyPoint>(sum);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.rgb(131, 175, 155));
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

    //获取SinGragh的纵向高度
    public int getSinHeight() {
        int height = 0;
        for (int i = 0; i < weightList.size(); i++) {
            height += weightList.get(i);
        }
        height--;

        if(height==0){
            height=1;
        }

        height *= (Constant.getScreenWidth() / 5);
        return height;
    }

    public int getSinWidth() {
        return 180;
    }

    public List<MyPoint> getPointList() {
        int weightSum=0;
        for(int i=0;i<weightList.size();i++){
            weightSum+=weightList.get(i);
        }
        int type = sum % 2;
        int singleRec = Constant.getScreenWidth() /10;
        int x_start = 0;
        int y_start = this.getSinHeight() / 2;

        for (int i = 0; i <sum; i++) {
            float x_value = x_start;
            float y_value = y_start;
            float y_next_value=y_start;

            float Ai = 0;
            float weight = 0;
            weight+=weightList.get(i)/2.0;
            for(int k=0;k<i;k++){
                weight+=weightList.get(k);
            }
            if (i<sum/2) {
                weight=weightSum/(float)2-weight;
            }else{
                weight=weight-weightSum/(float)2;
            }
            Ai=weight*singleRec;

            for (int j = 0; j < 180; j++) {
                if (i < sum / 2) {
                    y_value = (float) (Ai * Math.sin(Math.PI * (j + 90) / 180) + y_start - Ai);
                } else {
                    y_value = (float) (-Ai * Math.sin(Math.PI * (j + 90) / 180) + y_start + Ai);
                }
                x_value = x_value + 1;
            }
            pointList.add(new MyPoint(x_value, y_value));
        }

        return pointList;
    }

    private void mydraw(Canvas canvas) {
        int weightSum=0;
        for(int i=0;i<weightList.size();i++){
            weightSum+=weightList.get(i);
        }
        int type = sum % 2;
        int singleRec = Constant.getScreenWidth() /10;
        int x_start = 0;
        int y_start = this.getSinHeight() / 2;


//        switch (type) {
//            case 0:
                for (int i = 0; i <sum; i++) {
                    float x_value = x_start;
                    float y_value = y_start;
                    float y_next_value=y_start;

                    float Ai = 0;
                    float weight = 0;
                    weight+=weightList.get(i)/2.0;
                    for(int k=0;k<i;k++){
                        weight+=weightList.get(k);
                    }
                    if (i<sum/2) {
                      weight=weightSum/(float)2-weight;
                    }else{
                        weight=weight-weightSum/(float)2;
                    }
                    Ai=weight*singleRec;

//                    if (i < sum / 2) {
//                        weight+=weightList.get(i)/2.0;
//                        for (int k = i+1; k < sum / 2; k++) {
//                            weight += weightList.get(k);
//                        }
//                        Ai = singleRec * weight;
//                    } else {
//                        weight+=weightList.get(i)/2.0;
//                        for (int k = sum / 2; k < i; k++) {
//                            weight += weightList.get(k);
//                        }
//                        Ai = singleRec * weight;
//                    }
                    System.out.println("singleRec=" + singleRec);
                    System.out.println("i=" + i);
                    System.out.println("weight=" + weight);
                    System.out.println("Ai=" + Ai);

                    for (int j = 0; j < 180; j++) {
                        if (i < sum / 2) {
                            y_value = (float) (Ai * Math.sin(Math.PI * (j + 90) / 180) + y_start - Ai);
                            y_next_value=(float) (Ai * Math.sin(Math.PI * (j +1+ 90) / 180) + y_start - Ai);
                        } else {
                            y_value = (float) (-Ai * Math.sin(Math.PI * (j + 90) / 180) + y_start + Ai);
                            y_next_value = (float) (-Ai * Math.sin(Math.PI * (j +1+ 90) / 180) + y_start + Ai);
                        }
                        canvas.drawLine(x_value,y_value,x_value+1,y_next_value,paint);
                        x_value = x_value + 1;
                    }
//                    pointList.add(new MyPoint(x_value, y_value));
                }
//                break;
//            case 1:
//                for (int i = 0; i < sum; i++) {
//                    float x_value = x_start;
//                    float y_value = y_start;
//                    float y_next_value=y_start;
//
//                    float Ai = 0;
//                    float weight = 0;
//                    weight+=weightList.get(i)/2.0;
//                    for(int k=0;k<i;k++){
//                        weight+=weightList.get(k);
//                    }
//                    if(i<sum/2) {
//                        weight=weightSum/(float)2-weight;
//                    }else{
//                        weight=weight-weightSum/(float)2;
//                    }
//                    Ai=weight*singleRec;
////                    float Ai = 0;
////                    float weight = 0;
////                    if (i < sum / 2) {
////                        for (int k = i; k < sum / 2; k++) {
////                            weight += weightList.get(k);
////                        }
////                        weight += weightList.get(sum / 2) / 2.0;
////                        Ai = singleRec * weight;
////                    } else {
////                        for (int k = sum / 2; k < i; k++) {
////                            weight += weightList.get(k);
////                        }
////                        weight -= weightList.get(sum / 2) / 2.0;
////                        Ai = singleRec * weight;
////                    }
//                    System.out.println("singleRec=" + singleRec);
//                    System.out.println("i=" + i);
//                    System.out.println("weight=" + weight);
//                    System.out.println("Ai=" + Ai);
//
//                    for (int j = 0; j < 180; j++) {
//                        if (i < sum / 2) {
//                            y_value = (float) (Ai * Math.sin(Math.PI * (j + 90) / 180) + y_start - Ai);
//                            y_next_value = (float) (Ai * Math.sin(Math.PI * (j +1+ 90) / 180) + y_start - Ai);
//                        } else if (i == (sum - 1) / 2) {
//                            y_value = y_start;
//                            y_next_value=y_start;
//                        } else {
//                            y_value = (float) (-Ai * Math.sin(Math.PI * (j + 90) / 180) + y_start + Ai);
//                            y_next_value = (float) (-Ai * Math.sin(Math.PI * (j +1+ 90) / 180) + y_start + Ai);
//                        }
//                        canvas.drawLine(x_value,y_value,x_value+1,y_next_value,paint);
//                        x_value = x_value + 1;
//                    }
//                    pointList.add(new MyPoint(x_value, y_value));
//                }
//                break;
//        }
    }
}