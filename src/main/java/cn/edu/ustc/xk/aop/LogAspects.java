package cn.edu.ustc.xk.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * Created by xuke
 * Description: 用来测试Spring的AOP功能（LogAspects/MathCalculator/MainConfigOfAop三个类一起）
 * Date: 2019-10-03
 * Time: 13:27
 */
// 告诉Spring这个类是切面类
@Aspect
public class LogAspects {

    /**
     *  如果切入点表达式都一样的情况下，我们可以将表达式抽象出来。我们不需要有任何的实现，只需要给该方法上加上一个@Pointcut注解
     *  更详细的切入点表达式该如何写，可以参考Spring的官方文档。写法非常多。
     *  用于抽取公共的切入点表达式。另外，如果想要引用该切入点表达式的话，可以有两种办法：
     *       1. 如果是在本类中使用的话，我们只需要使用方法名和()即可，参见logStart方法上的标注形式
     *       2. 其他的切面类（可以理解为外部的类）想要引用的话，这时就要使用方法的全名进行标注了。参见logEnd方法上的标注形式
     */
    @Pointcut("execution(public int cn.edu.ustc.xk.aop.MathCalculator.divide(int, int))")
    public void pointCut(){}

    /**
     *  注意这个@Before注解是aspectj包下面的注解，不是junit下面的。
     *  注解@Before表示在目标方法之前切入logStart方法。
     *  这里的切入点表达式（@Before注解的value值），用来指定在哪个方法上进行切入，如果想切入到MathCalculator类下面的所有方法，
     *   则可以将divide换成*，并且，如果我们不想区分到底是什么参数，我们可以将参数标识成..，表示的意思是任意个参数，即：
     *   换成    @Pointcut("execution(public int cn.edu.ustc.xk.aop.MathCalculator.*(..))")
     *   切面功能想要拿到方法名/返回值/异常信息等等，需要借助JoinPoint类，且该joinPoint参数必须是第一个参数，否则会报错。
     */
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        // 获取到方法的参数
        Object[] args = joinPoint.getArgs();
        System.out.println(joinPoint.getSignature().getName() +
                "开始执行之前，参数列表为：" + Arrays.asList(args));
    }

    @After("cn.edu.ustc.xk.aop.LogAspects.pointCut()")
    public void logEnd(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName() + "方法结束");
    }

    // value表示的是切入点，returning表示利用什么参数来封装返回值，为了能返回任何类型的值，我们使用的是Object
    // joinPoint参数必须是第一个参数，
    // 否则报错：java.lang.IllegalArgumentException: error at ::0 formal unbound in pointcut
    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result){
        System.out.println(joinPoint.getSignature().getName() +
                "正常执行完成，结果为：" + result);
    }

    // value表示的是切入点，throwing表示利用什么参数来封装异常信息
    @AfterThrowing(value = "pointCut()", throwing = "message")
    public void logException(JoinPoint joinPoint, Exception message){
        System.out.println(joinPoint.getSignature().getName() +
                "执行出现异常，异常信息为：" + message);
    }

}
