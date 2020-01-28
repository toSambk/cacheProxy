package cacheProxy.proxy;

import net.sf.cglib.proxy.Enhancer;

import java.nio.file.Path;

public class CacheProxy {

    final private Interceptor interceptor;

    public CacheProxy(final Path rootFolderPath) {
        interceptor = new Interceptor(rootFolderPath);
    }

    public Object cache(final Object original) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(original.getClass());
        enhancer.setCallback(interceptor);
        return enhancer.create();
    }

}
