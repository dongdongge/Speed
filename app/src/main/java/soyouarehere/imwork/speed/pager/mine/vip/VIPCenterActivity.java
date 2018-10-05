package soyouarehere.imwork.speed.pager.mine.vip;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
import soyouarehere.imwork.speed.pager.mine.download.task.MyThreadPoolExecutor;
import soyouarehere.imwork.speed.function.log.LogUtil;

public class VIPCenterActivity extends BaseActivity {

    @BindView(R.id.vip_center_bt_test)
    Button vip_center_bt_test;

    @Override
    public void DataLoadError() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vipcenter;
    }

    @Override
    public void create(Bundle savedInstanceState) {
        vip_center_bt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxJavaTest();
            }
        });
    }

    public void RxJavaTest(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    LogUtil.e(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.execute(runnable);
        MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.execute(runnable);
        MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.execute(runnable);
        LogUtil.e("---先开三个---");
        LogUtil.e("核心线程数" + MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.getCorePoolSize());
        LogUtil.e("线程池数" + MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.getPoolSize());
        LogUtil.e("队列任务数" + MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.getQueue().size());
        MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.execute(runnable);
        MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.execute(runnable);
        MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.execute(runnable);
        MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.execute(runnable);
        MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.execute(runnable);
        MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.execute(runnable);
        LogUtil.e("---再开6个---");
        LogUtil.e("核心线程数" + MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.getCorePoolSize());
        LogUtil.e("线程池数" + MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.getPoolSize());
        LogUtil.e("队列任务数" + MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.getQueue().size());
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtil.e("----8秒之后----");
        LogUtil.e("核心线程数" + MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.getCorePoolSize());
        LogUtil.e("线程池数" + MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.getPoolSize());
        LogUtil.e("队列任务数" + MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.getQueue().size());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtil.e("----10秒之后----");
        LogUtil.e("核心线程数" + MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.getCorePoolSize());
        LogUtil.e("线程池数" + MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.getPoolSize());
        LogUtil.e("队列任务数" + MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.getQueue().size());
    }

}
