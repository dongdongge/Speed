package soyouarehere.imwork.speed.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.function.log.LogUtil;

/**
 * description 文件/文件夹工具类
 */
public class FileUtil {

    private static final String TAG = FileUtil.class.getSimpleName();

    /**
     * 文件转换map数组
     *
     * @param ctx  上下文
     * @param file 文件路径
     * @return map数组
     */
    public static Map<String, Object> loadResFile(Context ctx, String file) {
        try {
            String res = loadAssertFile(ctx, file);
            return fastJson2ObjMap(res);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    private static Map<String, Object> fastJson2ObjMap(String obj) {
        return JSON.parseObject(obj, Map.class);
    }
    /**
     * 载入声明文件
     *
     * @param cxt      上下文
     * @param fileName 文件名
     * @return 文件流字符串
     * @throws IOException
     */
    private static String loadAssertFile(Context cxt, String fileName) throws IOException {
        InputStream is = cxt.getAssets().open(fileName);
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        is.close();

        return new String(buffer);
    }

    /**
     * 获取该文件夹下的所有文件名称和路径
     */
    public static Map<String, String> _findAllFiles(String parentPath) {
        Map<String, String> map = new HashMap<>();
        File file = new File(parentPath);
        if (!file.exists()) {
            System.out.println("parentPath 不存在");
            return map;
        }
        if (file.isFile()) {
            map.put(file.getName(), file.getAbsolutePath());
            System.out.println("isFile 只有一个文件");
            return map;
        }
        return getAllFileNamePath(file.getAbsolutePath());
    }

    /**
     * 获取该文件夹下的所有文件名称和路径
     */
    private static Map<String, String> getAllFileNamePath(String parentPath) {
        Map<String, String> hashMap = new HashMap<>();
        File or = new File(parentPath);
        File[] files = or.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    hashMap.put(file.getName(), file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    Map<String, String> hashMap1 = getAllFileNamePath(file.getAbsolutePath());
                    hashMap.putAll(hashMap1);
                    continue;
                }
            }
        }
        return hashMap;
    }

    /**
     * SD卡是否能用
     *
     * @return true 可用,false不可用
     */
    public static boolean _isSDCardAvailable() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            LogUtil.e(TAG, "isSDCardAvailable : SD卡不可用!", e);
            return false;
        }
    }

    /**
     * 创建一个文件夹, 存在则返回, 不存在则新建
     *
     * @param parentDirectory 父目录路径
     * @param directory       目录名
     * @return 文件，null代表失败
     */
    public static File _generateDirectory(String parentDirectory, String directory) {
        if (TextUtils.isEmpty(parentDirectory) || TextUtils.isEmpty(directory)) {
            return null;
        }
        File file = new File(parentDirectory, directory);
        boolean flag;
        if (!file.exists()) {
            flag = file.mkdir();
        } else {
            flag = true;
        }
        return flag ? file : null;
    }

    /**
     * 创建一个文件夹, 存在则返回, 不存在则新建
     *
     * @param parentDirectory 父目录
     * @param directory       目录名
     * @return 文件，null代表失败
     */
    public static File _generateDirectory(File parentDirectory, String directory) {
        if (parentDirectory == null || TextUtils.isEmpty(directory)) {
            return null;
        }
        File file = new File(parentDirectory, directory);
        boolean flag;
        if (!file.exists()) {
            flag = file.mkdir();
        } else {
            flag = true;
        }
        return flag ? file : null;
    }


    /**
     * 创建一个文件, 存在则返回, 不存在则新建
     *
     * @param catalogPath 父目录路径
     * @param name        文件名
     * @return 文件，null代表失败
     */
    public static File _generateFile(String catalogPath, String name) {
        if (TextUtils.isEmpty(catalogPath) || TextUtils.isEmpty(name)) {
            Log.e(TAG, "generateFile : 创建失败, 文件目录或文件名为空, 请检查!");
            return null;
        }
        boolean flag;
        File file = new File(catalogPath, name);
        if (!file.exists()) {
            try {
                flag = file.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "generateFile : 创建" + catalogPath + "目录下的文件" + name + "文件失败!", e);
                flag = false;
            }
        } else {
            flag = true;
        }
        return flag ? file : null;
    }

    /**
     * 创建一个文件, 存在则返回, 不存在则新建
     *
     * @param catalog 父目录
     * @param name    文件名
     * @return 文件，null代表失败
     */
    public static File _generateFile(File catalog, String name) {
        if (catalog == null || TextUtils.isEmpty(name)) {
            Log.e(TAG, "generateFile : 创建失败, 文件目录或文件名为空, 请检查!");
            return null;
        }
        boolean flag;
        File file = new File(catalog, name);
        if (!file.exists()) {
            try {
                flag = file.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "generateFile : 创建" + catalog + "目录下的文件" + name + "文件失败!", e);
                flag = false;
            }
        } else {
            flag = true;
        }
        return flag ? file : null;
    }

    /**
     * 根据全路径创建一个文件
     *
     * @param filePath 文件全路径
     * @return 文件，null代表失败
     */
    public static File _generateFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            Log.e(TAG, "generateFile : 创建失败, 文件目录或文件名为空, 请检查!");
            return null;
        }
        boolean flag;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                flag = file.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "generateFile : 创建" + file.getName() + "文件失败!", e);
                flag = false;
            }
        } else {
            flag = true;
        }
        return flag ? file : null;
    }

    /**
     * 计算文件/文件夹的大小
     *
     * @param file 文件或文件夹
     * @return 文件大小
     */
    public static long _calculateFileSize(File file) {
        if (file == null) {
            return 0;
        }

        if (!file.exists()) {
            return 0;
        }

        long result = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File subFile : files) {
                    if (subFile.isDirectory()) {
                        result += _calculateFileSize(subFile);
                    } else {
                        result += subFile.length();
                    }
                }
            }
        }
        result += file.length();
        return result;
    }

    /**
     * 删除文件/文件夹
     * 如果是文件夹，则会删除其下的文件以及它本身
     *
     * @param file file
     * @return true代表成功删除
     */
    public static boolean _deleteFile(File file) {
        if (file == null) {
            return true;
        }
        if (!file.exists()) {
            return true;
        }
        boolean result = true;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File subFile : files) {
                    if (subFile.isDirectory()) {
                        if (!_deleteFile(subFile)) {
                            result = false;
                        }
                    } else {
                        if (!subFile.delete()) {
                            result = false;
                        }
                    }
                }
            }
        }
        if (!file.delete()) {
            result = false;
        }

        return result;
    }

    //返回"/data"目录
    public static String _getDataDirectory() {
        return Environment.getDataDirectory().getAbsolutePath();
    }

    //返回"/storage/emulated/0"目录
    public static String _getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    //返回"/system"目录
    public static String _getRootDirectory() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    //返回"/cache"目录
    public static String _getDownloadCacheDirectory() {
        return Environment.getDownloadCacheDirectory().getAbsolutePath();
    }

    /**
     * @param type 所放的文件的类型，传入的参数是Environment类中的DIRECTORY_XXX静态变量
     * @return 返回"/storage/emulated/0/xxx"目录
     * 例如传入Environment.DIRECTORY_ALARMS则返回"/storage/emulated/0/Alarms"
     */
    public static String _getExternalStoragePublicDirectory(String type) {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
        //返回的目录有可能不存在
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    //返回"/data/user/0/com.xxx.xxx/cache"目录
    public static String _getCacheDir() {
        return BaseApplication.getInstance().getCacheDir().getAbsolutePath();
    }

    //返回"/data/user/0/com.xxx.xxx/files"目录
    public static String _getFilesDir() {
        return BaseApplication.getInstance().getFilesDir().getAbsolutePath();
    }

    //返回"/storage/emulated/0/Android/data/com.xxx.xxx/cache"目录
    public static String getExternalCacheDir() {
        return BaseApplication.getInstance().getExternalCacheDir().getAbsolutePath();
    }

    //返回"/storage/emulated/0"目录  获取外部储存根目录
    public static String _getExternalRootDir() {
        if (Environment.isExternalStorageEmulated()) {
            return null;
        }
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * @param type 所放的文件的类型，传入的参数是Environment类中的DIRECTORY_XXX静态变量
     * @return 返回"/storage/emulated/0/Android/data/com.xxx.xxx/files/Alarms"目录
     * 例如传入Environment.DIRECTORY_ALARMS则返回"/storage/emulated/0/Android/data/com.xxx.xxx/files/Alarms"
     */
    public static String _getExternalFilesDir(String type) {
        File file = BaseApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_ALARMS);
        //返回的目录有可能不存在
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }


    /**
     * 获取默认下载文件夹的位置 /storage/emulated/0/speed_file
     * */
    public static String _getDefaultDownloadFilePosition(){
        if (!Environment.isExternalStorageEmulated()){
            return null;
        }
        String rootPath = _getExternalStorageDirectory()+"/speed_file";
        File file = new File(rootPath);
        if (!file.exists()){
            file.mkdirs();
        }
        return rootPath;
    }

}
