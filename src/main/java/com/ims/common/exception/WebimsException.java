package com.ims.common.exception;

/**
 * 自定义异常

 */
public class WebimsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String appmsg;
    private int appcode = 500;
    
    public WebimsException(String appmsg) {
		super(appmsg);
		this.appmsg = appmsg;
	}
	
	public WebimsException(String appmsg, Throwable e) {
		super(appmsg, e);
		this.appmsg = appmsg;
	}
	
	public WebimsException(String appmsg, int appcode) {
		super(appmsg);
		this.appmsg = appmsg;
		this.appcode = appcode;
	}
	
	public WebimsException(String msg, int code, Throwable e) {
		super(msg, e);
		this.appmsg = msg;
		this.appcode = code;
	}

	public String getAppmsg() {
		return appmsg;
	}

	public void setAppmsg(String appmsg) {
		this.appmsg = appmsg;
	}

	public int getAppcode() {
		return appcode;
	}

	public void setAppcode(int appcode) {
		this.appcode = appcode;
	}

	
	
	
}
