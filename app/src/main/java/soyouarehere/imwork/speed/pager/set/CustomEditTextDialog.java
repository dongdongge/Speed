package soyouarehere.imwork.speed.pager.set;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import soyouarehere.imwork.speed.R;

public class CustomEditTextDialog extends Dialog {

    private String title;
    private String leftText;
    private String rightText;

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
    private OnClickDialogListener onClickDialogListener;

    public CustomEditTextDialog(@NonNull Context context) {
        super(context);
    }

    public CustomEditTextDialog(Context context, OnClickDialogListener onClickDialogListener) {
        super(context, R.style.CustomDialog);
        this.context = context;
        this.iscancelable = true;
        this.isBackCancelable = true;
        this.onClickDialogListener = onClickDialogListener;
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
        params.width = (int) (getScreenSize(context)[0]*0.8);
        params.height =WindowManager.LayoutParams.WRAP_CONTENT ;
        window.setAttributes(params);
    }
    @NonNull
    private static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }

    private void initView() {
        this.view = View.inflate(context, R.layout.custom_edit_text_dialog_layout, null);
        EditText custom_edit_content = view.findViewById(R.id.custom_edit_content);
        Button cancle = view.findViewById(R.id.custom_edit_cancel);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCustomEditTextDialog().isShowing()) {
                    getCustomEditTextDialog().dismiss();
                }
            }
        });
        Button sure = view.findViewById(R.id.custom_edit_sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = custom_edit_content.getText().toString().trim();
                if (getCustomEditTextDialog().isShowing()){
                    getCustomEditTextDialog().dismiss();
                }
                onClickDialogListener.clickSure(content);
            }
        });
    }

    public CustomEditTextDialog getCustomEditTextDialog() {
        if (this != null) {
            return this;
        }
        return null;
    }

    public interface OnClickDialogListener {
        void clickSure(String content);

        void clickCancle();
    }


}
