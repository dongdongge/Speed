package soyouarehere.imwork.speed.pager.mine.download.downloading;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.adapter.recycle_view.RecyclerBaseAdapter;
import soyouarehere.imwork.speed.app.adapter.recycle_view.ViewHolder;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;
import soyouarehere.imwork.speed.function.log.LogUtil;

/**
 * Created by li.xiaodong on 2018/7/25.
 */

public class DownloadAdapter extends RecyclerBaseAdapter<DownloadFileInfo> {

    private OnClickAdapterItem onClickAdapterItem;
    public DownloadAdapter(@NonNull Context context, @NonNull List<DownloadFileInfo> mDataList,OnClickAdapterItem onClickAdapterItem) {
        super(context, mDataList);
        this.onClickAdapterItem = onClickAdapterItem;
    }

    @Override
    protected void bindDataForView(ViewHolder holder, DownloadFileInfo info, int position) {
        TextView name = holder.getView(R.id.tv_item_movie_name);
        TextView tv_item_movie_size = holder.getView(R.id.tv_item_movie_size);
        TextView tv_item_movie_time = holder.getView(R.id.tv_item_movie_time);
        TextView tv_item_movie_speed = holder.getView(R.id.tv_item_movie_speed);
        CheckBox radioButton = holder.getView(R.id.rb_item_movie_control);
        int id = info.getFileStatue().equals("stop")?R.mipmap.download_item_pause_icon_style2:R.mipmap.download_item_resume_icon_style2;
        radioButton.setButtonDrawable(id);
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onClickAdapterItem.callBack(isChecked,position,info);
            }
        });
        name.setText(info.getFileName());
        tv_item_movie_time.setText(info.getShowProgress()==null?"- - -":info.getShowProgress());
        tv_item_movie_size.setText(info.getShowSize()==null?"未知":info.getShowSize());
        tv_item_movie_speed.setText(info.getDownloadSpeed()==null?"- - -":info.getDownloadSpeed());
        if (info.getProgress()>0&&info.getTotal()>0){
            ProgressBar progressBar =  holder.getView(R.id.pb_item_movie_pb);
            LogUtil.e("adapter",info.getProgress(),info.getTotal());
            int pressTemp = (int)(info.getProgress()*10000/info.getTotal());
            LogUtil.e(pressTemp);
            progressBar.setProgress(pressTemp);
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onClickAdapterItem.onLongClick(position,info);
                return true;
            }
        });
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

    public interface OnClickAdapterItem{
        void callBack(boolean isChecked,int position,DownloadFileInfo info);
        void onLongClick(int position,DownloadFileInfo info);
    }

}
