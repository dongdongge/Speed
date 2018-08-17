package soyouarehere.imwork.speed.app.constant;

/**
 * description 常量类
 * modify by
 */
public interface BaseConstants {
    /*------------------------------------------ app文件存储模块  ------------------------------------------*/
    String APP_IMAGE = "image";               //应用图片存储目录名
    String APP_TMP = "temp";                  //应用临时文件目录名

    /*------------------------------------------ preference存储模块  -------------------------------------*/

    String APP_SHARE = "app_share";                         // 默认preference名字
    String APP_SHARE_USER = "app_user_share";               //用户sharePreference
    String APP_CONFIG = "app_config";                       // 默认的全局配置模块
    // 存放下载信息的数据库 名字 包括下载的状态等 文件放在哪个文件夹下；
    String APP_DOWNLOAD_INFO = "downloadInfo";

    String APP_BASE_URL = "base_url";

    // 存放下载文件的位置信息 默认是位于外部存储卡根目录下(/storage/emulated/0/speed_file)  可以更改位置等信息；
    String APP_DOWNLOAD_POSITION = "download";
}
