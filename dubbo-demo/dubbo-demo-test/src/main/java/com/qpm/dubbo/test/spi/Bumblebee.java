package com.qpm.dubbo.test.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;

/**
 * @Author kangqiang.w
 * @Date 2020/5/1
 */
@Activate(value = "robot:autobot", group = "autoBotFighterGroup")
public class Bumblebee implements Robot{
    @Override
    public Robot sayHelloAndReturnSelf(URL url) {
        System.out.println("Hello, I am Bumblebee");
        return this;
    }
}
