package com.yollpoll.framework.annotation;

import com.yollpoll.framework.base.BaseViewModel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by spq on 2021/2/13
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewModel {
    Class<? extends BaseViewModel> value() default BaseViewModel.class;
}
