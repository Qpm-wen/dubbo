package com.qpm.dubbo.test.spi;

/**
 * @Author kangqiang.w
 * @Date 2020/5/1
 */
public class Bumblebee implements Robot{
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Bumblebee");
    }
}
