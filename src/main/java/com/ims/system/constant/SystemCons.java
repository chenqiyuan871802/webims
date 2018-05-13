package com.ims.system.constant;
/**
 * 
 * 类名:com.ims.system.constant.SystemCons
 * 描述:系统常量表
 * 编写者:陈骑元
 * 创建时间:2018年5月1日 下午5:02:40
 * 修改说明:
 */
public interface SystemCons {
	
	/**
	 * 当前状态：启用
	 */
	public static final String ENABLED_YES = "1";
	
	/**
	 * 当前状态：停用
	 */
	public static final String ENABLED_NO = "0";
	/**
	 * 编辑模式：只读
	 */
	public static final String EDITMODE_READ="0";
	/**
	 * 编辑模式：可编辑
	 */
	public static final String EDITMODE_EDIT="1";
	
	/**
	 * Cache对象前缀
	 *
	 */
	public static  final class CACHE_PREFIX{
		//全局参数
		public static final String PARAM = "webims:param:";
		//字典
		public static final String DICT = "webims:dict:";
		
	}

}
