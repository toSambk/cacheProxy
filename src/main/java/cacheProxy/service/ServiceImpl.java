package cacheProxy.service;

import cacheProxy.annotations.CacheableElement;

public class ServiceImpl implements Service {

    @Override
    @CacheableElement
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
