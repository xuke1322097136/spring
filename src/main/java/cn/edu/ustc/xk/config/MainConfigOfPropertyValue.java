package cn.edu.ustc.xk.config;

import cn.edu.ustc.xk.bean.People;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by xuke
 * Description: 用于测试@Value注解和@PropertySource注解引用属性文件，配合着IOCTest_PropertyValue类使用
 * Date: 2019-10-01
 * Time: 21:15
 *   使用@PropertySource读取外部配置文件，读取其中的k/v保存到运行环境中
 *   classpath:表示的是类路径，即src/main/java或者src/main/resource
 */
@Configuration
@PropertySource("classpath:/people.properties")
public class MainConfigOfPropertyValue {

    @Bean
    public People people() {
        return new People();
    }

}
