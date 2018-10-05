package soyouarehere.imwork.speed.pager.mine.download.task;

import okhttp3.OkHttpClient;
import soyouarehere.imwork.speed.function.rxbus.RxBus;
import soyouarehere.imwork.speed.function.rxbus.RxBusEvent;
import soyouarehere.imwork.speed.pager.mine.download.resouce.ResourceFile;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;
import soyouarehere.imwork.speed.pager.mine.download.task.broken.BrokenRunnable;
import soyouarehere.imwork.speed.pager.mine.download.task.imp.TaskCallBack;
import soyouarehere.imwork.speed.function.log.LogUtil;

/**
 * Created by li.xiaodong on 2018/8/7.
 */

public class TaskManager {

    private OkHttpClient mClient;//OKHttpClient;

    public TaskManager() {
        mClient = new OkHttpClient.Builder().build();
    }

    public static TaskManager getInstance() {
        return TaskManagerHelp.INSTANCE;
    }

    private static class TaskManagerHelp {
        private static final TaskManager INSTANCE = new TaskManager();
    }

    /**
     * 执行下载任务 Callable  进行判断
     */
    public void executeCallableTask(DownloadFileInfo fileInfo) {
        // 需要进行判断  当前是否存在该任务
        // 将任务放进线程池中去执行任务;
        LogUtil.e("继续下载  管理类executeCallableTask +2");
        MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.submit(generateTask(fileInfo));
    }

    /**
     * 生成任务的方法
     * */
    public BrokenRunnable generateTask(DownloadFileInfo fileInfo) {
        LogUtil.e("继续下载  管理类generateTask +3");
        return new BrokenRunnable(fileInfo, new TaskCallBack() {
            @Override
            public void progress(DownloadFileInfo info) {
                LogUtil.e("进度" + info.getShowProgress() + "当前文件大小" + info.getShowProgressSize() + "文件总大小" + info.getShowSize());
                RxBus.getInstance().post(new RxBusEvent<DownloadFileInfo>(info));
            }

            @Override
            public void finish(DownloadFileInfo info) {
                RxBus.getInstance().post(new RxBusEvent<DownloadFileInfo>(info));
                LogUtil.e("下载完成" + info.toString());
                // 发送消息更新已完成列表  删除下载中的列表
            }

            @Override
            public void fail(String msg) {
                LogUtil.e("下载失败" + msg);
            }
        });

    }

    /**
     * 继续下载
     */
    public void resumeContinueDownload(DownloadFileInfo info) {
        LogUtil.e("继续下载  管理类resumeContinueDownload +1");
        executeCallableTask(info);
    }

    /**
     * 准备任务   只有一个URL
     */
    public void prepareTask(String url, PrepareCallBack prepareCallBack) {
        MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.execute(new ReadyTask(url, prepareCallBack));
    }

    /**
     * 准备任务 携带信息；
     */
    public void prepareTask(ResourceFile file, PrepareCallBack prepareCallBack) {
        // 检查本地是否存在该任务，或者文件，判断该文件是否允许断点续传，

    }

    public interface PrepareCallBack {
        void onSuccess(DownloadFileInfo fileInfo);

        /**
         * code :0 检查url合法失败
         * :1 根据url创建文件名字失败
         * :2 该任务已经在任务列表
         * :3 创建文件失败
         * :4 获取文件大小失败
         */
        void fail(int code, String msg);
    }
}
