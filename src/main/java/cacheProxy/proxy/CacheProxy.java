package cacheProxy.proxy;

import cacheProxy.NotADirectoryException;
import net.sf.cglib.proxy.Enhancer;

import java.io.File;

public class CacheProxy {

    final private Interceptor interceptor;

    public CacheProxy(File rootFolder) {
        if (!rootFolder.isDirectory()) {
            throw new NotADirectoryException();
        }
        interceptor = new Interceptor(rootFolder);
    }

    public Object cache(final Object original) {
        System.out.println("------");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(original.getClass());
        enhancer.setCallback(interceptor);
        return enhancer.create();
    }

}
