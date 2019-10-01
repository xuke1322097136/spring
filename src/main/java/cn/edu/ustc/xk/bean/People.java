package cn.edu.ustc.xk.bean;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by xuke
 * Description: 用于测试@Value注解和@PropertySource注解引用属性文件，配合着IOCTest_PropertyValue类使用
 * Date: 2019-10-01
 * Time: 21:32
 * @Value 注解的使用方法;
 *   1. 通过@Value直接指定值；
 *   2. 通过SpEL(#{})的形式赋值；
 *   3. 通过配置文件(properties文件)来获取值(${})，即，获取到在运行环境中的值。这里必须配合@PropertySource注解使用。
 *       为了保证在这能获取到值，我们需要在注册该bean的Config类(MainConfigOfPropertyValue)中指定配置文件的位置。
 *       加载完外部配置文件之后，我们可以使用"${}"的形式获取到值.
 */
@Data
public class People {
    @Value("张三")
    private String name;

    @Value("#{20 - 2}")
    private int age;

    @Value("${people.nickName}")
    private String nickName;

}
