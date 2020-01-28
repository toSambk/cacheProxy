package cacheProxy.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Interceptor implements MethodInterceptor {

    final private Map<CacheKey, Object> cacheMap = new HashMap<>();

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        CacheKey currentKey = new CacheKey(method, args);
        Object result;

        if(cacheMap.containsKey(currentKey)) {
            System.out.println("Результат найден в кэше");
            result = cacheMap.get(currentKey);
        } else {
            System.out.println("Результат не найден в кэше");
            result = proxy.invokeSuper(obj, args);
            cacheMap.put(currentKey, result);
        }

        return result;

    }



}
