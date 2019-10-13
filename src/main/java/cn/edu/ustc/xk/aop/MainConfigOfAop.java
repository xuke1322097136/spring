package cn.edu.ustc.xk.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by xuke
 * Description:用来测试Spring的AOP功能（LogAspects/MathCalculator/MainConfigOfAop三个类一起）
 * Date: 2019-10-03
 * Time: 13:28
 *
 *   具体的步骤如下：
 *       1.) 在pom文件中导入AOP模块的依赖：spring-aspects，保证我们导入的包含有切面包spring-aspects和aspectjweaver包即可；
 *       2.) 定义一个业务处理类(MathCalculator)，在业务逻辑进行运行的时候，我们对日志信息进行打印(方法执行之前/方法运行结束
 *            之后/方法出现异常等等)；
 *       3.) 定义一个日志切面类(LogAspects)：切面类里面的方法需要动态感知MathCalculator的divide方法运行到哪里，然后执行通知；
 *           具体的通知方法有：
 *              前置通知（注解@Before）：logStart，在目标方法divide方法运行之前执行；
 *              后置通知（注解@After）：logEnd，在目标方法divide方法运行结束之后执行；
 *              返回通知（注解@AfterReturning）：logReturn，在目标方法divide方法正常返回之后执行；
 *              异常通知（注解@AfterThrowing）：logException，在目标方法divide方法出现异常之后执行；
 *              环绕通知（注解@Around）：动态代理，手动推进目标方法往上面所有情形执行（调用joinPoint.proceed()方法）
 *        4.) 给切面类上的所有目标方法标注在何时何地(插入到哪个方法上)执行（其实就是标注上通知注解）；
 *        5.) 将切面类(LogAspects)和业务逻辑类（目标方法所在的类MathCalculator）都加入到容器中，详见MainConfigOfAop中的实现；
 *        6.) 必须告诉Spring上述加入到容器中的类哪一个是切面类，即在哪个类加上@Aspect注解；
 *        7.) 给配置类加上@EnableAspectJAutoProxy注解，开启基于注解的AOP模式，开启动态代理模式。
 *               一般在spring中注解为：@EnableXXX形式，表示的意思都是开启某一项功能。
 */
@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAop {

    // 将业务逻辑类加入到容器中
    @Bean
    public MathCalculator calculator(){
        return new MathCalculator();
    }

    // 将切面类加入到容器中
    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }
}
