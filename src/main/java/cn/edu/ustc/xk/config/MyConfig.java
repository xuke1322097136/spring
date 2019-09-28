package cn.edu.ustc.xk.config;

import cn.edu.ustc.xk.bean.Person;
import cn.edu.ustc.xk.service.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * Created by xuke
 * Description:
 * Date: 2018-12-27
 * Time: 22:20
 */
@Configuration // 告诉Spring这是一个配置类
/**
 * 批量扫描,value或者basePackages用来指定扫描的包，还可以使用includeFilters指定只包含哪些包，excludeFilters指定的规则(即type)排除掉哪些包。
 * 从源码看到：includeFilters和excludeFilters的类型都是Filter类型的数组。
 *
 * excludeFilters指定排除规则的时候，它的每一个元素类型都是@Filter类型的，里面可以指定排除规则，在这使用的是默认的根据注解排除，即在这排除掉Controller和Service类型
 * includeFilters指定包含规则时候，我们必须得加上useDefaultFilters才行！！！这样禁用掉了默认规则，我们指定的规则才能发挥作用，即includeFilters要和useDefaultFilters=false搭配使用
 *
 * 在这说明一下排除或者是包含规则的使用方法，即type的取值情况：
 *    FilterType.ANNOTATION：按照注解的方式来过滤；（在这测试的是Controller注解）
 *    FilterType.ASSIGNABLE_TYPE：按照给定的类型来过滤；（在这测试的是BookService类型）
 *    FilterType.ASPECTJ：使用ASPECTJ表达式来过滤；
 *    FilterType.REGEX：按照正则表达式来过滤；
 *    FilterType.CUSTOM：按照用户自定义规则来过滤。（根据源码的描述知道自定义形式必须是TypeFilter的实现类。源码：{@link org.springframework.core.type.filter.TypeFilter} implementation.
 *                                                    我们在这在config包里面实现我们自定义的TypeFilter,即：MyTypeFilter）
 */
//例子一：@ComponentScan(value = "cn.edu.ustc.xk", excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
//})

//例子二：@ComponentScan(value = "cn.edu.ustc.xk", includeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {BookService.class})
//}, useDefaultFilters = false)

@ComponentScan(value = "cn.edu.ustc.xk", includeFilters = {
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
}, useDefaultFilters = false)
public class MyConfig {

    // 给容器中注册一个Bean，类型为返回值的类型，id（即bean的名字）默认的是方法名
    @Bean
    public Person person(){
        return new Person("zhangsan", 12);
    }
}
