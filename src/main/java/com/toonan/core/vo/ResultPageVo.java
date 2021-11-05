package com.toonan.core.vo;

import java.util.List;




/**
 * 
 * 类名:com.toonan.mobile.model.MobileResult 
 * 描述:移动返回值统一接口 
 * 编写者:陈骑元 
 * 创建时间:2016年11月16日  下午7:40:26 
 * 修改说明:
 */
public class ResultPageVo {
	/**
	 * 应答代码
	 */
	private String ackCode;
	/**
	 * 分页实体
	 */
	private PageVo page;
	/**
	 * 分页内容
	 */
	private List<?> dataList;
	
	public ResultPageVo( PageVo page, List<?> dataList) {
		super();
		this.ackCode = ResultVo.ack_success;
		this.page = page;
		this.dataList = dataList;
	}
	
	public ResultPageVo() {
		super();
	}

	public String getAckCode() {
		return ackCode;
	}
	public void setAckCode(String ackCode) {
		this.ackCode = ackCode;
	}
	public PageVo getPage() {
		return page;
	}
	public void setPage(PageVo page) {
		this.page = page;
	}
	public List<?> getDataList() {
		return dataList;
	}
	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}
	

	

}
