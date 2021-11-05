package com.toonan.core.vo;

/**
 * 返回数据封装(成功数据 手机端返回具体实体):
 * @author wubj
 *
 */
public class ResultSuccessVo {
	/**
	 * 返回代码:恒为1
	 */
	protected String ackCode;
	public ResultSuccessVo() {
		this.ackCode = ResultVo.ack_success;
	}
	public String getAckCode() {
		return ackCode;
	}
	public void setAckCode(String ackCode) {
		this.ackCode = ackCode;
	}
	public ResultVo convertResultVo(){
		ResultVo rs=new ResultVo();
		if(this!=null){
			rs.setAckCode(this.getAckCode());
		}
		return rs;
	}
}
