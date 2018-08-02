package soyouarehere.imwork.speed.pager.mine.download.task;

/**
 * Created by li.xiaodong on 2018/8/2.
 */

public interface TaskCallBack {
    void progress(DownloadFileInfo info);
    void finish(DownloadFileInfo info);
    void fail(String msg);
}
