package com.wasteland.customprocessor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wasteland
 * @create 2025-03-04
 */
@Target(ElementType.TYPE) // 该注解只能用于类上
@Retention(RetentionPolicy.SOURCE) // 注解仅在源码阶段保留
public @interface ToString {
}
