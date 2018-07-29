package soyouarehere.imwork.speed.util.http;

import java.io.InputStream;


public class RealResponse {
    public InputStream inputStream;
    public InputStream errorStream;
    public int code;
    public long contentLength;
    public Exception exception;
}
