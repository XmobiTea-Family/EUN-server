package org.youngmonkeys.eun.app.entity;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EzyDataMember {
    int code() default 0;
    boolean isOptional() default false;
}
