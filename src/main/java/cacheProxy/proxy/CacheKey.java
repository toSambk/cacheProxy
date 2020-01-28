package cacheProxy.proxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class CacheKey {

    private Method method;

    private Object[] args;

    public CacheKey(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CacheKey)) return false;
        CacheKey key = (CacheKey) o;
        return getMethod().equals(key.getMethod()) &&
                Arrays.equals(getArgs(), key.getArgs());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getMethod());
        result = 31 * result + Arrays.hashCode(getArgs());
        return result;
    }

}
