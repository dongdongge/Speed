package soyouarehere.imwork.speed.pager.mine.download.task.bean;

import com.google.gson.Gson;

/**
 * Created by li.xiaodong on 2018/8/2.
 *
 * 文件下载的信息类 ，在创建任务之初最先构建一个信息类 用来记录此时的下载信息，
 */

public class DownloadFileInfo{

    private String url;
    /**
     * 文件大小 b
     * */
    private long total;
    /**
     * 当前下载文件的大小
     * */
    private long progress;
    /**
     * 文件名称
     * */
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
     * 文件的路径 /storage/emulute/0/speed_file
     * */
    private String filePath;

    /**
     * 文件状态  downloading(正在下载中) stop(下载停止) 下载完成(complete)
     * */

    private String fileStatue;

    /**
     * 下载速度
     * */
    private String downloadSpeed;

    /**
     * 是否支持断点续传  通过构建downloadFile 获取headers 判断得出
     * */
    private boolean isSupportInterrupt;

    /**
     * 当前使用多少个线程去下载该文件
     * */
    private int threadNumber;

    /**
     * 下载该文件所耗用的时长是多少  long类型 转为时间；
     * */
    private long downloadFileTime;

    /**
     * 创建任务。文件 的第一时间； long 类型的时间戳 自行转化为可视化时间；
     * */
    private long createTaskTime;
    /**
     * 文件类型
     * */
    private String fileType;

    /**
     * 错误信息
     * */
    private String msg;

    /**
     * 是否支持断点续传的原因
     * */
    private String reasonMsg;


    public String getReasonMsg() {
        return reasonMsg;
    }

    public void setReasonMsg(String reasonMsg) {
        this.reasonMsg = reasonMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSupportInterrupt() {
        return isSupportInterrupt;
    }

    public void setSupportInterrupt(boolean supportInterrupt) {
        isSupportInterrupt = supportInterrupt;
    }

    public String getFileType() {
        return fileType;
    }


    public void setFileType(String fileType) {
        this.fileType = fileType;
    }


    public int getThreadNumber() {
        return threadNumber;
    }

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    public long getDownloadFileTime() {
        return downloadFileTime;
    }

    public void setDownloadFileTime(long downloadFileTime) {
        this.downloadFileTime = downloadFileTime;
    }

    public long getCreateTaskTime() {
        return createTaskTime;
    }

    public void setCreateTaskTime(long createTaskTime) {
        this.createTaskTime = createTaskTime;
    }

    public String getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(String downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public String getFileStatue() {
        return fileStatue;
    }

    public void setFileStatue(String fileStatue) {
        this.fileStatue = fileStatue;
    }

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
