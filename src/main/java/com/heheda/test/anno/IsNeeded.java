package com.heheda.test.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: test
 * @description: 是否需要注解
 * @author: clx
 * @create: 2019-08-10 14:18
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface IsNeeded
{

    /**
     * 是否需要从解析excel赋值
     * @return
     *         true:需要  false:不需要
     * @see [类、类#方法、类#成员]
     */
    boolean isNeeded() default true;
}
