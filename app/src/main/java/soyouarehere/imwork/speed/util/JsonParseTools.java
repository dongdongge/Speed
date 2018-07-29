package soyouarehere.imwork.speed.util;

import android.support.annotation.Nullable;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class JsonParseTools {


    public static String mapParseJsonString(@Nullable Map<String,String> map){
        if (map==null||map.isEmpty()) return "-1";
        JSONObject jsonObject = new JSONObject();
        try {
            for (String key:map.keySet()) {
                jsonObject.put(key,map.get(key));
            }
            return jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "-1";
    }
    public static String mapMapParseJsonString(Map<String,Map<String,String>> mapMap){
        JSONObject jsonObject = new JSONObject();
        try {
            for (String key:mapMap.keySet()) {
                jsonObject.put(key,mapParseJsonString(mapMap.get(key)));
            }
            return jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "-1";
    }


    public static String listMapParseJsonString(List<Map<String,Object>> mapList){
        return "-1";

    }
    public static void jsonParseMap(String jsonString){

    }
    public static void jsonParseListMap(String jsonString){

    }

}
