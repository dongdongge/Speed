package soyouarehere.imwork.speed.framework.http_processor.base;

/**
 * 网络层传输的数据都是二进制 json xml 字节流 文件(转为二进制) 图片， 共同点都是string 大部分
 * 网络访问
 * 泛型；
 */

public interface ICallBack {

    void onSuccess(String response);
    void onFailure(String msg,String option);
}
