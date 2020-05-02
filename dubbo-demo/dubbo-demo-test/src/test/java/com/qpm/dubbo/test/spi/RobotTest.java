package com.qpm.dubbo.test.spi;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    @Test
    public void sayHello() throws Exception {
        System.out.println("Dubbo SPI");
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();
        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();
    }


    @Test
    public void testWrapper() {
        System.out.println("Dubbo SPI Wrapper");
        Robot robot = ExtensionLoader.getExtensionLoader(Robot.class)
                .getAdaptiveExtension();    // 自适应
        robot.sayHello();
    }

}