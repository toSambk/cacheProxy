package cacheProxy.service;

import cacheProxy.annotations.CacheStore;
import cacheProxy.annotations.CacheableElement;

public class ServiceImpl implements Service {

    @Override
    @CacheableElement(store = CacheStore.DISK)
    public boolean doHardWork(int a, int b) {
        return a > b;
    }

    @Override
    public int maxNumber(int a, int b) {
        return Math.max(a,b);
    }

    @Override
    @CacheableElement(keyName = "testName")
    public int minNumber(int a, int b) {
        return Math.min(a, b);
    }

}
