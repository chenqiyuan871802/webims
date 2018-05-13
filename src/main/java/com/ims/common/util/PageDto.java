package com.ims.common.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;

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

	/**
	 * 总记录数
	 */
	private int total;
	/**
	 * 查询数据列表
	 */
	private List<?> rows = Collections.emptyList();

	public PageDto(){
		
	}

	public PageDto(Page<?> page) {
		super();
		this.total =page.getTotal();
		this.rows = page.getRecords();
	}
	public PageDto(int total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
   
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
