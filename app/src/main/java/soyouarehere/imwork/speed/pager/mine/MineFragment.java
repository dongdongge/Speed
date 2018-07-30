package soyouarehere.imwork.speed.pager.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
import soyouarehere.imwork.speed.app.base.mvp.BaseFragment;
import soyouarehere.imwork.speed.pager.mine.download.DownloadActivity;
/**
 * @desc 承载用户信息界面  download publish 等
 * @author li.xiaodong
 * @time 2018/7/30 17:10
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @BindView(R.id.mine_setting)
    ImageButton mineSetting;
    @BindView(R.id.mine_msg)
    ImageButton mineMsg;
    @BindView(R.id.mine_user_head_img)
    ImageView mineUserHead;
    @BindView(R.id.mine_user_name_tv)
    TextView mineName;
    @BindView(R.id.mine_user_vip)
    Button mineVIP;
    @BindView(R.id.mine_publish_live)
    Button minePublic;
    @BindView(R.id.mine_download)
    Button mineDownload;
    @BindView(R.id.mine_space)
    Button mineSpace;
    @BindView(R.id.mine_center)
    Button mineCenter;
    @BindView(R.id.mine_play_reword)
    Button minePlayReword;
    @BindView(R.id.mine_collector)
    Button mineCollector;
    @BindView(R.id.mine_link)
    Button mineLink;

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    BaseActivity baseActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        baseActivity = (BaseActivity) getActivity().getParent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        mineDownload.setOnClickListener(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_setting:
                break;
            case R.id.mine_msg:
                break;
            case R.id.mine_user_head_img:
                break;
            case R.id.mine_user_vip:
                break;
            case R.id.mine_publish_live:
                break;
            case R.id.mine_download:
                getActivity().startActivity(new Intent(getActivity(), DownloadActivity.class));
                break;
            case R.id.mine_space:
                break;
            case R.id.mine_center:
                break;
            case R.id.mine_play_reword:
                break;
            case R.id.mine_collector:
                break;
            case R.id.mine_link:
                break;
            default:
                break;
        }
    }
}
