package cn.edu.ustc.xk.config;

import cn.edu.ustc.xk.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * Created by xuke
 * Description:
 * Date: 2019-01-18
 * Time: 16:42
 */
@Configuration
public class MyConfig2 {

    /**
     *  默认是单实例的，可以使用@Scope注解来指定多实例,它可以取到的值有：
     *  ConfigurableBeanFactory#SCOPE_PROTOTYPE,它对应的值是prototype(多实例的)
     * 	ConfigurableBeanFactory#SCOPE_SINGLETON，它对应的值是singleton（单实例的，默认值)
     * 	org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST，它对应的值是request(同一次请求创建一个实例)
     * 	org.springframework.web.context.WebApplicationContext#SCOPE_SESSION，它对应的值是session(同一个session创建一个实例)
     *
     * 注意：（具体测试方法：我们查看"给容器中添加person"这个语句有没有打印来判断方法是否调用）
     * 1.）单实例的时候，此时有上面那句话输出，即表明：IOC容器启动时候就会调用方法，创建对象放到IOC容器中，以后每一次获取则是直接从容器中拿。
     * 2.）多实例的时候，没有上面那句话输出，即表明：IOC容器启动时候并不会调用方法创建对象放到容器中。
     *     而是只有当真正获取对象的时候，才会调用方法创建对象放入到容器中去，而且是每获取一次就创建一次对象，也就是每次获取对象的时候，方法都会被执行
     *
     *  @Lazy注解 懒加载（针对的是单实例bean，所以跟@Scope注解不搭配使用，两者避开使用）：
     *        前面提到的，单实例bean的时候，创建容器的时候就会创建对象；
     *        懒加载的意思就是：容器启动的时候不创建对象，第一次使用（或者获取）bean的时候创建对象，并初始化。
     *        现在是不是和用@Scope注解有点像了呢？都是真正使用的时候才会 创建对象!
     *        其实是的，但是@Lazy和@bean注解标注的是单例对象，也就是说他只会创建一次对象；而@Scope注解则会创建多个对象。
      */
  //  @Scope("prototype")
    @Lazy
    @Bean("person")
    public Person person(){
        System.out.println("给容器中添加person");
        return new Person("xuke", 100);
    }

}
