package com.qpm.dubbo.test.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * Java SPI Interface Demo from
 * http://dubbo.apache.org/zh-cn/docs/source_code_guide/dubbo-spi.html
 *
 * @Author kangqiang.w
 * @Date 2020/5/1
 */
@SPI("bumblebee")
public interface Robot {

    /**
     * sayHelloAndReturnSelf 方法
     *
     * @PARAM URL 为了设置 自适应 方法
     * @Return Robot 返回自身实现类
     */
    @Adaptive
    Robot sayHelloAndReturnSelf(URL url);
}
