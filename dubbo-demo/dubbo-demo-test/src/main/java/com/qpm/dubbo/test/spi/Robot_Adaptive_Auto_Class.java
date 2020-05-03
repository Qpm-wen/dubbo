package com.qpm.dubbo.test.spi;
import org.apache.dubbo.common.extension.ExtensionLoader;
/**
 * Adaptive 类自动生成的代码。
 *
 * @Author kangqiang.w
 * @Date 2020/5/3
 */
public class Robot_Adaptive_Auto_Class {

    /**
     * 这是 Dubbo SPI 自动生成的代码
     * https://dubbo.apache.org/zh-cn/docs/source_code_guide/adaptive-extension.html
     * 有时，有些拓展并不想在框架启动阶段被加载，而是希望在拓展方法被调用时，根据运行时参数进行加载
     */
    public class Robot$Adaptive implements com.qpm.dubbo.test.spi.Robot {
        public void sayHelloAndReturnSelf(org.apache.dubbo.common.URL arg0)  {
            // 1、URL 参数是否为 null， 因此 URL 参数是必须的
            if (arg0 == null)
                throw new IllegalArgumentException("url == null");
            org.apache.dubbo.common.URL url = arg0;

            // 2、根据 URL 获取选择哪个扩展实例
            String extName = url.getParameter("robot", "bumblebee");
            if(extName == null)
                throw new IllegalStateException("Failed to get extension (com.qpm.dubbo.test.spi.Robot) name from url (" + url.toString() + ") use keys([robot])");

            // 3、获取实例
            com.qpm.dubbo.test.spi.Robot extension
                    = (com.qpm.dubbo.test.spi.Robot)ExtensionLoader.getExtensionLoader(com.qpm.dubbo.test.spi.Robot.class).getExtension(extName);

            // 4、执行实例方法
            extension.sayHelloAndReturnSelf(arg0);
        }
    }

}
