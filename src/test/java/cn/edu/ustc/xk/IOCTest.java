package cn.edu.ustc.xk;

import cn.edu.ustc.xk.bean.Person;
import cn.edu.ustc.xk.config.*;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by xuke
 * Description:
 * Date: 2019-01-15
 * Time: 13:17
 */

public class IOCTest {

    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig.class);
        /**
         * 测试批量扫描@ComponentScan(只含value值，其他的excludeFilters，includeFilters等不指定的时候)：
         * 得到容器中所有的bean的名字：myConfig，bookController，bookDAO，bookService.（注意：它们都是类名的首字母小写形式）
         * 因为Configuration标注里面也包含Component注解，所以它也是一个组件；
         * 另外，@Controller注解，@Service注解和@Repository注解都会被扫描到容器中去
         *
         */
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String definitionName : definitionNames) {
            System.out.println(definitionName);
        }

    }

    @Test
    public void test02() {
        /**
         *       // 在这加载的是MyConfig2这个类的信息
         *         AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig2.class);
         *         String[] definitionNames = applicationContext.getBeanDefinitionNames();
         *         for (String definitionName: definitionNames) {
         *             System.out.println(definitionName);
         *         }
         *
         *        Object bean1 = applicationContext.getBean("person");
         *         Object bean2 = applicationContext.getBean("person");
         *         System.out.println(bean1 == bean2);
         */
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig2.class);
        System.out.println("IOC容器初始化完成");
        Object bean1 = applicationContext.getBean("person");
        Object bean2 = applicationContext.getBean("person");
        System.out.println(bean1 == bean2);
    }

    @Test
    public void test03() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig3.class);
        // 获得Person类型的bean
        String[] names = applicationContext.getBeanNamesForType(Person.class);
        for (String name: names) {
            System.out.println(name);
        }

        System.out.println("...............................");
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("os.name");
        System.out.println(property);
    }

    // 测试@Import注解
    @Test
    public void testImport() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig4.class);

        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String definitionName : definitionNames) {
            System.out.println(definitionName);
        }
    }

    @Test
    public void test05() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig5.class);
        /**
         *  使用Spring提供的FactoryBean（工厂Bean），调用getObject()方法返回的对象T（泛型T就是我们要返回什么类型的对象），将该对象放入到容器中。
         *  在我们的例子中，看着我们装配的是colorFactoryBean类型的bean，但是实际上装配的却是泛型指定的类型，即Color类型的。
         *  如果我们就想获取FactoryBean本身，即获取ColorFactoryBean类型的bean，我们根据id获取bean的时候，我们可以在前面加上一个&符号。
         *  这是因为在BeanFactory工厂里面,它定义了一个前缀（摘自BeanFactory的源码：String FACTORY_BEAN_PREFIX = "&";）,Spring就知道拿的是FactoryBean本身。
         *
         *  bean1获取到的是FactoryBean里面的getObject方法得到的泛型类型；
         *  加上&之后，bean2获取到的是FactoryBean本身。
         */

        Object factoryBean1 = applicationContext.getBean("colorFactoryBean");
        Class<?> bean1 = factoryBean1.getClass();
        System.out.println(bean1);

        System.out.println("...........................");

        Object factoryBean2 = applicationContext.getBean("&colorFactoryBean");
        Class<?> bean2 = factoryBean2.getClass();
        System.out.println(bean2);
    }

    /** 测试bean的生命周期:
     * 对象创建（构造器，不是初始化阶段，只有对象创建完了才会调用我们的初始化方法）：
     *        1）单实例：在容器启动的时候就会创建对象；
     *        2）多实例：只有当我们每次获取的时候才会创建。
     * 对象的销毁:对于单实例的bean，容器一关闭bean就会销毁掉；而对于多实例的bean，容器不会管理bean的销毁，需要手动销毁掉
     */

     @Test
    public void testLifeCycle() {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
         System.out.println("容器初始化完成");

        // applicationContext.getBean("lifeCycle");

         applicationContext.close();
    }

    /**
     * 这个例子对应的bean是DefinedBeanLifeCycle以及它实现的接口的方法的调用时机
     */
    @Test
    public void testDefinedBeanLifeCycle() {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
        System.out.println("容器初始化完成");

        applicationContext.close();
    }
    /**
     * 这个例子对应的bean是LifeCycle02以及@PostConstruc和@PreDestroy标注的方法的执行时机
     */
    @Test
    public void testLifeCycle02() {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
        System.out.println("容器初始化完成");

        applicationContext.close();
    }


}