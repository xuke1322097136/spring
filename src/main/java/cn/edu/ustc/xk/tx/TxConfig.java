package cn.edu.ustc.xk.tx;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
/**
 * Created by xuke
 * Description: 用来理解声明性事务及其原理
 * Date: 2019-10-13
 * Time: 22:11
 *   环境搭建：
 *       1. 导入相关依赖，主要包含：数据源/数据库驱动/Spring-jdbc模块。前面两个已经导入了，所以在这需要重新导入jdbc模块的依赖，
 *            可以看到maven管理的jar包里面多了spring-jdbc和spring-tx两个jar包；
 *       2. 配置数据源和JdbcTemplate(Spring提供的简化数据库操作的工具)操作数据。即将数据源的各种信息设置好；
 *       3. 给方法标注上@Transactional注解，表示该方法是一个事务方法；
 *       4. 利用注解@EnableTransactionManagement 开启基于注解的事务管理功能；
 *       5. 在容器中注册事务管理器。
 *
 *   原理：其实和分析@EnableAspectJAutoProxy一样的，从@EnableTransactionManagement开始分析：
 *       1. 首先从该注解出发，发现它会利用TransactionManagementConfigurationSelector给容器中导入两个组件：
 *          a.) AutoProxyRegistrar;
 *          b.) ProxyTransactionManagementConfiguration
 *       2. 分析一下上面两个组件的作用：
 *          a.)AutoProxyRegistrar发现他里面有一个registerBeanDefinitions方法，而registerBeanDefinitions方法里面又会导入
 *             一个组件：InfrastructureAdvisorAutoProxyCreator(源码第76行)，其实我们发现源码第100行，有一个熟悉的组件：
 *             AnnotationAwareAspectJAutoProxyCreator，这个组件是我们才做切面，负责帮我们创建代理对象的。回到我们最新的组件：
 *             InfrastructureAdvisorAutoProxyCreator，点进去之后，我们也看到它其实也是一个后置处理器。继续点进去也可以看到，
 *             其实它也是一个SmartInstantiationAwareBeanPostProcessor。它的作用都是利用后置处理器机制在对象创建完成以后，
 *             接着包装对象成一个代理对象，而代理对象里面又增强器，然后代理对象执行方法利用拦截器链进行调用（详见AOP原理.txt）。
 *          b.) ProxyTransactionManagementConfiguration：通过源码也可以看到@Transactional能声明的一些属性，在
 *             SpringTransactionAnnotationParser.parseTransactionAnnotation里面能看到。当然，也可以直接@Transactional点
 *             进去看里面有哪些set方法。
 *             I.)首先它会在容器中注册一个transactionAdvisor，叫做事务增强器，bean的名字叫做internalTransactionAdvisor。而
 *                这个增强器里面它又会添加一个用来解析事务注解信息使用的：AnnotationTransactionAttributeSource，接着他又会
 *                增加一个事务拦截器：TransactionInterceptor，该拦截器中就保存了上面添加的事务属性信息和事务管理器。
 *                TransactionInterceptor点进去之后，我们发现它其实也是一个MethodInterceptor。上面的AutoProxyRegistrar注解
 *                会负责生成一个代理对象，而代理对象在执行目标方法的时候，MethodInterceptor就会工作，
 *                拦截器链(MethodInterceptor数组)就会执行，而在这的拦截器链只有一个，即 TransactionInterceptor。
 *             II.) 上述的拦截器链的作用，我们可以查看TransactionInterceptor.invoke()方法的源码invoke方法会调用
 *                   invokeWithinTransaction()方法，该方法会：
 *                   A.) 先获取事务的相关属性；（第283行）
 *                   B.) 再获取PlatformTransactionManager，如果事先没有添加指定的transactionManager，最终会从容器中按照类型
 *                      获取一个PlatformTransactionManager；
 *                   C.) 执行目标方法。由源码里的第294行：invocation.proceedWithInvocation()来负责执行，如果出现异常的话，
 *                       则会获取到事务管理器，利用事务管理回滚操作。具体可以在第298行：completeTransactionAfterThrowing
 *                       里面的逻辑可以看到回滚操作在第551行的rollback()方法来完成。相反，如果正常的话，获取到事务管理器，
 *                       然后提交事务。（第304行的commitTransactionAfterReturning()方法来完成事务提交）
 *
 */
@EnableTransactionManagement
@ComponentScan("cn.edu.ustc.xk.tx")
@Configuration
public class TxConfig {

    // 配置数据源
    @Bean
    public DataSource dataSource() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/database01");
        return  dataSource;
    }

    /**
     * 在这可以利用自动注入的形式从容器中去拿dataSource这个bean，例如下面这种形式：
     *        public JdbcTemplate jdbcTemplate(DataSource dataSource)
     *   而我们采用的是调方法的形式，所以在这会有一个疑问？是不是我们每生成一个JdbcTemplate都会去调用一次dataSource()方法，
     *   然后把里面的逻辑都执行一遍，又重新生成一个数据源呢？对于配置类TxConfig而言，只要它里面的方法（如dataSource()方法）
     *   是给容器中加组件的话，第二次我们调方法的时候，相当于是从容器中找组件，而不是说把方法里面的逻辑又运行一遍。这是Spring
     *   对配置文件(@Configuration标注的类)的特殊处理。也就是说，配置类中给容器加组件的方法，多次调用只会创建一次而已，接下来
     *   的几次调用都只是从容器中直接获取。
     */
    @Bean
    public JdbcTemplate jdbcTemplate() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    // 添加事务管理器，事务管理器需要管理数据源，所以我们也得添加到构造函数中
    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {

        // 如果是orm或者是jpa需要new的是JtaTransactionManager，具体可以查看PlatformTransactionManager的实现类
        return new DataSourceTransactionManager(dataSource());
    }
}
