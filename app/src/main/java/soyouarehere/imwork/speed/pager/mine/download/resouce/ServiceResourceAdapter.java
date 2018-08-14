package soyouarehere.imwork.speed.pager.mine.download.resouce;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.util.List;

import soyouarehere.imwork.speed.app.adapter.recycle_view.RecyclerBaseAdapter;
import soyouarehere.imwork.speed.app.adapter.recycle_view.ViewHolder;

/**
 * Created by li.xiaodong on 2018/8/6.
 */

public class ServiceResourceAdapter extends RecyclerBaseAdapter<ResourceFile> {

    protected ServiceResourceAdapter(@NonNull Context context, @NonNull List<ResourceFile> mDataList) {
        super(context, mDataList);
    }

    @Override
    protected void bindDataForView(ViewHolder holder, ResourceFile resourceRe, int position) {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
}
