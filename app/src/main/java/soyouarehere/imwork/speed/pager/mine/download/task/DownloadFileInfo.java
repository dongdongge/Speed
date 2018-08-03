package soyouarehere.imwork.speed.pager.mine.download.task;

import com.google.gson.Gson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by li.xiaodong on 2018/8/2.
 */

public class DownloadFileInfo {

    private String url;
    /**
     * 文件大小 b
     * */
    private long total;
    /**
     * 当前下载文件的大小
     * */
    private long progress;
    private String fileName;
    /**
     * 文件总大小
     * */
    private String showSize;
    /**
     * 文件下载进度百分比
     * */
    private String showProgress;
    /**
     * 当前下载的文件的大小
     * */
    private String showProgressSize;
    /**
     * 文件的路径
     * */
    private String filePath;


    public DownloadFileInfo(String url) {
        this.url = url;
    }

    public String getShowProgressSize() {
        return showProgressSize;
    }

    public void setShowProgressSize(String showProgressSize) {
        this.showProgressSize = showProgressSize;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getShowSize() {
        return showSize;
    }

    public void setShowSize(String showSize) {
        this.showSize = showSize;
    }

    public String getShowProgress() {
        return showProgress;
    }

    public void setShowProgress(String showProgress) {
        this.showProgress = showProgress;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}