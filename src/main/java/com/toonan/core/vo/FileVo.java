package com.toonan.core.vo;

import java.io.File;

/**
 * 
 * @ClassName:  FileVo   
 * @Description:TODO
 * @author: 陈骑元（chenqiyuan@toonan.com)
 * @date:   2020年12月9日 下午8:41:00     
 * @Copyright: 2020 www.toonan.com Inc. All rights reserved. 
 * 注意：本内容仅限于广州市图南软件有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class FileVo {
	
	/*
	 * 文件域
	 */
	public String fileForm;
	/**
	 * 文件名
	 */
	public String fileName;
	
	public File file;

	public String getFileForm() {
		return fileForm;
	}

	public void setFileForm(String fileForm) {
		this.fileForm = fileForm;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
