package soyouarehere.imwork.speed.pager.set.select_path;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
import soyouarehere.imwork.speed.util.FileUtil;
import soyouarehere.imwork.speed.function.log.LogUtil;

public class SelectPathActivity extends BaseActivity {


    /**
     * 搜索控件
     */
    @BindView(R.id.select_path_search)
    EditText select_path_search;

    /**
     * 索引列表
     */
    @BindView(R.id.select_path_rcy_index)
    RecyclerView select_path_rcy_index;

    /**
     * 展示列表
     */
    @BindView(R.id.select_path_rcy)
    RecyclerView select_path_rcy;

    @Override
    public void DataLoadError() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_path;
    }


    @Override
    public void create(Bundle savedInstanceState) {
        initData();
        initView();
    }

    AdapterSelectPath adapterSelectPath;
    AdapterSelectPathIndex adapterSelectPathIndex;

    private void initView() {
        select_path_rcy.setLayoutManager(new LinearLayoutManager(this));
        adapterSelectPath = new AdapterSelectPath(this, fileBeansindex, new AdapterSelectPath.OnclickSelectPath() {
            @Override
            public void callBackItem(SelectFileBean selectFileBean, int position) {
                listIndex.add(selectFileBean.getCurrentFilePath());
                fileBeansindex.clear();
                fileBeansindex.addAll(formatData(selectFileBean.getCurrentFilePath()));
                adapterSelectPath.notifyDataSetChanged();
                adapterSelectPathIndex.notifyDataSetChanged();
            }
        });
        select_path_rcy.setAdapter(adapterSelectPath);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        select_path_rcy_index.setLayoutManager(manager);
        adapterSelectPathIndex = new AdapterSelectPathIndex(this, listIndex, new AdapterSelectPathIndex.OnClickItemIndex() {
            @Override
            public void onCallBack(String s, int position) {
                List<String> stringList = new ArrayList<>();
                for (int i = 0; i < position+1; i++) {
                    stringList.add(listIndex.get(i));
                }
                listIndex.clear();
                listIndex.addAll(stringList);
                adapterSelectPathIndex.notifyDataSetChanged();
                fileBeansindex.clear();
                fileBeansindex.addAll(formatData(s));
                adapterSelectPath.notifyDataSetChanged();
            }
        });
        select_path_rcy_index.setAdapter(adapterSelectPathIndex);

    }


    List<SelectFileBean> fileBeansindex = new ArrayList<>();
    List<String> listIndex = new ArrayList<>();

    public void initData() {
        // 获取外部存储根目录 /storage/emulated/0
        String rootPath = FileUtil._getExternalStorageDirectory();
        LogUtil.e("rootPath", rootPath);
        listIndex.add(rootPath);
        File file = new File(rootPath);
        if (!file.exists()) {
            return;
        }
        fileBeansindex.addAll(formatData(file.getPath()));
//
//        if (file.isDirectory()) {
//            File[] files = file.listFiles();
//            if (files.length > 0) {
//                for (File child : files) {
//                    SelectFileBean bean = new SelectFileBean();
//                    bean.setCurrentFilePath(child.getPath());
//                    bean.setFileName(child.getName());
//                    bean.setFileType(child.isDirectory() ? "0" : "1");
//                    bean.setUpdateTime(String.valueOf(child.lastModified()));
//                    if (child.isDirectory()){
//                        File[] files1= child.listFiles();
//                        bean.setChildFileNumber(String.valueOf(files1.length));
//                    }
//                    fileBeansindex.add(bean);
//                }
//            }
//        }
    }

    public List<SelectFileBean> formatData(String filePath) {
        List<SelectFileBean> listIndexss = new ArrayList<>();
        File file = new File(filePath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files.length > 0) {
                for (File child : files) {
                    SelectFileBean bean = new SelectFileBean();
                    bean.setCurrentFilePath(child.getPath());
                    bean.setFileName(child.getName());
                    bean.setFileType(child.isDirectory() ? "0" : "1");
                    bean.setUpdateTime(String.valueOf(child.lastModified()));
                    if (child.isDirectory()) {
                        File[] files1 = child.listFiles();
                        bean.setChildFileNumber(String.valueOf(files1.length));
                    }
                    listIndexss.add(bean);
                }
            }
        }
        return listIndexss;
    }

}
