package com.toonan.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**   
 * @ClassName:  RepeatSubmit   
 * @Description:未经严格测试，慎用
 * @author: 黄健达 (lsr@sina.com)    
 * @date:   May 20, 2021 4:13:52 PM      
 */   
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RepeatSubmit {
    
    /**
     * <p>方法唯一标识号，推荐UUID</p> 
     * @author 黄健达 (lsr@sina.com)
     * @date: May 21, 2021 9:08:19 AM 
     * @return String
    */
    String identification();
    
    /**
     * <p>解锁时间，单位「秒」，默认：300秒 </p> 
     * @author 黄健达 (lsr@sina.com)
     * @date: May 20, 2021 4:26:03 PM 
     * @return long
    */
    long timeout() default 300;

    /**
     * <p>关键参数，唯一标识任务。默认accessToken</p> 
     * @author 黄健达 (lsr@sina.com)
     * @date: May 20, 2021 4:26:13 PM 
     * @return String[]
    */
    String[] params() default "accessToken";
}
