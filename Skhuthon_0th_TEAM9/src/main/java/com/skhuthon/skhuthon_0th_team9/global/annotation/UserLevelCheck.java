package com.skhuthon.skhuthon_0th_team9.global.annotation;

import com.skhuthon.skhuthon_0th_team9.app.domain.user.UserAccessLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UserLevelCheck {

    UserAccessLevel level() default UserAccessLevel.UNREGISTERED_USER;
}
