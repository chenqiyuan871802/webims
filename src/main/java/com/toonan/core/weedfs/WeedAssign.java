package com.toonan.core.weedfs;
/**
 * 
 * @ClassName:  Assign   
 * @Description:weedfs 逻辑卷地址
 * @author: 陈骑元（chenqiyuan@toonan.com)
 * @date:   2020年5月6日 上午10:28:37     
 * @Copyright: 2020 www.toonan.com Inc. All rights reserved. 
 * 注意：本内容仅限于广州市图南软件有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class WeedAssign {
	
	/**
	 * 文件fid
	 */
	private String fid;
	/**
	 * 文件url地址
	 */
	private String url;
	/**
	 * 文件公用地址
	 */
	private String publicUrl;
	/**
	 * 申请的数量
	 */
	private int count;
	
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPublicUrl() {
		return publicUrl;
	}
	public void setPublicUrl(String publicUrl) {
		this.publicUrl = publicUrl;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
