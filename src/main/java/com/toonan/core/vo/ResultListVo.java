package com.toonan.core.vo;

import java.util.List;
/**
 * 返回数据封装(成功数据 手机端返回具体实体：dataList数据区):
 * @author wubj
 *
 */
public class ResultListVo extends ResultSuccessVo{
	/**
	 * 数据区：List(分页列表、不分页列表数据区)
	 */
	private List<?> dataList;
	/**
	 * 构造器：返回成功
	 */
	public ResultListVo(){
		this.ackCode = ResultVo.ack_success;
	}
	/**
	 * 构造器：返回成功及dataList数据区
	 * @param data_list
	 */
	public ResultListVo(List<?> dataList) {
		super();
		this.dataList = dataList;
	}
	public List<?> getDataList() {
		return dataList;
	}
	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}
	public ResultVo convertResultVo(){
		ResultVo rs=new ResultVo();
		if(this!=null){
			rs.setAckCode(this.getAckCode());
			rs.setDataList(this.getDataList());
		}
		return rs;
	}
}
