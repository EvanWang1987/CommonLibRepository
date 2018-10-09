package com.github.evan.common_utils.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Evan on 2018/9/22.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseField {

    /**
     * 字段名
     * @return
     */
    String columnName();

    /**
     * 字段数据类型
     * @return
     */
    String columnDataType();

    /**
     * 主键
     * @return
     */
    boolean isPrimaryKey() default false;

    /**
     * 唯一
     * @return
     */
    boolean isUnique() default false;

    /**
     * 不可存null
     * @return
     */
    boolean isNotNull() default false;

    /**
     * 默认值
     * @return
     */
    String defaultValue() default "";

    /**
     * 是否是新添加字段
     * @return
     */
    boolean isNewAddColumn() default false;

}
