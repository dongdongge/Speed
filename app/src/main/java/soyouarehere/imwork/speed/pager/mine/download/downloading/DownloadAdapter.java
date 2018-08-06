package soyouarehere.imwork.speed.pager.mine.download.downloading;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.adapter.recycle_view.RecyclerBaseAdapter;
import soyouarehere.imwork.speed.app.adapter.recycle_view.ViewHolder;
import soyouarehere.imwork.speed.pager.mine.download.task.DownloadFileInfo;
import soyouarehere.imwork.speed.util.log.LogUtil;

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
        TextView tv_item_movie_size = holder.getView(R.id.tv_item_movie_size);
        TextView tv_item_movie_time = holder.getView(R.id.tv_item_movie_time);
        name.setText(info.getFileName());
        tv_item_movie_time.setText(info.getShowProgress()==null?"- - -":info.getShowProgress());
        tv_item_movie_size.setText(info.getShowSize()==null?"未知":info.getShowSize());
        if (info.getProgress()>0&&info.getTotal()>0){
            ProgressBar progressBar =  holder.getView(R.id.pb_item_movie_pb);
            LogUtil.e("adapter",info.getProgress(),info.getTotal());
            int pressTemp = (int)(info.getProgress()*10000/info.getTotal());
            LogUtil.e(pressTemp);
            progressBar.setProgress(pressTemp);
        }

//        progressBar.setProgress(info.getProgress());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_movie_layout,parent,false);
        return new ViewHolder(view);
    }


    public  void updataItem(int position){
        synchronized (this){
            updateItems(position,1);
        }
    }

    public   void updataAllItem(){
        synchronized(this){
            updateAll();
        }
    }


}
