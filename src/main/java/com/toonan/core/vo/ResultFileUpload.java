package com.toonan.core.vo;

import java.io.Serializable;

/**
 * @ClassName: ResultFileUpload
 * @Description: 文件上传返回结果
 * @author: 黄健达 (lsr@sina.com)
 * @date: Sep 16, 2021 10:39:45 AM
 */
public class ResultFileUpload implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6290687638202504935L;
    private String fid;
    private String originalFilename;
    private String contentType;
    private long size;

    public ResultFileUpload() {
    }

    public ResultFileUpload(String fid, String originalFilename, String contentType, long size) {
        this.fid = fid;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getFid() {
        return fid;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
