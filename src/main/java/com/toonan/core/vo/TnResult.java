package com.toonan.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("基础返回对象")
public abstract class TnResult {
    /**
     * 状态码
     */
    @ApiModelProperty("状态码")
    protected long appCode;
    /**
     * 提示信息
     */
    @ApiModelProperty("提示信息")
    protected String appMsg;

    @ApiModelProperty(hidden = true)
    static String successMsg = "成功";

    @ApiModelProperty(hidden = true)
    static String errorMsg = "失败";
    
}
