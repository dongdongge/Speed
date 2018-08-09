package soyouarehere.imwork.speed.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by li.xiaodong on 2018/8/7.
 */

public final class CloseUtils {

    public static void closeQuietly(Closeable closeable){
        if (null != closeable){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
