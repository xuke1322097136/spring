package cn.edu.ustc.xk.config;

import cn.edu.ustc.xk.bean.RainBow;
import cn.edu.ustc.xk.bean.Yellow;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Created by xuke
 * Description:
 * Date: 2019-01-18
 * Time: 20:57
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     * AnnotationMetadata：获取当前类的注解信息
     * BeanDefinitionRegistry： BeanDefinition注册类，把所有需要添加到容器中的bean，
     *                          可以调用BeanDefinitionRegistry.registerBeanDefinition()方法来进行手工注册
     *
     */
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        boolean b1 = registry.containsBeanDefinition("cn.edu.ustc.xk.bean.Blue");
        boolean b2 = registry.containsBeanDefinition("cn.edu.ustc.xk.bean.Yellow");

        // 我们假定的是：如果存在Blue这个bean和Yellow这个bean的话，我们就注册RainBow类型的bean
        if(b1 && b2){
            // 指定bean的定义信息，即bean的类型信息,scope等等
            RootBeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);
            // 注册一个名为rainBow的bean，第一参数是bean的名字
            registry.registerBeanDefinition("rainbow", beanDefinition);
        }
    }
}
