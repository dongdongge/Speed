package soyouarehere.imwork.speed.pager.mine.download.newtask;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by li.xiaodong on 2018/7/31.
 */

public class CheckUrl {

    public static boolean checkUrlValid(String urlString) {
        if (urlString == null || urlString.length() <= 0) {
            return false;
        }
        int i = 3;
        while (i > 0) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(8000);
                int state = connection.getResponseCode();
                if (state == 200) {
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
                i--;
            }
        }
        return false;
    }

}
