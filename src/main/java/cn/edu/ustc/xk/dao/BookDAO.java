package cn.edu.ustc.xk.dao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

/**
 * Created by xuke
 * Description: 其实装载到IOC容器中的是bookDAO这个bean，即类名(BookDAO)的首字母小写
 * Date: 2019-01-18
 * Time: 15:26
 */
@Repository
@Setter
@Getter
public class BookDAO {

     private int label = 1;

    @Override
    public String toString() {
        return "BookDAO{" +
                "label=" + label +
                '}';
    }
}
