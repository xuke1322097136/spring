package cn.edu.ustc.xk.extend;

import cn.edu.ustc.xk.bean.Color;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xuke
 * Description: 在cn.edu.ustc.xk.extend报下面讲解Spring的扩展原理
 * Date: 2019-10-15
 * Time: 21:57
 */
/**
 *  扩展原理：
 *    1. BeanPostProcessor：bean后置处理器，bean创建对象初始化前后进行拦截工作的；
 *    2. BeanFactoryPostProcessor：beanFactory的后置处理器。根据源码的注释信息可以知道：它的调用时机是在BeanFactory
 *        标准初始化之后调用，用来定制和修改BeanFactory的内容。【所有的bean定义已经保存加载到beanFactory中，
 *        但是bean的实例还未创建】。
 *
 *  BeanFactoryPostProcessor执行原理（我们写一个MyBeanFactoryPostProcessor测一下）：
 *     我们可以在MyBeanFactoryPostProcessor的postProcessBeanFactory方法上打一个断点，可以看到具体的执行流程：
 *        1. IOC容器创建；
 *        2. 从invokeBeanFactoryPostProcessors(beanFactory)方法点进去，它会来到PostProcessorRegistrationDelegate类的
 *           第141行，这一行的意思其实就是直接在BeanFactory中找到所有类型是 BeanFactoryPostProcessor的组件，然后回调它们的
 *           postProcessBeanFactory方法；
 *        3.从这也能看出，为啥BeanFactoryPostProcessor是在bean实例创建之前执行的，因为bean实例创建的步骤是在
 *           registerBeanPostProcessors(beanFactory)这一步完成的，而BeanFactoryPostProcessor是在
 *           invokeBeanFactoryPostProcessors(beanFactory)这一步完成的，显然位于bean实例化之前执行。
 *
 *  接下来我们看一看 BeanFactoryPostProcessor的一个子接口：BeanDefinitionRegistryPostProcessor，该接口里面有一个方法：
 *        postProcessBeanDefinitionRegistry()，根据源码的注释信息可以知道，它的执行时机是：【所有bean的定义信息将要被加载，
 *        但是bean的实例还未创建】。由此可见，它的执行时机是在BeanFactoryPostProcessor之前执行，因为BeanFactoryPostProcessor
 *        是在bean的定义信息已经被加载后执行的，而BeanDefinitionRegistryPostProcessor则是bean的定义信息将要被加载，所以，
 *        它们的执行先后顺序一下子就知道了。
 *    BeanDefinitionRegistryPostProcessor执行原理（我们也写一个MyBeanDefinitionRegistryPostProcessor测一下），
 *    根据IOCTest_Extend里面的执行结果，可以看到BeanDefinitionRegistryPostProcessor是优先于BeanFactoryPostProcessor执行的，
 *    我们可以利用BeanDefinitionRegistryPostProcessor给容器中再额外添加一些组件。我们可以在
 *    MyBeanDefinitionRegistryPostProcessor里的两个方法上分别打个断点，看看原理：
 *       1. IOC容器创建；
 *       2. 跟上面的一样，从refresh -> invokeBeanFactoryPostProcessors(beanFactory)进来，它会拿到所有类型都是：
 *          BeanDefinitionRegistryPostProcessor类型的组件，依次触发它们的 postProcessBeanDefinitionRegistry()方法，
 *          可以从PostProcessorRegistrationDelegate的第125行可以看到，接着在第130行可以看到，它会调用
 *          MyBeanDefinitionRegistryPostProcessor.invokeBeanFactoryPostProcessors方法；
 *        3. 接着往下走，走到第141行，我们就可以看到上面熟悉的东西了，首先在BeanFactory中找到所有类型是
 *           BeanFactoryPostProcessor的组件，然后回调它们的postProcessBeanFactory方法；【这一步跟上面是一样的】。
 *           所以， BeanDefinitionRegistryPostProcessor中规定的方法要优先于BeanFactoryPostProcessor里面的
 *           postProcessBeanFactory方法。
 */
@ComponentScan("cn.edu.ustc.xk.extend")
@Configuration
public class ExtendConfig {

    @Bean
    public Color color(){
        return new Color();
    }
}
