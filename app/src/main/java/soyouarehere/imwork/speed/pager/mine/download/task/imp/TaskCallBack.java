package soyouarehere.imwork.speed.pager.mine.download.task.imp;

import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;

/**
 * Created by li.xiaodong on 2018/8/2.
 */

public interface TaskCallBack {
    void progress(DownloadFileInfo info);
    void finish(DownloadFileInfo info);
    void fail(String msg);
}
