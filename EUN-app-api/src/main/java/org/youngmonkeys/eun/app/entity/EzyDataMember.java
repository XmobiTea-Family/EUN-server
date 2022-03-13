package org.youngmonkeys.eun.app.entity;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EzyDataMember {
    int code();
    boolean isOptional() default false;
}
