# spring
注解版spring学习笔记:   

容器：AnnotationConfigApplicationContext

1. 组件添加（待补充）；
2. 组件赋值（待补充）；
3. 组件注入（待补充）。
    
最详细的Spring核心IOC的源码分析：   
https://blog.csdn.net/nuomizhende45/article/details/81158383      
Spring常见注解：   
https://blog.csdn.net/u010648555/article/details/76299467   
BeanFactory和FactoryBean的区别:   
https://www.cnblogs.com/aspirant/p/9082858.html      
容器中已经注册了bean，但是还没创建对象，啥意思？39讲第7分钟。    
BeanFactory里面包含了BeanPostProcessor和一些跟系统环境/系统属性相关的bean，
BeanFactoryPostProcessor又分为两大类，一种是它的子类，一种是它自己这种类型的，这两个类是在beanFactory的
标准初始化之后使用。此时bean的定义都已经保存加载到beanFactory中了，但是还没有创建bean的实例。

