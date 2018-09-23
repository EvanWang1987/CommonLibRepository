package com.github.evan.common_utils.db.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Evan on 2018/9/22.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseTable {

    public String tableName();

    public int sinceDbVersion();

    public boolean isSaveLastVersionTableData() default true;

    public boolean isGenerateAndroidPrimaryKey() default true;
}
