package com.jiuzhong.eightdiagrams.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jiuzhong.eightdiagrams.utils.DensityUtil;

/**
 * Created ljy on 2019/4/3 11:05
 */
public class EightDiagramsView extends View {
    //    定义画笔
    Paint paint;

    float bigX;//大圆横坐标
    float bigY;//大圆纵坐标
    float bigR;//大圆半径

    float left = 200;//距离左侧距离
    float top;//距离右侧距离

    float whiteX;
    float whiteY;

    float blackX;
    float blackY;

    float r;

    float x;
    float y;

    float x1;
    float y1;


    int width;
    int height;

    int cen = 45;


    public EightDiagramsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        set(-90);
    }

    /**
     * 数据初始化
     * @param context
     */
    private void initData(Context context){
        width = DensityUtil.getScreenWidth(context);
        height = DensityUtil.getScreenHeight(context);
        bigX = width/2;
        bigY = height/2 - 200;
        bigR = width/2 - left;

        top = bigY - bigR;

        whiteX = bigX;
        whiteY = top + bigR/2;

        blackX = bigX;
        blackY = top + 3 * bigR/2;

        r = 180;

        x = left + bigR/2;
        y = top;

        x1 = left + bigR/2;
        y1 = top + bigR;
    }

    /**
     * 重写draw方法
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //实例化画笔对象
        paint = new Paint();
        //给画笔设置颜色
        paint.setColor(Color.BLACK);
        //设置画笔属性
        paint.setStyle(Paint.Style.FILL);//画笔属性是空心圆
        paint.setStrokeWidth(5);//设置画笔粗细
        paint.setAntiAlias(true);//取消锯齿

        setDraw(canvas);

    }

    private void setDraw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        RectF oval2 = new RectF(left, top, left + 2 * bigR, top + 2 * bigR);
        canvas.drawArc(oval2, r, 180, false, paint);
        paint.setStyle(Paint.Style.FILL);//画笔属性是实心圆
        paint.setColor(Color.BLACK);
        canvas.drawArc(oval2, r, -180, false, paint);

        paint.setStrokeWidth(1);//设置画笔粗细
        RectF oval = new RectF(x, y, x + bigR, y + bigR);
        canvas.drawArc(oval, r, 180, false, paint);
        paint.setStyle(Paint.Style.FILL);//画笔属性是空心圆
        paint.setColor(Color.WHITE);
        RectF oval1 = new RectF(x1, y1, x1 + bigR, y1 + bigR);
        canvas.drawArc(oval1, r, -180, false, paint);

        paint.setColor(Color.BLACK);
        /**
         * 画实心小白圆
         */
        paint.setStyle(Paint.Style.FILL);//画笔属性是实心圆
        paint.setColor(Color.WHITE);
        canvas.drawCircle(whiteX, whiteY, 30, paint);

        /**
         * 画实心小黑圆
         */
        paint.setStyle(Paint.Style.FILL);//画笔属性是实心圆
        paint.setColor(Color.BLACK);
        canvas.drawCircle(blackX, blackY, 30, paint);

        /**
         * 重新绘制大圆边框
         */
        paint.setStrokeWidth(8);//设置画笔粗细
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(bigX, bigY, bigR, paint);

//        paint.setStyle(Paint.Style.STROKE);//画笔属性是实心圆
//        canvas.drawCircle(bigX, bigY, bigR + 100, paint);


        //设置文字大小
        paint.setTextSize(120);
        paint.setStrokeWidth(5);//设置画笔粗细
        paint.setStyle(Paint.Style.FILL);//画笔属性是实心圆
        setText(canvas);

//        canvas.rotate(45, bigX, bigY);
        canvas.translate(100,100);
        canvas.rotate(45);
    }

    private void setText(Canvas canvas){
        String[] text = {"☲", "☱", "☰", "☳", "☵", "☴", "☷", "☶", };
//        paint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < 8; i ++){
            float x = (float) (bigX + (bigR + 80) * Math.cos((45 * i + cen) * Math.PI/180));
            float y = (float) (bigY + (bigR + 80) * Math.sin((45 * i + cen) * Math.PI/180));

//            RectF rectF = new RectF(x -50, y - 50, x + 50, y + 50);
//            canvas.drawRect(rectF, paint);
//            canvas.rotate(45);

            // 文字宽
            float textWidth = paint.measureText(text[i]);
//            canvas.drawText(text[i], x - textWidth/2, y + textWidth/3, paint);
            drawText(canvas, text[i], x - textWidth/2, y + textWidth/3, paint, 0);
        }
    }

    void drawText(Canvas canvas ,String text , float x ,float y,Paint paint ,float angle){
        if(angle != 0){
            canvas.rotate(angle, x, y);
        }
        canvas.drawText(text, x, y, paint);
        if(angle != 0){
            canvas.rotate(-angle, x, y);
        }
    }
    /**
     * 移动动画
     * @param i 移动的角度
     */
    public void set(int i) {
        /**
         * 小白圆圈移动
         */
        whiteX = (float) (bigX + bigR/2 * Math.cos(i * 3.14 / 180));
        whiteY = (float) (bigY + bigR/2 * Math.sin(i * 3.14 / 180));

        /**
         * 小黑圆圈移动
         */
        blackX = (float) (bigX + bigR/2 * Math.cos((i + 180) * 3.14 / 180));
        blackY = (float) (bigY + bigR/2 * Math.sin((i + 180) * 3.14 / 180));

        /**
         * 黑半圆移动
         */
        x = (float) (left + bigR/2 + bigR/2 * Math.sin((90 + i) * 3.14 / 180));
        y = (float) (top + bigR/2 * (1 - Math.cos((90 + i) * 3.14 / 180)));

        /**
         * 白半圆移动
         */
        x1 = (float) (left + bigR/2 - bigR/2 * Math.sin((90 + i) * 3.14 / 180));
        y1 = (float) (top + bigR - bigR/2 * (1 - Math.cos((90 + i) * 3.14 / 180)));

        r = i + 180;

        cen = i+ 90;
        invalidate();
    }
}
