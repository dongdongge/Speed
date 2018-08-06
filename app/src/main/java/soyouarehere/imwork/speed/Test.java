package soyouarehere.imwork.speed;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.NumberFormat;

/**
 * Created by li.xiaodong on 2018/8/2.
 */

public class Test {


    public static void main(String[] args) {
        long long1 = 24563*10000;
        long long2 = 95126;
        float result = (float) long1 / long2;
        System.out.println(result);
        System.out.println((int)long1 / long2);

    }

}
