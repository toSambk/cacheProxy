package cacheProxy.service;

import cacheProxy.annotations.CacheableElement;

@CacheableElement
public class ServiceImplCommon implements Service {

    @Override
    public boolean doHardWork(int a, int b) {
        return true;
    }

    @Override
    public int maxNumber(int a, int b) {
        return 5;
    }

    @Override
    public int minNumber(int a, int b) {
        return 0;
    }
}
