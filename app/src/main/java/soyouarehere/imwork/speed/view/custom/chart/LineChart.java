package soyouarehere.imwork.speed.view.custom.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 折线图
 */
public class LineChart extends View {

    private int viewSize;//获取空间的尺寸，也就是我们布局的尺寸大小（不知道理解的是否正确）
    private Paint linePaint;// 线条画笔和点画笔

    private Path mPath;// 路径对象
    private TextPaint mTextPaint;// 文字画笔

    float lift;
    float top;
    float right;
    float buttom;
    // X轴坐标最大值
    private int maxX;
    // Y轴坐标最大值
    private int maxY;
    public LineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        intPathAndPaint();
    }

    /**
     * 初始化画笔;
     */
    private void intPathAndPaint() {
        //第一步，初始化对象
        linePaint = new Paint();
        linePaint.setColor(Color.BLUE);//线条的颜色
        linePaint.setStrokeWidth(8);//线条的宽度
        linePaint.setAntiAlias(true);//取消锯齿
        linePaint.setStyle(Paint.Style.STROKE);//粗线


        //初始化Path
        mPath = new Path();

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTextPaint.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //第二步骤，我们在这里获取每个用到的坐标点和尺寸

        viewSize = w;//获取空间的尺寸，
        Log.i("Text", "viewSize:" + viewSize);

        //这个是我们上下左右需要用到的坐标点
        lift = viewSize * (1 / 16f);
        top = viewSize * (1 / 16f);
        right = viewSize * (15 / 16f);
        buttom = viewSize * (8 / 16f);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //定义一个绘制X,Y轴的方法
        drawXY(canvas);

        //绘制X和Y轴上的提示文字
        drawXYelement(canvas);
    }

    private float spaceX, spaceY;// 刻度间隔
    /*
     * 绘制X和Y轴对应的文字
     * */
    int[] index_x = {0, 1, 2, 3, 4, 5, 6, 7};
    int[] index_y = {0, 1, 2, 3, 4, 5, 6, 7};

    /**
     * 画网格
     *
     * @param canvas
     */
    private void drawTable(Canvas canvas) {
        canvas.save();
        int num = 0;//用于给X轴赋值
        int num_y = 0;//用于给Y轴赋值

        for (float y = buttom - spaceY; y > top; y -= spaceY) {
            for (float x = lift; x < right; x += spaceX) {
                mTextPaint.setTextSize(28);

                /*
                 * 绘制横轴刻度数值
                 */
                if (y == buttom - spaceY) {
                    canvas.drawText("" + index_x[num], x - 12, buttom + (top / 3), mTextPaint);
                    Log.i("Text", "num-" + num);
                }

                /*
                 * 绘制纵轴刻度数值
                 * 简单来说就是，Y轴上的坐标点，X轴是恒定不变的，但是Y轴是变化的（buttom - 间距）+10的距离向上绘制
                 */
                if (x == lift) {
                    canvas.drawText("" + index_y[num_y], lift - (lift / 2), y + 10, mTextPaint);

                    Log.i("Text", "lift:" + lift);
                    Log.i("Text", "lift - (1/16):" + (lift - (lift / 2)));

                }

                num++;
            }
            num_y++;
        }
        canvas.restore();
    }

    private void drawXY(Canvas canvas) {
        canvas.save();
        /*
         * 第三步，我们来通过viewSize尺寸来获取三个坐标点
         * 第一个（X,Y）--(lift,top)
         * 第二个（X,Y）--(lift,button)
         * 第三个个（X,Y）--(right,buttom)
         * */
        mPath.moveTo(lift, top);
        mPath.lineTo(lift, buttom);
        mPath.lineTo(right, buttom);

        //使用Path链接这三个坐标
        canvas.drawPath(mPath, linePaint);
        // 添加 画网格的方法;
        drawLines(canvas);
        // 释放画布
        canvas.restore();
    }

    private List<PointF> pointFs = new ArrayList<>();// 数据列表

    private void drawLines(Canvas canvas) {
        //重置线条画笔.因为都是细线,
        linePaint.setStrokeWidth(2);
        // 设置数据
        int count = pointFs.size();

        //计算横纵坐标刻度间隔
        spaceY = (buttom - top) / count;
        spaceX = (right - lift) / count;
        // 将除数的值长度减1 ,n条数据  n-1条线
        int divesor = count-1;
        // 计算横轴数据的最大值
        maxX  =0;
        Collections.max(pointFs,new ComparablePointY());


    }

    private void drawXYelement(Canvas canvas) {
        // 锁定画布
        canvas.save();
        mTextPaint.setTextSize(36);//文字大小

        /*
         * Y轴文字提示
         * drawText(String ,x,y,TextPaint)
         * (lift,top)
         * */
        mTextPaint.setTextAlign(Paint.Align.LEFT);//左对齐
        canvas.drawText("Y", lift + 20, top, mTextPaint);


        /*
         * X轴文字提示
         * drawText(String ,right,buttom,TextPaint)
         * */
        mTextPaint.setTextAlign(Paint.Align.RIGHT);//右对齐
        canvas.drawText("X", right, buttom + 50, mTextPaint);
        // 释放画布
        canvas.restore();
    }

    class ComparablePointY implements Comparator<PointF> {

        @Override
        public int compare(PointF o1, PointF o2) {
            return (int) (o1.y-o2.y);
        }
    }

}
