package cn.edu.ustc.xk.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * Created by xuke
 * Description: 自定义的组件(blue)如果想要使用Spring容器底层的一些组件(ApplicationContext等)
 * Date: 2019-01-18/2019-10-02
 * Time: 20:36
 *
 *   blue组件通过@Bean的方式注入，详见MainConfigOfAutowired。可以看到在注入blue组件的时候，Spring底层的一些组件
 *   也附带注册进去了。
 */
public class Blue implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {

    // 为了方便我们在别的地方使用该组件
    private ApplicationContext applicationContext;

    // 如果想要使用ApplicationContext组件的时候，我们只需要实现该方法就行了，因为底层的ApplicationContextAwareProcessor会
    // 回调该方法来完成注入操作。ApplicationContextAwareProcessor（实现了BeanPostProcessor）类中的invokeAwareInterfaces
    // 方法中的最后一行代码可以看到会对我们重写的这个方法进行回调。
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext invoked!");
         // 如果我们想要使用该applicationContext组件的话，我们可以在方法外面声明一个全局变量。
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("setBeanName invoked!");
        System.out.println("这个bean的名称为：" + name);

    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        System.out.println("setEmbeddedValueResolver invoked!");

        // 字符串解析器，比如常见的就是SpEL表达式(#{}的形式)和取出某个变量或者配置文件(${}的形式)中的值。
        String resolveString = resolver.resolveStringValue("解析得到的是：" + "${os.name}" + "  " + "#{2 * 3}");
        System.out.println(resolveString);

    }
}
