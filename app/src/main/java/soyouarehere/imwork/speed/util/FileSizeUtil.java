package soyouarehere.imwork.speed.util;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;

import soyouarehere.imwork.speed.util.log.LogUtil;

/**
 * Created by li.xiaodong on 2018/8/1.
 */

public class FileSizeUtil {
    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值
    /**
     * 获取文件指定文件的指定单位的大小
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath,int sizeType){
        File file=new File(filePath);
        long blockSize=0;
        try {
            if(file.isDirectory()){
                blockSize = getFileSizes(file);
            }else{
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小","获取失败!");
        }
        return FormetFileSize(blockSize, sizeType);
    }
    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath){
        File file=new File(filePath);
        long blockSize=0;
        try {
            if(file.isDirectory()){
                blockSize = getFileSizes(file);
            }else{
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小","获取失败!");
        }
        return FormetFileSize(blockSize);
    }
    /**
     * 获取指定文件大小
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception
    {
        long size = 0;
        if (file.exists()){
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        }
        else{
            file.createNewFile();
            Log.e("获取文件大小","文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception
    {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++){
            if (flist[i].isDirectory()){
                size = size + getFileSizes(flist[i]);
            }
            else{
                size =size + getFileSize(flist[i]);
            }
        }
        return size;
    }
    /**
     * 转换文件大小
     * @param fileS
     * @return
     */
    public static String FormetFileSize(long fileS)
    {
        String fileSizeString = "";
        String wrongSize="0B";
        if(fileS==0){
            return wrongSize;
        }
        if (fileS < 1024){
            fileSizeString = FormatFileSize(fileS,1) + "B";
        }
        else if (fileS < 1048576){
            fileSizeString = FormatFileSize(fileS,1024) + "KB";
        }
        else if (fileS < 1073741824){
            fileSizeString = FormatFileSize(fileS,1048576) + "MB";
        }
        else{
            fileSizeString = FormatFileSize(fileS,1073741824) + "GB";
        }
        return fileSizeString;
    }

    public static double FormatFileSize(long long1,long long2){
        return BigDecimalUtils._div(long1,long2,2);
    }

    /**
     * 转换文件大小,指定转换的类型
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS,int sizeType)
    {
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong=FormatFileSize(fileS,1);
                break;
            case SIZETYPE_KB:
                fileSizeLong=FormatFileSize(fileS, 1024);
                break;
            case SIZETYPE_MB:
                fileSizeLong=FormatFileSize(fileS, 1048576);
                break;
            case SIZETYPE_GB:
                fileSizeLong=FormatFileSize(fileS, 1073741824);
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * timeDiffSize         : 时间差  代表了一段时间内
     * fileDiffSize         : 文件差  代表了一段时间内的文件大小变动的情况
     * remainingFileSize    : 剩余文件差 代表了还剩余到少文件
     *  result              : 一段时间内的下载速度和按照这个速度下载完剩余文件需要的时间是多少
     * */
    public static String[] formatProgressSpeed(long fileDiffSize, long timeDiffSize, long remainingFileSize) {
        LogUtil.e("文件和时间大小", fileDiffSize, timeDiffSize);
        String endBS = "B/s";
        String endKBS = "KB/s";
        String endMBS = "MB/s";
        String endGBS = "GB/s";
        String tempEnd = "";
        // 将时间格式化为 S
        double time = BigDecimalUtils._div(timeDiffSize, 1000, 2);
        double fileSize = fileDiffSize;
        double remianFileSize = remainingFileSize;
        if (fileDiffSize < 1024) {
            tempEnd = endBS;
        }
        if (fileDiffSize > 1024) {
            tempEnd = endKBS;
            fileSize = BigDecimalUtils._div(fileDiffSize, 1024, 2);
            remianFileSize = BigDecimalUtils._div(remainingFileSize, 1024, 2);
        }
        if (fileDiffSize > 1048576) {
            tempEnd = endMBS;
            fileSize = BigDecimalUtils._div(fileDiffSize, 1048576, 2);
            remianFileSize = BigDecimalUtils._div(remainingFileSize, 1048576, 2);
        }
        if (fileDiffSize > 1073741824) {
            tempEnd = endGBS;
            fileSize = BigDecimalUtils._div(fileDiffSize, 1073741824, 2);
            remianFileSize = BigDecimalUtils._div(remainingFileSize, 1073741824, 2);
        }
        // 计算得出当前速度是多少
        double speed = BigDecimalUtils._div(fileSize, time, 2);
        // 计算得出剩余文件还有多少秒可以下载完成
        double reamingTime = BigDecimalUtils._div(remianFileSize,speed,2);
        String shengyuTime = TimeUtls._formatTime((long) reamingTime);
        // 将得到的秒转化为日时分秒
        LogUtil.e("strings",speed + tempEnd," 剩余"+shengyuTime);
        return new String[]{speed + tempEnd," 剩余"+shengyuTime};
    }

}
