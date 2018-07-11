package com.wz.servant;

import com.wz.service.Service;

import java.util.ServiceLoader;

/**
 * @projectName: Guava
 * @package: com.wz.servant
 * @className: ServantExample
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/7/11 23:43
 **/
public class ServantExample {
    public static void main(String[] args) {
        ServiceLoader<Service> services = ServiceLoader.load(Service.class);
        for (Service service : services) {
            service.show();
        }
    }
}
