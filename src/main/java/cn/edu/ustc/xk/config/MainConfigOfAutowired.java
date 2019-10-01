package cn.edu.ustc.xk.config;

import cn.edu.ustc.xk.dao.BookDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by xuke
 * Description: 这里使用BookService和BookDAO来测试@Autowired注解的使用
 * Date: 2019-10-01
 * Time: 23:22
 *
 * 在这使用包扫描完成bookService和bookDAO这两个bean的注册（注意：容易中注入的bean都是类名首字母小写的这个bean）
 */
@ComponentScan({"cn.edu.ustc.xk.service", "cn.edu.ustc.xk.dao"})
@Configuration
public class MainConfigOfAutowired {

    @Primary
    @Bean(name = "bookDAO2")
    public BookDAO bookDAO(){
        BookDAO bookDAO = new BookDAO();
        bookDAO.setLabel(2);
        return bookDAO;
    }
}
