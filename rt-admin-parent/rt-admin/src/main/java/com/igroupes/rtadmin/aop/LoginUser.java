package com.igroupes.rtadmin.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface LoginUser {
    /**
     * true: 表示修饰的类和方法必须登录
     * false: 表示不一定要登录，但是会传递用户id,如果方法参数中有com.igroupes.rtadmin.dto.UserInfo对象，在没有登录情况下，赋值为null
     * @return
     */
    boolean require() default  true;
}
