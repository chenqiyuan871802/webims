package com.toonan.core.vo;

import java.util.Objects;
import java.util.function.Function;

import org.springframework.beans.BeanUtils;

import com.toonan.core.constant.WebplusCons;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用返回对象
 */
@Data
@ApiModel("通用返回对象")
public class CommonResult<T> extends TnResult {

    /**
     * 数据封装
     */
    private T data;

    protected CommonResult() {
    }

    public CommonResult(long appCode, String appMsg, T data) {
        super.appCode = appCode;
        this.data = data;
        super.appMsg = appMsg;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(WebplusCons.SUCCESS, successMsg, null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(WebplusCons.SUCCESS, successMsg, data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(WebplusCons.SUCCESS, message, data);
    }

    /**
     * 返回失败结果
     * 
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed() {
        return new CommonResult<T>(WebplusCons.ERROR, errorMsg, null);
    }

    /**
     * 返回失败结果
     * 
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(WebplusCons.ERROR, message, null);
    }

    @SuppressWarnings({ "hiding", "unchecked" })
    public <T, R> CommonResult<R> copyOf(Function<T, R> fun) {
        CommonResult<R> result = new CommonResult<R>();
        BeanUtils.copyProperties(this, result, "data");
        T sourceData = (T) this.getData();
        if (Objects.nonNull(sourceData)) {
            R r = fun.apply(sourceData);
            result.setData(r);
        }
        return result;
    }
}
