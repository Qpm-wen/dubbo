package com.qpm.dubbo.test.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;

/**
 * @Author kangqiang.w
 * @Date 2020/5/1
 */
@Activate(value = "robot:autobot", group = "autobotLeader")
public class OptimusPrime implements Robot{

    @Override
    public Robot sayHelloAndReturnSelf(URL url) {
        System.out.println("hello, I am Optimus Prime.");
        return this;
    }
}
