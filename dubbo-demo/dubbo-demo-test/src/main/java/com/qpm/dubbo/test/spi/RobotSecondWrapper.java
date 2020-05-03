package com.qpm.dubbo.test.spi;

import org.apache.dubbo.common.URL;

/**
 * Robot 扩展 第二层 Wrapper
 *
 * @Author kangqiang.w
 * @Date 2020/5/3
 */
public class RobotSecondWrapper implements Robot{

    private Robot robot;

    public RobotSecondWrapper(Robot robot) {
        this.robot = robot;
    }

    @Override
    public Robot sayHelloAndReturnSelf(URL url) {
        System.out.println("Second Robot first Wrapper： before sayHello");
        this.robot.sayHelloAndReturnSelf(url);
        System.out.println("Second Robot first Wrapper： after sayHello");
        return this;
    }
}
