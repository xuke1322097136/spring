package cn.edu.ustc.xk.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by xuke
 * Description:
 * Date: 2019-01-18
 * Time: 19:28
 */
public class WindowsCondition implements Condition {
    /**
     * ConditionContext ： 判断条件能使用的上下文环境
     * AnnotatedTypeMetadata ：标注了Conditional注解的注释信息
     *
     */
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        /**
         *   ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();// IOC容器使用的bean工厂（负责创建对象和装配对象使用的）
         *   ClassLoader classLoader = context.getClassLoader();// 获取类加载器
         *   BeanDefinitionRegistry registry = context.getRegistry();// 获取bean定义的注册类，所有的bean定义都得在BeanDefinitionRegistry里面注册
         */
        // 判断是否是windows系统
        Environment environment = context.getEnvironment();// 获取当前环境信息
        String property = environment.getProperty("os.name");
        // Windows中的W是大写的
        if (property.contains("Windows")){
            return true;
        }
        return false;
    }
}
