package com.qpm.dubbo.test.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;

/**
 * @Author kangqiang.w
 * @Date 2020/5/3
 */
public class AutoRobot implements Robot{

    @Override
    public Robot sayHelloAndReturnSelf(URL url) {
        System.out.println("Adptive class");
        return this;
    }
}
