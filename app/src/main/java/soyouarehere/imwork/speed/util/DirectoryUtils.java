package soyouarehere.imwork.speed.util;

import android.content.Context;
import android.os.Environment;

/**
 * Created by li.xiaodong on 2018/7/31.
 */

public class DirectoryUtils {


    private static final String TAG = "DirectoryUtils";

    public static void _getEnvironmentDirectories() {
        //:/system
        String rootDir = Environment.getRootDirectory().toString();
        System.out.println("Environment.getRootDirectory()==>:" + rootDir);

        //:/data 用户数据目录
        String dataDir = Environment.getDataDirectory().toString();
        System.out.println("Environment.getDataDirectory()==>:" + dataDir);

        //:/cache 下载缓存内容目录
        String cacheDir = Environment.getDownloadCacheDirectory().toString();
        System.out.println("Environment.getDownloadCacheDirectory()==>:" + cacheDir);

        //这个不一定是外部存储
        String storageDir = Environment.getExternalStorageDirectory().toString();
        System.out.println("Environment.getExternalStorageDirectory()==>:" + storageDir);

        //:/mnt/sdcard/Pictures或者/storage/emulated/0/Pictures或者/storage/sdcard0/Pictures
        String publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        System.out.println("Environment.getExternalStoragePublicDirectory()==>:" + publicDir);

        //获取SD卡是否存在:mounted
        String storageState = Environment.getExternalStorageState().toLowerCase();
        System.out.println("Environment.getExternalStorageState()==>:" + storageState);

        //设备的外存是否是用内存模拟的，是则返回true。(API Level 11)
        boolean isEmulated = Environment.isExternalStorageEmulated();
        System.out.println("Environment.isExternalStorageEmulated()==>:" + isEmulated);

        //设备的外存是否是可以拆卸的，比如SD卡，是则返回true。(API Level 9)
        boolean isRemovable = Environment.isExternalStorageRemovable();
        System.out.println("Environment.isExternalStorageRemovable()==>:" + isRemovable);
    }

    public static void _getApplicationDirectories(Context context) {

        //获取当前程序路径 应用在内存上的目录 :/data/data/com.xxx/files
        String filesDir = context.getFilesDir().toString();
        System.out.println("context.getFilesDir()==>:" + filesDir);

        //应用的在内存上的缓存目录 :/data/data/com.xxx/cache
        String cacheDir = context.getCacheDir().toString();
        System.out.println("context.getCacheDir()==>:" + cacheDir);

        //应用在外部存储上的目录 :/storage/emulated/0/Android/data/com.xxx/files/Movies
        String externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES).toString();
        System.out.println("context.getExternalFilesDir()==>:" + externalFilesDir);

        //应用的在外部存储上的缓存目录 :/storage/emulated/0/Android/data/com.xxx/cache
        String externalCacheDir = context.getExternalCacheDir().toString();
        System.out.println("context.getExternalCacheDir()==>:" + externalCacheDir);

        //获取该程序的安装包路径 :/data/app/com.xxx.apk
        String packageResourcePath = context.getPackageResourcePath();
        System.out.println("context.getPackageResourcePath()==>:" + packageResourcePath);

        //获取程序默认数据库路径 :/data/data/com.xxx/databases/mufeng
        String databasePat = context.getDatabasePath("mufeng").toString();
        System.out.println("context.getDatabasePath(\"mufeng\")==>:" + databasePat);
    }
}
