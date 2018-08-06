package soyouarehere.imwork.speed.pager.mine.download.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MyThreadPoolExecutor {
    public static final String LOG_TAG = "MyThreadPoolExecutor";

    /**
     * 获取CPU 核数
     * */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * 线程池中核心线程池的大小
     * */
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    /**
     * 线程池中最大可以容纳多少线程池
     * */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    /**
     * 非核心线程的超时时长，当系统中非核心线程闲置时间超过keepAliveTime之后，则会被回收。
     * 如果ThreadPoolExecutor的allowCoreThreadTimeOut属性设置为true，则该参数也表示核心线程的超时时长
     * */
    private static final int KEEP_ALIVE = 1;
    /**
     * unit 第三个参数的单位，有纳秒、微秒、毫秒、秒、分、时、天等
     * */
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;


    /**
     *
     * 1.ArrayBlockingQueue：这个表示一个规定了大小的BlockingQueue，ArrayBlockingQueue的构造函数接受一个int类型的数据，
     * 该数据表示BlockingQueue的大小，存储在ArrayBlockingQueue中的元素按照FIFO（先进先出）的方式来进行存取。
     *
     * 2.LinkedBlockingQueue：这个表示一个大小不确定的BlockingQueue，在LinkedBlockingQueue的构造方法中可以传一个int类型
     * 的数据，这样创建出来的LinkedBlockingQueue是有大小的，也可以不传，不传的话，
     * LinkedBlockingQueue的大小就为Integer.MAX_VALUE，
     * */
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(128);



}
