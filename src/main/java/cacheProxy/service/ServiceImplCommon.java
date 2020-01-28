package cacheProxy.service;

import cacheProxy.annotations.CacheStore;
import cacheProxy.annotations.CacheableElement;

@CacheableElement(store = CacheStore.DISK)
public class ServiceImplCommon implements Service {

    @Override
    public boolean doHardWork(int a, int b) {
        return a < b;
    }

    @Override
    public int maxNumber(int a, int b) {
        return Math.max(a,b);
    }

    @Override
    public int minNumber(int a, int b) {
        return Math.min(a,b);
    }
}
