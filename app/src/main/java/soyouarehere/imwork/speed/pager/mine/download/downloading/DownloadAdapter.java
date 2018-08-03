package soyouarehere.imwork.speed.pager.mine.download.downloading;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.adapter.RecyclerBaseAdapter;
import soyouarehere.imwork.speed.app.adapter.ViewHolder;
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
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_movie_layout,parent,false);
        return new ViewHolder(view);
    }
}
