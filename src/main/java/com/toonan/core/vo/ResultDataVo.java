package com.toonan.core.vo;


public class ResultDataVo {
	private String ackCode;
	private Object data;
	private String message;
	
	public ResultDataVo(Object data) {
		super();
		this.ackCode=ResultVo.ack_success;
		this.data = data;
	}
	
	public ResultDataVo(Object data,String message) {
		super();
		this.ackCode=ResultVo.ack_success;
		this.data = data;
		this.message = message;
	}
	
	public String getAckCode() {
		return ackCode;
	}
	public void setAckCode(String ackCode) {
		this.ackCode = ackCode;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
