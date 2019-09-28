package cn.edu.ustc.xk.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by xuke
 * Description:
 * Date: 2019-01-19
 * Time: 17:44
 */
public class LifeCycle02 {
    public LifeCycle02(){
        System.out.println("LifeCycle02 Constructor.................");
    }

    @PostConstruct
    public void init(){
        System.out.println("LifeCycle02 .......@PostConstruct...........");
    }

    @PreDestroy
    public void destory(){
        System.out.println("LifeCycle02 .....@PreDestroy...........");
    }
}
