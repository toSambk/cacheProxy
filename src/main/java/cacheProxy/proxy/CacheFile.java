package cacheProxy.proxy;

import java.io.Serializable;

public class CacheFile implements Serializable {

    private final static long serialVersionUID = 1L;

    private CacheKey cacheKey;

    private Object result;

    CacheFile(CacheKey cacheKey, Object result) {
        this.cacheKey = cacheKey;
        this.result = result;
    }

    public CacheKey getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(CacheKey cacheKey) {
        this.cacheKey = cacheKey;
    }

    Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
