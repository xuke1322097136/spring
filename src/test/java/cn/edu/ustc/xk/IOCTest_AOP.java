package cn.edu.ustc.xk;

import cn.edu.ustc.xk.aop.MathCalculator;
import cn.edu.ustc.xk.config.MainConfigOfAop;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by xuke
 * Description: 用来测试Spring的AOP功能（LogAspects/MathCalculator/MainConfigOfAop三个类一起）
 * Date: 2019-10-03
 * Time: 14:19
 */
public class IOCTest_AOP {

    @Test
    public void testAOP(){

        try(AnnotationConfigApplicationContext applicationContext =
                     new AnnotationConfigApplicationContext(MainConfigOfAop.class)){

            // 从容器中拿到业务逻辑类的bean
            MathCalculator calculator = applicationContext.getBean(MathCalculator.class);
            calculator.divide(1, 1);
        }

    }
}
