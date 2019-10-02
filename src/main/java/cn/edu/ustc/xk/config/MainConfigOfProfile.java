package cn.edu.ustc.xk.config;

import cn.edu.ustc.xk.bean.Yellow;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Created by xuke
 * Description: 主要用来测试注解@Profile的作用
 * Date: 2019-10-02
 * Time: 23:52
 *   注解@Profile的作用：Spring为我们提供了可以根据当前环境，动态地激活和切换一系列相关组件的功能。
 *    例如：我们要求开发环境、测试环境和生产环境分别使用不同的数据源，我们需要根据不同的环境
 *          来进行切换和选择。
 *     为了达到测试的目的，我们需要加入数据源和数据库驱动的依赖到我们的项目中来。在这我们使用的是c3p0数据源。
 *     另外，为了达到复习的目的，我们将属性配置到了文件当中，通过@PropertySource来读取配置文件。
 *     最后，我们通过三种不同的方式获取到值。
 *     (成员变量完成用户名注入/参数的形式完成密码的注入/通过字符串解析器来完成驱动的注入)
 *
 */
@PropertySource("classpath:/dbconfig.properties")
@Configuration
public class MainConfigOfProfile implements EmbeddedValueResolverAware {

    @Value("${db.user}")
    private String user;

    private StringValueResolver valueResolver;
    private String driveClass;

    // 通过set方法将Spring底层的字符串芥子气注入到我们的主配置类中
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.valueResolver = resolver;
        driveClass = valueResolver.resolveStringValue("${db.driverClass}");
    }

    // 用来测试：没有被环境变量@Profile变量标识的bean，在任何环境下都是能加载的。
    @Bean
    public Yellow yellow(){
        return new Yellow();
    }

    @Profile("test")
    @Bean("testDataSource")
    public DataSource dataSourceTest(@Value("${db.password}") String password) throws Exception {
        // 我们使用的是c3p0的数据源，所以我们使用的是ComboPooledDataSource
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/database01");
        dataSource.setDriverClass(driveClass);

        return dataSource;
    }

    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDev(@Value("${db.password}") String password) throws Exception {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/database02");
        dataSource.setDriverClass(driveClass);

        return dataSource;
    }

    @Profile("prod")
    @Bean("prodDataSource")
    public DataSource dataSourceProd(@Value("${db.password}") String password) throws Exception {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/database03");
        dataSource.setDriverClass(driveClass);

        return dataSource;
    }
}
