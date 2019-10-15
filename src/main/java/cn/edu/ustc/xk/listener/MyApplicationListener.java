package cn.edu.ustc.xk.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by xuke
 * Description:
 * Date: 2019-10-15
 * Time: 23:50
 */
@Component
public class MyApplicationListener implements ApplicationListener {

    // 当容器中发布此事件之后，该方法将会被触发。
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("收到的事件：" + event);
    }
}
