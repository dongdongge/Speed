package soyouarehere.imwork.speed.pager.mine.download.downloading;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.adapter.recycle_view.RecycleDividerItemDecoration;
import soyouarehere.imwork.speed.app.base.mvp.BaseFragment;
import soyouarehere.imwork.speed.app.rxbus.RxBus2;
import soyouarehere.imwork.speed.app.rxbus.RxBusEvent;
import soyouarehere.imwork.speed.app.rxbus.RxBusEvent2;
import soyouarehere.imwork.speed.pager.mine.download.DownloadActivity;
import soyouarehere.imwork.speed.pager.mine.download.task.DownloadFileInfo;
import soyouarehere.imwork.speed.util.DensityUtil;
import soyouarehere.imwork.speed.util.log.LogUtil;

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
    @BindView(R.id.tv_msg_down_fragment)
    TextView textView;

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

    public static List<DownloadFileInfo> initData() {
        hashMap = new HashMap<>();
        List<DownloadFileInfo> fileInfos = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            DownloadFileInfo info = new DownloadFileInfo("url");
            info.setFileName("我不是药神纪录片" + i);
            fileInfos.add(info);
            hashMap.put(info.getFileName(), i);
        }
        return fileInfos;
    }

    public RecycleDividerItemDecoration getDivider() {
        RecycleDividerItemDecoration dividerItemDecoration = new RecycleDividerItemDecoration(getContext());
        dividerItemDecoration.setDeliverHeight(DensityUtil.dip2px(getContext(), 1.0f));
        dividerItemDecoration.setPaintColor(getResources().getColor(R.color.color_c9c9c9));
        return dividerItemDecoration;
    }

    List<DownloadFileInfo> infoList;
    static HashMap<String, Integer> hashMap;
    DownloadAdapter adapter;

    @Override
    protected void initView() {
        downloadingRcy.setLayoutManager(new LinearLayoutManager(getContext()));
        downloadingRcy.addItemDecoration(getDivider());
        infoList = initData();
        adapter = new DownloadAdapter(getContext(), infoList);
        downloadingRcy.setAdapter(adapter);
        accuptMsg();
    }

    public void accuptMsg() {
        int temp = 0;
        mSubscription.add(RxBus2.getInstance().register(RxBusEvent2.class).subscribe(new Consumer<RxBusEvent2>() {
            @Override
            public void accept(RxBusEvent2 event) throws Exception {
                LogUtil.e("接受到了消息" + event.getT().toString());
                notifyItem((DownloadFileInfo) event.getT());
                textView.setText("lxd" + "" + temp);
            }
        }));

    }

    private void notifyItem(DownloadFileInfo downloadFileInfo) {
        if (hashMap.containsKey(downloadFileInfo.getFileName())) {
            infoList.get(0).setProgress(downloadFileInfo.getProgress());
            adapter.updataItem(hashMap.get(downloadFileInfo.getFileName()));
        } else {
            hashMap.put(downloadFileInfo.getFileName(), infoList.size());
            infoList.add(downloadFileInfo);
            adapter.updataAllItem();
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        LogUtil.e("childFragment", "onPause");
    }
}
