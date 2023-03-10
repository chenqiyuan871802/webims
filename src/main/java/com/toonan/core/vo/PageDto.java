package com.toonan.core.vo;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;


/**
 * 
 * 类名:com.ims.common.util.Page
 * 描述:分页
 * 编写者:陈骑元
 * 创建时间:2018年4月9日 下午10:22:26 
 * 修改说明:
 */
public class PageDto implements Serializable {

	private static final long serialVersionUID = 1L;
    private int code=0;
    private String msg;

	/**
	 * 总记录数
	 */
	private long count;
	/**
	 * 查询数据列表
	 */
	private List<?> data = Collections.emptyList();

	public PageDto(){
		
	}


	public PageDto(long total, List<?> rows) {
		super();
		this.count = total;
		this.data = rows;
	}


	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
