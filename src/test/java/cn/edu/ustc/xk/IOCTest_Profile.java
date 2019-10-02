package cn.edu.ustc.xk;

import cn.edu.ustc.xk.bean.Yellow;
import cn.edu.ustc.xk.config.MainConfigOfProfile;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.time.YearMonth;
import java.util.Arrays;

/**
 * Created by xuke
 * Description: 用来测试@Profile注解使用的
 * Date: 2019-10-03
 * Time: 0:24
 *
 * 注解@Profile：
 *    1. 指定组件在哪个环境的情况下才能被注册到容器中，如果不指定的话，那么在任何环境下都能注册到这个组件;
 *    2. 加了环境标识的bean，只有这个环境被激活的时候才会被注册到容器中。也就是说，我们只加上了@Profile注解加到特定的bean上，
 *       此时还是没有发挥作用的，在任何环境下都用不了这个组件的。当然了，你如果指定@Profile("default")环境的话，那么你也
 *       不需要激活就可以使用，此时该组件代表的是默认环境下可以使用的组件。所以，我们可以通过下面两种方式来激活：
 *          a.) 使用命令行参数的方式：在虚拟机参数位置加载：-Dspring.profiles.active=test代表的就是激活测试环境下的组件，
 *              这里的test对应的就是@Profile("test")里面的test；
 *          b.)使用代码的方式：我们在注册容器的时候，不再使用有参构造器，我们使用无参构造器完成容器的构造，然后完成环境设置，
 *             最后模仿有参构造器里的步骤完成注册操作和刷新操作。（参见AnnotationConfigApplicationContext有参构造器的源码）
 *    3. 没有被环境变量@Profile变量标识的bean，在任何环境下都是能加载的。
 *    4. @Profile变量还可以标注在类上面，标注在类上面的时候，只有在该特定环境下的时候，整个配置类里面的所有配置才会生效。
 */
public class IOCTest_Profile {
    @Test
    public void testParameter() {
        try (AnnotationConfigApplicationContext applicationContext =
                     new AnnotationConfigApplicationContext(MainConfigOfProfile.class);) {

            String[] datasources = applicationContext.getBeanNamesForType(DataSource.class);
            // 加上@Profile一个输出都没有，不加的话有三个输出结果
            Arrays.asList(datasources).stream().forEach(System.out::println);

            // 可以验证上面的第三点，Yellow没有被任何一个@Profile标识，所以它在任何环境下都是可以获取到的。
            Yellow yellow = applicationContext.getBean(Yellow.class);
            System.out.println(yellow);
        }
    }

    @Test
    public void testCode(){

        // 1. 创建一个IOC容器
        try(AnnotationConfigApplicationContext applicationContext =
                    new AnnotationConfigApplicationContext()) {
            // 2. 设置需要激活的环境，可以设置多个
            applicationContext.getEnvironment().setActiveProfiles("test","prod");
            // 3. 注册主配置类
           applicationContext.register(MainConfigOfProfile.class);
            // 4. 启动刷新容器
            applicationContext.refresh();

            String[] datasources = applicationContext.getBeanNamesForType(DataSource.class);
            Arrays.asList(datasources).stream().forEach(System.out::println);

            // 同上！可以验证上面的第三。同时，我们可以看到不同容器中我们注册的yellow也是不一样的。
            Yellow yellow = applicationContext.getBean(Yellow.class);
            System.out.println(yellow);
        }
    }

}
