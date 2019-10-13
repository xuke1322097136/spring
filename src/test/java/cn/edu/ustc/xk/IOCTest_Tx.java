package cn.edu.ustc.xk;

import cn.edu.ustc.xk.tx.EmployeeService;
import cn.edu.ustc.xk.tx.TxConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by xuke
 * Description: 测试TxConfig配置类已经他用到的EmployeeService类和EmployeeDao类。
 *              具体测试的时候，我们可以查看有没有添加注解@Transactional来对比我们有没有将数据插入到数据库中。
 *              虽然都会报数学异常的错，但是没有添加@Transactional的话是不会多一条记录的。
 * Date: 2019-10-13
 * Time: 23:04
 */

public class IOCTest_Tx {

    @Test
    public void testTx(){

        try(AnnotationConfigApplicationContext ioc =
                    new AnnotationConfigApplicationContext(TxConfig.class);){
            EmployeeService service = ioc.getBean(EmployeeService.class);
            service.insertEmployee();

        }
    }
}
