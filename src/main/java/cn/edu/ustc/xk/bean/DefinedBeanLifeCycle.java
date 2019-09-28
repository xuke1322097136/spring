package cn.edu.ustc.xk.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by xuke
 * Description:
 * Date: 2019-01-19
 * Time: 17:18
 */
public class DefinedBeanLifeCycle implements InitializingBean, DisposableBean {

    public DefinedBeanLifeCycle(){
        System.out.println("自定义的bean构造了...........");
    }

    /**
     * afterPropertiesSet方法来自于InitializingBean接口，他的调用时机可以查看下面一段源注释：
     *        Invoked by the containing {@code BeanFactory} after it has set all bean properties
     *  意思就是我们的BeanFactory创建好对象并且把bean里面所有的属性设置好值之后，就会调用该方法，
     *  其实它的作用也就是相当于我们的初始化方法。。
     *
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet.......自定义的bean初始化了........");
    }

    /**
     * destroy方法来自于DisposableBean接口，他的调用时机也可以查看下面一段源注释：
     *      Invoked by the containing {@code BeanFactory} on destruction of a bean.
     *  意思是BeanFactory在销毁的时候会把这些单实例bean给我们调用destroy方法给销毁掉,
     *  其实也就是在容器关闭的时候会调用该方法
     *
     * @throws Exception
     */
    public void destroy() throws Exception {
        System.out.println("destroy.........自定义的bean销毁了......");
    }
}
