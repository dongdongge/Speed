package soyouarehere.imwork.speed.pager.mine.download.task.single;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import soyouarehere.imwork.speed.function.log.LogUtil;

/**
 * Created by li.xiaodong on 2018/8/7.
 */

public class TaskFutureTask extends FutureTask<String> {

    private String name;

    public TaskFutureTask(@NonNull Callable<String> callable) {
        super(callable);
        this.name = ((CallableTask) callable).getName();
    }

//    public TaskFutureTask(@NonNull Runnable runnable, String result) {
//        super(runnable, result);
//    }

    @Override
    protected void done() {
        super.done();
        if (isCancelled()) {
            LogUtil.e("已经取消任务",""+name);
        } else {
            LogUtil.e("已经结束任务",""+name);
        }

    }
}
