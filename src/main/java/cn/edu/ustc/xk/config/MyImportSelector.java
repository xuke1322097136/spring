package cn.edu.ustc.xk.config;

import cn.edu.ustc.xk.bean.Blue;
import cn.edu.ustc.xk.bean.Yellow;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Created by xuke
 * Description:
 * Date: 2019-01-18
 * Time: 20:22
 */
// 自定义需要返回需要导入到容器中的组件,返回值是导入到容器中的组件全限定名。
public class MyImportSelector implements ImportSelector {

    /**
     *     AnnotationMetadata：能够获取到@Import注解的类的所有注解信息，在我们这，@Import标注的类是MyConfig4，所以我们能够获取到除了@Import注解里面的信息，
     *      还能获取到这个类里面的所有注解信息，如@Conditional,@Configuration等注解.
     *      不信我们可以debug一下，在selectImports方法里面打个断点，可以看到注解数组长度为2，即MyConfig4的两个注解：@Configuration和@Import
     *
     *      拷贝类的全限定名：copy reference，然后paste without formatting
     */
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"cn.edu.ustc.xk.bean.Blue", "cn.edu.ustc.xk.bean.Yellow"};
    }
}
