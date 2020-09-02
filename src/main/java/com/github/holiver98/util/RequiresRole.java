package com.github.holiver98.util;

import com.github.holiver98.model.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@RequiresAuthentication
public @interface RequiresRole {
    public Role role() default Role.CUSTOMER;
}
