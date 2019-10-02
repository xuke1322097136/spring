package cn.edu.ustc.xk.config;

import cn.edu.ustc.xk.bean.DefinedBeanLifeCycle;
import cn.edu.ustc.xk.bean.LifeCycle;
import cn.edu.ustc.xk.bean.LifeCycle02;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by xuke
 * Description: 验证bean的生命周期，集合bean下面的三各类来看：LifeCycle/LifeCycle02/DefinedBeanLifeCycle
 * Date: 2019-01-19
 * Time: 16:39
 */

/**
 * bean的生命周期：也就是 1. bean创建 ---> 2. 初始化 --->3.  销毁的过程。
 * 容器管理bean的整个完成的生命周期：我们可以自定义初始化方法和销毁方法：
 *
 *     1. 对象创建（构造器）：1）单实例：在容器启动的时候就会创建对象；2）多实例：只有当我们每次获取的时候才会创建
 *         1.1 BeanPostProcessor.postProcessBeforeInitialization方法调用
 *     2. 初始化：对象创建完成并且将里面的属性赋值好，接着会调用我们的初始化方法（initMethod指定的方法）
 *         2.1 BeanPostProcessor.postProcessAfterInitialization方法调用
 *     3. 销毁的时机：对于单实例的bean，容器一关闭bean就会销毁掉；而对于多实例的bean，容器不会管理bean的销毁，需要手动销毁掉。
 *
 *   方法一：通过@Bean注解来指定初始化方法（initMethod）和销毁方法（destroyMethod）：【示例演示：LifeCycle这个bean】
 *   方法二：通过让我们定义的bean实现InitializingBean接口（定义初始化逻辑），实现DisposableBean接口（定义销毁逻辑）；【示例演示：DefinedBeanLifeCycle这个bean】
 *   方法三：可以使用JSR250规范里面的两个注解：【示例演示：LifeCycle02这个bean】
 *             @PostConstruct: The PostConstruct annotation is used on a method that needs to be executed
 *                             after dependency injection is done（在bean创建完成且属性赋值完成的时候调用该注解标注的方法来执行初始化）
 *             @PreDestroy: The PreDestroy annotation is used on methods as a callback notification to signal that the instance is in the process of being removed by the container
 *                           意思是就是在容器移除bean之前，会给一个回调通知，通知我们进行清理工作。
 *   方法四：BeanPostProcessor接口：bean的后置处理器，在bean的初始化前后进行一些处理工作：
 *              postProcessBeforeInitialization方法：由源码里面的注释可以知道：它是在任何初始化（initMethod或者afterPropertiesSet或者@PostConstruct标注的方法）调用之前调用；
 *              postProcessAfterInitialization方法：它是在初始化之后调用。
 *
 * 下面我们MyBeanPostProcessor里面的postProcessBeforeInitialization和postProcessAfterInitialization打上断点分析下源码：
 *
 *              摘自AbstractAutowireCapableBeanFactory里面的部分源码：可以看到我们的applyBeanPostProcessorsBeforeInitialization是在初始化之前执行的
 *                                                                    这里的初始化可以是上面说的三种方式：initMethod或者afterPropertiesSet或者@PostConstruct标注的方法
 *
 *              if (mbd == null || !mbd.isSynthetic()) {
 * 			         wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 * 	             	}
 * 		       try {
 * 			         invokeInitMethods(beanName, wrappedBean, mbd);
 * 		           }
 *
 * 		       if (mbd == null || !mbd.isSynthetic()) {
 * 		    	   wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 * 		}
 *     applyBeanPostProcessorsBeforeInitialization方法的具体实现：
 *     得到我们容器中所有的BeanPostProcessor，然后逐个postProcessBeforeInitialization方法，一旦返回了null，后面的BeanPostProcessor将不会再 执行，直接跳出for循环。
 *
 *   而且我们发现下面的一个细节（摘自AbstractAutowireCapableBeanFactory里面的doCreateBean()方法）：
 *        try {
 *  * 			  populateBean(beanName, mbd, instanceWrapper); // 负责装配我们创建出来的对象里面的属性
 *  * 		      exposedObject = initializeBean(beanName, exposedObject, mbd);// 调用我们的BeanPostProcessor和初始化方法
 *  *           }
 *
 *  也就是说，执行顺序是这样的：
 *     BeanPostProcessors的执行原理：
 *         populateBean(beanName, mbd, instanceWrapper); // 负责装配我们创建出来的bean里面的属性（比如说getter/setter方法等）
 *         initializeBean ：{
 *             applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 *             invokeInitMethods(beanName, wrappedBean, mbd);// 执行自定义初始化方法
 *             applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 *          }
 *
 *  Spring底层对BeanPostProcessor的使用：
 *          1.）ApplicationContextAwareProcessor：帮我们在组件里面注册IOC容器。具体实现的话我们只需要让组件实现ApplicationContextAware接口就行
 *                                                 实现里面的setApplicationContext方法，然后将我们的IOC容器保存起来就行。这些操作都是由该处理器完成的。
 *                                                 当该组件要用到ApplicationContext组件的时候，IOC容器启动时会在帮我们创建该组件的时候，
 *                                                 同时利用setApplicationContext方法创建一个ApplicationContext对象。
 *          2.）InitDestroyAnnotationBeanPostProcessor：它其实就是用来处理@PostConstruct注解和@PreDestroy注解的；
 *          3.）AutowiredAnnotationBeanPostProcessor：处理我们的自动装配值注解@Autowired注解的；
 *          4.）AsyncAnnotationBeanPostProcessor：处理@Asyc注解的。
 *          还有很多的BeanPostProcessor，在这不做展开了。
 *
 */
@ComponentScan("cn.edu.ustc.xk.config")
@Configuration
public class MainConfigOfLifeCycle {

  //  @Scope("prototype")
    @Bean(initMethod = "init", destroyMethod = "destory")
    public LifeCycle lifeCycle(){
        return new LifeCycle();
    }

    @Bean
    public DefinedBeanLifeCycle definedBeanLifeCycle(){
        return new DefinedBeanLifeCycle();
    }

    @Bean
    public LifeCycle02 dLifeCycle02(){
        return new LifeCycle02();
    }

}
