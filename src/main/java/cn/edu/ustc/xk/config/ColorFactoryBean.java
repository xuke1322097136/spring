package cn.edu.ustc.xk.config;

import cn.edu.ustc.xk.bean.Color;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by xuke
 * Description:
 * Date: 2019-01-18
 * Time: 21:32
 */
public class ColorFactoryBean implements FactoryBean<Color> {

    public Color getObject() throws Exception {
        return new Color();
    }

    public Class<?> getObjectType() {
        return Color.class;
    }

    // 返回true表示的是单例，false表示的是多例
    public boolean isSingleton() {
        return true;
    }
}
