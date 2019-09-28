package cn.edu.ustc.xk.config;

import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * Created by xuke
 * Description:
 * Date: 2019-01-18
 * Time: 16:18
 */
public class MyTypeFilter implements TypeFilter {
    /**
     *
     * @param metadataReader : 读取到的当前正在扫描的类的信息
     * @param metadataReaderFactory ：可以获取到其他任何类的信息
     * @return
     * @throws IOException
     */
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {

        metadataReader.getAnnotationMetadata();// 获取当前类注解的信息
        metadataReader.getResource(); // 获取当前类资源（比如类的路径，即存在哪个盘下面等）

        ClassMetadata classMetadata = metadataReader.getClassMetadata();// 获取当前正在扫描的类的类信息，比如：它的类型啊，它实现了什么接口啊等等。
        String className = classMetadata.getClassName();

        // 组件里面含有"er"的都代表匹配成功，在这输出：myTypeFilter(虽然我们没有使用任何的注解来标注MyTypeFilter,它也会进行匹配)，
        //                                            bookController，bookService都是匹配成功的，都含有"er"
        if (className.contains("er")){
            return true; // 代表匹配成功
        }
        // 返回的false表示一个都不匹配
        return false;
    }
}
