package com.toonan.core.wechat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.toonan.core.matatype.Dto;
import com.toonan.core.matatype.Dtos;
import com.toonan.core.util.WebplusHttp;
import com.toonan.core.util.WebplusUtil;


/**
 * 
 * @ClassName:  WechatApi   
 * @Description:微信api接口公共类
 * @author: 陈骑元（chenqiyuan@toonan.com)
 * @date:   2020年7月5日 下午3:58:00     
 * @Copyright: 2020 www.toonan.com Inc. All rights reserved. 
 * 注意：本内容仅限于广州市图南软件有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class WechatApi {
	
	private static Log log = LogFactory.getLog(WechatApi.class);
	/**
	 * 
	 * 简要说明：获取微信AccessToken
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年7月5日 下午5:37:24 
	 * @param 说明
	 * @return 说明
	 */
	public static String getAccessToken(String appId,String appSecret) {
		if(WebplusUtil.isNotAnyEmpty(appId,appSecret)) {
			Dto pDto=Dtos.newDto();
			pDto.put("grant_type",WechatCons.GRANT_TYPE);
			pDto.put("appid",appId);
			pDto.put("secret",appSecret);
			String jsonString=WebplusHttp.doGet(WechatCons.GET_ACCESS_TOKEN, pDto);
			log.info("调用微信获取accessToken接口返回信息："+jsonString);
			if(WebplusUtil.isNotEmpty(jsonString)) {
				JSONObject json = JSONObject.parseObject(jsonString);
				String errcode=json.getString("errcode");
				if(WechatCons.ERRCODE_SUCCESS.equals(errcode)) {
					
					return json.getString("access_token");
				}
			}
		}
		
		return "";
	}
	/**
	 * 
	 * 简要说明：通过临时code获取登录凭证openid
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年7月5日 下午6:03:45 
	 * @param 说明
	 * @return 说明
	 */
	public static String getOpenid(String appId,String appSecret,String code) {
		if(WebplusUtil.isNotAnyEmpty(appId,appSecret,code)) {
			Dto pDto=Dtos.newDto();
			pDto.put("grant_type",WechatCons.GRANT_TYPE_AUTHORIZATION);
			pDto.put("appid",appId);
			pDto.put("secret",appSecret);
			pDto.put("js_code",code);
			String jsonString=WebplusHttp.doGet(WechatCons.JSCODE_SESSION, pDto);
			log.info("调用微信获取openid接口返回信息："+jsonString);
			if(WebplusUtil.isNotEmpty(jsonString)) {
				JSONObject json = JSONObject.parseObject(jsonString);
				String openid=json.getString("openid");
				if(WebplusUtil.isNotEmpty(openid)) {
					
					return openid;
				}
			}
		}
		return "";
	}
   
	public static void main(String[] args) {
		String s=getOpenid("wx696cbd92976c1325","17f77cff9eee5debbd85cc8253cda779","0536r9ll2FSrR54muxll2JYVB626r9lL");
        System.out.println(s);
	}
}
