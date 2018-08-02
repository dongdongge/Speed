package soyouarehere.imwork.speed;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.NumberFormat;

/**
 * Created by li.xiaodong on 2018/8/2.
 */

public class Test {


    public static void main(String[] args){
//        NumberFormat format = NumberFormat.getPercentInstance();
//        format.setMaximumFractionDigits(2);
//        long l1 = 26565656;
//        long l5 = 6865465;
//        String precent = format.format((double) l5/l1);
//        System.out.println(precent);
//        String url1 = "http://192.168.22.30:8080/static/file/download/我不是药神.mp4";
//        String url2 = "http://192.168.22.30:8080";
        String url3 = "http://192.168.22.30:8080/static/file/download/我不是药神.mp4?name=lxd&age=9";
//        String url4 = "http://192.168.22.30/static/file/download/我不是药神.mp4";
//        String url5 = "http://192.168.22.30";
//        String url6 = "http://abc";
//        String url7 = "www.baidu.com";
//        String url8 = "https://www.baidu.com";
//        System.out.println("url1==>"+url1+"==>"+checkUrl(url1));
//        System.out.println("url2==>"+url2+"==>"+checkUrl(url2));
        System.out.println("url3==>"+url3+"==>"+checkUrl(url3));
//        System.out.println("url4==>"+url4+"==>"+checkUrl(url4));
//        System.out.println("url5==>"+url5+"==>"+checkUrl(url5));
//        System.out.println("url6==>"+url6+"==>"+checkUrl(url6));
//        System.out.println("url7==>"+url7+"==>"+checkUrl(url7));
//        System.out.println("url8==>"+url8+"==>"+checkUrl(url8));
//        checkUrl(null);

    }

    public static boolean checkUrl(@NonNull String url) {
        if (url.contains("?")){
            url = url.substring(0,url.indexOf('?'));
        }
        if (url.contains("//")&&url.indexOf("/",8)!=-1){
            int index = url.indexOf("/",8);
            url = url.substring(0,index);
        }
        System.out.println(url);
        return url.matches("^((https|http|ftp|rtsp|mms)?://)"
                + "+(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" + "|" + "([0-9a-z_!~*'()-]+\\.)*"
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." + "[a-z]{2,6})" + "(:[0-9]{1,10})?"
                + "((/?)|" + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$");
    }

}
