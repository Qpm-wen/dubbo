package com.qpm.dubbo.test.spi;

import org.apache.dubbo.common.extension.SPI;

/**
 * Java SPI Interface Demo from
 * http://dubbo.apache.org/zh-cn/docs/source_code_guide/dubbo-spi.html
 *
 * @Author kangqiang.w
 * @Date 2020/5/1
 */
@SPI
public interface Robot {

    void sayHello();
}
