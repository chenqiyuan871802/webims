package com.toonan.core.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.TypeUtils;

/**
 * @ClassName: WebplusFieldUtils
 * @Description: field工具类
 * @author: 黄健达 (lsr@sina.com)
 * @date: Sep 10, 2021 5:53:54 PM
 */
public class WebplusFieldUtils {
    private static final Logger logger = LoggerFactory.getLogger(WebplusFieldUtils.class);

    /**
     * <p>
     * Reads the named {@link Field}. Superclasses will be considered.
     * </p>
     * 
     * @author 黄健达 (lsr@sina.com)
     * @date: Sep 10, 2021 5:56:26 PM
     * @param target
     * @param fieldName
     * @return Object 内容
     */
    public static <T> T readVlaue(Object target, String fieldName) {
        return readVlaue(target, fieldName, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T readVlaue(Object target, String fieldName, T defaulValue) {
        Assert.isTrue(Objects.nonNull(target) && StringUtils.isNotBlank(fieldName), "参数缺失");
        Object obj = null;
        try {
            List<Field> fieldsList = FieldUtils.getAllFieldsList(target.getClass());
            Optional<Field> targetField = fieldsList.parallelStream().filter(e -> e.getName().equals(fieldName)).findFirst();
            if (targetField.isPresent()) {
                obj = FieldUtils.readField(targetField.get(), target, true);
            }
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }
        return Objects.nonNull(obj) ? (T) obj : defaulValue;
    }

    /**
     * <p>
     * 填充目标对象中的空值，create_by与craete_time字段强制填充 TODO
     * </p>
     * 
     * @author 黄健达 (lsr@sina.com)
     * @date: Sep 14, 2021 4:47:58 PM
     * @param <T>
     * @param source
     * @param target void
     */
    public static <T> void filling(T source, T target) {
        Assert.isTrue(TypeUtils.isAssignable(source.getClass(), target.getClass()), "对象类型不匹配！");
        // TODO
    }

}
