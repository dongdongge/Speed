package soyouarehere.imwork.speed.app.base.adapter.recycle_view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.util.DensityUtil;


/**
 * RecycleView分割线
 * Created by Administrator on 2017/8/24 0024.
 */

public class RecycleDividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "SectionDecoration";

    private TextPaint textPaint;
    private Paint paint;
    private int topGap;
    private int alignBottom;
    private Paint.FontMetrics fontMetrics;
    private int deliverHeight;
    private Paint deliverPaint;

    public RecycleDividerItemDecoration(Context context) {
        Resources res = context.getResources();
        //设置悬浮栏的画笔---paint
        paint = new Paint();
        paint.setColor(res.getColor(R.color.color_gray_ripple));
        paint.setAntiAlias(true);

        //设置悬浮栏中文本的画笔
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(DensityUtil._dip2px(context, 14));
        textPaint.setColor(context.getResources().getColor(R.color.color_333333));
        textPaint.setTextAlign(Paint.Align.LEFT);
        fontMetrics = textPaint.getFontMetrics();

        deliverPaint = new Paint();
        deliverPaint.setAntiAlias(true);
        deliverPaint.setColor(res.getColor(R.color.color_gray_ripple));
        //决定悬浮栏的高度等
        topGap = res.getDimensionPixelOffset(R.dimen.view_distance_24);
        //决定文本的显示位置等
        alignBottom = res.getDimensionPixelSize(R.dimen.view_distance_8);
        //线的高度
        deliverHeight = res.getDimensionPixelOffset(R.dimen.view_distance_line);
    }

    /* 参数设置*/
    public void setDeliverHeight(int deliverHeight) {
        this.deliverHeight = deliverHeight;
    }

    public void setDeliverPaint(Paint paint) {
        this.deliverPaint = paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void setTextPaint(TextPaint paint) {
        this.textPaint = paint;
        fontMetrics = textPaint.getFontMetrics();
    }

    public void setAlignBottom(int alignBottom) {
        this.alignBottom = alignBottom;
    }

    public void setTopGap(int topGap) {
        this.topGap = topGap;
    }

    public void setPaintColor(int color) {
        deliverPaint.setColor(color);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, deliverHeight);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        drawDeliver(c, parent, left, right, childCount);
    }


    /**
     * 画线
     *
     * @param c
     * @param parent
     * @param left
     * @param right
     * @param childCount
     */
    private void drawDeliver(Canvas c, RecyclerView parent, int left, int right, int childCount) {
        if (deliverHeight != 0) {
            for (int i = 0; i < childCount - 1; i++) {
                View view = parent.getChildAt(i);
                float top = view.getBottom();
                float bottom = view.getBottom() + deliverHeight;
                c.drawRect(left, top, right, bottom, deliverPaint);
            }
        }
    }
}
