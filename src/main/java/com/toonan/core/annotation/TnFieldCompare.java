package com.toonan.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: TnFieldCompare
 * @Description: 需求比较FIELD
 * @author: 黄健达 (lsr@sina.com)
 * @date: Sep 14, 2021 4:17:30 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TnFieldCompare {

    /**
     * <p>
     * 变更更正数据项名称
     * </p>
     * 
     * @author 黄健达 (lsr@sina.com)
     * @date: Sep 14, 2021 4:18:09 PM
     * @return String
     */
    public abstract String bggzsjxmc();
}
