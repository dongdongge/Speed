package soyouarehere.imwork.speed.pager.mine.download.complete;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.base.adapter.recycle_view.RecyclerBaseAdapter;
import soyouarehere.imwork.speed.app.base.adapter.recycle_view.ViewHolder;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;

public class CompleteAdapter extends RecyclerBaseAdapter<DownloadFileInfo> {
    OnCompleteListener onCompleteListener;
    protected CompleteAdapter(@NonNull Context context, @NonNull List<DownloadFileInfo> mDataList,OnCompleteListener onCompleteListener) {
        super(context, mDataList);
        this.onCompleteListener = onCompleteListener;
    }

    @Override
    protected void bindDataForView(ViewHolder holder, DownloadFileInfo downloadFileInfo, int position) {
        TextView name = holder.getView(R.id.tv_item_movie_name);
        name.setText(downloadFileInfo.getFileName());
        TextView tv_item_movie_size = holder.getView(R.id.tv_item_movie_size);
        tv_item_movie_size.setText(downloadFileInfo.getShowSize()==null?"未知":downloadFileInfo.getShowSize());
        TextView tv_item_movie_time = holder.getView(R.id.tv_item_movie_time);
        tv_item_movie_time.setVisibility(View.GONE);
        TextView tv_item_movie_speed = holder.getView(R.id.tv_item_movie_speed);
        tv_item_movie_speed.setText("下载完成");
        tv_item_movie_speed.setTextColor(Color.parseColor("#01a7ee"));
        Drawable img = getContext().getResources().getDrawable(R.mipmap.complete_flag_left);
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        tv_item_movie_speed.setCompoundDrawables(img, null, null, null); //设置左图标
        ProgressBar progressBar =  holder.getView(R.id.pb_item_movie_pb);
        progressBar.setVisibility(View.GONE);
        TextView textView = holder.getView(R.id.tv_item_movie_jiasu);
        textView.setVisibility(View.GONE);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onCompleteListener.onLongClick(downloadFileInfo,position);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCompleteListener.onClickListener(downloadFileInfo);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_movie_layout,parent,false);
        return new ViewHolder(view);
    }

    public interface OnCompleteListener {
        void onClickListener(DownloadFileInfo info);
        void onLongClick(DownloadFileInfo info,int position);
    }
}
