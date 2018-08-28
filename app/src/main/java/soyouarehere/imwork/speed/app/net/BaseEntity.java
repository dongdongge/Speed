package soyouarehere.imwork.speed.app.net;

import com.google.gson.Gson;

/**
 * 基本数据模型
 */

public class BaseEntity<T> {


    /**
     * status : 0
     * result : {"code":"1000","msg":"成功"}
     * data : {}
     */

    private int code;
    private String msg;
    private T body;

    @Override
    public String toString() {
        return new Gson().toString();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
