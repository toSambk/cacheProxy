package cacheProxy.service;

public class ServiceImpl implements Service {

    @Override
    public boolean doHardWork(int a, int b) {
        return a > b;
    }

    @Override
    public int maxNumber(int a, int b) {
        return Math.max(a,b);
    }

}
