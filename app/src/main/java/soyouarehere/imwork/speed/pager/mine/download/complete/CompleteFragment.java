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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.base.mvp.BaseFragment;
import soyouarehere.imwork.speed.app.rxbus.RxBus2;
import soyouarehere.imwork.speed.app.rxbus.RxBusEvent2;
import soyouarehere.imwork.speed.pager.mine.download.DownloadHelp;
import soyouarehere.imwork.speed.pager.mine.download.task.TaskHelp;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;
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
        adapter = new CompleteAdapter(this.getActivity(), completeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);
        initData();
        initListener();
    }

    List<DownloadFileInfo> completeList = new ArrayList<>();

    /**
     * 初始化数据 从数据库中获取已经完成的任务；
     */
    private void initData() {
        completeList.clear();
        completeList.addAll(DownloadHelp.getDataDownloadComplete());
        adapter.updateAll();
    }

    public void initListener() {
        accuptMsg();
    }
    private void accuptMsg() {
        mSubscription.add(RxBus2.getInstance().register(RxBusEvent2.class).subscribe(new Consumer<RxBusEvent2>() {
            @Override
            public void accept(RxBusEvent2 event) throws Exception {
                LogUtil.e("接受到了消息" + event.getT().toString());
                DownloadFileInfo info = (DownloadFileInfo) event.getT();
                // 标志着下载完成
                if (info.getFileStatue().equals("complete")){
                    completeList.add(info);
                    adapter.updateAll();
                }
            }
        }));

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
