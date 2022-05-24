package org.youngmonkeys.xmobitea.eun.app.entity;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EzyDataMember {
    int code();
    boolean isOptional() default false;
}
