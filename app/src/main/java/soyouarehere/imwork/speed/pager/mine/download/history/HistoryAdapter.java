package soyouarehere.imwork.speed.pager.mine.download.history;

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
import soyouarehere.imwork.speed.pager.mine.download.downloading.MovieModule;

/**
 * Created by li.xiaodong on 2018/7/25.
 */

public class HistoryAdapter extends RecyclerBaseAdapter<MovieModule> {

    public HistoryAdapter(@NonNull Context context, @NonNull List<MovieModule> mDataList) {
        super(context, mDataList);
    }

    @Override
    protected void bindDataForView(ViewHolder holder, MovieModule movieModule, int position) {
        TextView name = holder.getView(R.id.tv_item_movie_name);
        name.setText(movieModule.getName());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_movie_layout,parent,false);
        return new ViewHolder(view);
    }
}
