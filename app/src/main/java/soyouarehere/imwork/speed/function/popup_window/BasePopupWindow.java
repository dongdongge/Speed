package soyouarehere.imwork.speed.function.popup_window;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import soyouarehere.imwork.speed.R;

/**
 * popupWindow 基类 view 的控制和监听 交由子类实现
 */
public abstract class BasePopupWindow extends PopupWindow {

    private View conentView;
    FrameLayout frameLayout;
    View mainView;
    private Activity activity;

    public BasePopupWindow(Activity activity) {
        this.activity = activity;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        conentView = LayoutInflater.from(activity).inflate(R.layout.base_popup_window_layout, null);
        frameLayout = conentView.findViewById(R.id.base_popup_window_view);
        if (getLayoutId() != 0) {
            mainView = LayoutInflater.from(activity).inflate(getLayoutId(), frameLayout, false);
            childFindViewById(mainView);
            frameLayout.addView(mainView);
        }
        int h = activity.getWindowManager().getDefaultDisplay().getHeight();
        int w = activity.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w / 2 + 40);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，
        // 设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.base_pop_window_style);
    }

    /**
     * 提取子类的view
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 提取子类view
     *
     * @param view
     */
    public abstract void childFindViewById(View view);

    /**
     * 显示popupWindow
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            setWindowBackground(0.5f);
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 5);
        } else {
            dismiss();
        }
    }


    @Override
    public void dismiss() {
        super.dismiss();
        setWindowBackground(1f);
    }


    /**
     * 设置activity的背景透明度
     * @param bgAlpha 透明度值
     */
    private void setWindowBackground(float bgAlpha){
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        //0.0-1.0
        lp.alpha = bgAlpha;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);

    }

}
