package com.toonan.core.wechat;
/**
 * 
 * @ClassName:  WechatCons   
 * @Description:微信常用变量
 * @author: 陈骑元（chenqiyuan@toonan.com)
 * @date:   2020年7月5日 下午3:58:19     
 * @Copyright: 2020 www.toonan.com Inc. All rights reserved. 
 * 注意：本内容仅限于广州市图南软件有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class WechatCons {
	/**
	 * 微信基本接口
	 */
	public static String WECHAT_BASE_URL="https://api.weixin.qq.com/";
	/**
	 * 获取ACCESS_TOKEN 接口
	 */
	public static String GET_ACCESS_TOKEN=WECHAT_BASE_URL+"cgi-bin/token";
	/**
	 * 获取openid接口
	 */
	public static String JSCODE_SESSION=WECHAT_BASE_URL+"sns/jscode2session";
	/**
	 * 授权类型
	 */
	public static String GRANT_TYPE="client_credential";
	/**
	 * 
	 */
	public static String GRANT_TYPE_AUTHORIZATION="client_credential";
	/**
	 * 成功代码
	 */
	public static String ERRCODE_SUCCESS="0";
	

}
