package cacheProxy.proxy;

import cacheProxy.annotations.CacheableElement;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Interceptor implements MethodInterceptor {

    final private Map<CacheKey, Object> cacheMap = new HashMap<>();

    final private Path rootFolder;

    Interceptor(final Path rootFolder) {
        this.rootFolder = rootFolder;
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        boolean clazzIsAnnotated = method.getDeclaringClass().isAnnotationPresent(CacheableElement.class);
        boolean methodIsAnnotated = method.isAnnotationPresent(CacheableElement.class);
        if (!(clazzIsAnnotated || methodIsAnnotated)) {
            System.out.println("Метод " + method.getName() + " запрещен для кэширования");
            return proxy.invokeSuper(obj, args);
        }

        CacheableElement cacheableElement;
        if(methodIsAnnotated) {
            cacheableElement  = method.getAnnotation(CacheableElement.class);
        } else {
            cacheableElement = method.getDeclaringClass().getAnnotation(CacheableElement.class);
        }

        String keyName = cacheableElement.keyName().equals("DEFAULT") ?
                    method.getDeclaringClass().getCanonicalName() + "." + method.getName() : cacheableElement.keyName();

        CacheKey currentKey = new CacheKey(keyName, args);
        Object result;
        switch (cacheableElement.store()) {
            case JVM:
                result = cacheJVM(currentKey, obj, args, proxy);
                break;

            case DISK:
                result = cacheDisk(currentKey, obj, args, proxy);
                break;

            default:
                throw new RuntimeException();
        }
        return result;
    }


    private Object cacheJVM(CacheKey currentKey, Object obj, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Поиск кэша в JVM...");
        Object result;
        if (cacheMap.containsKey(currentKey)) {
            System.out.println("Результат найден в кэше");
            result = cacheMap.get(currentKey);
        } else {
            System.out.println("Результат не найден в кэше. Добавляем...");
            result = proxy.invokeSuper(obj, args);
            cacheMap.put(currentKey, result);
            System.out.println("Добавлено - " + currentKey.getKeyForMethod() + " с результатом " + result);
        }
        return result;
    }

    private Object cacheDisk(CacheKey currentKey, Object obj, Object[] args, MethodProxy proxy) throws Throwable {


        return null;
    }







}
