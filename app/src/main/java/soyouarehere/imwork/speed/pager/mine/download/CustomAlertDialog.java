package soyouarehere.imwork.speed.pager.mine.download;

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
import android.widget.TextView;

import soyouarehere.imwork.speed.R;

public class CustomAlertDialog extends Dialog {
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
    private String msg;
    private OnClickInterface onClickInterface;

    private String leftText;
    private String rightText;

    public CustomAlertDialog( Context context, boolean isCancelable, boolean isBackCancelable, String msg,OnClickInterface onClickInterface) {
        super(context, R.style.CustomDialog);
        this.context = context;
        this.iscancelable = isCancelable;
        this.isBackCancelable = isBackCancelable;
        this.msg = msg;
        this.onClickInterface = onClickInterface;
    }
    public CustomAlertDialog( Context context,String leftText,String rightText,String msg,OnClickInterface onClickInterface) {
        super(context, R.style.CustomDialog);
        this.context = context;
        this.iscancelable = true;
        this.isBackCancelable = true;
        this.msg = msg;
        this.onClickInterface = onClickInterface;
        this.leftText = leftText;
        this.rightText = rightText;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * //这行一定要写在前面
         * */
        initItemView();
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
    private void initItemView() {
        this.view = View.inflate(context, R.layout.custom_alert_dialog_layout, null);
        TextView msg = view.findViewById(R.id.tv_msg_custom_alert);
        msg.setText(this.msg);
        Button cancle = view.findViewById(R.id.bt_cancel_custom_alert);
        if (leftText==null&&!leftText.isEmpty()){
            cancle.setText(leftText);
        }
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCustomAlertDialog().isShowing()){
                    getCustomAlertDialog().dismiss();
                }
            }
        });
        Button sure = view.findViewById(R.id.bt_sure_custom_alert);
        if (rightText==null&&!rightText.isEmpty()){
            cancle.setText(rightText);
        }
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.clickSure();
                dismiss();
            }
        });

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public CustomAlertDialog getCustomAlertDialog(){
        if (this!=null){
            return this;
        }
        return null;
    }

    public interface OnClickInterface{
         void clickSure();
         void clickCancel();
    }

}
