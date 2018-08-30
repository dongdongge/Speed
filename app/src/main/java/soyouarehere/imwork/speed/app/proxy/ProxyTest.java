package soyouarehere.imwork.speed.app.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {


    public static void main(String args[]){
        Iface iface = (Iface) Proxy.newProxyInstance(Iface.class.getClassLoader(), new Class<?>[]{Iface.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Integer integer = (Integer) args[0];
                String str = (String) args[1];
                System.out.println("参数: "+integer+","+str);
                System.out.println("方法名: "+method.getName());
                Annotation[] annotations = method.getAnnotations();
                System.out.println("注解: "+annotations[0].toString());
                return null;
            }
        });
        iface.getData(4,"lxd");
    }


    public interface Iface{
        @Deprecated
        public void getData(int page,String name);
    }

}
