package cn.edu.ustc.xk.extend;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by xuke
 * Description: 定义自己的BeanFactoryPostProcessor，看看它的执行时机，主要是验证下面这句话：
 *               BeanFactoryPostProcessor执行时机是所有的bean定义已经保存加载到beanFactory中，但是bean的实例还未创建。
 *               要让MyBeanFactoryPostProcessor发挥作用，则必须将它加入到容器当中，而且得在配置类上加上包扫描。
 * Date: 2019-10-15
 * Time: 22:05
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor.postProcessBeanFactory method invoked!");

        int count = beanFactory.getBeanDefinitionCount();
        String[] names = beanFactory.getBeanDefinitionNames();
        System.out.println("容器中含有" + count + "个bean");
        System.out.println("所有的bean是：" + Arrays.asList(names));
    }
}
