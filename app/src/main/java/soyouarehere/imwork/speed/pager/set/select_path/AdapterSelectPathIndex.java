package soyouarehere.imwork.speed.pager.set.select_path;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.base.adapter.recycle_view.RecyclerBaseAdapter;
import soyouarehere.imwork.speed.app.base.adapter.recycle_view.ViewHolder;
import soyouarehere.imwork.speed.function.log.LogUtil;

public class AdapterSelectPathIndex extends RecyclerBaseAdapter<String>{

    OnClickItemIndex onClickItemIndex;
    protected AdapterSelectPathIndex(@NonNull Context context, @NonNull List<String> mDataList,OnClickItemIndex onClickItemIndex) {
        super(context, mDataList);
        this.onClickItemIndex= onClickItemIndex;
    }

    @Override
    protected void bindDataForView(ViewHolder holder, String s, int position) {
        TextView textView = holder.getView(R.id.select_path_index_text);
        String cotent = s;
        if (cotent.contains("/")){
            int index = cotent.lastIndexOf("/");
            cotent = cotent.substring(index+1,cotent.length());
            LogUtil.e("cotent",cotent,"index",index);
        }
        textView.setText(cotent);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemIndex.onCallBack(s,position);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.select_path_adapter_index_layout,parent,false);
        return new ViewHolder(view);
    }



    public interface OnClickItemIndex{
        void onCallBack(String s,int position);
    }

}
