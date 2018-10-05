package soyouarehere.imwork.speed.pager.mine.download.resouce;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.base.adapter.recycle_view.RecyclerBaseAdapter;
import soyouarehere.imwork.speed.app.base.adapter.recycle_view.ViewHolder;

/**
 * Created by li.xiaodong on 2018/8/6.
 */

public class ServiceResourceAdapter extends RecyclerBaseAdapter<ResourceFile> {
    ServiceResourceListener serviceResourceListener;
    protected ServiceResourceAdapter(@NonNull Context context, @NonNull List<ResourceFile> mDataList,ServiceResourceListener serviceResourceListener) {
        super(context, mDataList);
        this.serviceResourceListener = serviceResourceListener;
    }

    @Override
    protected void bindDataForView(ViewHolder holder, ResourceFile resourceRe, int position) {
        TextView fileName = holder.getView(R.id.service_resource_file_name);
        TextView fileSize = holder.getView(R.id.service_resource_file_size);
        TextView fileUrl = holder.getView(R.id.service_resource_file_url);
        fileName.setText(resourceRe.getName());
        fileSize.setText(resourceRe.getFileLength()+"B");
        fileUrl.setText(resourceRe.getUrl());
        ImageButton imageButton = holder.getView(R.id.service_resource_download);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceResourceListener.callBack(resourceRe);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.service_resource_activity_adapter_list_layout, parent, false);
        return new ViewHolder(view);
    }

    public interface ServiceResourceListener{
        void callBack(ResourceFile file);
    }
}
