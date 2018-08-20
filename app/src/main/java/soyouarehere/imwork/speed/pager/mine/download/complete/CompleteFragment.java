package soyouarehere.imwork.speed.pager.mine.download.complete;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.app.base.mvp.BaseFragment;
import soyouarehere.imwork.speed.app.rxbus.RxBus2;
import soyouarehere.imwork.speed.app.rxbus.RxBusEvent2;
import soyouarehere.imwork.speed.pager.mine.download.CustomAlertDialog;
import soyouarehere.imwork.speed.pager.mine.download.DownloadHelp;
import soyouarehere.imwork.speed.pager.mine.download.task.TaskHelp;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;
import soyouarehere.imwork.speed.util.PreferenceUtil;
import soyouarehere.imwork.speed.util.log.LogUtil;

public class CompleteFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @BindView(R.id.complete_fragment_rcy)
    RecyclerView recyclerView;

    public CompleteFragment() {
        // Required empty public constructor
    }

    public static CompleteFragment newInstance(String param1, String param2) {
        CompleteFragment fragment = new CompleteFragment();
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
        return R.layout.fragment_complete;
    }

    CompleteAdapter adapter;

    @Override
    protected void initView() {
        adapter = new CompleteAdapter(this.getActivity(), completeList, new CompleteAdapter.OnCompleteListener() {
            @Override
            public void onClickListener(DownloadFileInfo info) {
                LogUtil.e("你点击了当前信息"+info);
            }

            @Override
            public void onLongClick(DownloadFileInfo info,int position) {
                String msg = String.format("(%s)你确定删除该任务下载记录以及文件吗?",info.getFileName());
                showAlertDialog(msg,info);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);
        initData();
        initListener();
    }



    List<DownloadFileInfo> completeList = new ArrayList<>();
    Map<String,Integer> integerMap = new HashMap<>();
    /**
     * 初始化数据 从数据库中获取已经完成的任务；
     */
    private void initData() {
        completeList.clear();
        integerMap.clear();
        completeList.addAll(DownloadHelp.getDataDownloadComplete());
        if (completeList.size()>0){
            for (int i = 0; i < completeList.size(); i++) {
                integerMap.put(completeList.get(i).getFileName(),i);
            }
        }
        adapter.updateAll();
    }

    public void initListener() {
        accuptMsg();
    }
    private void accuptMsg() {
        LogUtil.e("注册监听=====完成任务界面");
        mSubscription.add(RxBus2.getInstance().register(RxBusEvent2.class).subscribe(new Consumer<RxBusEvent2>() {
            @Override
            public void accept(RxBusEvent2 event) throws Exception {
                LogUtil.e("接受到了消息" + event.getT().toString());
                DownloadFileInfo info = (DownloadFileInfo) event.getT();
                // 标志着下载完成
                if (info.getFileStatue().equals("complete")&&!integerMap.containsKey(info.getFileName())){
                    completeList.add(info);
                    integerMap.put(info.getFileName(),completeList.size()-1);
                    adapter.updateAll();
                }
            }
        }));

    }
    public void showAlertDialog(String msg,DownloadFileInfo info) {
        new CustomAlertDialog(this.getActivity(),"取消","确定",msg, new CustomAlertDialog.OnClickInterface() {
            @Override
            public void clickSure() {
                deleteDownloadInfo(info);
                initData();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void clickCancel() {

            }
        }).show();
    }
    // 删除文件以及下载记录信息;
    public void deleteDownloadInfo(DownloadFileInfo info){
        PreferenceUtil.deleteDownloadFileInfo(BaseApplication.getInstance(),info.getFileName());
        File file = new File(info.getFilePath(),info.getFileName());
        if (file.exists()){
            file.delete();
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
