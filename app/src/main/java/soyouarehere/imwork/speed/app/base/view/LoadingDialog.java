package soyouarehere.imwork.speed.app.base.view;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import soyouarehere.imwork.speed.R;


/**
 * 网络加载框
 */

public class LoadingDialog extends Dialog {

    private TextView tvMessage;

    public LoadingDialog(Context context) {
        this(context, R.style.LoadingDialogLight);
        init();
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    private void init() {
        setContentView(R.layout.base_layout_loading_dialog);
        getWindow().setGravity(Gravity.CENTER);
        // 设置当返回键按下是否关闭对话框
        setCancelable(true);
        // 设置当点击对话框以外区域是否关闭对话框
        setCanceledOnTouchOutside(false);
        tvMessage = this.findViewById(R.id.tv_message);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void setTvMessage(String message) {
        tvMessage.setText(message);
    }

}
