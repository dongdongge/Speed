package soyouarehere.imwork.speed.app.base.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * 弹窗公共类
 */
public class CommonDialog extends BaseDialog{

    public CommonDialog(@NonNull Context context, OnBaseDialogListener onBaseDialogListener) {
        super(context,onBaseDialogListener);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void childFindViewById(View view) {
        if (view==null){
            return;
        }
    }

    /**
     * 将收集的结果返回给调用者
     * @return
     */
    @Override
    public Object setResultObject() {
        return null;
    }


}
