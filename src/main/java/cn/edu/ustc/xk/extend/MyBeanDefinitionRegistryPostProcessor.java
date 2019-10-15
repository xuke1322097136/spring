package cn.edu.ustc.xk.extend;

import cn.edu.ustc.xk.bean.Color;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.stereotype.Component;

/**
 * Created by xuke
 * Description: 测一下BeanDefinitionRegistryPostProcessor的执行时机，具体的执行结果可以在IOCTest_Extend里面可以看到
 * Date: 2019-10-15
 * Time: 22:55
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    //BeanDefinitionRegistry其实就是bean定义信息的存储中心，以后BeanFactory就是按照BeanDefinitionRegistry里面
    // 保存的每一个bean定义信息来创建bean实例。
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        int count = registry.getBeanDefinitionCount();
        System.out.println("MyBeanDefinitionRegistryPostProcessor中bean的数量为：" + count);
        RootBeanDefinition beanDefinition = new RootBeanDefinition(Color.class);
        /**
         * 也可以通过构建器来构建BeanDefinition，二者的效果是一样的。
         *         AbstractBeanDefinition beanDefinition1 = BeanDefinitionBuilder.
         *                 rootBeanDefinition(Color.class).
         *                 getBeanDefinition();
         */
        registry.registerBeanDefinition("hello", beanDefinition);
    }

    // 这个方法来源于BeanFactoryPostProcessor
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor中含有的bean的数量："
                + beanFactory.getBeanDefinitionCount());
    }
}
