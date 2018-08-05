package soyouarehere.imwork.speed.pager.mine.download.downloading;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.adapter.recycle_view.RecyclerBaseAdapter;
import soyouarehere.imwork.speed.app.adapter.recycle_view.ViewHolder;
import soyouarehere.imwork.speed.pager.mine.download.task.DownloadFileInfo;

/**
 * Created by li.xiaodong on 2018/7/25.
 */

public class DownloadAdapter extends RecyclerBaseAdapter<DownloadFileInfo> {

    public DownloadAdapter(@NonNull Context context, @NonNull List<DownloadFileInfo> mDataList) {
        super(context, mDataList);
    }

    @Override
    protected void bindDataForView(ViewHolder holder, DownloadFileInfo info, int position) {
        TextView name = holder.getView(R.id.tv_item_movie_name);
        name.setText(info.getFileName());
        if (info.getProgress()>0&&info.getProgress()>0){
            ProgressBar progressBar =  holder.getView(R.id.pb_item_movie_pb);
            NumberFormat numberFormat = NumberFormat.getInstance();

            // 设置精确到小数点后2位

            numberFormat.setMaximumFractionDigits(2);
            String press = numberFormat.format((float)info.getProgress()/info.getTotal());
            int pressInt = Integer.valueOf(press)*100;
            progressBar.setProgress(pressInt);
        }

//        progressBar.setProgress(info.getProgress());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_movie_layout,parent,false);
        return new ViewHolder(view);
    }


    public synchronized void updataItem(int position){
        updateItems(position,0);
    }

    public  synchronized void updataAllItem(){
       updateAll();
    }


}
