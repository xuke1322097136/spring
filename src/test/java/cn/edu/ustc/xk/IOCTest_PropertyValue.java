package cn.edu.ustc.xk;

import cn.edu.ustc.xk.bean.People;
import cn.edu.ustc.xk.config.MainConfigOfPropertyValue;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;

/**
 * Created by xuke
 * Description: 测试@Value注解/@PropertySource注解/ConfigurableEnvironment的getProperty方法
 * Date: 2019-10-01
 * Time: 18:08
 */
public class IOCTest_PropertyValue {

    @Test
    public void test() {

//        可以用try-with-resource的形式完成？因为它实现了AutoClosable接口，就不用手动close了
        try (AnnotationConfigApplicationContext ioc =
                     new AnnotationConfigApplicationContext(MainConfigOfPropertyValue.class)) {

            /** 输出结果，可以看到我们的结果除了ioc容器自带的这些类和我们自定义的mainConfigOfPropertyValue和person
             *
             * org.springframework.context.annotation.internalConfigurationAnnotationProcessor
             * org.springframework.context.annotation.internalAutowiredAnnotationProcessor
             * org.springframework.context.annotation.internalCommonAnnotationProcessor
             * org.springframework.context.event.internalEventListenerProcessor
             * org.springframework.context.event.internalEventListenerFactory
             * mainConfigOfPropertyValue
             * person
             */

            Arrays.asList(ioc.getBeanDefinitionNames()).stream().forEach(System.out::println);

            System.out.println("..........................");

            // 通过id获取Person这个bean
            People people = (People) ioc.getBean("people");

            //  输出结果People(name=张三, age=18, nickName="张三zhangsan")
            System.out.println(people);

            System.out.println("..........................");

//            如何获取到运行中的值？
            ConfigurableEnvironment configurableEnvironment = ioc.getEnvironment();
            String nickName = configurableEnvironment.getProperty("people.nickName");
            System.out.println(nickName);

        }
    }

}
