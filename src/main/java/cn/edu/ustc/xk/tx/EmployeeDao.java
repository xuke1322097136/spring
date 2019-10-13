package cn.edu.ustc.xk.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by xuke
 * Description: 用来直接跟数据库的employee打交道
 * Date: 2019-10-13
 * Time: 22:39
 */
@Repository
public class EmployeeDao {

    // 利用jdbcTemplate来操纵数据库
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(){
        String sql = "insert into employee(id, lastName, email, gender,d_id) values(?,?,?,?,?)";

        // 随机生成一个用户名
        String lastName = UUID.randomUUID().toString().substring(0, 4);
        String email = lastName + "@ustc.edu.cn";
         // 增删改都调用update方法。目前的id值已经到3了，所以我们插入第四条。但是，id不是自增的吗？
        jdbcTemplate.update(sql, 6, lastName, email, 1, 3);
    }
}
