package soyouarehere.imwork.speed.view.sliding_card;

import android.support.annotation.NonNull;


import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.support.design.widget.CoordinatorLayout.Behavior;
@CoordinatorLayout.DefaultBehavior(SlidingCardBehavior.class)
public class SlidingCardBehavior extends Behavior<SlidingCardLayout> {


    /**
     * 负责掌管子控件的测量
     *
     * @param parent
     * @param child
     * @param parentWidthMeasureSpec
     * @param widthUsed
     * @param parentHeightMeasureSpec
     * @param heightUsed
     * @return
     */
    @Override
    public boolean onMeasureChild(CoordinatorLayout parent,
                                  SlidingCardLayout child,
                                  int parentWidthMeasureSpec,
                                  int widthUsed,
                                  int parentHeightMeasureSpec,
                                  int heightUsed) {
        // 当滑动到额时候不断地额进行测量 改变

        // 当前的控件的高度 = 父容器给的高度 - 上面和下面几个child的头部高度
        // 代表了上面和下面child的头部的高度
        int offset = getChildMeasureOffset(parent, child);
        // 父控件的高度  并不是MeasureSpec 进行转化
        int heightMeasureSpec = View.MeasureSpec.getSize(parentHeightMeasureSpec) - offset;

        // 每一个卡片的高度是变化的
        child.measure(parentHeightMeasureSpec, View.MeasureSpec.makeMeasureSpec(heightMeasureSpec, View.MeasureSpec.EXACTLY));
        return true;
    }

    int mInitialOffset;

    /**
     * 测量子控件后  进行摆放控件  ，给控件一个位置
     *
     * @param parent
     * @param child
     * @param layoutDirection
     * @return
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, SlidingCardLayout child, int layoutDirection) {
        // 对于当前的判断  只需要进行y轴的移动摆放即可 不需要child.layout();
        // 只考虑y轴的偏移

        // 先调用自己的layout  摆放parent所有的子控件 然后再去摆放自己的位置
        parent.onLayoutChild(child, layoutDirection);

        // 拿到上面的chilid  获取每一个child头部的高度累加
        SlidingCardLayout previous = getPreviousChild(parent, child);
        if (previous != null) {
            int offset = previous.getTop() + previous.getHeaderViewHeight();
            child.offsetTopAndBottom(offset);
        }
        // 初始高度
        mInitialOffset = child.getTop();
        return true;
    }

    /**
     * 拿到上面的chilid  获取每一个child头部的高度累加
     *
     * @param parent
     * @param child
     * @return
     */
    private SlidingCardLayout getPreviousChild(CoordinatorLayout parent, SlidingCardLayout child) {
        // 当前的下标
        int cardIndex = parent.indexOfChild(child);

        for (int i = cardIndex; i >= 0; i--) {
            View view = parent.getChildAt(i);
            if (view instanceof SlidingCardLayout) {
                return (SlidingCardLayout) view;
            }
        }
        return null;
    }

    /**
     * 计算偏移  上面和下面child的头部的高度
     *
     * @param parent 父布局
     * @param child  当前布局
     * @return
     */
    private int getChildMeasureOffset(CoordinatorLayout parent, SlidingCardLayout child) {

        // 累加
        int offset = 0;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (view != child && view instanceof SlidingCardLayout) {
                offset += ((SlidingCardLayout) view).getHeaderViewHeight();
            }

        }
        return offset;
    }

    /**
     * 判断滑动的方向
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param axes
     * @param type
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull SlidingCardLayout child,
                                       @NonNull View directTargetChild,
                                       @NonNull View target,
                                       int axes,
                                       int type) {
        // 判断滑动的方向
        // &运算 为 type 包含 SCROLL_AXIS_VERTICAL 的方向运动 如果包含返回true  否则返回false
        boolean isVertical = (type & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        // 滑动的是某一个的view 其他的不动；  判断当前的view；触发下面的方法 onNestedScroll
        return isVertical && child == target;
    }

    /**
     * 滑动之前调用的方法  可以有滑动的距离等 过度动画
     * @param parent
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed 默认是没有值的 [0] 代表x  [1] 代表y  经过后有值传到了下一步
     * @param type
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout parent,
                                  @NonNull SlidingCardLayout child,
                                  @NonNull View target,
                                  int dx,
                                  int dy,
                                  @NonNull int[] consumed,
                                  int type) {
        // 监听滑动的距离
        // 1. 控制自己移动
        consumed[1]= scroll(child,
                dy,
                mInitialOffset,
                mInitialOffset + child.getHeight() - child.getHeaderViewHeight());
        //控制上边和下边 child的移动
        shiftSliding(consumed[1],parent,child);
    }

    /**
     * 控制移动
     *
     * @param parent
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     * @param type
     */
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout parent,
                               @NonNull SlidingCardLayout child,
                               @NonNull View target,
                               int dxConsumed,
                               int dyConsumed,
                               int dxUnconsumed,
                               int dyUnconsumed, int type) {
        // 监听滑动的距离
        // 1. 控制自己移动
        int shift = scroll(child,
                dyUnconsumed,
                mInitialOffset,
                mInitialOffset + child.getHeight() - child.getHeaderViewHeight());
        //控制上边和下边 child的移动
        shiftSliding(shift,parent,child);
        // 2. 控制其他的移动

    }

    /**
     * 控制上边和下边 child的移动
     * @param shift
     * @param parent
     * @param child
     */
    private void shiftSliding(int shift, CoordinatorLayout parent, SlidingCardLayout child) {
        if (shift==0){
            return;
        }
        // 往上
        if (shift>0){
            SlidingCardLayout current = child;
            SlidingCardLayout cardLayout = getPreviousChild(parent,child);
            while (cardLayout!=null){
                // 当前的current 已经移动了一点 ，。 但是后面的card还没有移动 ，那么会有一个重叠的高度 就是该偏移量
                int offset = getHeaderOverlap(child,current);
                if (offset>0){
                    cardLayout.offsetTopAndBottom(-offset);
                    current = cardLayout;
                    cardLayout = getPreviousChild(parent,current);
                }
            }
        }else {// 往下  推动下面所有的卡片  找到下面所有的卡片

            SlidingCardLayout current = child;
            SlidingCardLayout cardLayout = getNextChild(parent,child);
            while (cardLayout!=null){
                // 当前的current 已经移动了一点 ，。 但是后面的card还没有移动 ，那么会有一个重叠的高度 就是该偏移量
                int offset = getHeaderOverlap(current,child);
                if (offset>0){
                    cardLayout.offsetTopAndBottom(offset);
                    current = cardLayout;
                    cardLayout = getNextChild(parent,current);
                }
            }
        }


    }

    /**
     * 计算重叠高度
     * @param above
     * @param below
     * @return
     */
    private int getHeaderOverlap(SlidingCardLayout above, SlidingCardLayout below) {
        return above.getTop() + above.getHeaderViewHeight() - below.getTop();
    }

    /**
     * 找到下一个卡片
     * @param parent
     * @param child
     * @return
     */
    private SlidingCardLayout getNextChild(CoordinatorLayout parent, SlidingCardLayout child) {
        int cardIndex = parent.indexOfChild(child);

        for (int i = cardIndex +1; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (view instanceof SlidingCardLayout) {
                return (SlidingCardLayout) view;
            }
        }


        return null;
    }

    /**
     * 控制自己移动  判断往上还是往下
     *
     * @param child
     * @param dy
     * @param minOffset
     * @param maxOffset
     * @return // 往上是正 往下是负;
     */
    private int scroll(SlidingCardLayout child, int dy, int minOffset, int maxOffset) {
        // 计算偏移量  获取当前距离顶部的距离
        int initialOffset = child.getTop();
        // 原来的高度 加上偏移的量 获取移动后的高度距离； dyUnconsumed因为是一个负的值但是 offset是一个正的值
        int offset = clamp(initialOffset - dy, minOffset, maxOffset) - initialOffset;
        // 控制dyUnconsumed的范围  范围是 dyUnconsumed:[min,max]; 最小值为初始的高度 已经记录下来了
        child.offsetTopAndBottom(offset);
        return -offset;
    }

    /**
     * 计算移动的范围
     *
     * @param i
     * @param minOffset
     * @param maxOffset
     * @return
     */
    private int clamp(int i, int minOffset, int maxOffset) {
        if (i > maxOffset) {
            return maxOffset;
        } else if (i < minOffset) {
            return minOffset;
        } else {
            return i;
        }
    }


}
