package soyouarehere.imwork.speed.app.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import soyouarehere.imwork.speed.util.log.LogUtil;


/**
 * Created by li.xiaodong on 2018/7/2.
 * 圆形显示百分比
 */
public class PercentView extends View {
    private final static String TAG = PercentView.class.getSimpleName();
    Paint paint;
    Paint storkePaint;
    RectF oval;

    public PercentView(Context context) {
        super(context);
        init();
    }

    public PercentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PercentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        storkePaint = new Paint();
        paint.setAntiAlias(true);
        oval = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        LogUtil.e(TAG, widthMode, width, heightMode, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.e(TAG, "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        storkePaint.setColor(Color.RED);
        storkePaint.setStyle(Paint.Style.STROKE);
        int width = getWidth();
        int height = getHeight();
        float radius = width / 4;
        //定义底图圆的大小和边界  圆心的x坐标。 圆心的y坐标。  radius：圆的半径。paint：绘制时所使用的画笔。
        canvas.drawCircle(width / 2, width / 2, radius, paint);
        paint.setColor(Color.BLUE);
        //用于定义的圆弧的形状和大小的界限  代表了一个矩形的左上角和右下角坐标的值  //确定外切矩形范围
        oval.set(width / 2 - radius, width / 2 - radius, width / 2 + radius, width / 2 + radius);
        //根据进度画圆弧
        // oval 定义的圆弧的形状和大小的范围
        // startAngle 这个参数的作用是设置圆弧是从哪个角度来顺时针绘画的
        // sweepAngle 参数的作用是设置圆弧扫过的角度
        // useCenter  这个参数的作用是设置我们的圆弧在绘画的时候，是否经过圆形  值得注意的是，这个参数在我们的 mPaint.setStyle(Paint.Style.STROKE); 设置为描边属性的时候，是看不出效果的
        // paint  画笔
        canvas.drawArc(oval, 90, 45, true, paint);

        canvas.drawRect(oval,storkePaint);
    }
}
