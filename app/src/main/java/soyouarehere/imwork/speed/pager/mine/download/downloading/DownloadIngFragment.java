package soyouarehere.imwork.speed.pager.mine.download.downloading;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.app.base.adapter.recycle_view.RecycleDividerItemDecoration;
import soyouarehere.imwork.speed.app.base.mvp.BaseFragment;
import soyouarehere.imwork.speed.function.rxbus.RxBus;
import soyouarehere.imwork.speed.function.rxbus.RxBusEvent;
import soyouarehere.imwork.speed.pager.mine.download.CustomAlertDialog;
import soyouarehere.imwork.speed.pager.mine.download.DownloadHelp;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;
import soyouarehere.imwork.speed.pager.mine.download.task.TaskManager;
import soyouarehere.imwork.speed.util.DensityUtil;
import soyouarehere.imwork.speed.util.PreferenceUtil;
import soyouarehere.imwork.speed.function.log.LogUtil;

public class DownloadIngFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @BindView(R.id.downloading_rcy)
    RecyclerView downloadingRcy;

    public DownloadIngFragment() {
        // Required empty public constructor
    }

    public static DownloadIngFragment newInstance(String param1, String param2) {
        DownloadIngFragment fragment = new DownloadIngFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_download_ing;
    }

    /**
     * 初始化数据
     */
    public void initData() {
        infoList = new ArrayList<>();
        infoList.addAll(DownloadHelp.getDataDownloadIng());
    }

    public RecycleDividerItemDecoration getDivider() {
        RecycleDividerItemDecoration dividerItemDecoration = new RecycleDividerItemDecoration(getContext());
        dividerItemDecoration.setDeliverHeight(DensityUtil._dip2px(getContext(), 1.0f));
        dividerItemDecoration.setPaintColor(getResources().getColor(R.color.color_c9c9c9));
        return dividerItemDecoration;
    }

    volatile List<DownloadFileInfo> infoList;
    DownloadAdapter adapter;

    @Override
    protected void initView() {
        downloadingRcy.setLayoutManager(new LinearLayoutManager(getContext()));
        downloadingRcy.addItemDecoration(getDivider());
        initData();
        adapter = new DownloadAdapter(getContext(), infoList, new DownloadAdapter.OnClickAdapterItem() {
            @Override
            public void callBack(boolean isChecked, int position, DownloadFileInfo info) {
                LogUtil.e("当前索引", position, isChecked);
                // 恢复下载
                if (isChecked) {
                    // 暂停下载
                    info.setFileStatue("stop");
                } else {
                    // 恢复下载
                    TaskManager.getInstance().resumeContinueDownload(info);
                }
                adapter.updataItem(position);
            }

            @Override
            public void onLongClick(int position, DownloadFileInfo info) {
                String msg = String.format("(%s)你确定删除该条任务吗",info.getFileName());
                showAlertDialog(msg,info,position);

            }
        });
        downloadingRcy.setAdapter(adapter);
        ((DefaultItemAnimator) downloadingRcy.getItemAnimator()).setSupportsChangeAnimations(false);
        accuptMsg();
    }

    private void showAlertDialog(String msg,DownloadFileInfo info,int position) {
        new CustomAlertDialog(this.getActivity(),"取消","确定",msg, new CustomAlertDialog.OnClickInterface() {
            @Override
            public void clickSure() {
                deleteDownloadInfo(info);
                infoList.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void clickCancel() {

            }
        }).show();
    }
    // 删除文件以及下载记录信息;
    private void deleteDownloadInfo(DownloadFileInfo info){
        PreferenceUtil.deleteDownloadFileInfo(BaseApplication.getInstance(),info.getFileName());
        File file = new File(info.getFilePath(),info.getFileName());
        if (file.exists()){
            file.delete();
        }
    }

    public void accuptMsg() {
        mSubscription.add(RxBus.getInstance().register(RxBusEvent.class).subscribe(new Consumer<RxBusEvent>() {
            @Override
            public void accept(RxBusEvent event) throws Exception {
                LogUtil.e("接受到了消息" + event.getT().toString());
                notifyItem((DownloadFileInfo) event.getT());
            }
        }));

    }

    private void notifyItem(DownloadFileInfo downloadFileInfo) {
        int position = positionBean(downloadFileInfo.getFileName());
        if (position != -1){
            if (downloadFileInfo.getFileStatue().equals("complete")){
                try {
                    LogUtil.e(infoList);
                    LogUtil.e("大小",infoList.size(),"位置",position);
                    infoList.remove(position);
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    LogUtil.e(""+e.getMessage());
                }
                return;
            }
            infoList.get(position).setProgress(downloadFileInfo.getProgress());
            infoList.get(position).setShowProgress(downloadFileInfo.getShowProgress());
            infoList.get(position).setDownloadSpeed(downloadFileInfo.getDownloadSpeed());
            adapter.updataItem(position);
        }else {
            infoList.add(0,downloadFileInfo);
            adapter.updataItem(0);
        }
    }
    private int positionBean(String name){
        LogUtil.e("定为position",name);
        for (int i = 0; i < infoList.size(); i++) {
            if (infoList.get(i).getFileName().equals(name)) {
                LogUtil.e("位置为位置为",i);
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.e("childFragment", "onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.e("childFragment", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
