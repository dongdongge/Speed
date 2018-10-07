package soyouarehere.imwork.speed.framework.http_processor.base;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class HttpCallBack<Result> implements ICallBack{


    @Override
    public void onSuccess(String response) {

        Gson gson = new Gson();
        Class<?> clz = analysisClassInfo(this);
        Result objectResult = (Result) gson.fromJson(response,clz);
        onSuccess(objectResult);
    }

    /**
     * 将转化后的结果返回给子类
     * @param result
     */
    public abstract void onSuccess(Result result);

    /**
     * 反射获取 该对象的所有参数-> 然后 获取第一个参数类型 -> 强转为类类型；
     * @param object
     * @return
     */
   public static Class<?> analysisClassInfo(Object object){
       Type genType = object.getClass().getGenericSuperclass();
       Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
       return (Class<?>)params[0];
   }
}
