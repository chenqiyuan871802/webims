package com.toonan.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.assertj.core.util.Lists;
import org.springframework.web.multipart.MultipartFile;

import com.toonan.core.matatype.Dto;
import com.toonan.core.vo.FileVo;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * 类名:com.ims.common.core.asset.HttpRequestProxy
 * 描述:
 * 编写者:陈骑元
 * 创建时间:2017年4月29日 下午4:01:20
 * 修改说明:
 */
public class WebplusHttp {
	
	private static final int BUFFER = 1024*2;

	/**
	 * 连接超时
	 */
	private static int connectTimeOut = 5000;

	/**
	 * 读取数据超时
	 */
	private static int readTimeOut = 10000;

	/**
	 * 请求编码
	 */
	public static String requestEncoding = "UTF-8";

	private static Log log = LogFactory.getLog(WebplusHttp.class);
	
	private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36";

	private static final MediaType CONTENT_TYPE_FORM = MediaType.parse("application/x-www-form-urlencoded");
	private static HttpClient httpClient = null;  
    private static final String CHARSET = "UTF-8";  
	private WebplusHttp() {
	}
	public static synchronized HttpClient getSaveHttpClient(){  
        if(httpClient == null){  
        	HttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
        	HttpConnectionManagerParams managerParams = new HttpConnectionManagerParams();
        	managerParams.setConnectionTimeout(30000);
        	managerParams.setMaxTotalConnections(50);
        	managerParams.setDefaultMaxConnectionsPerHost(30);
        	managerParams.setSoTimeout(20000);
        	manager.setParams(managerParams);
        	httpClient = new HttpClient(manager);
        	httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,CHARSET);
        }  
        return httpClient;  
    }
	/**
	 * 
	 * 简要说明：默认使用UTF-8请求
	 * 编写者：陈骑元
	 * 创建时间：2017年4月29日 下午5:12:04
	 * @param 说明
	 * @return 说明
	 */
	public static String doGet(String reqUrl,Dto parameters){
		
		return doGet(reqUrl,parameters,requestEncoding);
	}
	/**
	 * <pre>
	 * 发送带参数的GET的HTTP请求
	 * </pre>
	 * 
	 * @param reqUrl
	 *            HTTP请求URL
	 * @param parameters
	 *            参数映射表
	 * @return HTTP响应的字符串
	 */
	public static String doGet(String reqUrl, Dto  parameters,
			String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			url_con = doHttpGetConnection(reqUrl,parameters);

			InputStream in = url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in,
					recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer temp = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				temp.append(tempLine);
				temp.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = temp.toString();
			rd.close();
			in.close();
		} catch (IOException e) {
			log.error("网络故障", e);
		} finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}

		return responseContent;
	}

	private static HttpURLConnection doHttpGetConnection(String reqUrl,
			Dto  parameters) throws IOException {
		HttpURLConnection url_con = null;
		String params = getDtoParam(parameters,WebplusHttp.requestEncoding);
		if(WebplusUtil.isNotEmpty(params)) {
			if(reqUrl.indexOf("?")>-1) {
				reqUrl+="&"+params;
			}else {
				reqUrl+="?"+params;
			}
		}
		
		URL url = new URL(reqUrl);
		url_con = (HttpURLConnection) url.openConnection();
		url_con.setRequestMethod("GET");
		System.setProperty("sun.net.client.defaultConnectTimeout",
				String.valueOf(WebplusHttp.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
		System.setProperty("sun.net.client.defaultReadTimeout",
				String.valueOf(WebplusHttp.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
		
		return url_con;
	}

	/**
	 * <pre>
	 * 发送不带参数的GET的HTTP请求
	 * </pre>
	 * 
	 * @param reqUrl
	 *            HTTP请求URL
	 * @return HTTP响应的字符串
	 */
	public static String doGet(String reqUrl, String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			StringBuffer params = new StringBuffer();
			String queryUrl = reqUrl;
			int paramIndex = reqUrl.indexOf("?");

			if (paramIndex > 0) {
				queryUrl = reqUrl.substring(0, paramIndex);
				String parameters = reqUrl.substring(paramIndex + 1,
						reqUrl.length());
				String[] paramArray = parameters.split("&");
				for (int i = 0; i < paramArray.length; i++) {
					String string = paramArray[i];
					int index = string.indexOf("=");
					if (index > 0) {
						String parameter = string.substring(0, index);
						String value = string.substring(index + 1,
								string.length());
						params.append(parameter);
						params.append("=");
						params.append(URLEncoder.encode(value,
								WebplusHttp.requestEncoding));
						params.append("&");
					}
				}

				params = params.deleteCharAt(params.length() - 1);
			}

			URL url = new URL(queryUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("GET");
			System.setProperty("sun.net.client.defaultConnectTimeout",
					String.valueOf(WebplusHttp.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
			System.setProperty("sun.net.client.defaultReadTimeout",
					String.valueOf(WebplusHttp.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
			
			url_con.setDoOutput(true);
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();
			InputStream in = url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in,
					recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer temp = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				temp.append(tempLine);
				temp.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = temp.toString();
			rd.close();
			in.close();
		} catch (IOException e) {
			log.error("网络故障", e);
		} finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}

		return responseContent;
	} 
	
	/**
	 * 
	 * 简要说明：发送带参数的HTTP请求
	 * 编写者：陈骑元
	 * 创建时间：2017年8月3日 下午2:00:59
	 * @param 说明
	 * @return 说明
	 */
	public static String doPost(String reqUrl,Dto parameters){
		
		return doPost(reqUrl,parameters,WebplusHttp.requestEncoding);
	}
	/**
	 * <pre>
	 * 发送带参数的POST的HTTP请求
	 * </pre>
	 * 
	 * @param reqUrl
	 *            HTTP请求URL
	 * @param parameters
	 *            参数映射表
	 * @return HTTP响应的字符串
	 */
	public static String doPost(String reqUrl, Dto  parameters,
			String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			String params = getDtoParam(parameters,WebplusHttp.requestEncoding);
			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("POST");
			System.setProperty("sun.net.client.defaultConnectTimeout",
					String.valueOf(WebplusHttp.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
			System.setProperty("sun.net.client.defaultReadTimeout",
					String.valueOf(WebplusHttp.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
			
			url_con.setDoOutput(true);
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();

			InputStream in = url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in,
					recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				tempStr.append(tempLine);
				tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = tempStr.toString();
			rd.close();
			in.close();
		} catch (IOException e) {
			log.error("网络故障", e);
		} finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}
		return responseContent;
	}
	
	
	/**
	 * <pre>
	 * 发送带参数的POST的HTTP请求
	 * </pre>
	 * 
	 * @param reqUrl
	 *            HTTP请求URL
	 * @param parameters
	 *            参数映射表
	 * @return HTTP响应的字符串
	 */
	public static String doJsonPost(String reqUrl, Dto  parameters,
			String jsonData) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			String params = getDtoParam(parameters,WebplusHttp.requestEncoding);
			 if(WebplusUtil.isNotEmpty(params)) {
	            	if(reqUrl.indexOf("?")>-1) {
	            		reqUrl=reqUrl+"&"+params;
	            	}else {
	            		reqUrl=reqUrl+"?"+params;
	            	}
	          }
		     URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("POST");
			System.setProperty("sun.net.client.defaultConnectTimeout",
					String.valueOf(WebplusHttp.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
			System.setProperty("sun.net.client.defaultReadTimeout",
					String.valueOf(WebplusHttp.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
			url_con.setDoOutput(true);
			url_con.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
			url_con.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
			
			byte[] b = jsonData.toString().getBytes(WebplusHttp.requestEncoding);
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();

			InputStream in = url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in,
					WebplusHttp.requestEncoding));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				tempStr.append(tempLine);
				tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = tempStr.toString();
			rd.close();
			in.close();
		} catch (IOException e) {
			log.error("网络故障", e);
		} finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}
		return responseContent;
	}

	/**
	 * 
	 * @param reqUrl
	 * @param parameters
	 * @param recvEncoding
	 * @param fileIn
	 *            文件流
	 * @return
	 */
	public static String uploadMedia(String reqUrl, Dto  parameters,
			String recvEncoding, InputStream fileIn, String fileName,
			String contentType) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			// 设置边界
			String BOUNDARY = "----------" + System.currentTimeMillis();
			String params = getDtoParam(parameters,WebplusHttp.requestEncoding);

			URL urlObj = new URL(reqUrl + "&" + params.toString());
			// 连接
			url_con = (HttpURLConnection) urlObj.openConnection();
			/**
			 * 设置关键值
			 */
			url_con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
			url_con.setDoInput(true);
			url_con.setDoOutput(true);
			url_con.setUseCaches(false); // post方式不能使用缓存

			// 设置请求头信息
			url_con.setRequestProperty("Connection", "Keep-Alive");
			url_con.setRequestProperty("Charset",recvEncoding);

			// 设置边界
			url_con.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);

			// 请求正文信息

			// 第一部分：
			StringBuilder sb = new StringBuilder();
			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
					+ fileName + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");

			byte[] head = sb.toString().getBytes(recvEncoding);

			// 获得输出流
			OutputStream out = new DataOutputStream(url_con.getOutputStream());
			// 输出表头
			out.write(head);
			// 文件正文部分
			// 把文件已流文件的方式 推入到url中
			DataInputStream in = new DataInputStream(fileIn);
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();

			// 结尾部分
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(recvEncoding);// 定义最后数据分隔线

			out.write(foot);
			out.flush();
			out.close();

			InputStream iddn = url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(iddn,
					recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				tempStr.append(tempLine);
				tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = tempStr.toString();
			rd.close();
		} catch (IOException e) {
			log.error("网络故障", e);
		} finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}
		return responseContent;
	}
	
	/**
	 * 文件下载(返回文件):请求失败或者找不到文件返回null
	 * @param reqUrl （请求地址）
	 * @param params 参数
	 * @return
	 */
	public static boolean  download(String reqUrl,Dto params,final HttpServletResponse response){
		 log.info("接口："+reqUrl+" -->>请求发送参数："+params);
		 boolean result=false;
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse closeableResponse=null;
		InputStream inputStream=null;
		OutputStream os = null;
		HttpPost httppost = new HttpPost(reqUrl);
		// 创建参数队列
		List<org.apache.http.NameValuePair> formparams = new ArrayList<org.apache.http.NameValuePair>();
		if (params != null&& !params.isEmpty()) {//表单提交参数
			for (Entry<String, Object> entry : params.entrySet()) {
				formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue() == null ? "": entry.getValue().toString()));
			}
		}
		UrlEncodedFormEntity uefEntity=null;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, requestEncoding);
			httppost.setEntity(uefEntity);
			closeableResponse = httpclient.execute(httppost);
			StatusLine status=closeableResponse.getStatusLine();//HTTP/1.1 200 OK
			HttpEntity resEntity = closeableResponse.getEntity();// 获取响应实体
			if(status.getStatusCode()==200&&resEntity != null){//请求成功
				inputStream = resEntity.getContent();//响应流
				Header head=resEntity.getContentType();
				if(WebplusUtil.isEmpty(head)||(WebplusUtil.isNotEmpty(head)&&"Content-Type".equals(head.getName()))){
					// 文件以流的方式发送到客户端浏览器
					os = response.getOutputStream();
					byte[] buffer = new byte[8096];
					int byteread = 0;
					while ((byteread = inputStream.read(buffer, 0, BUFFER)) != -1) {
						os.write(buffer, 0, byteread);
						os.flush();
					}
					  result=true;
				}else{
					
					BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream,
							requestEncoding));
					String tempLine = rd.readLine();
					StringBuffer tempStr = new StringBuffer();
					String crlf = System.getProperty("line.separator");
					while (tempLine != null) {
						tempStr.append(tempLine);
						tempStr.append(crlf);
						tempLine = rd.readLine();
					}
					String responseContent = tempStr.toString();
					rd.close();
					log.error("向来穗请求下载文件失败："+responseContent);
				}
				
			
			}else{//请求失败
				log.error(reqUrl+"=====>>>>>>来穗接口返回错误"+">>>>>>"+status.toString());
			
			}
		} catch (Exception e) {
			log.error("=====>>>>>>"+reqUrl+"来穗接口访问不了错误response异常"+">>>>>>"+e);
			
		} finally {
						
			try {
				if(os!=null){
					os.close();
				}
				if(inputStream!=null){
					inputStream.close();
				}
				if(closeableResponse!=null){
					closeableResponse.close();
				}
			} catch (Exception e) {
			}
			
			
		}
		return result;
		
	}
	
	/**
	 * 文件下载(返回文件):请求失败或者找不到文件返回null
	 * @param reqUrl （请求地址）
	 * @param params 参数
	 * @return
	 */
	public static String  downloadBase64(String reqUrl,Dto params){
		 log.info("接口："+reqUrl+" -->>请求发送参数："+params);
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse closeableResponse=null;
		InputStream inputStream=null;
		ByteArrayOutputStream outStream=null;
		HttpPost httppost = new HttpPost(reqUrl);
		// 创建参数队列
		List<org.apache.http.NameValuePair> formparams = new ArrayList<org.apache.http.NameValuePair>();
		if (params != null&& !params.isEmpty()) {//表单提交参数
			for (Entry<String, Object> entry : params.entrySet()) {
				formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue() == null ? "": entry.getValue().toString()));
			}
		}
		UrlEncodedFormEntity uefEntity=null;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, requestEncoding);
			httppost.setEntity(uefEntity);
			closeableResponse = httpclient.execute(httppost);
			StatusLine status=closeableResponse.getStatusLine();//HTTP/1.1 200 OK
			HttpEntity resEntity = closeableResponse.getEntity();// 获取响应实体
			if(status.getStatusCode()==200&&resEntity != null){//请求成功
				inputStream = resEntity.getContent();//响应流
				Header head=resEntity.getContentType();
				if(WebplusUtil.isEmpty(head)){
					 outStream = new ByteArrayOutputStream();
		            byte[] buff = new byte[100];
		            int rc = 0;
		            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
		                outStream.write(buff, 0, rc);
		            }
		            byte[]  data = outStream.toByteArray();
		            return new String(Base64.encodeBase64(data));

				}else{
					BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream,
							requestEncoding));
					String tempLine = rd.readLine();
					StringBuffer tempStr = new StringBuffer();
					String crlf = System.getProperty("line.separator");
					while (tempLine != null) {
						tempStr.append(tempLine);
						tempStr.append(crlf);
						tempLine = rd.readLine();
					}
					String responseContent = tempStr.toString();
					rd.close();
					log.error("请求下载文件失败："+responseContent);
				}
				
			
			}else{//请求失败
				log.error(reqUrl+"=====>>>>>>下载文件返回错误"+">>>>>>"+status.toString());
			
			}
		} catch (Exception e) {
			log.error("=====>>>>>>"+reqUrl+"下载文件返回response异常"+">>>>>>"+e);
			
		} finally {
						
			try {
				
				if(inputStream!=null){
					inputStream.close();
				}
				if(closeableResponse!=null){
					closeableResponse.close();
				}
			} catch (Exception e) {
			}
			
			
		}
		return "";
		
	}
	/**
	 * 
	 * 简要说明：
	 * 编写者：陈骑元
	 * 创建时间：2017年11月6日 上午9:59:02
	 * @param 说明
	 * @return 说明
	 */
	public static String post(String url, String params) {
		RequestBody body = RequestBody.create(CONTENT_TYPE_FORM, params);
		Request request = new Request.Builder().url(url).post(body).build();
		return exec(request);
	}
   /**
    * 
    * 简要说明：
    * 编写者：陈骑元
    * 创建时间：2017年11月6日 上午9:59:10
    * @param 说明
    * @return 说明
    */
	private static String exec(okhttp3.Request request) {
		try {
			okhttp3.Response response = new OkHttpClient().newCall(request).execute();
			if (!response.isSuccessful())
				throw new RuntimeException("Unexpected code " + response);
			return response.body().string();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * 简要说明：发送SSL的post请求
	 * 编写者：陈骑元
	 * 创建时间：2017年11月2日 下午4:34:56
	 * @param 说明
	 * @return 说明
	 */
	public static String postSSL(String url, String data, String certPath, String certPass) {
		HttpsURLConnection conn = null;
		OutputStream out = null;
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {
			KeyStore clientStore = KeyStore.getInstance("PKCS12");
			clientStore.load(new FileInputStream(certPath), certPass.toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(clientStore, certPass.toCharArray());
			KeyManager[] kms = kmf.getKeyManagers();
			SSLContext sslContext = SSLContext.getInstance("TLSv1");

			sslContext.init(kms, null, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
			URL _url = new URL(url);
			conn = (HttpsURLConnection) _url.openConnection();

			conn.setConnectTimeout(25000);
			conn.setReadTimeout(25000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);

			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
			conn.connect();

			out = conn.getOutputStream();
			out.write(data.getBytes(Charsets.toCharset(requestEncoding)));
			out.flush();

			inputStream = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream, Charsets.toCharset(requestEncoding)));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(inputStream);
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	
	
	public static final String httpClientPost(String url,Dto paramDto) {
		String result = "";
		HttpClient client = WebplusHttp.getSaveHttpClient();
		PostMethod postMethod = new PostMethod(url);
		try {
			for(Map.Entry<String,Object> entry:paramDto.entrySet()){    
			    postMethod.addParameter(entry.getKey(), entry.getValue()+"");
			}   
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == 200){
				result = postMethod.getResponseBodyAsString();
			}
			
		} catch (Exception e) {
			log.error(e);
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}
	/**
	 * 将参数转换成string
	 * @param paramDto
	 * @param requestEncoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("rawtypes")
	private static String getDtoParam(Dto paramDto,String requestEncoding) throws IOException{
		StringBuffer params = new StringBuffer();
		if(WebplusUtil.isNotEmpty(paramDto)){
			// 设置边界
			for (String key:paramDto.keySet()) {
				
				params.append(key);
				params.append("=");
				params.append(URLEncoder.encode(paramDto.getString(key),
						requestEncoding));
				params.append("&");
			}

			if (params.length() > 0) {
				params = params.deleteCharAt(params.length() - 1);
			}
		}
		
		return params.toString();
	}
	/**
	 * 
	 * 简要说明：上传文件
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年4月28日 下午7:54:41 
	 * @param 说明
	 * @return 说明
	 */
	public static boolean   submitFile(String url,File file) {
		if(WebplusUtil.isNotEmpty(file)) {
			if(!file.exists()) {
		        log.error("上传文件不存在，请检查文件路径是否正确");
				return false;
			}
			PostMethod postMethod = new PostMethod(url);
			 try {
				HttpClient httpClient = WebplusHttp.getSaveHttpClient();
				
				Part[] parts = { new FilePart(file.getName(), file) };
				postMethod.setRequestEntity(new MultipartRequestEntity(parts, postMethod.getParams()));
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			    int status = httpClient.executeMethod(postMethod);
			    if(status==HttpStatus.SC_OK||status==HttpStatus.SC_CREATED) {
			    	String json=postMethod.getResponseBodyAsString();
			    	log.info("上传文件成功：返回来信息：	"+json);
			    	return true;
			    }
			    log.error("上传文件失败：status="+status+",错误原因="+postMethod.getStatusText());
			} catch (Exception e) {
				log.error("上传文件报错："+e);
			}finally {
				postMethod.releaseConnection();
			}
			
		}
		return false;
	}
	
	/**
	 * 
	 * 简要说明：HTTP delete 方法
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年5月6日 下午2:13:55 
	 * @param 说明
	 * @return 说明
	 */
	public static String delete(String url,Dto pDto) {
		DeleteMethod deleteMethod = new DeleteMethod(url);
		try {
			HttpClient httpClient = WebplusHttp.getSaveHttpClient();
			
			List<NameValuePair> data = Lists.newArrayList();
	        if(WebplusUtil.isNotEmpty(pDto)){
	            Set<String> keys =pDto.keySet();
	            for(String key:keys){
	            	NameValuePair nameValuePair = new NameValuePair(key, pDto.getString(key));
	                data.add(nameValuePair);
	            }
	        }
	        deleteMethod.setQueryString(data.toArray(new NameValuePair[0]));
	        int statusCode= httpClient.executeMethod(deleteMethod);
	        if (statusCode == HttpStatus.SC_OK) {
	        	String json=deleteMethod.getResponseBodyAsString();
	        	log.info("执行delete方法返回信息："+json);
	        	return json;
	        }
	        log.error("执行delete失败：statusCode="+statusCode+",statusLnine"+deleteMethod.getStatusLine());
            
		} catch (Exception e) {
			log.error("执行delete方法失败："+e);
		}
       
		return "";
	}
	
	
	
	/**
	 * 
	 * 简要说明：delete 方法删除文件
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年5月6日 下午2:13:55 
	 * @param 说明
	 * @return 说明
	 */
	public static boolean  deleteFile(String url) {
		DeleteMethod deleteMethod = new DeleteMethod(url);
		try {
			HttpClient httpClient = WebplusHttp.getSaveHttpClient();
	        httpClient.executeMethod(deleteMethod);
            return true;
		} catch (Exception e) {
			log.error("执行delete方法失败："+e);
		}
       
		return false;
	}
	
	/**
	 * 
	 * 简要说明：通过url下载地址获取输入流
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年5月7日 上午9:09:58 
	 * @param 说明
	 * @return 说明
	 */
	 public static InputStream getInputStream(String url) {
		 GetMethod getMethod = new GetMethod(url); 
		
		 try {
			 HttpClient httpClient = WebplusHttp.getSaveHttpClient();
			 int statusCode= httpClient.executeMethod(getMethod);
			 if(statusCode == HttpStatus.SC_OK) {
				 
				 return getMethod.getResponseBodyAsStream();
			 }
		} catch (Exception e) {
			log.error("通过url地址下载文件流失败：url="+url+" 错误信息："+e);
			
		}
		 return null;
	 }
	 /**
		 * 
		 * 简要说明：
		 * 编写者：陈骑元（chenqiyuan@toonan.com）
		 * 创建时间： 2020年12月9日 下午8:32:52 
		 * @param 说明
		 * @return 说明
		 */
		public static String uploadFile(String url,Dto pDto,List<FileVo> fileList) {
			CloseableHttpClient httpClient = null;
			CloseableHttpResponse response = null;
			InputStream in=null;
			String res="";
			try {
				httpClient = HttpClients.createDefault();
				// 把一个普通参数和文件上传给下面这个地址 是一个servlet
				HttpPost httpPost = new HttpPost(url);
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();
				ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, requestEncoding);// 设置编码
				for (FileVo fileVo : fileList) {
					File file = fileVo.getFile();
					if (WebplusUtil.isNotEmpty(file) && file.exists()) {
						FileBody bin = new FileBody(file, contentType);
						builder.addPart(fileVo.getFileForm(), bin);
					}

				}
				for (String key : pDto.keySet()) {

					StringBody comment = new StringBody(pDto.getString(key), contentType);
					builder.addPart(key, comment);
				}
				httpPost.setEntity(builder.build());// 参数实体设置到请求中
				response = httpClient.execute(httpPost);// 响应实例
				StatusLine status = response.getStatusLine();// HTTP/1.1 200 OK
				HttpEntity resEntity = response.getEntity();// 获取响应实体
				if (status.getStatusCode() == 200 && resEntity != null) {//请求成功
					// 获取请求响应结果
					in = resEntity.getContent();//响应流
					res = inputStreamToStr(in, requestEncoding);//响应结果字符串
					EntityUtils.consume(resEntity);//关闭资源
				} else {
					log.error("=====>>>>>>来穗接口返回错误"+">>>>>>"+status.toString());
				}
					
			} catch (Exception e) {
				log.error("发送上传文件报错出错："+e);
			}finally {
				try {
					if (in != null) {
						in.close();
					}
					if (response != null) {
						response.close();
					}
					if (httpClient != null) {
						httpClient.close();
					}
				} catch (IOException e) {
					log.error("上传文件报错：" + e);
				}
			}
			
			return res;
		}
	    
		/**
		 * 实现Inputstream转换成String
		 * @param in输入流
		 * @param encode指定编码
		 * @return
		 */
		public static String inputStreamToStr(InputStream in,String encode){
			String str = "";  
			try {  
				if (WebplusUtil.isNotEmpty(encode)){  
					encode =requestEncoding;// 默认以utf-8形式 
				}  
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, encode));  
		    	StringBuffer sb = new StringBuffer();  
		             
		    	while ((str = reader.readLine()) != null){  
		    		sb.append(str).append("\n");  
		     	}
		      	//去掉最后一行换行符
		      	if(sb!=null&&sb.length()>1){
		      		sb.deleteCharAt(sb.length()-1);//删除最后一个字符   \n
		      	}
		      	return sb.toString();  
			}catch (UnsupportedEncodingException e1) {  
				log.info("inputStreamToStr方法捕捉的异常"+e1.getMessage());
			}catch (IOException e){  
				log.info("inputStreamToStr方法捕捉的异常"+e.getMessage());
			}  
			return str; 
		}
	/**
	 * 
	 * 简要说明：上传文件
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年5月6日 上午11:24:44 
	 * @param 说明
	 * @return 说明
	 */
	public static String  uploadFile(String url,File file){
		if(!file.exists()) {
	        log.error("上传文件不存在，请检查文件路径是否正确");
			return "";
		}
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(url);
            // 把文件转换成流对象FileBody
            FileBody bin = new FileBody(file);
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("file", bin)
                    .build();
            httpPost.setEntity(reqEntity);
            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);
            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            if (WebplusUtil.isNotEmpty(resEntity)) {
            	  String res = EntityUtils.toString(resEntity,"UTF-8");
                  log.info("文件上传返回信息："+res);
            	  return res;
            }
            
            // 销毁
            EntityUtils.consume(resEntity);
        }catch (Exception e){
        	log.error("上传文件报错："+e);
        }finally {
            try {
                if(response != null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            try {
                if(httpClient != null){
                    httpClient.close();
                }
            } catch (IOException e) {
            	log.error("上传文件报错："+e);
            }
        }
        return "";
    }
	
	/**
	 * 
	 * 简要说明：上传网络流文件
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年5月6日 上午11:24:44 
	 * @param 说明
	 * @return 说明
	 */
	public static boolean   uploadFile(String url,MultipartFile file){
		if(WebplusUtil.isEmpty(file)) {
	        log.error("上传文件失败：网络流文件为空");
			return false;
		}
		if(file.getSize()==0) {
			log.error("上传文件失败：网络流文件为零");
			return false;
		}
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
        	String fileName=file.getOriginalFilename();
            httpClient = HttpClients.createDefault();
            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(Charset.forName("utf-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//加上此行代码解决返回中文乱码问题
            builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
            HttpEntity reqEntity = builder.build();
            httpPost.setEntity(reqEntity);
            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);
            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            if (WebplusUtil.isNotEmpty(resEntity)) {
            	  String res = EntityUtils.toString(resEntity,"UTF-8");
            	  log.info("文件上传返回信息："+res);
            	  if(res.indexOf("name")>-1) {
            		  return true;
            	  }
                  return false;
            }
            // 销毁
            EntityUtils.consume(resEntity);
        }catch (Exception e){
        	log.error("上传文件报错："+e);
        }finally {
            try {
                if(response != null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            try {
                if(httpClient != null){
                    httpClient.close();
                }
            } catch (IOException e) {
            	log.error("上传文件报错："+e);
            }
        }
        log.error("上传文件失败：网络异常");
        return false;
    }
	
	/**
	 * @return 连接超时(毫秒)
	 * @see com.WebplusHttp.common.web.HttpRequestProxy#connectTimeOut
	 */
	public static int getConnectTimeOut() {
		return WebplusHttp.connectTimeOut;
	}

	/**
	 * @return 读取数据超时(毫秒)
	 * @see com.WebplusHttp.common.web.HttpRequestProxy#readTimeOut
	 */
	public static int getReadTimeOut() {
		return WebplusHttp.readTimeOut;
	}

	/**
	 * @return 请求编码
	 * @see com.WebplusHttp.common.web.HttpRequestProxy#requestEncoding
	 */
	public static String getRequestEncoding() {
		return requestEncoding;
	}

	/**
	 * @param connectTimeOut
	 *            连接超时(毫秒)
	 * @see com.WebplusHttp.common.web.HttpRequestProxy#connectTimeOut
	 */
	public static void setConnectTimeOut(int connectTimeOut) {
		WebplusHttp.connectTimeOut = connectTimeOut;
	}

	/**
	 * @param readTimeOut
	 *            读取数据超时(毫秒)
	 * @see com.WebplusHttp.common.web.HttpRequestProxy#readTimeOut
	 */
	public static void setReadTimeOut(int readTimeOut) {
		WebplusHttp.readTimeOut = readTimeOut;
	}

	/**
	 * @param requestEncoding
	 *            请求编码
	 * @see com.WebplusHttp.common.web.HttpRequestProxy#requestEncoding
	 */
	public static void setRequestEncoding(String requestEncoding) {
		WebplusHttp.requestEncoding = requestEncoding;
	}
   
	public static void main(String[] args) {
		WebplusHttp.deleteFile("http://192.168.1.229:7071/4,09b99841b5");
	}
}
