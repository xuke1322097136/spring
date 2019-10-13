package cn.edu.ustc.xk.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xuke
 * Description: 用来处理数据库database01里面的employee
 * Date: 2019-10-13
 * Time: 22:39
 *
 *    添加事务的意思其实是如果该方法里面有任何一个步骤出现问题的话，比如说insertEmployee方法里面还有其他的dao操作，
 *        如果有一个dao操作失败，那么整个方法都应该回滚。为了模拟事务控制，我们可以添加了一个分母为0的除法操作。
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    // 告诉Spring这个方法是一个事务方法，这样spring在执行这个方法的时候，就会进行事务控制。如果所有的操作都正常执行，
    // 那么就会正常提交，如果方法出现异常，那么这个方法将会回滚。
     @Transactional
    public void insertEmployee(){
        employeeDao.insert();
        System.out.println("insert success");
        int a = 10 / 0;
    }
}
