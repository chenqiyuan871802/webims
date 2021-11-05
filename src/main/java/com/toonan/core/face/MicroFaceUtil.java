package com.toonan.core.face;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.toonan.core.constant.WebplusCons;
import com.toonan.core.matatype.Dto;
import com.toonan.core.matatype.Dtos;
import com.toonan.core.util.WebplusHttp;
import com.toonan.core.util.WebplusJson;
import com.toonan.core.util.WebplusUtil;
import com.toonan.core.vo.R;

/**
 * 
 * @ClassName:  MicroFaceUtil   
 * @Description:南沙微警人脸识别工具类
 * @author: 陈骑元（chenqiyuan@toonan.com)
 * @date:   2021年3月25日 上午9:20:50     
 * @Copyright: 2021 www.toonan.com Inc. All rights reserved. 
 * 注意：本内容仅限于广州市图南软件有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class MicroFaceUtil {
    
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(MicroFaceUtil.class);
	/**
	 * 微警人脸认证客户端编号
	 */
	public static String MICRO_FACE_CLIENT_ID="";
	/**
	 * 微警人脸认证客户端秘钥
	 */
	public static String MICRO_FACE_CLIENT_SECRET="";
	/**
	 * 获取令牌URL
	 */
	public static String ACCESS_TOKEN_URL="https://rz.weijing.gov.cn/v2/api/getaccesstoken";
	/**
	 * 获取认证URL 
	 */
	public static String AUTH_REQ_URL="https://rz.weijing.gov.cn/v2/api/authreq";
	/**
	 * 脱敏记录URL
	 */
	public static String CONCEAL_HIST_URL="https://rz.weijing.gov.cn/v2/api/concealhist";
	
	
	/**
	 * 成功代码
	 */
	public static String RETCODE_SUCCESS="0";
	/**
	 * 认证类型
	 */
	public static String AUTH_TYPE="MiniRegular";
	/**
	 * 认证模式
	 */
	public static Integer MODE=66;
	/**
	 * 二维码有效期
	 */
	public static String EXP_TYPE="exp_sec";
	/**
	 * 二维码有效期单位秒
	 */
	public static Long EXP_SEC=420l;
	/**
	 * 
	 * 简要说明：获取微警认证令牌
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2021年3月25日 上午9:27:22 
	 * @param 说明
	 * @return 说明
	 */
	public static  R  getAccessToken() {
		Dto pDto =Dtos.newDto();
		pDto.put("clientId",MICRO_FACE_CLIENT_ID);
		pDto.put("clientSecret",MICRO_FACE_CLIENT_SECRET);
		String jsonString= WebplusHttp.doGet(ACCESS_TOKEN_URL, pDto);
		log.info("调用微警人脸识别获取令牌接口返回信息：	"+jsonString+"；发送参数:"+WebplusJson.toJson(pDto));
		if(WebplusUtil.isNotEmpty(jsonString)) {
			JSONObject jsonObject = JSONObject.parseObject(jsonString);
			String retCode=jsonObject.getString("retCode");
			String retMessage=jsonObject.getString("retMessage");
			if(RETCODE_SUCCESS.equals(retCode)) {
				String accessToken=jsonObject.getString("accessToken");
				return R.ok().put("accessToken",accessToken);
			}else {
				log.error("调用微警人脸识别获取令牌失败：错误原因"+retMessage);
				return R.error(retMessage);
			}
		}else {
			
			return R.error("调用微警人脸识别接口网络异常");
		}
	}
	/**
	 * 
	 * 简要说明：根据证件号码和姓名获取认证凭证
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2021年3月25日 上午9:58:30 
	 * @param 说明
	 * @return 说明
	 */
	public static R  getCertToken(String zjhm,String xm,String accessToken,String subject) {
		if(WebplusUtil.isEmpty(zjhm)) {
			
			return R.error("证件号码[zjhm]参数不能为空");
		}
		if(WebplusUtil.isEmpty(xm)) {
			
			return R.error("姓名[xm]参数不能为空");
		}
		if(WebplusUtil.isEmpty(accessToken)) {
			
			return R.error("令牌[accessToken]不能为空");
		}
		if(WebplusUtil.isEmpty(subject)) {
			subject="身份验证";
		}
		Dto modelDto=Dtos.newDto();
		modelDto.put("accessToken", accessToken);
		modelDto.put("authType",AUTH_TYPE);
		modelDto.put("mode", MODE);
		Dto idInfoDto=Dtos.newDto();
		idInfoDto.put("idNum", zjhm);
		idInfoDto.put("fullName",xm);
		modelDto.put("idInfo", idInfoDto);
		Dto expDto=Dtos.newDto();
		expDto.put("expType",EXP_TYPE);
		expDto.put("expSec", EXP_SEC);
		modelDto.put("exp", expDto);
		Dto businessDto=Dtos.newDto();
		businessDto.put("subject", subject);
		modelDto.put("businessInfo", businessDto);
		String jsonData=WebplusJson.toJson(modelDto);
		String jsonString=WebplusHttp.doJsonPost(AUTH_REQ_URL, Dtos.newDto(), jsonData);
		log.info("调用微警认证凭证接口返回信息："+jsonString+" 发送参数:"+jsonData);
		if(WebplusUtil.isEmpty(jsonString)) {
			log.error("调用微警认证凭证接口失败,网络异常");
			return R.error("调用微警认证凭证接口失败,网络异常");
		}
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		String retCode=jsonObject.getString("retCode");
		String retMessage=jsonObject.getString("retMessage");
		if(RETCODE_SUCCESS.equals(retCode)) {
			JSONObject tokenInfo=	 jsonObject.getJSONObject("tokenInfo");
			String certToken=tokenInfo.getString("certToken");
			String expireAt=	tokenInfo.getString("expireAt");
		    return R.ok().put("certToken",certToken).put("expireAt",expireAt);
		}else {
			log.error("调用微警认证凭证接口失败,错误原因="+retMessage+" 错误代码="+retCode);
			return R.error(retMessage);
		}
		
	}
	/**
	 * 
	 * 简要说明：获取脱敏认证记录
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2021年3月25日 上午11:15:52 
	 * @param 说明
	 * @return 说明
	 */
	public static R getConcealhist(String accessToken,String certToken) {
		if(WebplusUtil.isEmpty(accessToken)) {
			return R.error("令牌[accessToken]不能为空");
		}
        if(WebplusUtil.isEmpty(certToken)) {
        	return R.error("认证凭证[certToken]不能为空");
		}
		Dto concealDto=Dtos.newDto();
		concealDto.put("accessToken", accessToken);
		Dto historyDto=Dtos.newDto();
		historyDto.put("certToken", certToken);
		concealDto.put("authHistQry",historyDto);
		String jsonData=WebplusJson.toJson(concealDto);
		String jsonString=WebplusHttp.doJsonPost(CONCEAL_HIST_URL, Dtos.newDto(), jsonData);
		log.info("调用微警认证脱敏认证记录接口返回信息："+jsonString+" 发送参数:"+jsonData);
		if(WebplusUtil.isEmpty(jsonString)) {
			log.error("调用微警认证脱敏认证记录失败,网络异常");
			return R.error("调用微警认证脱敏认证记录接口失败,网络异常");
		}
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		String retCode=jsonObject.getString("retCode");
		
		String retMessage=jsonObject.getString("retMessage");
		if(RETCODE_SUCCESS.equals(retCode)) {
			JSONObject authDataObj=	 jsonObject.getJSONObject("authData");
			String resCode=authDataObj.getString("resCode");
			if(RETCODE_SUCCESS.equals(resCode)) {
				Dto dataDto=Dtos.newDto();
				Dto authObject=jsonObjectToDto(authDataObj);
				dataDto.putAll( authObject);
			    return R.toData(dataDto);
			}
		}
		log.error("调用微警认证脱敏认证记录接口失败,错误原因="+retMessage+" 错误代码="+retCode);
		return R.error(retMessage);
	}
	
	
	/**
	 * 
	 * 简要说明：Jsonobjct 转Dto 
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年12月11日 下午5:08:08 
	 * @param 说明
	 * @return 说明
	 */
   private static    Dto jsonObjectToDto(JSONObject jsonObject) {
	     Dto dataDto=Dtos.newDto();
	     for(String key:jsonObject.keySet()) {
	    	 dataDto.put(key, jsonObject.get(key));
	     }
	    
	    return  dataDto;
   }
	
	public static void main(String[] args) {
		MICRO_FACE_CLIENT_ID="b9a0717ff85bf3b7";
		MICRO_FACE_CLIENT_SECRET="12f601c7-c9d2-4398-b0e8-ecd6f02572e4";
		R  r=getAccessToken();
		if(WebplusCons.SUCCESS==r.getAppCode()) {
			String accessToken=r.getString("accessToken");
			R  rs =getCertToken("412828198503202419 ","王科孝",accessToken,"");
			rs.println();
			R s=getConcealhist(accessToken,rs.getString("certToken"));
			s.println();
		}
	}
}
