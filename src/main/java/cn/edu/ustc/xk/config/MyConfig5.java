package cn.edu.ustc.xk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xuke
 * Description:
 * Date: 2019-01-18
 * Time: 21:35
 */
@Configuration
public class MyConfig5 {

    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }
}
