package com.qpm.dubbo.test.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.config.configcenter.Constants;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.jupiter.api.Test;

import java.util.List;

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
     *  根据 URL 的配置，获得一系列的 扩展对象 Extension。
     *
     */
    @Test
    public void testActivate_Single() {
        System.out.println("Dubbo SPI Activate");
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        List<Robot> robots = extensionLoader.getActivateExtension(
                URL.valueOf("dubbo://127.0.0.1:20880/RobotService?robot=bumblebee"),
                "robot"
        );
        assertEquals(1, robots.size());
    }

    /**
     * 自激活(被动激活) 机制实现
     *  根据 URL 的配置，获得一系列的 扩展对象 Extension。
     *
     */
    @Test
    public void testActivate_Multi() {
        System.out.println("Dubbo SPI Activate");
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        List<Robot> robots = extensionLoader.getActivateExtension(
                URL.valueOf("dubbo://127.0.0.1:20880/RobotService"),
                new String[]{
                        // 通过这个组合，会直接过滤默认值，假如设置了 bumblebee 做默认值，
                        // 那机制不管是否设置 bumblebee ，都会返回 Activate 数组
                        CommonConstants.REMOVE_VALUE_PREFIX + CommonConstants.DEFAULT_KEY,
                        "optimusPrime",
                        "bumblebee",
                }
        );
        assertEquals(2, robots.size());
    }


    /**
     * 自激活(被动激活) 机制实现
     *  根据 URL 的配置，获得一系列的 扩展对象 Extension。
     *
     *  根据 group 值，做组级别过滤
     *
     */
    @Test
    public void testActivate_Group() {
        System.out.println("Dubbo SPI Activate");
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        // robot = autobot 的 扩展有两个，但 group = autobotLeader 只有一个
        List<Robot> robots = extensionLoader.getActivateExtension(
                URL.valueOf("dubbo://127.0.0.1:20880/RobotService?robot=autobot"),
                new String[]{},
                "autobotLeader"
        );
        assertEquals(1, robots.size());
    }

}