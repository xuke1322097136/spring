package cn.edu.ustc.xk.config;

import cn.edu.ustc.xk.bean.Color;
import cn.edu.ustc.xk.bean.Red;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by xuke
 * Description:
 * Date: 2019-01-18
 * Time: 20:11
 */
@Configuration
// Import注解导入进来的组件，它的id是类的权限定名,自定义的导入选择器MyImportSelector负责导入Blue和Yellow
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MyConfig4 {
}
