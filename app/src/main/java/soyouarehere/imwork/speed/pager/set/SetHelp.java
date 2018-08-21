package soyouarehere.imwork.speed.pager.set;

import com.google.gson.Gson;

import java.io.File;
import java.util.Map;

import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.pager.mine.download.task.TaskManager;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;
import soyouarehere.imwork.speed.util.PhoneSpaceSizeUtils;
import soyouarehere.imwork.speed.util.PreferenceUtil;
import soyouarehere.imwork.speed.util.log.LogUtil;

public class SetHelp {
    /**
     * 清空下载文件等信息；
     */
    protected static void clearSpace() {
        Map<String, ?> mapFileList = PreferenceUtil.getDownloadFileInfoAll(BaseApplication.getInstance());
        LogUtil.e(mapFileList);
        if (mapFileList == null || mapFileList.isEmpty()) {
            return;
        }
        Gson gson = new Gson();
        for (String str : mapFileList.keySet()) {
            DownloadFileInfo info = gson.fromJson(mapFileList.get(str).toString(), DownloadFileInfo.class);
            File file = new File(info.getFilePath(), info.getFileName());
            if (file.exists()) {
                file.delete();
            }
        }
        PreferenceUtil.clearDownloadFileInfo(BaseApplication.getInstance());
    }

    /**
     * 获取手机存放总大小
     * */
    protected static String getPhoneSize(){
        String AllSize = PhoneSpaceSizeUtils.getExternalMemorySize(BaseApplication.getInstance());
        String haveSize = PhoneSpaceSizeUtils.getAvailableExternalMemorySize(BaseApplication.getInstance());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("手机外部储存总大小: ")
                .append(AllSize)
                .append("\n")
                .append("手机外部储存可用大小: ")
                .append(haveSize);
        return stringBuffer.toString();
    }


}
