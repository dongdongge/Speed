package soyouarehere.imwork.speed.pager.mine.download;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;
import soyouarehere.imwork.speed.util.PreferenceUtil;

/**
 * 下载的辅助类
 */
public class DownloadHelp {

    /**
     * 获取数据库中的所有任务 以list<T> 返回
     */
    public static List<DownloadFileInfo> getAllDataListTask() {
        Gson gson = new Gson();
        List<DownloadFileInfo> fileInfos = new ArrayList<>();
        Map<String, ?> map = PreferenceUtil.getDownloadFileInfoAll(BaseApplication.getInstance());
        for (String key : map.keySet()) {
            DownloadFileInfo info = gson.fromJson(map.get(key).toString(), DownloadFileInfo.class);
            fileInfos.add(info);
        }
        return fileInfos;
    }

    /**
     * 获取所有数据库中的数据 以Map<String,DownloadFileInfo> 返回
     */
    public static Map<String, DownloadFileInfo> getAllDataMapTask() {
        Gson gson = new Gson();
        Map<String, DownloadFileInfo> fileInfoMap = new HashMap<>();
        Map<String, ?> map = PreferenceUtil.getDownloadFileInfoAll(BaseApplication.getInstance());
        for (String key : map.keySet()) {
            DownloadFileInfo info = gson.fromJson(map.get(key).toString(), DownloadFileInfo.class);
            fileInfoMap.put(key, info);
        }
        return fileInfoMap;
    }
    /**
     * 从数据库中获取正在下载的任务
     */
    public static List<DownloadFileInfo> getDataDownloadIng() {
        List<DownloadFileInfo> fileInfos = getAllDataListTask();
        List<DownloadFileInfo> downloadIngs = new ArrayList<>();
        for (DownloadFileInfo in : fileInfos) {
            if (in.getFileStatue()==null){
                break;
            }
            if (in.getFileStatue().equals("downloading")||in.getFileStatue().equals("stop")) {
                downloadIngs.add(in);
            }
        }
        return downloadIngs;
    }

    /**
     * 从数据库中获取已经下载完成的任务
     */
    public static List<DownloadFileInfo> getDataDownloadComplete() {
        List<DownloadFileInfo> fileInfos = getAllDataListTask();
        List<DownloadFileInfo> downloadIngs = new ArrayList<>();
        for (DownloadFileInfo in : fileInfos) {
            if (in.getFileStatue().equals("complete")) {
                downloadIngs.add(in);
            }
        }
        return downloadIngs;
    }
}
