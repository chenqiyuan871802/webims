package com.ims.common.web;


import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 类名:com.ims.common.web.BaseController
 * 描述:基础控制类
 * 编写者:陈骑元
 * 创建时间:2018年4月6日 下午11:15:54
 * 修改说明:
 */
public class BaseController {
    @Autowired
    protected  HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    
    protected org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

   


}
