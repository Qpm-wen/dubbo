package com.qpm.dubbo.test.spi;

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
    public void sayHello() {
        System.out.println("Second Robot first Wrapper： before sayHello");
        this.robot.sayHello();
        System.out.println("Second Robot first Wrapper： after sayHello");
    }
}
