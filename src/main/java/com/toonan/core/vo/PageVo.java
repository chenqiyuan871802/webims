package com.toonan.core.vo;

/**
 * 
 * 类名:com.toonan.mobile.model.Page 描述:分页参数返回实体 编写者:陈骑元 创建时间:2016年11月16日
 * 下午7:45:48 修改说明:
 */
public class PageVo {
	/**
	 * 记录总数
	 */
	private long count;
	/**
	 * 当前页
	 */
	private int currentPage;

	/**
	 * 每页记录数
	 */
	private int pageSize;

	public PageVo() {

		super();
	}

	public PageVo(long count, int currentPage, int pageSize) {
		super();
		this.count = count;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
