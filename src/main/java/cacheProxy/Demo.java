package cacheProxy;

import cacheProxy.proxy.CacheProxy;
import cacheProxy.service.Service;
import cacheProxy.service.ServiceImpl;
import cacheProxy.service.ServiceImplCommon;

import java.nio.file.Paths;

public class Demo {

    public static void main(String[] args) {

        String rootFolderPath = "C:\\Users\\Sam\\Desktop\\CacheSerialized";
        CacheProxy cacheProxy = new CacheProxy(Paths.get(rootFolderPath));

        Service service = new ServiceImpl();

        Service proxyService = (Service) cacheProxy.cache(service);
        proxyService.doHardWork(2, 1);
        proxyService.doHardWork(1, 0);
        proxyService.doHardWork(2, 1);
        proxyService.minNumber(1,10);

        Service anotherService = new ServiceImplCommon();
        Service proxyServiceAnother = (Service) cacheProxy.cache(anotherService);
        proxyServiceAnother.minNumber(1, 8);
        proxyServiceAnother.maxNumber(1, 10);
        proxyServiceAnother.maxNumber(1, 10);
        proxyServiceAnother.doHardWork(1, 10);


    }

}
