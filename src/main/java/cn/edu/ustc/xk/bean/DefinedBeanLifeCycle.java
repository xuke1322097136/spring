package cn.edu.ustc.xk.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by xuke
 * Description:
 *     1. InitializingBean的执行时机是在源码里的invokeInitMethods方法有一个判断。
 *              除了通过实现InitializingBean来完成初始化，还可以通过@Bean注解，然后配合initMethod属性来制定初始化方法。
 *              这两种初始化方法的逻辑都是在invokeInitMethods方法【initializeBean()方法里面的一个小步骤】中执行。
 *      2. DisposableBean的执行时机是在applyBeanPostProcessorsAfterInitialization之后执行的，具体的逻辑也是
 *              在initializeBean()方法里面执行的，而且在该方法里面只是注册bean的销毁方法！！真正的销毁方法是在容器关闭
 *              之后使用的。
 *
 * Date: 2019-01-19
 * Time: 17:18
 */
public class DefinedBeanLifeCycle implements InitializingBean, DisposableBean {

    public DefinedBeanLifeCycle(){
        System.out.println("自定义的bean构造了...........");
    }

    /**
     * afterPropertiesSet方法来自于InitializingBean接口，他的调用时机可以查看下面一段源注释：
     *        Invoked by the containing {@code BeanFactory} after it has set all bean properties
     *  意思就是我们的BeanFactory创建好对象并且把bean里面所有的属性设置好值之后（即初始化好之后），就会调用该方法，
     *  其实它的作用也就是相当于我们的初始化方法。
     *  补充1：实例化(对象创建)：一般是由类创建的对象，在构造一个实例的时候需要在内存中开辟空间，即Students = new Student();
     *         初始化 ：实例化的基础上，并且为对象中的值进行赋一下初始值；
     *
     *  补充2：如果项目中bean是通过注解方式管理的，需要在初始化完成后，执行指定方法，
     *         仅仅需要在需要执行的方法上添加@PostConstruct注解即可，如下面的例子：
     *    示例： @PostConstruct
     *          public void initMethod(){
     *               System.out.println(">>>>>>>>>initMethod<<<<<<<<<<<");
     *           }
     *
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet.......自定义的bean初始化了........");
    }

    /**
     * destroy方法来自于DisposableBean接口，他的调用时机也可以查看下面一段源注释：
     *      Invoked by the containing {@code BeanFactory} on destruction of a bean.
     *  意思是BeanFactory在销毁的时候会把这些单实例bean给我们调用destroy方法给销毁掉,
     *  其实也就是在容器关闭的时候会调用该方法
     *
     * @throws Exception
     */
    public void destroy() throws Exception {
        System.out.println("destroy.........自定义的bean销毁了......");
    }
}
