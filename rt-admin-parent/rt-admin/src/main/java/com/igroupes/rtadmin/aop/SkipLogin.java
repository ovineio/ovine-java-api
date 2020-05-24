package com.igroupes.rtadmin.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SkipLogin {
    // 用于类被@LoginUser(require=true)修改，通过@SkipLogin表示不用登陆（排除方法）
}
