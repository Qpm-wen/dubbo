package com.qpm.dubbo.test.spi;

/**
 * Robot 扩展 第一层包装
 *
 * @Author kangqiang.w
 * @Date 2020/5/2
 */
public class RobotFirstWrapper implements Robot{

    private Robot robot;

    public RobotFirstWrapper(Robot robot) {
        this.robot = robot;
    }


    @Override
    public void sayHello() {
        System.out.println("First Robot first Wrapper： before sayHello");
        this.robot.sayHello();
        System.out.println("First Robot first Wrapper： after sayHello");
    }
}
