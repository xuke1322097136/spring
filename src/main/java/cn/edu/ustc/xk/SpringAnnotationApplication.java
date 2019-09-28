package cn.edu.ustc.xk;

import cn.edu.ustc.xk.bean.Person;
import cn.edu.ustc.xk.config.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by xuke
 * Description:
 * Date: 2018-12-27
 * Time: 22:19
 */

/**
 * 给容器中注册bean的方法：
 *  1.) 利用包扫描+组件标注注解（例如：@Controller/@Service/@Repository/@Component）；【自己写的类，可以使用批量扫描这种方式】
 *  2.) @Bean；【导入第三方包里面的组件】
 *  3.）@Import【快速给容器中导入一个组件。打开@Import注释的源码，可以看到ImportSelector接口和ImportBeanDefinitionRegistrar接口】
 *       a. @Import({要导入到容器中的组件数组})，容器中会自动注册这个组件，id默认是类的权限定名；
 *       b. @ImportSelector: 导入选择器，返回需要导入的组件的全限定名的数组；（详见MyImportSelector）【SpringBoot里面用的很多】
 *       c. @ImportBeanDefinitionRegistrar: 通过registerBeanDefinitions方法来手工注册bean（详见MyImportBeanDefinitionRegistrar）.
 *   4.） 使用Spring提供的FactoryBean（工厂Bean），调用getObject()方法返回的对象T（泛型T就是我们要返回什么类型的对象），将该对象放入到容器中。
 *        在我们的例子中，看着我们装配的是colorFactoryBean类型的bean，但是实际上装配的却是泛型指定的类型，即Color类型的。
 *        如果我们就想获取FactoryBean本身，即获取ColorFactoryBean类型的bean，我们根据id获取bean的时候，我们可以在前面加上一个&符号。
 *        这是因为在BeanFactory工厂里面,它定义了一个前缀（摘自BeanFactory的源码：String FACTORY_BEAN_PREFIX = "&";）,Spring就知道拿的是FactoryBean本身。
 *
 *
 *
 */
public class SpringAnnotationApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig.class);

        Person person = applicationContext.getBean(Person.class);
        System.out.println(person);

        // 得到Person类型的bean的名字
        String[] names = applicationContext.getBeanNamesForType(Person.class);
        for (String name : names) {
            System.out.println(name);
        }
    }
}
