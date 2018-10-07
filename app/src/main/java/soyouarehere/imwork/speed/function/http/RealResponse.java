package soyouarehere.imwork.speed.function.http;

import java.io.InputStream;

/**
 * 返回实体类
 */
public class RealResponse {
    public InputStream inputStream;
    public InputStream errorStream;
    public int code;
    public long contentLength;
    public Exception exception;
}
