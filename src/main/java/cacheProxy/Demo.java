package cacheProxy;

import cacheProxy.proxy.CacheProxy;
import cacheProxy.service.Service;
import cacheProxy.service.ServiceImpl;
import cacheProxy.service.ServiceImplCommon;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;

public class Demo {

    public final static Logger log = Logger.getLogger(Demo.class);

    public static void main(String[] args) {

        String rootFolderPath = "C:\\Users\\Sam\\Desktop\\CacheSerialized";

        CacheProxy cacheProxy = new CacheProxy(new File(rootFolderPath));

        Service service = new ServiceImpl();

        Service proxyService = (Service) cacheProxy.cache(service);
        proxyService.doHardWork(2, 1);
        proxyService.doHardWork(1, 0);
        proxyService.doHardWork(2, 1);
        proxyService.minNumber(1, 10);

        Service anotherService = new ServiceImplCommon();
        Service proxyServiceAnother = (Service) cacheProxy.cache(anotherService);
        proxyServiceAnother.minNumber(1, 8);
        proxyServiceAnother.maxNumber(1, 10);
        proxyServiceAnother.maxNumber(1, 10);
        proxyServiceAnother.doHardWork(1, 10);


    }

}
