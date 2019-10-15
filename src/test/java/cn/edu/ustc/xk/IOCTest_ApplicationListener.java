package cn.edu.ustc.xk;

import cn.edu.ustc.xk.listener.ListenerConfig;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by xuke
 * Description:
 * Date: 2019-10-15
 * Time: 23:52
 */
public class IOCTest_ApplicationListener {

    @Test
    public void test(){
        try(AnnotationConfigApplicationContext applicationContext =
                    new AnnotationConfigApplicationContext(ListenerConfig.class);){

            // 自定义的事件
            applicationContext.publishEvent(new ApplicationEvent(new String("my publish event")){});
        }

    }
}
