package cacheProxy.proxy;

import java.util.Arrays;
import java.util.Objects;

public class CacheKey {

    private String keyForMethod;

    private Object[] args;

    public CacheKey(String keyForMethod, Object[] args) {
        this.keyForMethod = keyForMethod;
        this.args = args;
    }

    public String getKeyForMethod() {
        return keyForMethod;
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CacheKey)) return false;
        CacheKey cacheKey = (CacheKey) o;
        return getKeyForMethod().equals(cacheKey.getKeyForMethod()) &&
                Arrays.equals(getArgs(), cacheKey.getArgs());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getKeyForMethod());
        result = 31 * result + Arrays.hashCode(getArgs());
        return result;
    }
}
