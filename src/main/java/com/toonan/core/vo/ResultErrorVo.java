package com.toonan.core.vo;
/**
 * 返回数据封装(失败数据 手机端返回具体实体):
 * @author wubj
 *
 */
public class ResultErrorVo {
	/**
	 * 返回代码：失败恒为0
	 */
	private String ackCode;
	/**
	 * 错误代码：
	 */
	private String errorCode;
	/**
	 * 错误信息描述：用于描述具体错误
	 */
	private String errorMessage;
	/**
	 * 构造器：
	 */
	public ResultErrorVo() {
		this.ackCode = ResultVo.ack_error;
	}
	/**
	 * 构造器：
	 * @param ackCode:错误代码
	 * @param errorMessage:错误信息描述
	 */
	public ResultErrorVo(String errorCode, String errorMessage) {
		this.ackCode = ResultVo.ack_error;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	public String getAckCode() {
		return ackCode;
	}
	public void setAckCode(String ackCode) {
		this.ackCode = ackCode;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public ResultVo convertResultVo(){
		ResultVo rs=new ResultVo();
		if(this!=null){
			rs.setAckCode(this.getAckCode());
			rs.setErrorCode(this.getErrorCode());
			rs.setErrorMessage(this.getErrorMessage());
		}
		return rs;
	}
}
