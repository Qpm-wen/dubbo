package com.qpm.dubbo.test.spi;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    /**
     * 基础的 SPI 测试
     * STEP <br>
     *     1 编写 Root.java 接口
     *     2 编写 optimusPrime 实现
     *     3 编写 bumblebee 实现
     *     4 编写 com.qpm.dubbo.test.spi.Robot 的配置
     *     5 运行测试
     * @throws Exception
     */
    @Test
    public void sayHello() throws Exception {
        System.out.println("Dubbo SPI");
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();
        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();
    }

    /**
     * 测试 SPI ExtensionLoader 的 getDefaultExtension 对象
     * - 为 Robot.java 的 SPI 注解增加 value， 即设置默认值
     * - 重复 {@link #sayHello()} 的设置步骤
     * - 运行测试
     *
     * @throws Exception
     */
    @Test
    public void loadDefaultExtension() throws Exception {
        System.out.println("Dubbo load default Extension");
        Robot defaultExtension = ExtensionLoader.getExtensionLoader(Robot.class).getDefaultExtension();
        assertTrue(defaultExtension instanceof Bumblebee);
    }


    /**
     * 测试多层 Wrapper
     * - 根据 Dubbo SPI 定义 RobotFirstWrapper 第一层封装
     * - 根据 Dubbo SPI 定义 RobotSecondWrapper 第二层封装
     * - 在配置文件中增加定义
     *      # 双层包装类
     *      firstWrapper=com.qpm.dubbo.test.spi.RobotFirstWrapper
     *      secondWrapper=com.qpm.dubbo.test.spi.RobotSecondWrapper
     * - {@link #loadDefaultExtension()}
     */
    @Test
    public void testWrapperAndMultiWrapper() {
        System.out.println("Dubbo SPI Wrapper adn Multi Wrapper");
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        extensionLoader.getDefaultExtension().sayHello();
        // 双层 Wrapper console:
        /*
            First Robot first Wrapper： before sayHello
            Second Robot first Wrapper： before sayHello
            Hello, I am Bumblebee
            Second Robot first Wrapper： after sayHello
            First Robot first Wrapper： after sayHello
         */
    }

}