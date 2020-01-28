package cacheProxy.proxy;

import net.sf.cglib.proxy.Enhancer;

public class CacheProxy {

    final private Interceptor interceptor;

    public CacheProxy() {
        interceptor = new Interceptor();
    }

    public Object cache(Object original) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(original.getClass());
        enhancer.setCallback(interceptor);

        return enhancer.create();
    }

}
