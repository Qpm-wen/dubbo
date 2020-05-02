package com.qpm.dubbo.test.spi;

/**
 * @Author kangqiang.w
 * @Date 2020/5/2
 */
public class OptimusPrimePlus implements Robot{

    private Robot robot;

    public OptimusPrimePlus(Robot robot) {
        this.robot = robot;
    }


    @Override
    public void sayHello() {
        System.out.println("Optimus Prime Plus");
        this.robot.sayHello();
    }
}
