package soyouarehere.imwork.speed.app.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import soyouarehere.imwork.speed.R;


/**
 * 弹窗基类
 */
public abstract class BaseDialog extends Dialog {
    /**
     * 控制点击dialog外部是否dismiss
     */
    private boolean iscancelable;
    /**
     * 控制返回键是否dismiss
     */
    private boolean isBackCancelable;
    private View view;
    private Context context;
    public OnBaseDialogListener onBaseDialogListener;

    public BaseDialog(@NonNull Context context, OnBaseDialogListener onBaseDialogListener) {
        super(context, R.style.BaseDialog);
        this.context = context;
        this.iscancelable = true;
        this.isBackCancelable = true;
        this.onBaseDialogListener = onBaseDialogListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setContentView(view);
        /**
         * 点击外部不可dismiss
         * */
        setCancelable(iscancelable);
        setCanceledOnTouchOutside(isBackCancelable);
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (getScreenSize(context)[0] * 0.8);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

    TextView title, leftButton, rightButton;
    FrameLayout frameLayout;
    View mainView;

    private void initView() {
        this.view = LayoutInflater.from(context).inflate(R.layout.base_dialog_layout,null);
        title = view.findViewById(R.id.base_dialog_layout_title);
        frameLayout = view.findViewById(R.id.base_dialog_layout_view);
        leftButton = view.findViewById(R.id.base_dialog_layout__cancel);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onBaseDialogListener != null) {
                    onBaseDialogListener.clickCancel("");
                }
            }
        });
        rightButton = view.findViewById(R.id.base_dialog_layout_sure);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onBaseDialogListener != null) {
                    onBaseDialogListener.clickSure(setResultObject());
                }

            }
        });
        if (getLayoutId() != 0) {
            mainView = LayoutInflater.from(context).inflate(getLayoutId(), frameLayout,false);
            // 提取子类view 并赋值
            childFindViewById(mainView);
            frameLayout.addView(mainView, 0);
        }
    }

    @Override
    public void dismiss() {
        if (this.isShowing()) {
            super.dismiss();
        }
    }

    @Override
    public void show() {
        if (!this.isShowing()) {
            super.show();
        }
    }

    /**
     * 提取子类布局
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


    public abstract Object setResultObject();

    /**
     * 子类中重写其方法 设置左侧显示文字
     *
     * @param text
     */
    public void setLeftButtonText(String text) {
        if (leftButton != null) {
            leftButton.setText(text);
        }
    }

    /**
     * 子类中重写其方法 设置右侧显示文字
     *
     * @param text
     */
    public void setRightButtonText(String text) {
        if (rightButton != null) {
            rightButton.setText(text);
        }
    }

    /**
     * 子类中重写其方法 设置提示标题
     *
     * @param text
     */
    public void setTitleText(String text) {
        title.setText(text);
    }


    /**
     * 设置是否显示标题
     *
     * @param booleanValue
     */
    public void setTitleVisibly(boolean booleanValue) {
        if (title != null) {
            title.setVisibility(booleanValue ? View.VISIBLE : View.GONE);
        }
    }

    @NonNull
    private static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }
}
