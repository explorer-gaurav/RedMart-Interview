package com.gauravsaluja.redmart.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Fragment scope
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {

}