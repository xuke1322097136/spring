package cn.edu.ustc.xk.listener;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xuke
 * Description: 用来测试Spring中ApplicationListener的用法
 * Date: 2019-10-15
 * Time: 23:46
 *
 *   ApplicationListener是用来监听容器中发布的事件的，完成事件驱动模型的开发。根据ApplicationListener的定义可以知道，它监听
 *      的是ApplicationEvent及其下面的子事件.
 *
 *    写事件监听器的步骤：
 *        1. 写一个监听器(MyApplicationListener)来监听某一个事件ApplicationEvent；
 *               【其实还有一种方法，利用注解：@EventListener（详见BookService类），此种用的较多】
 *        2. 将监听器加入到容器中；
 *        3. 只要容器中有相关事件的发布，我们就能监听到这个事件，Spring默认发布的两个事件：
 *                ContextRefreshedEvent：容器刷新完成（所有的bean都创建完成）之后，将会发布这个事件；
 *                ContextClosedEvent：关闭容器会发布这个事件
 *        4. 自己如何发布一个事件？可以直接在容器中调用publishEvent()方法，详见IOCTest_ApplicationListener类中。
 *
 *     事件监听器的原理，我们给MyApplicationListener.onApplicationEvent上打一个断点：
 *     1. 容器创建并调用refresh方法；
 *     2. 来到finishRefresh()，表示容器刷新完成，此时会发布ContextRefreshedEvent事件；
 *     3. 来到publishEvent(new ContextRefreshedEvent(this));即来到第一个事件：ContextRefreshedEvent；
 *          事件发布流程：
 *                 a.) 拿到事件的多播器（派发器）：getApplicationEventMulticaster();
 *                 b.) 派发事件：multicastEvent()。该方法里面的逻辑是：获取到所有的ApplicationListener，如果有Executor，
 *                              可以支持利用Executor来异步派发（源码第134行：Executor executor = getTaskExecutor();），
 *                               否则利用同步的方式直接执行invokeListener，接着会来到SimpleApplicationEventMulticaster的
 *                              第170行，拿到lister，回调onApplicationEvent()方法。
 *       4. 接着来到我们自定义的事件发布；
 *       5. 最后关闭容器的时候会发布ContextClosedEvent事件。
 *
 *       事件多播器（getApplicationEventMulticaster();）是如何拿到的呢？具体流程：
 *        1. 容器创建并调用refresh方法；
 *        2. refresh方法里面有一步叫：initApplicationEventMulticaster();该方法的执行逻辑是：先在容器中找到id叫做
 *           applicationEventMulticaster的组件，如果有的话，那么将会直接拿到该组件，如果没有的话，那么它会自己创建
 *           一个SimpleApplicationEventMulticaster类型的多播器，并注册到beanFactory中，然后你如果想在其他组件中
 *           要派发事件的时候，你就可以使用直接注入的方式使用它了。
 *
 *         容器中到底有哪些监听器？
 *         其实可以看到refresh方法里面有一步叫:registerListeners()，它的作用就是注册事件监听器。首先会从容器中拿到所有的
 *         事件监听器，然后将它们注册到ApplicationEventMulticaster中（见下面的源码），这样，派发器就能将事件派发出去了。
 *           源码第818行： getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
 *
 *        注解@EventListener的原理，可以看到该注解的源码注释上说利用的是EventListenerMethodProcessor来处理@EventListener
 *            注解的，EventListenerMethodProcessor实现了一个接口：SmartInitializingSingleton。该接口里面有一个
 *            afterSingletonsInstantiated方法，具体它的执行时机【执行时机：创建完所有的单实例bean之后执行】，我们可以给
 *            EventListenerMethodProcessor.afterSingletonsInstantiated打上一个断点，然后debug。
 *            SmartInitializingSingleton执行原理：
 *              1. 容器创建并调用refresh方法；
 *              2. refresh方法里面有一步叫：finishBeanFactoryInitialization(beanFactory),该方法用来初始化剩下的单实例bean，
 *                 然后会来到beanFactory.preInstantiateSingletons();它也是用来初始化剩下的单实例bean的，接着来到了
 *                 smartSingleton.afterSingletonsInstantiated()（源码第863行），在这段代码的上面，可以看到一个
 *                 熟悉的getBean()方法，由此可见是在创建所有的bean之后才执行的afterSingletonsInstantiated()方法。
 *                 其实也可以看到finishBeanFactoryInitialization(beanFactory)的步骤：
 *                   I). 创建所有的单实例bean（getBean()方法来负责完成）；
 *                   II). 获取到所有创建好的单实例bean，判断它是不是SmartInitializingSingleton类型的，如果是的话，那么就调用
 *                         smartSingleton.afterSingletonsInstantiated()方法。
 *
 *
 *
 */
@ComponentScan("cn.edu.ustc.xk.listener")
@Configuration
public class ListenerConfig {
}
