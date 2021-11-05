package com.toonan.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.toonan.core.lsapi.LsApiUtil;
import com.toonan.core.minio.MinioClientUtil;

/**
 * 
 * @ClassName:  WeedfsConfig   
 * @Description:weedfs配置
 * @author: 陈骑元（chenqiyuan@toonan.com)
 * @date:   2020年5月8日 上午10:35:06     
 * @Copyright: 2020 www.toonan.com Inc. All rights reserved. 
 * 注意：本内容仅限于广州市图南软件有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

@Configuration
public class WebplusConfig  {
    /**
     * 
     * 简要说明：来穗API接入
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2020年10月22日 下午6:43:54 
     * @param 说明
     * @return 说明
     */
	@Value("${ls.url}")
	public void setLsUrl(String lsUrl) {
		LsApiUtil.lsUrl = lsUrl;
	}
    /**
     * 
     * 简要说明：来穗API的cdkey
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2020年10月22日 下午6:44:15 
     * @param 说明
     * @return 说明
     */
	@Value("${ls.cdkey}")
	public void setLsCdkey(String lsCdkey) {
		LsApiUtil.lsCdkey=lsCdkey;
	}
	
    /**
     * 
     * 简要说明：mimio访问地址
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2021年8月20日 下午1:43:53 
     * @param 说明
     * @return 说明
     */
	@Value("${minio.endpoint}")
	public void setEndpoint(String endpoint) {
		MinioClientUtil.ENDPOINT = endpoint;
	}
	/**
	 * 
	 * 简要说明：minio文件服务器用户账号
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2021年8月20日 下午1:44:06 
	 * @param 说明
	 * @return 说明
	 */
	@Value("${minio.accessKey}")
	public void setAccessKey(String accessKey) {
		MinioClientUtil.ACCESS_KEY=accessKey;
	}
	/**
	 * 
	 * 简要说明：minio访问秘钥
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2021年8月20日 下午1:45:01 
	 * @param 说明
	 * @return 说明
	 */
	@Value("${minio.secretKey}")
	public void setSecretKey(String secretKey) {
		MinioClientUtil.SECRET_KEY=secretKey;
	}
	
	

	
	
	
	
}
