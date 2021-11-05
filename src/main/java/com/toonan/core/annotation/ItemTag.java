package com.toonan.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * 类名:com.toonan.annotation.Dict
 * 描述:字典标签
 * 编写者:陈骑元
 * 创建时间:2018年12月17日 下午6:51:16
 * 修改说明:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ItemTag {

    /**
     * 类型
     *
     * @return
     */
    public String type() default "";

    /**
     * 默认值
     *
     * @return
     */
    public String defaultDisplay() default "";

    /**
     * 字典文本的后缀
     *
     * @return
     */
    public String suffix() default "dict";
    /**
     * 
     * 简要说明：redis 类型 1字符串 2哈希 3列表
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2020年4月22日 上午11:21:19 
     * @param 说明
     * @return 说明
     */
    public String redisType() default "3";
}
