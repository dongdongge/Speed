package soyouarehere.imwork.speed.pager.mine.download.task;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadPoolExecutor {
    public static final String LOG_TAG = "MyThreadPoolExecutor";

    /**
     * 获取CPU 核数
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * 线程池中核心线程池的大小
     */
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    /**
     * 线程池中最大可以容纳多少线程
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    /**
     * 非核心线程的超时时长，当系统中非核心线程闲置时间超过keepAliveTime之后，则会被回收。
     * 如果ThreadPoolExecutor的allowCoreThreadTimeOut属性设置为true，则该参数也表示核心线程的超时时长
     */
    private static final int KEEP_ALIVE = 1;
    /**
     * unit 第三个参数的单位，有纳秒、微秒、毫秒、秒、分、时、天等
     */
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;


    /**
     * 1.ArrayBlockingQueue：这个表示一个规定了大小的BlockingQueue，ArrayBlockingQueue的构造函数接受一个int类型的数据，
     * 该数据表示BlockingQueue的大小，存储在ArrayBlockingQueue中的元素按照FIFO（先进先出）的方式来进行存取。
     * <p>
     * 2.LinkedBlockingQueue：这个表示一个大小不确定的BlockingQueue，在LinkedBlockingQueue的构造方法中可以传一个int类型
     * 的数据，这样创建出来的LinkedBlockingQueue是有大小的，也可以不传，不传的话，
     * LinkedBlockingQueue的大小就为Integer.MAX_VALUE，
     */
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(128);
    /**
     * 当线程空闲一段时间后 回收线程;
     */
    private static final int KEEP_ALIVE_SECONDS = 5;

    /**
     *
     *创建新的线程
     * */
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "MyThread #" + mCount.getAndIncrement());
        }
    };
    private static class DefaultThreadFactory implements ThreadFactory{
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private  ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private  String namePrefix;
        DefaultThreadFactory() {
            SecurityManager var1 = System.getSecurityManager();
            this.group = var1 != null?var1.getThreadGroup():Thread.currentThread().getThreadGroup();
            this.namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
        }
        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread var2 = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
            if(var2.isDaemon()) {
                var2.setDaemon(false);
            }
            if(var2.getPriority() != 5) {
                var2.setPriority(5);
            }
            return var2;
        }
    }
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;

    private MyThreadPoolExecutor() {

    }

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, new DefaultThreadFactory());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }

    public static MyThreadPoolExecutor getInstance() {
        return MyThreadPoolExecutorHelp.INSTANCE;
    }

    private static class MyThreadPoolExecutorHelp {
        static final MyThreadPoolExecutor INSTANCE = new MyThreadPoolExecutor();
    }


}
