package com.qpm.dubbo.test.spi;

import org.apache.dubbo.common.URL;
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
        optimusPrime.sayHelloAndReturnSelf(null);
        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        assertTrue(bumblebee.sayHelloAndReturnSelf(null) instanceof Bumblebee);
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
        extensionLoader.getDefaultExtension().sayHelloAndReturnSelf(null);
        // 双层 Wrapper console:
        /*
            First Robot first Wrapper： before sayHello
            Second Robot first Wrapper： before sayHello
            Hello, I am Bumblebee
            Second Robot first Wrapper： after sayHello
            First Robot first Wrapper： after sayHello
         */
    }

    /**
     * 测试 自适应方法
     * 注意:
     * 1 被 {@link org.apache.dubbo.common.extension.Adaptive} 注解的方法，一定要带有 {@link org.apache.dubbo.common.URL 参数}
     * not found url parameter or url attribute in parameters of method sayHello
     *
     * 测试步骤：
     * 0 注释上述包装类测试用例
     * 1 为 sayHello 方法增加 {@link org.apache.dubbo.common.extension.Adaptive} 注解，并加上 {@link URL} 参数
     * 2 通过 URL 调用测试用例
     *
     * 原理：
     * extensionLoader.getAdaptiveExtension(); 返回的是一个 Dubbo SPI 自己生成的类，在这里测试用例，生成的代码
     * 已经贴在 {@link Robot_Adaptive_Auto_Class}，具体的调用细节可以跳转查看
     *
     */
    @Test
    public void testAdaptive() {
        System.out.println("Dubbo SPI Adaptive ");
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        Robot autoRobot = extensionLoader.getAdaptiveExtension();   // 源码从这里开始读
        assertTrue(
                autoRobot.sayHelloAndReturnSelf(URL.valueOf("dubbo://127.0.0.1:20880/RobotService?robot=bumblebee"))
                instanceof
                Bumblebee);

        // console: Hello, I am Bumblebee
        assertTrue(
                autoRobot.sayHelloAndReturnSelf(URL.valueOf("dubbo://127.0.0.1:20880/RobotService?robot=optimusPrime"))
                instanceof
                OptimusPrime);
        // console：hello, I am Optimus Prime.
    }

    /**
     * 自激活(被动激活) 机制实现
     *
     */
    public void testActivate() {

    }


}