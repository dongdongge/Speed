package soyouarehere.imwork.speed.app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.util.FileUtil;
import soyouarehere.imwork.speed.util.log.LogUtil;

/**
 * 地址选择对话框
 */
@SuppressWarnings("unchecked")
@SuppressLint("InflateParams")
public class AddressDialog extends AlertDialog {

    private DialogSelectionListener mListener;
    private List<Map<String, Object>> mProvinceList;
    private List<Map<String, Object>> mCityList;
    private List<Map<String, Object>> mDistrictList;
    private List<Map<String, Object>> mStreetList;
    private Map<String, Object> mMap;
    private List<String> labelValueList = new ArrayList<>();

    private TextView mAddrView;
    private ViewFlipper mViewFlipper;

    private Animation mLeftInAnimation;
    private Animation mLeftOutAnimation;
    private Animation mRightInAnimation;
    private Animation mRightOutAnimation;

    @SuppressWarnings("ResourceType")
    public AddressDialog(Context context, DialogSelectionListener listener, Map<String, Object> mList) {
        super(context);
        setTitle("请选择地址");
        initData(context, mList);
        mListener = listener;
        initView(context);
        initShowData();
        setCurrentTitle();
        setButton(BUTTON_POSITIVE, "确定",
                new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismissWithSelection();
                    }

                });
    }
    /**
     * 初始化数据
     */
    private void initData(Context context, Map<String, Object> mList) {
        if (mList != null && mList.size() > 0) {
            mMap = mList;
        } else {
            mMap = FileUtil.loadResFile(context, "address.json");
            if (mMap == null) {
                LogUtil.e("获取数据异常");
            }
        }
        mProvinceList = new ArrayList<>();
        mProvinceList.add(mMap);
    }

    /**
     * 初始化布局  adapter  默认显示数据
     */
    public void initView(Context context) {
        View view = getLayoutInflater().inflate(R.layout.dialog_address, null);
        setView(view);
        mAddrView = view.findViewById(R.id.addr);
        mViewFlipper = view.findViewById(R.id.flipper);
        mLeftInAnimation = AnimationUtils.loadAnimation(context, R.anim.push_left_enter);
        mLeftOutAnimation = AnimationUtils.loadAnimation(context, R.anim.push_left_exit);
        mRightInAnimation = AnimationUtils.loadAnimation(context, R.anim.push_right_enter);
        mRightOutAnimation = AnimationUtils.loadAnimation(context, R.anim.push_right_exit);
        prepareListView(R.id.province, mProvinceList);
    }

    /**
     * 初始化当前显示数据
     */
    private void initShowData() {
        labelValueList.add("" + mMap.get("label") + ";" + mMap.get("value"));
    }

    @Override
    public void show() {
        if (mMap != null && !isShowing()) {
            super.show();
        } else {
            LogUtil.e("数据为空 或 正在isShowing");
        }
    }

    /**
     * 地址列表渲染
     *
     * @param id   ID
     * @param data map数组
     */
    private void prepareListView(int id, List<Map<String, Object>> data) {
        ListView listView = (ListView) mViewFlipper.findViewById(id);
        SimpleAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.list_item_spinner,
                new String[]{
                        "label"
                }, new int[]{
                R.id.title
        });
        listView.setOnItemClickListener(mClickListener);
        listView.setAdapter(adapter);
    }

    /**
     * 点击事件监听器
     */
    private OnItemClickListener mClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                //省级
                case R.id.province: {
                    mCityList =  setData(position,R.id.city,mProvinceList);
                }
                break;

                //市级
                case R.id.city: {
                    mDistrictList =  setData(position,R.id.district,mCityList);
                }
                break;

                //区级
                case R.id.district: {
                    mStreetList =  setData(position,R.id.street,mDistrictList);
                }
                break;
                //街道
                case R.id.street: {
                    setData(position,R.id.street,mStreetList);
                }
                break;
                default:
                    break;
            }
            setCurrentTitle();
        }

    };
    /**
     * 初始化下一步
     * */
    private List<Map<String, Object>> setData(int position, int layoutId, List<Map<String, Object>> mPreData) {
        Map<String, Object> map = mPreData.get(position);
        setCurrentShowData((String) map.get("label"), (String) map.get("value"),mPreData);
        if (map.containsKey("children")) {
            List<Map<String, Object>> mNextData = (List<Map<String, Object>>) map.get("children");
            prepareListView(layoutId, mNextData);
            showNext();
            return mNextData;
        } else {
            LogUtil.e("不存在子类");
            dismissWithSelection();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        if (mViewFlipper.getCurrentView().getId() == R.id.province) {
            super.onBackPressed();
        } else {
            showPrev();
            if (labelValueList.size() > 1) {
                labelValueList.remove(0);
                setCurrentTitle();
            }
        }
    }

    /**
     * 显示下一级页面
     */
    private void showNext() {
        mViewFlipper.setInAnimation(mLeftInAnimation);
        mViewFlipper.setOutAnimation(mLeftOutAnimation);
        mViewFlipper.showNext();
    }


    /**
     * 显示上一级页面
     */
    private void showPrev() {
        mViewFlipper.setInAnimation(mRightInAnimation);
        mViewFlipper.setOutAnimation(mRightOutAnimation);
        mViewFlipper.showPrevious();
    }

    /**
     * 获取选中并DISMISS
     */
    private void dismissWithSelection() {
        if ((mListener != null) && (labelValueList.size() > 0)) {
            String[] tempArray = labelValueList.get(0).split(";");
            mListener.OnSelectItem(tempArray[0], tempArray[1]);
        }
        dismiss();
    }

    /**
     * 跟随用户操作该改变当前的状态
     */
    private void setCurrentShowData(String label, String value,List<Map<String, Object>> mPreData) {
        LogUtil.e("当前索引"+labelValueList.get(0));
        // 如果集合中只有一个
        if (labelValueList.size() < 1) {
            return;
        }
        String[] tempArray = labelValueList.get(0).split(";");
        String tempIndex = "";
        if (tempArray[0].contains(",")){
            String[] arr =  tempArray[0].split(",");
            tempIndex = arr[arr.length-1];
        }else {
            tempIndex = tempArray[0];
        }
        // 如果点击相同的不进行任何赋值操作
        if (label.equals(tempIndex)){
            LogUtil.e("你点击了相同的选中条目");
            return;
        }
        // 如果点击的是同一级的数据,就先去删除,然后再添加
        for (Map<String, Object> objectMap: mPreData){
            if (objectMap.get("label").equals(tempIndex)){
                LogUtil.e("存在"+tempIndex);
                labelValueList.remove(0);
            }
        }
        String[] tempArray2 = labelValueList.get(0).split(";");
        labelValueList.add(0, "" + tempArray2[0] + "," + label + ";" + tempArray2[1] + "," + value);
        LogUtil.e("设置过后的结果是"+labelValueList.get(0));
    }

    /**
     * 显示当前地址
     *
     * @param
     */
    private void setCurrentTitle() {
         String format = "当前地址：%s";
        mAddrView.setText(String.format(format, labelValueList.get(0).split(";")[0]));
    }

    public interface DialogSelectionListener {

        public void OnSelectItem(String name, String code);
    }
}