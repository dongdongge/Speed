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

public class AdapterSelectPath extends RecyclerBaseAdapter<SelectFileBean>{
    OnclickSelectPath onclickSelectPath;
    protected AdapterSelectPath(@NonNull Context context, @NonNull List<SelectFileBean> mDataList,OnclickSelectPath onclickSelectPath) {
        super(context, mDataList);
        this.onclickSelectPath = onclickSelectPath;
    }

    @Override
    protected void bindDataForView(ViewHolder holder, SelectFileBean selectFileBean, int position) {
        TextView name = holder.getView(R.id.select_path_adapter_file_name);
        name.setText(selectFileBean.getFileName());
        TextView number = holder.getView(R.id.select_path_adapter_child_file_number);
        number.setText(selectFileBean.getChildFileNumber()!=null?selectFileBean.getChildFileNumber():"0");
        TextView time = holder.getView(R.id.select_path_adapter_update_time);
        time.setText(selectFileBean.getUpdateTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectFileBean.getChildFileNumber()!=null&&Integer.parseInt(selectFileBean.getChildFileNumber())>0){
                    onclickSelectPath.callBackItem(selectFileBean,position);
                }
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.select_path_adapter_item_layout,parent,false);
        return new ViewHolder(view);
    }


    public interface OnclickSelectPath{
        void callBackItem(SelectFileBean selectFileBean,int position);
    }

}
