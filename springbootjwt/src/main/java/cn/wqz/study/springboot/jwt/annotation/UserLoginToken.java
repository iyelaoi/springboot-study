package cn.wqz.study.springboot.jwt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
public @interface UserLoginToken {
    boolean required() default true;
}
