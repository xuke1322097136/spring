package cn.edu.ustc.xk.aop;

/**
 * Created by xuke
 * Description: 用来测试Spring的AOP功能（LogAspects/MathCalculator/MainConfigOfAop三个类一起）
 * Date: 2019-10-03
 * Time: 13:28
 */
public class MathCalculator {

    public int divide(int a, int b){
        System.out.println("执行目标方法：divide");
        return a / b;
    }
}
