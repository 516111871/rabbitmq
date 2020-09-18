package com.demo.annotation;

import javax.xml.bind.Element;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsumerAnnotation {
    /**
     *queueName
     * @return
     */
    String queueName() default "";

    /**
     * 持久化
     * @return
     */
    boolean durable() default true;

    /**
     * 独享
     * @return
     */
    boolean exclusive() default false;

    /**
     * 自动删除
     * @return
     */
    boolean autoDel() default false;

    /**
     * json字符串
     * @return
     */
    String arguments() default "";

}
