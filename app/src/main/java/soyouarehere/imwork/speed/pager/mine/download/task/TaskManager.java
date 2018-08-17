package soyouarehere.imwork.speed.pager.mine.download.task;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import soyouarehere.imwork.speed.pager.mine.download.resouce.ResourceFile;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;
import soyouarehere.imwork.speed.pager.mine.download.task.broken.BrokenRunnable;
import soyouarehere.imwork.speed.util.log.LogUtil;

/**
 * Created by li.xiaodong on 2018/8/7.
 */

public class TaskManager {

    private OkHttpClient mClient;//OKHttpClient;
    /**
     * 存放任务的集合；进行取消再次进行任务的
     */
    private static Map<String, BrokenRunnable> brokenRunnableHashMap = new HashMap<>();

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
    public void executeCallableTask(BrokenRunnable brokenRunnable) {
        if (!brokenRunnableHashMap.containsKey(brokenRunnable.getName())) {
            LogUtil.e("将任放在内存中 方便暂停 删除等");
            brokenRunnableHashMap.put(brokenRunnable.getName(), brokenRunnable);
            // 将任务放进线程池中去执行任务;
            MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.submit(brokenRunnable);
        } else {
            LogUtil.e("任务列表中存在相同的任务");
        }
    }
    /**
     * 移除任务
     * */
    public void removeBrokenRunnable(String name) {
        if (brokenRunnableHashMap.containsKey(name)) {
            brokenRunnableHashMap.remove(name);
        }
    }
    /**
     * 停止任务 将线程打断，停止执行任务；
     */
    public void pauseBrokenRunnable(String name) {
        if (brokenRunnableHashMap.containsKey(name)) {
            brokenRunnableHashMap.get(name).onThreadPause();
        }
    }

    /**
     * 继续下载
     */
    public void resumeContinueDownload(String name) {
        if (brokenRunnableHashMap.containsKey(name)) {
            brokenRunnableHashMap.get(name).onThreadResume();
        }
    }

    /**
     * 准备任务   只有一个URL
     */
    public void prepareTask(String url, PrepareCallBack prepareCallBack) {
        MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.execute(new ReadyTask(url, prepareCallBack));
    }

    /**
     * 准备任务 携带信息；
     * */
    public void prepareTask(ResourceFile file,PrepareCallBack prepareCallBack){
        // 检查本地是否存在该任务，或者文件，判断该文件是否允许断点续传，

    }

    public interface PrepareCallBack {
        void onSuccess(DownloadFileInfo fileInfo);

        /**
         * code :0 检查url合法失败
         *      :1 根据url创建文件名字失败
         *      :2 该任务已经在任务列表
         *      :3 创建文件失败
         *      :4 获取文件大小失败
         * */
        void fail(int code,String msg);
    }
}
