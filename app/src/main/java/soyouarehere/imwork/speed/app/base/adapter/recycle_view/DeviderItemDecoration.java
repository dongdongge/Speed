package soyouarehere.imwork.speed.app.base.adapter.recycle_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 分割线 使用listview中的资源文件;
 */
public class DeviderItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 默认的值
     */
    private int mOrientation = LinearLayoutManager.VERTICAL;
    private Drawable mDivider;
    private  int [] attrs = new int[]{android.R.attr.listDivider};


    /**
     * 初始化该类
     * @param context 传入上下文
     * @param orientation 传入布局的样式横向还是竖向
     */
    public DeviderItemDecoration(Context context, int orientation) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        mDivider = typedArray.getDrawable(0);
        setOrientation(orientation);
    }

    /**
     * 设置当前的布局为那种方式
     * @param orientation
     */
    private void setOrientation(int orientation) {
        if (orientation != LinearLayoutManager.HORIZONTAL&&orientation!=LinearLayoutManager.VERTICAL){
            throw new IllegalArgumentException("请设置Horizontal 或者vertical");
        }
        this.mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
