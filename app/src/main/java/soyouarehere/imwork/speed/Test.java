package soyouarehere.imwork.speed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import soyouarehere.imwork.speed.util.IdCardValidator;
import soyouarehere.imwork.speed.util.IdcardUtils;

/**
 * Created by li.xiaodong on 2018/8/2.
 */

public class Test {


    public static void main(String[] args) {
        List<String> strings = new ArrayList();
        for (int i = 0; i < 6; i++) {
            strings.add("蛮王" + i);
        }
        List<String> stringListSub = strings.subList(0, 3);
        System.out.println(stringListSub.toString());
        strings.clear();
        System.out.println(strings.toString());
        System.out.println(stringListSub.toString());
        strings.addAll(stringListSub);
        System.out.println(stringListSub);
    }


}



