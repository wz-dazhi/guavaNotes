package com.wz.service;

/**
 * @projectName: Guava
 * @package: com.wz.service
 * @className: SimpleService
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/7/11 23:39
 **/
public class SimpleService implements Service {
    @Override
    public void show() {
        System.out.println("hi i com from service.");
    }
}
