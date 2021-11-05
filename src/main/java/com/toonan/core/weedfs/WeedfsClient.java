package com.toonan.core.weedfs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.toonan.core.matatype.Dtos;
import com.toonan.core.util.WebplusHttp;
import com.toonan.core.util.WebplusJson;
import com.toonan.core.util.WebplusUtil;


/**
 * 
 * @ClassName:  WeedfsClient   
 * @Description:weedfs客户端上传文件工具类
 * @author: 陈骑元（chenqiyuan@toonan.com)
 * @date:   2020年4月28日 下午7:47:29     
 * @Copyright: 2020 www.toonan.com Inc. All rights reserved. 
 * 注意：本内容仅限于广州市图南软件有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class WeedfsClient {
	
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(WeedfsClient.class);
	/**
	 * 分配卷url
	 */
	private static String assign ="dir/assign";
	/**
	 * 获取文件存储存储位置
	 */
	private static String lookup ="dir/lookup";
	/**
	 * 缓冲流
	 */
	private final static int BUFFER_SIZE = 1024 * 2;
	/**
	 * 文件服务器ip
	 */
	public static String fileSysIp="";
	/**
	 * 
	 */
	public static String fileSysPort="";
	
	
	
	/**
	 * 
	 * 简要说明：分配一个逻辑卷和fid
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年5月6日 上午10:35:42 
	 * @param 说明
	 * @return 说明
	 */
	public static WeedAssign getAssign() {
		StringBuffer sb=new StringBuffer();
		sb.append("http://");
		sb.append(fileSysIp);
		sb.append(":");
		sb.append(fileSysPort);
		sb.append("/");
		sb.append(assign);
		String json=WebplusHttp.doGet(sb.toString(), Dtos.newDto());
		log.info("weedfs文件服务器申请逻辑卷返回信息："+json);
		if(WebplusUtil.isNotEmpty(json)) {
			WeedAssign weedAssign=WebplusJson.fromJson(json, WeedAssign.class);
			return weedAssign;
		}
		
		return null;
	}
	/**
	 * 
	 * 简要说明：通过fid查询 VolumeServer
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年5月6日 下午8:05:40 
	 * @param 说明
	 * @return 说明
	 */
    public static String getFidVolumeServer(String fid) {
    	 if(WebplusUtil.isEmpty(fid)) {
    		 log.error("weedfs获取 volume server失败：fid为空");
    		 return "";
    	 }
    	 int index=fid.indexOf(",");
    	 if(index==-1) {
    		 log.error("weedfs获取 volume server失败：fid校验不合法");
    		 return "";
    	 }
    	 String volumeId=fid.substring(0,index);
    	 StringBuffer sb=new StringBuffer();
 		sb.append("http://");
 		sb.append(fileSysIp);
 		sb.append(":");
 		sb.append(fileSysPort);
 		sb.append("/");
 		sb.append(lookup);
 		sb.append("?volumeId=").append(volumeId);
 		String jsonStr=WebplusHttp.doGet(sb.toString(), Dtos.newDto());
		log.info("weedfs获取 volume server返回信息："+jsonStr);
		if(WebplusUtil.isNotEmpty(jsonStr)) {
			JSONObject json = JSONObject.parseObject(jsonStr);
			JSONArray jsonArray  =json.getJSONArray("locations");
             if(WebplusUtil.isNotEmpty(jsonArray)) {
            	 JSONObject jsonObject=jsonArray.getJSONObject(0);
            	 return jsonObject.getString("publicUrl");
             }
			
		}
        return "";
    }
	/**
	 * 
	 * 简要说明：
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年5月6日 上午10:56:54 
	 * @param 说明
	 * @return 说明
	 */
	public static String  submitFile(File file) {
		if(!file.exists()) {
			 log.error("weedfs上传文件失败：上传文件不存在，请检查文件路径是否正确");
			 return "";
		}
		WeedAssign weedAssign=getAssign();
		if(WebplusUtil.isEmpty(weedAssign)) {
			log.error("weedfs上传文件失败：weedfs文件服务器申请逻辑卷失败");
		}
		String fid=weedAssign.getFid();
		StringBuffer sb=new StringBuffer();
		sb.append("http://");
		sb.append(weedAssign.getPublicUrl());
		sb.append("/");
	    sb.append(fid);
		boolean result=WebplusHttp.submitFile(sb.toString(), file);
		if(result) {
			return fid;
		}
		return "";
	}
	/**
	 * 
	 * 简要说明：上传文件
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年5月8日 下午3:28:56 
	 * @param 说明
	 * @return 说明
	 */
	public static String submitFile(MultipartFile file) {
		WeedAssign weedAssign=getAssign();
		if(WebplusUtil.isEmpty(weedAssign)) {
			log.error("weedfs上传文件失败：weedfs文件服务器申请逻辑卷失败");
		}
		String fid=weedAssign.getFid();
		StringBuffer sb=new StringBuffer();
		sb.append("http://");
		sb.append(weedAssign.getPublicUrl());
		sb.append("/");
	    sb.append(fid);
		boolean result=WebplusHttp.uploadFile(sb.toString(), file);
		if(result) {
			return fid;
		}
		return "";
	}
    /**
     * 
     * 简要说明：删除文件
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2020年5月6日 下午2:01:48 
     * @param 说明
     * @return 说明
     */
	public static boolean deleteFile(String fid) {
		if(WebplusUtil.isEmpty(fid)) {
			log.error("weddfs删除文件失败：fid为空");
			return false;
		}
		String publicUrl=getFidVolumeServer(fid);
		if(WebplusUtil.isEmpty(publicUrl)) {
			log.error("weddfs删除文件失败：fid不合法，获取不到VolumeServer");
			return false;
		}
		StringBuffer sb=new StringBuffer();
		sb.append("http://");
		sb.append(publicUrl);
		sb.append("/");
		sb.append(fid);
		return WebplusHttp.deleteFile(sb.toString());
	}
	/**
	 * 
	 * 简要说明：获取指定下载流
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年5月6日 下午8:45:20 
	 * @param 说明 fid width宽度 height 高度
	 * @return 说明
	 */
	public static InputStream getInputStream(String fid,Integer width,Integer height) {
		if(WebplusUtil.isEmpty(fid)) {
			log.error("weddfs获取文件流失败：fid为空");
			return null;
		}
		String publicUrl=getFidVolumeServer(fid);
		if(WebplusUtil.isEmpty(publicUrl)) {
			log.error("weddfs获取文件流失败：fid不合法，获取不到VolumeServer");
		    return null;
		}
		StringBuffer sb=new StringBuffer();
		sb.append("http://");
		sb.append(publicUrl);
		sb.append("/");
		sb.append(fid);
		if(WebplusUtil.isNotEmpty(width)&&width>0) {
			sb.append("?width=").append(width);
		}
		if(WebplusUtil.isNotEmpty(height)&&height>0) {
			if(sb.toString().indexOf("?")>-1) {
				sb.append("&height=").append(height);
			}else {
				sb.append("&height=").append(height);
			}
		}
		
		return WebplusHttp.getInputStream(sb.toString());
	}
	/**
	 * 
	 * 简要说明：通过文件fid输出文件流，页面展示和下载使用
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年5月7日 下午7:35:01 
	 * @param 说明
	 * @return 说明
	 */
	public static boolean downloadImage(String fid,HttpServletResponse response) {
		
		return downloadImage(fid,0,0,response);
	}
	/**
	 * 
	 * 简要说明：获取文件服务器base64
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年10月15日 下午7:05:15 
	 * @param 说明
	 * @return 说明
	 */
	public static String getFileServerBase64(String fid) {
		 if(WebplusUtil.isEmpty(fid)){
				log.error("请求文件服务器base6失败：文件服务器fid参数为空");
				 return "";
				 
		  }
		 InputStream inputStream = null;	
		 ByteArrayOutputStream bos = null;
		 try {
			inputStream = getInputStream(fid, 0, 0);
			if (WebplusUtil.isNotEmpty(inputStream)) {
				byte[] buffer = new byte[1024];
				int len = 0;
				bos = new ByteArrayOutputStream();
				while ((len = inputStream.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				byte[] byteArray = bos.toByteArray();
				return new String(Base64.encodeBase64(byteArray));
			} 
		} catch (Exception e) {
			log.error("fid[" + fid + "]的文件请求文件服务器进行下载失败,下载失败原因：无法获取相应的文件流");
		}
		 return "";
	}
	/**
	 * 
	 * 简要说明：通过文件fid输出文件流，页面展示和下载使用
	 * 编写者：陈骑元
	 * 创建时间：2017年4月21日 上午9:56:39
	 * @param 说明
	 * @return 说明
	 */
	public static boolean  downloadImage(String fid,Integer width,Integer height,HttpServletResponse response) {
		 if(WebplusUtil.isEmpty(fid)){
			log.error("请求文件服务器下载文件失败：文件服务器fid参数为空");
			 return false;
		 }
		  
		  InputStream inputStream = null;
		  OutputStream os = null;
			try {
				inputStream = getInputStream(fid,width,height);
				if (WebplusUtil.isNotEmpty(inputStream)) {
					// 文件以流的方式发送到客户端浏览器
					os = response.getOutputStream();
					byte[] buffer = new byte[8096];
					int byteread = 0;
					while ((byteread = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
						os.write(buffer, 0, byteread);
						os.flush();
					}
					
					log.info("fid[" + fid + "]的文件请求文件服务器进行下载成功");
					return true;
				} else {
					log.error("fid[" + fid + "]的文件请求文件服务器进行下载失败,下载失败原因：无法获取相应的文件流");
				}

			} catch (Exception e) {
				log.error("fid[" + fid + "]的文件请求文件服务器进行下载失败,下载失败原因：" + e);
			} finally {
				try {
					if (WebplusUtil.isNotEmpty(inputStream)) {
						try {
							inputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (WebplusUtil.isNotEmpty(os)) {
						os.close();
					} 
				} catch (Exception e) {
					log.error("fid[" + fid + "]的文件请求文件服务器进行下载失败,下载失败原因：" + e);
				}
			}
	
		return false;
	}
	/**
	 * 
	 * 简要说明：下载到本地文件
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年5月7日 下午7:37:22 
	 * @param 说明
	 * @return 说明
	 */
	public static boolean downloadToLocal(String fid,String localFileName) {
		
		
		return downloadToLocal(fid,0,0,localFileName);
	}
	/**
	 * 
	 * 简要说明：通过fid把文件存储下载到本地地址
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年5月7日 下午6:59:45 
	 * @param 说明 fid 文件服务器fid localFileName本地文件名称
	 * @return 说明
	 */
	public static boolean downloadToLocal(String fid,Integer width,Integer height,String localFileName) {
		FileOutputStream output = null;
	    InputStream inputStream = null;
		try {
			inputStream =getInputStream(fid,width,height);
			if (WebplusUtil.isNotEmpty(inputStream)) {
				File storeFile = new File(localFileName);
				output = new FileOutputStream(storeFile);
				byte[] buffer = new byte[8096];
				int byteread = 0;
				while ((byteread = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
					output.write(buffer, 0, byteread);
					output.flush();
				}
				return true;
			}
			log.error("通过fid下载文件到本地失败：无法获取文件流");
		} catch (Exception e) {

			log.error("通过fid下载文件到本地失败：fid=" + fid + ",localFileName=" + localFileName + ",错误原因=" + e);
		} finally {
			try {
				if (WebplusUtil.isNotEmpty(inputStream)) {
					inputStream.close();
				}
				if (WebplusUtil.isNotEmpty(output)) {
					output.close();
				}
			} catch (Exception e) {
				log.error("通过fid下载文件到本地失败:关闭流失败");
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		WeedfsClient.fileSysIp="192.168.1.229";
		WeedfsClient.fileSysPort="9333";
		//File file=new File("E://Chrysanthemum.jpg");
	    
	 System.out.println(  WeedfsClient.getFileServerBase64("13,037c80612e41"));
	}
}
