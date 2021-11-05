package com.toonan.system.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.toonan.core.annotation.ItemTag;
import com.toonan.core.matatype.impl.BaseModel;

/**
 * <p>
 * 系统日志表
 * </p>
 *
 * @author 陈骑元
 * @since 2020-11-30
 */
@TableName("sys_log")
public class Log extends BaseModel<Log> {

    private static final long serialVersionUID = 1L;

    /**
     * 日志编号
     */
    @TableId("log_id")
    private String logId;
    /**
     * 日志内容
     */
    private String content;
    /**
     * 日志操作类型 0缺省1登陆2退出3查询4更新5删除
     */
    @ItemTag(type="log_type")
    @TableField("log_type")
    private String logType;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 操作用户编号
     */
    @TableField("user_id")
    private String userId;
    /**
     * 操作用户名称
     */
    @TableField("user_name")
    private String userName;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 请求参数
     */
    private String params;
    /**
     * 响应时间
     */
    @TableField("response_time")
    private Integer responseTime;
    /**
     * 描述
     */
    private String description;


    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Integer getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Integer responseTime) {
        this.responseTime = responseTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected Serializable pkVal() {
        return this.logId;
    }

    @Override
    public String toString() {
        return "Log{" +
        "logId=" + logId +
        ", content=" + content +
        ", logType=" + logType +
        ", createTime=" + createTime +
        ", method=" + method +
        ", userId=" + userId +
        ", userName=" + userName +
        ", ip=" + ip +
        ", params=" + params +
        ", responseTime=" + responseTime +
        ", description=" + description +
        "}";
    }
}
