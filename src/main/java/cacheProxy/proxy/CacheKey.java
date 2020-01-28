package cacheProxy.proxy;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class CacheKey implements Serializable {

    private final static long serialVersionUID = 100L;

    private String keyForMethod;

    private Object[] args;

    CacheKey(String keyForMethod, Object[] args) {
        this.keyForMethod = keyForMethod;
        this.args = args;
    }

    String getKeyForMethod() {
        return keyForMethod;
    }

    private Object[] getArgs() {
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

    @Override
    public String toString() {
        return "{" +
                "keyForMethod='" + keyForMethod + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
