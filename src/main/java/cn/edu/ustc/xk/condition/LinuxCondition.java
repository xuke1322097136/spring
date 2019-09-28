package cn.edu.ustc.xk.condition;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by xuke
 * Description:
 * Date: 2019-01-18
 * Time: 19:28
 */
public class LinuxCondition implements Condition {
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 判断是否是linux系统
        Environment environment = context.getEnvironment();// 获取当前环境信息
        String property = environment.getProperty("os.name");
        // 这里的linux中的l是小写的
        if (property.contains("linux")){
            return true;
        }
        return false;
    }
}

