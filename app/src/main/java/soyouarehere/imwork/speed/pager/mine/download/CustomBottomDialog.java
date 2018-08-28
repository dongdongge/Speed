package soyouarehere.imwork.speed.pager.mine.download;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

import soyouarehere.imwork.speed.R;


/**
 * Created by li.xiaodong on 2018/7/27.
 */

public class CustomBottomDialog extends Dialog {
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
    private List<String> itemList;
    private OnclickItemListener onclickItemListener;
    private CustomBottomDialog(@NonNull Context context) {
        super(context);
    }

    private CustomBottomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    private CustomBottomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public CustomBottomDialog(Context context, List<String> itemList, boolean isCancelable, boolean isBackCancelable, OnclickItemListener onclickItemListener) {
        super(context, R.style.CustomDialog);
        this.context = context;
        this.iscancelable = isCancelable;
        this.isBackCancelable = isBackCancelable;
        this.itemList = itemList;
        this.onclickItemListener = onclickItemListener;
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
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height =itemList.size()<5 ? WindowManager.LayoutParams.WRAP_CONTENT:getScreenSize(context)[1]/3 ;
        window.setAttributes(params);
    }

    private void initItemView() {
        this.view = View.inflate(context, R.layout.custom_dialog_layout, null);
        TextView cancel = view.findViewById(R.id.tv_cancel_custom_dialog);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (getCustomDialog().isShowing()){
                   getCustomDialog().dismiss();
               }
            }
        });
        ListView listView = view.findViewById(R.id.lv_custom_dialog_view);
        listView.setAdapter(new CustomDialogAdapter(itemList));
    }

    public CustomBottomDialog getCustomDialog(){
        if (this!=null){
            return this;
        }
        return null;
    }


    @NonNull
    private static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }

    class CustomDialogAdapter extends BaseAdapter{
        List<String> itemString;

        public CustomDialogAdapter(List<String> itemString) {
            this.itemString = itemString;
        }

        @Override
        public int getCount() {
            return itemString.size();
        }

        @Override
        public Object getItem(int position) {
            return itemString.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view =  LayoutInflater.from(context).inflate(R.layout.custom_dialog_item_text_layout,parent,false);
//            View view = View.inflate(context,R.layout.custom_dialog_item_text_layout,null);
            TextView textView = view.findViewById(R.id.item_view_show);
            textView.setText(itemString.get(position));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclickItemListener.clickCallBack(position);
                    if (getCustomDialog().isShowing()){
                        getCustomDialog().dismiss();
                    }
                }
            });
            return view;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public interface OnclickItemListener {
        void clickCallBack(int position);
    }
}


