package soyouarehere.imwork.speed.pager.mine.download.history;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.adapter.recycle_view.RecycleDividerItemDecoration;
import soyouarehere.imwork.speed.app.base.mvp.BaseFragment;
import soyouarehere.imwork.speed.pager.mine.download.downloading.MovieModule;
import soyouarehere.imwork.speed.util.DensityUtil;

public class HistoryFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.history_rcy)
    RecyclerView history_rcy;
    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        return R.layout.fragment_history;
    }

    @Override
    protected void initView() {
        history_rcy.setLayoutManager(new LinearLayoutManager(getContext()));
        history_rcy.addItemDecoration(getDivider());
        history_rcy.setAdapter(new HistoryAdapter(getContext(),initData()));
    }
    public RecycleDividerItemDecoration getDivider(){
        RecycleDividerItemDecoration dividerItemDecoration = new RecycleDividerItemDecoration(getContext());
        dividerItemDecoration.setDeliverHeight(DensityUtil.dip2px(getContext(), 1.0f));
        dividerItemDecoration.setPaintColor(getResources().getColor(R.color.color_c9c9c9));
        return dividerItemDecoration;
    }
    public static List<MovieModule> initData(){
        List<MovieModule> movieModules = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            MovieModule movieModule = new MovieModule();
            movieModule.setName("我不是药神纪录片"+i);
            movieModules.add(movieModule);
        }
        return movieModules;
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
