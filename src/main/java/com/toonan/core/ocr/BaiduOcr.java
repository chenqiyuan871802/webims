package com.toonan.core.ocr;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;
import com.toonan.core.matatype.Dto;
import com.toonan.core.matatype.impl.HashDto;
import com.toonan.core.util.WebplusFile;
import com.toonan.core.util.WebplusUtil;
import com.toonan.core.vo.R;

/**
 * 
 * @ClassName:  BaiduOcr   
 * @Description:百度Ocr识别工具类
 * @author: 陈骑元（chenqiyuan@toonan.com)
 * @date:   2020年9月23日 下午10:27:58     
 * @Copyright: 2020 www.toonan.com Inc. All rights reserved. 
 * 注意：本内容仅限于广州市图南软件有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class BaiduOcr {
	/**
	 * 身份证正面
	 */
	public static String SIDE_FRONT="front";
	/**
	 * OCR api_id api_key,
	 */
	public static String OCR_APP_ID="";
	public static String OCR_API_KEY="";
	public static String OCR_SECRET_KEY="";
	@SuppressWarnings("serial")
	public static Dto  IMAGE_STATUS=new HashDto(){{
		put("normal","识别正常");
		put("reversed_side","身份证正反面颠倒");
		put("non_idcard","上传的图片中不包含身份证");
		put("blurred","身份证模糊");
		put("other_type_card","其他类型证照");
		put("over_exposure","身份证关键字段反光或过曝");
		put("over_dark","身份证欠曝（亮度过低）");
		put("unknown","未知状态");

	}};
	
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(BaiduOcr.class);
	
	/**
	 * 
	 * 简要说明：解析身份证正面来自文件URL
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年9月23日 下午11:26:24 
	 * @param 说明
	 * @return 说明
	 */
	public static R identifyFrontIdCardFromFileUrl(String fileUrl) {
		
		String base64Image=WebplusFile.fromFileUrlToBase64(fileUrl);
	    return identifyFrontIdCardFromBase64(base64Image);
		
	}
	/**
	 * 
	 * 简要说明：解析身份证正面
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年9月23日 下午11:26:24 
	 * @param 说明
	 * @return 说明
	 */
	public static R identifyFrontIdCardFromFilePath(String filePath) {
		File file=new File(filePath);
		if(file.exists()) {
			String base64Image=WebplusFile.fromFileToBase64(filePath);
			return identifyFrontIdCardFromBase64(base64Image);
		}
		return R.error("解析身份证失败，图像路径文件不存在");
	}
	/**
	 * 
	 * 简要说明：解析身份证正面
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年9月23日 下午11:26:24 
	 * @param 说明
	 * @return 说明
	 */
	public static R identifyFrontIdCardFromBase64(String base64Image) {
		JSONObject res=identifyIdCard(base64Image,SIDE_FRONT);
		if(WebplusUtil.isEmpty(res)) {
			
			return R.error("解析身份证正面失败");
		}
		if(!res.has("image_status")) {
			return R.error("解析身份证正面失败");
		}
		String imageStatus=res.getString("image_status"); //图像状态
		if(WebplusUtil.isEmpty(imageStatus)) {  //说明识别失败
		  String errorCode=res.getString("error_code");
		  String errorMsg=res.getString("error_msg");
		  return R.error("身份证正面识别错误：错误码errorCode="+errorCode+",错误信息errorMsg="+errorMsg);
		}
		if("normal".equals(imageStatus)) {
			JSONObject wordsResult=res.getJSONObject("words_result");
			String xm=getWordsResultValue("姓名",wordsResult);
			String zjhm=getWordsResultValue("公民身份号码",wordsResult);
			String hjxz=getWordsResultValue("住址",wordsResult);
			String mz_dict=getWordsResultValue("民族",wordsResult);
			String xb_dict=getWordsResultValue("性别",wordsResult);
		    R r=R.ok();
			r.put("zjhm", zjhm);
			r.put("xm", xm);
			r.put("hjxz", hjxz);
			r.put("mz_dict", mz_dict+"族");
			r.put("xb_dict", xb_dict);
			return r;
		    
		}
		String imageStatusValue=IMAGE_STATUS.getString(imageStatus);
		log.error("解析身份证正面失败：错误原因="+imageStatusValue);
		return R.error("解析身份证正面失败：错误原因="+imageStatusValue);
		
	}
	/**
	 * 
	 * 简要说明：上传base64Image身份识别
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年9月23日 下午10:32:27 
	 * @param 说明
	 * @return 说明
	 */
	public static JSONObject identifyIdCard(String base64Image,String idCardSide) {
		if(WebplusUtil.isEmpty(base64Image)) {
			log.error("身份证base64图像数据参数不能为空");
			return null;
		}
		if(WebplusUtil.isEmpty(idCardSide)) {  // 默认正面
			idCardSide=SIDE_FRONT;  
		}
		 // 初始化一个AipOcr
    	AipOcr client = new AipOcr(OCR_APP_ID, OCR_API_KEY,OCR_SECRET_KEY);
    	HashMap<String,String>  options=new HashMap<String, String>();
        options.put("detect_direction", "false");
        options.put("detect_risk", "false");
        Base64 base64 = new Base64();
        base64Image = base64Image.replace("data:image/jpeg;base64,", "");
        base64Image=base64Image.replace("data:image/png;base64,", "");
        base64Image = base64Image.replace("data:image;base64,", "");
		// Base64解码
		byte[] buf = base64.decode(base64Image);
		for (int i = 0; i < buf.length; ++i) {
			if (buf[i] < 0) {// 调整异常数据
				buf[i] += 256;
			}
		}
		JSONObject res=client.idcard(buf, idCardSide, options);
		log.info("百度OCR身份证识别返回信息："+res.toString());
		return res ;
		
	}
	/**
	 * 
	 * 简要说明：获取结果值
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年9月23日 下午11:57:02 
	 * @param 说明
	 * @return 说明
	 */
	private static  String getWordsResultValue(String key,JSONObject wordsResult) {
		if(WebplusUtil.isNotEmpty(wordsResult)) {
			JSONObject wordsObject=wordsResult.getJSONObject(key);
			if(WebplusUtil.isNotEmpty(wordsObject)) {
				
				return wordsObject.getString("words");
			}
		}
		return "";
	}
	
	
}
