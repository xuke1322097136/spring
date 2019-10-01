package cn.edu.ustc.xk.config;

import cn.edu.ustc.xk.bean.Person;
import cn.edu.ustc.xk.condition.LinuxCondition;
import cn.edu.ustc.xk.condition.WindowsCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xuke
 * Description: 搭配着condition下面的两个类(WindowsCondition和LinuxCondition)来看
 * Date: 2019-01-18
 * Time: 19:16
 */
@Configuration
public class MyConfig3 {

    /**
     * @Conditional :
     *     1.) 标注在方法上：按照某种条件进行判断，满足条件才给容器中注册bean;
     *     2.) 标注在类上：满足当前条件，这个类中配置的所有的bean才会生效。
     *
     *  在这测试下面的情况：
     *  如果是windows系统，我们注册的是bill，如果是linux系统，我们注册的bean是linus
     *  @Conditional({Condition数组})
     *  测试linux的时候，我们需要手动设置虚拟机参数（VM options）：-Dos.name=linux（D和os.name之间不能有空格）
     */
    @Conditional({WindowsCondition.class})
    @Bean("bill")
    public Person person01(){
        return new Person("Bill Gates", 62);
    }

    @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person person02(){
        return new Person("linus", 48);
    }
}
