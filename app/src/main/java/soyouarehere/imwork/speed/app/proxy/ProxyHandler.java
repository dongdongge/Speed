package soyouarehere.imwork.speed.app.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 */
public class ProxyHandler implements InvocationHandler {
    private final  String TAG = ProxyHandler.class.getSimpleName();
    Object targetObj;
    public Object newProxyInstance(Object targetObj){
        return Proxy.newProxyInstance(targetObj.getClass().getClassLoader(),targetObj.getClass().getInterfaces(),this);
    }

    /**
     * 方法解析
     * @param proxy   指代我们所代理的那个真实对象
     * @param method  指代的是我们所要调用真实对象的某个方法的Method对象
     * @param args    指代的是调用真实对象某个方法时接受的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret;
        Log.e(TAG,"method name： "+method.getName());
        ret = method.invoke(targetObj,args);
        return ret;
    }
}
