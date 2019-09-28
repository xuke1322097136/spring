package cn.edu.ustc.xk.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by xuke
 * Description:
 * Date: 2019-01-19
 * Time: 21:20
 *
 * 后置处理器：初始化前后进行的处理工作。
 * 将后置处理器加入到容器中去
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    /**
     *
     * @param bean ：这个参数表示的意思是我们刚刚创建的实例对象，即初始化之前的这个对象
     * @param beanName ：刚创建的bean在容器中的名字
     * @return
     * @throws BeansException
     */
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization........." + beanName + "----->" + bean);

        // 源码里面关于返回值的说明：@return the bean instance to use, either the original（原来的bean） or a wrapped one（包装之后的bean）;
        // 我们在这直接返回原始的bean
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        System.out.println("postProcessAfterInitialization........." + beanName + "----->" + bean);

        return bean;
    }
}
