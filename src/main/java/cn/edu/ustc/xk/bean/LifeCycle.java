package cn.edu.ustc.xk.bean;

/**
 * Created by xuke
 * Description:
 * Date: 2019-01-19
 * Time: 16:42
 */
public class LifeCycle {

    public LifeCycle(){
        System.out.println("LifeCycle Constructor.................");
    }

    public void init(){
        System.out.println("LifeCycle init..................");
    }

    public void destory(){
        System.out.println("LifeCycle destory................");
    }
}
