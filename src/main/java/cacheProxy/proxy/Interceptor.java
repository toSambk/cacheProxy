package cacheProxy.proxy;

import cacheProxy.Demo;
import cacheProxy.annotations.CacheableElement;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Interceptor implements MethodInterceptor {

    final private Map<CacheKey, Object> cacheMap = new HashMap<>();

    final private File rootFolder;

    Interceptor(final File rootFolder) {
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
        if (methodIsAnnotated) {
            cacheableElement = method.getAnnotation(CacheableElement.class);
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
            result = cacheMap.get(currentKey);
            System.out.println("Объект " + currentKey.getKeyForMethod() + " загружен из кэша");
            Demo.log.info("Объект " + currentKey.getKeyForMethod() + " загружен из кэша JVM");
        } else {
            System.out.println("Объект " + currentKey.getKeyForMethod() + " не найден в кэше. Добавляем...");
            result = proxy.invokeSuper(obj, args);
            cacheMap.put(currentKey, result);
            System.out.println("Добавлено - " + currentKey.getKeyForMethod() + " с результатом " + result);
            Demo.log.warn("Объект " + currentKey.getKeyForMethod() + " был не найден и добавлен в кэш на JVM");
        }
        return result;
    }

    private Object cacheDisk(CacheKey currentKey, Object obj, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Поиск кэша на диске...");
        File[] files = rootFolder.listFiles((dir, filename) -> filename.equals(currentKey.toString()));
        File curFile;
        switch (files.length) {

            case 0:
                System.out.println("Объект " + currentKey.getKeyForMethod() + " не найден в кэшэ. Добавляем...");
                Object result = proxy.invokeSuper(obj, args);
                curFile = new File(rootFolder + File.separator + currentKey);
                CacheFile cacheFile = new CacheFile(currentKey, result);
                addCachedFile(curFile, cacheFile);
                System.out.println("Объект " + currentKey.getKeyForMethod() + " добавлен в кэш");
                Demo.log.warn("Объект " + currentKey.getKeyForMethod() + " был не найден и добавлен в кэш на диске");
                return result;

            case 1:
                curFile = files[0];
                CacheFile cachedFile = getCachedFile(curFile);
                System.out.println("Объект " + currentKey.getKeyForMethod() + " загружен из кэша с результатом " + cachedFile.getResult() );
                Demo.log.info("Объект " + currentKey.getKeyForMethod() + " загружен из кэша на диске");
                return cachedFile.getResult();

            default:
                throw new RuntimeException();

        }
    }


    private void addCachedFile(File file, CacheFile cacheFile) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            objectOutputStream.writeObject(cacheFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CacheFile getCachedFile(File file) {
        CacheFile result = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            result = (CacheFile) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }


}
