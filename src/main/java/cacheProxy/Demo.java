package cacheProxy;

import cacheProxy.proxy.CacheProxy;
import cacheProxy.service.Service;
import cacheProxy.service.ServiceImpl;

public class Demo {

    public static void main(String[] args) {

        CacheProxy cacheProxy = new CacheProxy();

        Service service = new ServiceImpl();

        Service proxyService = (Service) cacheProxy.cache(service);

        proxyService.doHardWork(2, 1);

        proxyService.doHardWork(1, 0);

        proxyService.doHardWork(2, 1);

    }

}
