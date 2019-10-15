package cn.edu.ustc.xk.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Created by xuke
 * Description: 用来测试@EventListener注解。可以看到最终IOCTest_ApplicationListener的输出结果中含有两组：
 *             一组是之前我们实现ApplicationListener接口来完成的，另一种是通过 @EventListener来完成的。
 * Date: 2019-10-16
 * Time: 0:41
 */
@Service
public class BookService {

    // @EventListener可以添加在任意方法上
    // 我们可以监听多个事件，使用的是数组的形式。事件发生以后，我们想拿到事件的话，只需要在参数位置加上一个事件即可拿到该事件
    @EventListener(classes = {ApplicationEvent.class})
    public void listen(ApplicationEvent event){
        System.out.println("BookService 监听到的事件：" + event);
    }
}
