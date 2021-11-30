package com.yollpoll.arch.annotation;


import com.yollpoll.arch.base.BaseViewModel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by spq on 2021/2/13
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewModel {
    Class<? extends BaseViewModel> value() default BaseViewModel.class;
}
