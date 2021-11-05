package com.toonan.core.vo;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.toonan.core.matatype.Dto;
import com.toonan.core.matatype.Dtos;
import com.toonan.core.util.WebplusUtil;


/**
 * 返回数据实体标准(成功/错误数据):
 * @author wubj
 *
 */
@SuppressWarnings("rawtypes")
public class ResultVo {
	/**
	 * 返回代码:
	 */
	private String ackCode;
	/**
	 * 错误代码：(1开头:初始化错误;	3开头：本地校验错误;	4开头:来穗错误;	5开头：捕捉的异常错误)
	 */
	private String errorCode;
	/**
	 * 错误信息描述：用于描述具体错误
	 */
	private String errorMessage;
	/**
	 * 数据区：object(详情、统计数值、复杂数据等数据区)
	 */
	private Object data;
	/**
	 * 数据区：分页数据展现
	 */
	private PageVo page;
	/**
	 * 数据区：list(分页列表、不分页列表数据区)
	 */
	private List<?> dataList;
	/**
	 * 返回代码：成功代码
	 */
	public static String ack_success = "1";
	/**
	 * 返回代码：错误代码
	 */
	public static String ack_error = "0";
	/**
	 * 错误代码：初始化错误
	 */
	public static String error_100 = "100";
	/**
	 * 错误代码：授权不通过错误
	 */
	public static String error_300 = "300";
	/**
	 * 错误代码：参数不能为空错误(null、"")
	 */
	public static String error_301 = "301";
	/**
	 * 错误代码：参数值校验错误(格式：日期格式、中英文数字、符合匹配规则等)
	 */
	public static String error_302 = "302";
	/**
	 * 错误代码：参数值校验错误(长度：字符长度)
	 */
	public static String error_303 = "303";
	/**
	 * 错误代码：参数值校验错误(大小：数字大小、日期大小)
	 */
	public static String error_304 = "304";
	/**
	 * 错误代码：参数值校验错误(值是否存在或者符合业务)
	 */
	public static String error_305 = "305";
	/**
	 * 错误代码：参数值校验错误(文件不存在或者不是有效文件)
	 */
	public static String error_306 = "306";
	/**
	 * 错误代码：参数值校验错误(文件无权访问)
	 */
	public static String error_307 = "307";
	/**
	 * 错误代码：来穗接口访问失败错误
	 */
	public static String error_404 = "404";
	/**
	 * 错误代码：来穗接口返回数据格式错误
	 */
	public static String error_405 = "405";
	/**
	 * 错误代码：捕捉的异常错误(控制器捕捉的最大异常错误)
	 */
	public static String error_500 = "500";
	/**
	 * 错误代码：捕捉的异常错误(各种数据封装成手机端返回具体实体错误)
	 */
	public static String error_501 = "501";
	/**
	 * 错误代码：捕捉的异常错误(程序中其他处捕捉到的异常错误)
	 */
	public static String error_505 = "505";
	/***************************************** 来穗错误码定义 start*****************************************/
	public static String ERROR_CODE_30001= "30001";
	public static String ERROR_MESSAGE_30001 = "令牌认证accessToken参数为空，系统拒绝登录";
	public static String ERROR_CODE_30002 = "30002";
	public static String ERROR_MESSAGE_30002 = "令牌认证accessToken校验不合法或已经超时，请重新登录获取accessToken";
	public static String ERROR_CODE_30004= "30004";
	public static String ERROR_MESSAGE_30004 = "用户系统登录名不正确，请核对登录名";
	public static String ERROR_CODE_30005= "30005";
	public static String ERROR_MESSAGE_30005 = "用户登录口令不正确，请核对登录口令";
	public static String ERROR_CODE_30006= "30006";
	public static String ERROR_MESSAGE_30006 = "用户已被锁定，请联系管理员进行解锁";

	public static String ERROR_CODE_40001 = "40001";
	public static String ERROR_MESSAGE_40001 = "参数不能为空";
	public static String ERROR_CODE_40002 = "40002";
	public static String ERROR_MESSAGE_40002= "字典代码校验不合法";
	public static String ERROR_CODE_40003 = "40003";
	public static String ERROR_MESSAGE_40003= "数据检验不合法";
	public static String ERROR_CODE_40004 = "40004";
	public static String ERROR_MESSAGE_40004= "参数长度不合法";
	public static String ERROR_CODE_40005 = "40005";
	public static String ERROR_MESSAGE_40005 = "证件号码不合法";
	public static String ERROR_CODE_40006 = "40006";
	public static String ERROR_MESSAGE_40006= "上传文件服务器失败";
	public static String ERROR_CODE_40007= "40007";
	public static String ERROR_MESSAGE_40007 = "下载文件失败";
	public static String ERROR_CODE_40008 = "40008";
	public static String ERROR_MESSAGE_40008 = "处理数据异常";
	public static String ERROR_CODE_40009 = "40009";
	public static String ERROR_MESSAGE_40009= "离线数据下载处理发生异常";
	/***************************************** 来穗错误码定义 end*****************************************/
	/**
	 * 错误信息描述初始化：操作异常
	 */
	public static String errorMessage_init="操作初始化异常";
	/**
	 * 构造器：
	 */
	public ResultVo(){
		
	}
	/**
	 * 构造器：返回错误/成功
	 * @param ackCode:返回代码
	 */
	public ResultVo(String ackCode) {
		this.ackCode = ackCode;
		this.errorMessage=errorMessage_init;
	}
	/**
	 * 构造器：返回错误
	 * @param errorCode:错误代码
	 * @param errorMessage:错误信息描述
	 */
	public ResultVo(String errorCode, String errorMessage) {
		this.ackCode = ResultVo.ack_error;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	/**
	 * 构造器：返回成功及data数据区
	 * @param data
	 */
	public ResultVo(Object data) {
		this.ackCode = ResultVo.ack_success;
		this.data = data;
	}
	/**
	 * 构造器：返回成功及dataList数据区
	 * @param data_list
	 */
	public ResultVo(List<?> dataList) {
		this.ackCode = ResultVo.ack_success;
		this.dataList = dataList;
	}
	/**
	 * 构造器：返回成功及dataList、page数据区
	 * @param page
	 * @param dataList
	 */
	public ResultVo(List<?> dataList,PageVo page) {
		this.ackCode = ResultVo.ack_success;
		this.page = page;
		this.dataList = dataList;
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
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
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
	/**
	 * 判断返回代码是否成功：true 是，false 否
	 * @return
	 */
	public boolean isSuccess(){
		if(this!=null&&ResultVo.ack_success.equals(this.getAckCode())){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断返回代码是否错误：true 是，false 否
	 * @return
	 */
	public boolean isError(){
		return !isSuccess();
	}
	/**
	 * 获取ResultErrorVo实体，如果成功返回null
	 * @return
	 */
	public ResultErrorVo getResultErrorVo(){
		ResultErrorVo rs=null;
		if(this!=null&&ResultVo.ack_error.equals(this.getAckCode())){
			rs=new ResultErrorVo(this.getErrorCode(),this.getErrorMessage());
		}
		return rs;
	}
	/**
	 * 获取ResultErrorVo实体，如果错误返回null
	 * @return
	 */
	public ResultSuccessVo getResultSuccessVo(){
		ResultSuccessVo rs=null;
		if(this!=null&&ResultVo.ack_success.equals(this.getAckCode())){
			rs=new ResultSuccessVo();
		}
		return rs;
	}
	/**
	 * 获取ResultDataVo实体，如果错误返回null
	 * @return
	 */
	public ResultDataVo getResultDataVo(){
		ResultDataVo rs=null;
		if(this!=null&&ResultVo.ack_success.equals(this.getAckCode())){
			rs=new ResultDataVo(this.getData());
		}
		return rs;
	}
	/**
	 * 获取ResultListVo实体，如果错误返回null
	 * @return
	 */
	public ResultListVo getResultListVo(){
		ResultListVo rs=null;
		if(this!=null&&ResultVo.ack_success.equals(this.getAckCode())){
			rs=new ResultListVo(this.getDataList());
		}
		return rs;
	}
	/**
	 * 获取ResultPageVo实体，如果错误返回null
	 * @return
	 */
	public ResultPageVo getResultPageVo(){
		ResultPageVo rs=null;
		if(this!=null&&ResultVo.ack_success.equals(this.getAckCode())){
			rs=new ResultPageVo(this.getPage(),this.getDataList());
		}
		return rs;
	}
	/**
	 * 获取ResultPageVo实体，如果错误返回null
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Dto getResultDto(){
		if(WebplusUtil.isNotEmpty(this)&&ResultVo.ack_success.equals(this.getAckCode())){
			if(WebplusUtil.isNotEmpty(this.getData())){
				Map map=(Map)this.getData();
				Dto dataDto=Dtos.newDto();
				dataDto.putAll(map);
				return dataDto;
			}
		}
		return Dtos.newDto() ;
	}
	
	/**
	 * 获取ResultPageVo实体，如果错误返回null
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Dto> getResultListDto(){
		if(WebplusUtil.isNotEmpty(this)&&ResultVo.ack_success.equals(this.getAckCode())){
			if(WebplusUtil.isNotEmpty(this.getDataList())){
				List<Dto> dataList=Lists.newArrayList();
				List<Map> mapList=(List<Map>)this.getDataList();
				for(Map map:mapList){
					Dto dataDto=Dtos.newDto();
					dataDto.putAll(map);
					dataList.add(dataDto);
				}
				return  dataList;
			}
		}
		return Lists.newArrayList();
	}
}
