package cn.edu.ustc.xk;

import cn.edu.ustc.xk.extend.ExtendConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by xuke
 * Description: 用来测试ExtendConfig配置类。可以看到最后的输出结果。
 * Date: 2019-10-15
 * Time: 22:14
 * *    可以看到IOCTest_Extend类的输出结果（只有MyBeanFactoryPostProcessor的输出结果）：
 *  * *    postProcessBeanFactory method invoked!
 *  *  *     容器中含有8个bean
 *  *  *  所有的bean是：[org.springframework.context.annotation.internalConfigurationAnnotationProcessor,
 *  *  *               org.springframework.context.annotation.internalAutowiredAnnotationProcessor,
 *  *  *               org.springframework.context.annotation.internalCommonAnnotationProcessor,
 *  *  *                org.springframework.context.event.internalEventListenerProcessor,
 *  *  *               org.springframework.context.event.internalEventListenerFactory,
 *  *  *               extendConfig,
 *  *  *                myBeanFactoryPostProcessor,
 *  *  *                color]
 *  *  *           color constructor invoked!
 *  *     可以看到在8个bean里面已经有color这个bean，只是当时还没有实例化，真正的实例化输出的是： color constructor invoked!
 *  *     由此可见，beanFactory的执行时机确实是bean的定义信息已经被加载，但是实力还没有被创建。
 *
 *  加上MyBeanDefinitionRegistryPostProcessor后的输出结果：
 *        MyBeanDefinitionRegistryPostProcessor中bean的数量为：9
 *        MyBeanDefinitionRegistryPostProcessor中含有的bean的数量：10
 *        MyBeanFactoryPostProcessor.postProcessBeanFactory method invoked!
 *        容器中含有10个bean
 *        所有的bean是：[org.springframework.context.annotation.internalConfigurationAnnotationProcessor,
 *        org.springframework.context.annotation.internalAutowiredAnnotationProcessor,
 *        org.springframework.context.annotation.internalCommonAnnotationProcessor,
 *        org.springframework.context.event.internalEventListenerProcessor,
 *        org.springframework.context.event.internalEventListenerFactory,
 *        extendConfig,
 *        myBeanDefinitionRegistryPostProcessor,
 *        myBeanFactoryPostProcessor,
 *        color,
 *        hello]
 *     color constructor invoked!
 *     color constructor invoked!
 *
 *     可以看到最先执行的是MyBeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry方法，接着执行的是
 *     MyBeanDefinitionRegistryPostProcessor.postProcessBeanFactory方法，接着执行的才是MyBeanFactoryPostProcessor里面
 *     的逻辑。
 */
public class IOCTest_Extend {

    @Test
    public void test(){
        try(AnnotationConfigApplicationContext applicationContext =
                    new AnnotationConfigApplicationContext(ExtendConfig.class)) {

        }
    }
}
