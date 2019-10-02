package cn.edu.ustc.xk;

import cn.edu.ustc.xk.config.MainConfigOfAutowired;
import cn.edu.ustc.xk.dao.BookDAO;
import cn.edu.ustc.xk.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * Created by xuke
 * Description: 测试@Autowired/@Resource/@Inject以及
 *
 * @Autowired 注解搭配@Primary和@Qualifer注解的使用
 * Date: 2019-10-01
 * Time: 23:21
 * <p>
 * 自动装配：Spring利用依赖注入（DI）来完成对IOC容器中各个组件的依赖关系赋值。
 *     I) 注解 @Autowired：（spring定义的注解）【推荐使用】
 *          a.) 默认优先按照类型（BookDAO）去容器中查找对应的组件，即：ioc.getBean(BookDAO.class);
 *          b.) 如果找到了多个相同类型的组件，接着将属性的名称(即BookService中的属性名称)作为组件的id去容器中查找对应的bean。
 *              即，此时相当于调用的是ioc.getBean("bookDAO")。因为此时BookService中定义的是private BookDAO bookDAO;
 *          c.) 当我们使用@Qualifer来指定使用哪个bean的时候，将不再根据属性的名称指定了，此时根据的是@Qualifer里面
 *              指定的组件id值(其实就是名字)来查找对应的bean。
 *          d.) 利用@Autowired实现自动装配的时候，自动装配一定要将属性赋好值，不然会报错。即，此例子中的BookService中
 *              使用的bookDAO一定是要装配好的。其实，这句话的意思其实是@Autowired里面的属性值required默认的是true，但是，
 *              我们如果想找得到就装配属性bookDAO，找不到对应的bean我们就不装配，我们就要将required设置成false。
 *          e.) @Primary注解的作用：让Spring进行自动装配的时候，默认使用首选的bean。也就是说，BookDAO类型的bean类型的bean可能
 *              有多个，我们可以利用@Primary来标注首选哪个bean。
 *     II) 注解@Resource(JSR250规范)，这是Java规范的注解。@Resource注解可以实现和@Autowired一样
 *         的自动装配功能，它默认情况下是按照组件名称来进行装配的。不支持@Primary注解和@Autowired(required = false)。
 *     III) 注解@Inject(JSR330规范)，这个也是Java规范的注解。它也可以实现@Autowired一样的注入功能，它是可以支持@Primary注解
 *         但是不支持@Autowired(required = false)。
 *
 *      IV) 拓展：其实这上面的三个注解都是由 AutowiredAnnotationBeanPostProcessor 后置处理器来完成的自动装配功能，
 *             包括@Value/@Lookup等注解都是由这个后置处理器来解析完成自动装配功能的。至于后置处理器的原理，我们可以
 *             查看MainConfigOfLifeCycle里面的解释或者再打个断点接着看看具体细节。
 *
 *    V ) 注解 @Autowired还可以用在【构造器】、【参数】、【方法】以及【属性】上，其实都是从容器中获取到参数组件的值。
 *      1）标注在setter方法上或者是@Bean标注的方法创建对象+方法参数，第二种方法中的参数所代表的组件默认都是从容器中获取，
 *         默认情况下不写@Autowired， 效果也是一样的，都能自动获取；
 *      2）标注在构造器上，如果组件只有一个有参构造器，这个有参构造器的注解 @Autowired也可以省略，参数位置的组件也可以
 *         自动从容器中获取。一般情况下，默认在加载ioc容器中的组件时，容器启动会调用无参构造器（只有午餐构造器）创建对象，
 *         再对这个类里面的其他组件进行初始化、 赋值操作等；
 *      3) 放置在参数的位置上，其实上面两种情况都穿插着讲了一些了。
 *
 *    VI) 自定义的组件如果想要使用Spring容器底层的一些组件(ApplicationContext/BeanFactory/...)，那么我们可以让自定义的组件
 *          实现相对应组件的XXXAware，在创建自定义对象的时候，会调用接口规定的方法（回调的形式）来完成相关组件的注入，从而
 *          将Spring底层的一些组件注入到自定义的Bean当中。相对应的，XXXAware其实是通过XXXAwareProcessor来完成注入的。
 *          如：ApplicationContextAware  ==> ApplicationContextAwareProcessor来完成注入。具体可以实现的接口可以查看
 *          Aware下面的接口。可以看下面的实验7.
 *          可以看到，我们在创建blue这个组件的时候，就会通过回调的方式来创建里面对应的ApplicationContext等组件。具体的原因
 *          可以查看ApplicationContextAwareProcessor（实现了BeanPostProcessor）类中的invokeAwareInterfaces方法中的
 *          最后一行代码可以看到会对我们重写的这个方法进行回调。
 * <p>
 *
 *
 * 本次测试的内容主要有：
 *
 *  1. 测试我们在BookService中自动导入(@Autowired)的BookDAO和我们通过注解@Repository自动加入早容器中的bookDAO是不是同一个；
 *    为此我们重写了BookService中的toString方法来查看是不是同一个bean。
 *
 *  2. 通过@Bean注解在配置类MainConfigOfAutowired中重新加入了一个BookDAO类型的bean。此时，容器中有两个类型均为BookDAO类型的
 *      bookDAO。一个是bookDAO，一个是bookDAO2。此时我们利用注解@Autowired完成自动注入的话，此时会按照属性名称去查找，
 *      或者我们需要通过注解@Qualifer来配合着指定哪个bean。ioc.getBean(BookDAO.class);
 *      这段代码将会报错，因为此时有两个类型的BookDAO，所以要将这段代码注释掉。可以看到，我们没有指定@Qualifer的时候，
 *      BookService中装配的是BookService{bookDAO=BookDAO{label=1}}，但是当我们利用@Qualifer指定组件的id值之后，我们看到最后
 *      最终的装配结果为BookService{bookDAO=BookDAO{label=2}}。
 *
 *   3. 测试@Autowired(required = false)的作用时，我们可以将@Qualifier("bookDAO3")，此时我们的容器中是没有bookDAO3
 *       这个bean，最后也没有报错，最终的结果只是显示为null。输出结果为：BookService{bookDAO=null}
 *
 *    4. 测试@Primary注解的作用，我们将配置类MainConfigOfAutowired里面的bean设置为首选的bean，也就是说加上@Primary注解，
 *       除此之外，为了让@Primary起效果，我们先暂时注释掉@Qualifier注解（二者不是矛盾的，是可以同时使用的）。
 *       可以看到输出结果为BookService{bookDAO=BookDAO{label=2}}， 其实也可以看出三个的
 *       优先级：@Qualifier > @Primary > 属性名称。
 *
 *    5. 测试@Resource注解：将之前BookService中的@Qualifier("bookDAO2")和@Autowired(required = false)都给注释掉，在BookService上
 *       就加上一个光秃秃的@Resource注解之后，我们此时仍然保留了@Primary注解，可以看到@Primary注解其实没有发挥作用，
 *        因为输出的是BookService{bookDAO=BookDAO{label=1}} ，当我们利用@Resource的name属性来完成指定的bean的装配，
 *        ，此时输出结果为BookService{bookDAO=BookDAO{label=2}}。
 *
 *     6. 测试@Inject注解，此时我们需要导入支持@Inject注解的依赖，@Resource注解则不需要从maven仓库中导入依赖。我们
 *        把@Resource注解给注释掉，加上@Inject注解，但是没有注释掉@Primary注解，可以看到@Primary注解还是能起作用的，因为最后的
 *        输出结果为BookService{bookDAO=BookDAO{label=2}}。
 *
 *     7. 验证XXXAware接口如何完成自动注入，即，自定义的组件如果想要使用Spring容器底层的一些组件。我们利用bean包下面的Blue
 *        这个类来做实验。可以看到在注入blue组件的时候，Spring底层的一些组件也附带注册进去了。
 */
public class IOCTest_Autowired {

    @Test
    public void test() {
        try (AnnotationConfigApplicationContext ioc =
                     new AnnotationConfigApplicationContext(MainConfigOfAutowired.class)) {

            // 查看容器中所包含的所有的bean
            Arrays.asList(ioc.getBeanDefinitionNames()).stream().forEach(System.out::println);

            // 根据类型来获取Bean，为了要打印bookService，所以我们在BookService中利用与alt+insert重写了toString方法
            BookService bookService = ioc.getBean(BookService.class);
            System.out.println(bookService);
            // 获取到通过@Repository自动加入早容器中的bean
//            BookDAO bookDAO = ioc.getBean(BookDAO.class);
//            System.out.println(bookDAO);

            System.out.println("......................");

        }
    }
}
