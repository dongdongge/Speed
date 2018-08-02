package soyouarehere.imwork.speed.pager.mine.download.task;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by li.xiaodong on 2018/8/2.
 */

public class DownloadFileInfo {

    private String url;
    private long total;
    private long progress;
    private String fileName;
    private String showSize;
    private String showProgress;
    private String filePath;


    public DownloadFileInfo(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "DownloadFileInfo{" +
                "url='" + url + '\'' +
                ", total=" + total +
                ", progress=" + progress +
                ", fileName='" + fileName + '\'' +
                ", showSize='" + showSize + '\'' +
                ", showProgress='" + showProgress + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
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
