package com.pplovely.anotationrouter;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)//设置为编译时生效
public @interface TestRouter {
    public String url() default "";
}
