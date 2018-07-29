package soyouarehere.imwork.speed.app.net;

/**
 * 基本数据模型
 * Created by Administrator on 2017/9/4 0004.
 */

public class BaseEntity<T> {


    /**
     * status : 0
     * result : {"code":"1000","msg":"成功"}
     * data : {}
     */

    private int status;
    private ResultBean result;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static class ResultBean {
        /**
         * code : 1000
         * msg : 成功
         */

        private String code;
        private String msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static class DataBean {
    }
}
