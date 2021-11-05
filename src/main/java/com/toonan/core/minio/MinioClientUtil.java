package com.toonan.core.minio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.toonan.core.util.WebplusUtil;

import io.minio.BucketExistsArgs;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.SetBucketPolicyArgs;
import io.minio.UploadObjectArgs;
import io.minio.http.Method;

/**
 * 
 * @ClassName: MinioClientUtil
 * @Description:minio客户端调用
 * @author: 陈骑元（chenqiyuan@toonan.com)
 * @date: 2021年4月3日 下午9:08:24
 * @Copyright: 2021 www.toonan.com Inc. All rights reserved.
 *             注意：本内容仅限于广州市图南软件有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class MinioClientUtil {
	
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(MinioClientUtil.class);
    /**
     * 对象存储服务的URL
     */
	public static String ENDPOINT = "";
	/**
	 * minio文件服务器用户账号
	 */
	public static String ACCESS_KEY = "";
	/**
	 * minio文件服务器账户秘钥
	 */
	public static String SECRET_KEY = "";
	 /**
     * 桶占位符
     */
    private static final String BUCKET_PARAM = "${bucket}";
    /**
     * bucket权限-只读
     */
    private static final String READ_ONLY = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucket\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "/*\"]}]}";
    /**
     * bucket权限-只读
     */
    private static final String WRITE_ONLY = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucketMultipartUploads\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:AbortMultipartUpload\",\"s3:DeleteObject\",\"s3:ListMultipartUploadParts\",\"s3:PutObject\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "/*\"]}]}";
    /**
     * bucket权限-读写
     */
    private static final String READ_WRITE = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucket\",\"s3:ListBucketMultipartUploads\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:DeleteObject\",\"s3:GetObject\",\"s3:ListMultipartUploadParts\",\"s3:PutObject\",\"s3:AbortMultipartUpload\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "/*\"]}]}";
  
    /**
     * 读写文件流
     */
    private static final int BUFFER = 1024*2;
    /**
     * 
     * 简要说明：获取 文件url前半段
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2021年4月3日 下午9:16:10 
     * @param 说明  文件桶bucket
     * @return 说明
     */
    public static String getObjectPrefixUrl(String bucket) {
        return String.format("%s/%s/", ENDPOINT, bucket);
    }

    /**
     * 
     * 简要说明：创建文件桶
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2021年4月3日 下午9:18:23 
     * @param 说明 文件桶名称
     * @return 说明
     */
    public static boolean  makeBucket(String bucket) {
        try {
			MinioClient client = MinioClient.builder().endpoint(ENDPOINT).credentials(ACCESS_KEY, SECRET_KEY).build();
			// 判断桶是否存在
			boolean isExist = client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
			if (!isExist) {
				// 新建桶
				client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
			} 
			return true;
		} catch (Exception e) {
			log.error("创建对象文件服务器minio文件桶失败："+e);
		}
        return false;
    }

    /**
     * 更新桶权限策略
     *
     * @param bucket 桶
     * @param policy 权限
     */
    public static void setBucketPolicy(String bucket, String policy) {
        try {
			MinioClient client = MinioClient.builder().endpoint(ENDPOINT).credentials(ACCESS_KEY, SECRET_KEY).build();
			switch (policy) {
			case "read-only":
				client.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket)
						.config(READ_ONLY.replace(BUCKET_PARAM, bucket)).build());
				break;
			case "write-only":
				client.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket)
						.config(WRITE_ONLY.replace(BUCKET_PARAM, bucket)).build());
				break;
			case "read-write":
				client.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket)
						.config(READ_WRITE.replace(BUCKET_PARAM, bucket)).build());
				break;
			case "none":
			default:
				break;
			}
		} catch (Exception e) {
			log.error("更新对象文件服务器minio文件桶权限失败："+e);
		}
    }

    /**
     * 
     * 简要说明：上传文件
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2021年4月3日 下午9:23:20 
     * @param 说明 bucket文件桶,objectKey 文件key filePath 文件本地路径
     * @return 说明
     */
    public static String uploadLocalFile(String bucket, String objectKey, String filePath) {
        try {
			MinioClient client = MinioClient.builder().endpoint(ENDPOINT).credentials(ACCESS_KEY, SECRET_KEY).build();
			boolean isExist = client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
			if (!isExist) { //判断桶是否存在不存在，就创建
				// 新建桶
				client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
			} 
			client.uploadObject(UploadObjectArgs.builder().bucket(bucket).object(objectKey).filename(filePath)
					.contentType("image/png").build());
			return getObjectPrefixUrl(bucket) + objectKey;
		} catch (Exception e) {
			log.error("上传本地文件到对象文件服务器minio失败："+e);
		}
        return "";
    }

    
	/**
	 * 
	 * 简要说明：上传文件流到对象文件服务器minio
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2021年4月3日 下午9:31:42
	 * @param bucket      桶
	 * @param objectKey   文件key
	 * @param inputStream 文件输入流
	 * @return 文件url
	 */
    public static String uploadInputStream(String bucket, String objectKey, InputStream inputStream) {
        try {
			MinioClient client = MinioClient.builder().endpoint(ENDPOINT).credentials(ACCESS_KEY, SECRET_KEY).build();
			client.putObject(PutObjectArgs.builder().bucket(bucket).object(objectKey)
					.stream(inputStream, inputStream.available(), -1).contentType("image/png").build());
			return getObjectPrefixUrl(bucket) + objectKey;
		} catch (Exception e) {
			log.error("上传文件流到对象文件服务minio失败："+e);
		}
        return "";
    }
    /**
     * 
     * 简要说明：上传文件流到对象文件服务器minio
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2021年4月3日 下午9:31:42
     * @param bucket      桶
     * @param objectKey   文件key
     * @param inputStream 文件输入流
     * @return 文件url
     */
    public static String uploadInputStream(String bucket, String objectKey, InputStream inputStream,String contentType) {
        try {
            MinioClient client = MinioClient.builder().endpoint(ENDPOINT).credentials(ACCESS_KEY, SECRET_KEY).build();
            client.putObject(PutObjectArgs.builder().bucket(bucket).object(objectKey)
                    .stream(inputStream, inputStream.available(), -1).contentType(contentType).build());
            return getObjectPrefixUrl(bucket) + objectKey;
        } catch (Exception e) {
            log.error("上传文件流到对象文件服务minio失败："+e);
        }
        return "";
    }

    /**
     * 简要说明：从对象文件服务器minio下载文件，并返回文件流
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2021年4月3日 下午9:31:42
     * @param bucket    桶
     * @param objectKey 文件key
     * @return 文件流
     */
    public static InputStream downloadFile(String bucket, String objectKey) {
        try {
			MinioClient client = MinioClient.builder().endpoint(ENDPOINT).credentials(ACCESS_KEY, SECRET_KEY).build();
			return client.getObject(GetObjectArgs.builder().bucket(bucket).object(objectKey).build());
		} catch (Exception e) {
			log.error("从对象文件服务minio下载文件失败："+e);
		}
        return null;
    }
    /**
     * 简要说明： 下载文件存储在指定位置
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2021年4月3日 下午9:31:42
     * @param bucket    桶
     * @param objectKey 文件key
     * @return 文件流
     */
    public static boolean downloadFile(String bucket, String objectKey,String saveFilePath) {
    	FileOutputStream fs=null;
    	InputStream inputStream=null;
    	int byteRead = 0;
        try {
        	
        	inputStream=downloadFile(bucket,objectKey);
        	if(inputStream!=null) {
        		fs = new FileOutputStream(saveFilePath);
    			byte[] buffer = new byte[1444];
    			while ((byteRead = inputStream.read(buffer)) != -1) {
    				fs.write(buffer, 0, byteRead);
    			}
    			
        	}
        	return  true;
		} catch (Exception e) {
			log.error("从对象文件服务minio下载文件失败："+e);
		}finally {
			
				try {
					if(fs!=null) {
					  fs.close();
					}
					if(inputStream!=null) {
						inputStream.close();
				     }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
        return false;
    }
    
    /**
     * 
     * 简要说明：通过网络流下载文件 服务器
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2021年9月16日 下午1:43:29 
     * @param 说明  bucket 文件桶 objectKey对象文件键
     * @return 说明
     */
   public static boolean downloadFile(String bucket,String objectKey,HttpServletResponse response) {
   	  InputStream inputStream=downloadFile(bucket,objectKey);
   	  if(WebplusUtil.isNotEmpty(inputStream)){
	    	  OutputStream os = null;
				try {
					
					
					
					objectKey = URLEncoder.encode(objectKey, "UTF-8");
					response.reset();
					response.setHeader("Content-Disposition", "attachment; filename=\"" + objectKey + "\"");
					response.setContentType("application/octet-stream;charset=UTF-8");
						// 文件以流的方式发送到客户端浏览器
						os = response.getOutputStream();
						byte[] buffer = new byte[8096];
						int byteread = 0;
						while ((byteread = inputStream.read(buffer, 0, BUFFER)) != -1) {
							os.write(buffer, 0, byteread);
							os.flush();
						}
						os.close();
						
						return true;

				} catch (Exception e) {
					log.error("通过流下载文件出错："+e);
				} finally {
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
		
	      }
	      log.error("下载文件没有文件流返回");
	      return false;
   }
    /**
     *
     * 简要说明：对象储存文件服务器minio文件复制
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2021年4月3日 下午9:31:42
     * @param sourceBucket    源桶
     * @param sourceObjectKey 源文件key
     * @param bucket          桶
     * @param objectKey       文件key
     * @return 新文件url
     */
    public static String copyFile(String sourceBucket, String sourceObjectKey, String bucket, String objectKey) throws Exception {
        try {
			MinioClient client = MinioClient.builder().endpoint(ENDPOINT).credentials(ACCESS_KEY, SECRET_KEY).build();
			CopySource source = CopySource.builder().bucket(sourceBucket).object(sourceObjectKey).build();
			client.copyObject(CopyObjectArgs.builder().bucket(bucket).object(objectKey).source(source).build());
			return getObjectPrefixUrl(bucket) + objectKey;
		} catch (Exception e) {
			log.error("对象储存文件服务minio文件复制失败："+e);
		}
        return "";
    }

    /**
     * 简要说明：删除文件
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2021年4月3日 下午9:31:42
     * @param bucket    桶
     * @param objectKey 文件key
     */
    public static boolean deleteFile(String bucket, String objectKey) {
        try {
			MinioClient client = MinioClient.builder().endpoint(ENDPOINT).credentials(ACCESS_KEY, SECRET_KEY).build();
			client.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(objectKey).build());
		    return true;
        } catch (Exception e) {
			log.error("从对象文件服务器minio删除文件失败:"+e);
		}
        return false;
    }

    /**
    * 简要说明：获取签名url
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2021年4月3日 下午9:31:42
     * @param bucket    桶
     * @param objectKey 文件key
     * @param expires   签名有效时间  单位秒
     * @return 文件签名地址
     */
    public static String getSignedUrl(String bucket, String objectKey, int expires) {
        try {
			MinioClient client = MinioClient.builder().endpoint(ENDPOINT).credentials(ACCESS_KEY, SECRET_KEY).build();
			return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucket)
					.object(objectKey).expiry(expires).build());
		} catch (Exception e) {
			log.error("从对象文件服务器minio获取文件签名URL失败："+e);
		}
        return "";
    }


    public static void main(String[] args) {
    	ENDPOINT="http://192.168.0.100:9000/";
    	ACCESS_KEY="toonan";
    	SECRET_KEY="Toonan2021";
    	//deleteFile("toonan","202104032159.jpg");
    	uploadLocalFile("toonan","20210403/202104032159.jpg","E://chenqiyuan.jpg");
    	//System.out.print(getSignedUrl("toonan","20210403/202104032159.jpg",400));
    }
}
