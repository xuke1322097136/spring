package cn.edu.ustc.xk.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xuke
 * Description:
 * Date: 2018-12-27
 * Time: 22:22
 */
// 引入了lombok，在这就不需要写setter/getter方法和toString()方法等
@Data
public class Person {
    private String name;
    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

}
