package soyouarehere.imwork.speed.example.sliding_card;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import soyouarehere.imwork.speed.R;

/**
 * 自定义控件
 */
@CoordinatorLayout.DefaultBehavior(SlidingCardBehavior.class)
public class SlidingCardLayout extends FrameLayout {
    public SlidingCardLayout(@NonNull Context context) {
        this(context,null);
    }

    public SlidingCardLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingCardLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_wedgit_card_layout, this);
//        View.inflate(context,R.layout.view_wedgit_card_layout,this);
        RecyclerView recyclerView = findViewById(R.id.view_sliding_card_rcy);
        // 避免出现RecyclerView has no LayoutManager的错误
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TextView header = findViewById(R.id.view_sliding_card_head);
        SlidingCardAdapter adapter = new SlidingCardAdapter(recyclerView);
        recyclerView.setAdapter(adapter);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlidingCardLayout);
        header.setBackgroundColor(array.getColor(R.styleable.SlidingCardLayout_android_colorBackground, Color.GREEN));
        header.setText(array.getText(R.styleable.SlidingCardLayout_android_text));
        array.recycle();
    }

    int mHeaderViewHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            mHeaderViewHeight = findViewById(R.id.view_sliding_card_head).getMeasuredHeight();
        }
    }

    public int getHeaderHeight() {
        return mHeaderViewHeight;
    }
}
