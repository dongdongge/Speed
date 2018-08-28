package soyouarehere.imwork.speed.pager.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
import soyouarehere.imwork.speed.app.base.mvp.BaseFragment;
import soyouarehere.imwork.speed.app.view.AddressDialog;
import soyouarehere.imwork.speed.pager.mine.download.CustomAlertDialog;
import soyouarehere.imwork.speed.pager.mine.download.CustomBottomDialog;
import soyouarehere.imwork.speed.pager.mine.download.DownloadActivity;
import soyouarehere.imwork.speed.pager.mine.download.task.TaskHelp;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;
import soyouarehere.imwork.speed.pager.set.SetActivity;
import soyouarehere.imwork.speed.pager.mine.vip.VIPCenterActivity;
import soyouarehere.imwork.speed.util.DirectoryUtils;
import soyouarehere.imwork.speed.util.MobilePhoneInfo;
import soyouarehere.imwork.speed.util.http.CallBackUtil;
import soyouarehere.imwork.speed.util.http.UrlHttpUtil;
import soyouarehere.imwork.speed.util.log.LogUtil;
import soyouarehere.imwork.speed.view.image.ImageViewActivity;
import soyouarehere.imwork.speed.view.sliding_card.SlidingCardActivity;

/**
 * @author li.xiaodong
 * @desc 承载用户信息界面  download publish 等
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
    String msg = "《将进酒》是唐代大诗人李白沿用乐府古题创作的一首诗。此诗为李白长安放还以后所作，思想内容非常深沉，艺术表现非常成熟，在同题作品中影响最大。诗人豪饮高歌，借酒消愁，抒发了忧愤深广的人生感慨。诗中交织着失望与自信、悲愤与抗争的情怀，体现出强烈的豪纵狂放的个性。全诗情感饱满，无论喜怒哀乐，其奔涌迸发均如江河流泻，不可遏止，且起伏跌宕，变化剧烈；在手法上多用夸张，且往往以巨额数量词进行修饰，既表现出诗人豪迈洒脱的情怀，又使诗作本身显得笔墨酣畅，抒情有力；在结构上大开大阖，充分体现了李白七言歌行的特色。";

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
        mineSetting.setOnClickListener(this);
        mineDownload.setOnClickListener(this);
        mineCenter.setOnClickListener(this);
        minePlayReword.setOnClickListener(this);
        mineLink.setOnClickListener(this::onClick);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void showAlertDialog(String msg) {
        new CustomAlertDialog(this.getActivity(), true, true, msg, new CustomAlertDialog.OnClickInterface() {
            @Override
            public void clickSure() {

            }

            @Override
            public void clickCancel() {

            }
        }).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_setting:
                getActivity().startActivity(new Intent(getActivity(), SetActivity.class));
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
                DirectoryUtils._getEnvironmentDirectories();
                DirectoryUtils._getApplicationDirectories(getContext());
                break;
            case R.id.mine_space:
                break;
            case R.id.mine_center:
                getActivity().startActivity(new Intent(getActivity(), VIPCenterActivity.class));
                break;
            case R.id.mine_play_reword:
                showAlertDialog(MobilePhoneInfo._getHardInfo());
                break;
            case R.id.mine_collector:
                break;
            case R.id.mine_link:
                showDemoDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 展示弹窗  ->  选择相应的界面
     */
    private void showDemoDialog() {
        if (list == null) {
            initData();
        }
        new CustomBottomDialog(getActivity(), list, true, true, position -> {
            startActivity(position);
        }).show();
    }

    /**
     * 初始化弹窗和demo的类名
     */
    List<String> list;
    List<Class> classList;
    private void initData() {
        list = new ArrayList<>();
        classList = new ArrayList<>();
        list.add("安卓巨图加载方案");
        list.add("Behavior实现卡片的层叠滑动");
        list.add("地址翻页选择");
        classList.add(ImageViewActivity.class);
        classList.add(SlidingCardActivity.class);
    }

    /**
     * 跳转到指定的界面展示
     * @param position 集合中的数据
     */
    private void startActivity(int position) {
        if (position==2){
         new AddressDialog(getActivity(), (name, code) -> {
             LogUtil.e(name,code);
         },null).show();
            return;
        }
        startActivity(new Intent(getActivity(),classList.get(position)));
    }
}
