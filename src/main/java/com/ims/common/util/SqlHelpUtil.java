package com.ims.common.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ims.common.matatype.Dto;

/**
 * 
 * 类名:com.ims.common.util.SqlHelpUtil
 * 描述:sql辅助工具类
 * 编写者:陈骑元
 * 创建时间:2018年5月9日 下午10:23:22
 * 修改说明:
 */
public class SqlHelpUtil {
	
	/**
	 * 
	 * 简要说明：模糊sql
	 * 编写者：陈骑元
	 * 创建时间：2018年5月9日 下午10:25:14
	 * @param 说明
	 * @return 说明
	 */
	public static void like(EntityWrapper<?> wrapper,String tableField,String value){
		
		if(IMSUtil.isNotAnyEmpty(tableField,value)){
			wrapper.like(tableField, "%"+value+"%");
		}
		
	}
	/**
	 * 
	 * 简要说明：模糊sql
	 * 编写者：陈骑元
	 * 创建时间：2018年5月9日 下午10:25:14
	 * @param 说明 pDto参数集合
	 * @return 说明
	 */
	public static void like(EntityWrapper<?> wrapper,String tableField,Dto pDto,String valueKey){
		
		if(IMSUtil.isNotAnyEmpty(tableField,valueKey)){
			String value=pDto.getString(valueKey);
			if(IMSUtil.isNotEmpty(value)){
				wrapper.like(tableField, "%"+value+"%");
			}
			
		}
		
	}
	/**
	 * 
	 * 简要说明：等于sql
	 * 编写者：陈骑元
	 * 创建时间：2018年5月9日 下午10:25:14
	 * @param 说明
	 * @return 说明
	 */
	public static void eq(EntityWrapper<?> wrapper,String tableField,String value){
		
		if(IMSUtil.isNotAnyEmpty(tableField,value)){
			wrapper.eq(tableField, value);
		}
		
	}
	/**
	 * 
	 * 简要说明：等于sql
	 * 编写者：陈骑元
	 * 创建时间：2018年5月9日 下午10:25:14
	 * @param 说明 pDto参数集合
	 * @return 说明
	 */
	public static void eq(EntityWrapper<?> wrapper,String tableField,Dto pDto,String valueKey){
		
		if(IMSUtil.isNotAnyEmpty(tableField,valueKey)){
			String value=pDto.getString(valueKey);
			if(IMSUtil.isNotEmpty(value)){
				wrapper.eq(tableField, value);
			}
			
		}
		
	}
	/**
	 * 
	 * 简要说明：或者sql
	 * 编写者：陈骑元
	 * 创建时间：2018年5月9日 下午10:25:14
	 * @param 说明
	 * @return 说明
	 */
	public static void or(EntityWrapper<?> wrapper,String tableField,String value){
		
		if(IMSUtil.isNotAnyEmpty(tableField,value)){
			wrapper.or(tableField, value);
		}
		
	}
	/**
	 * 
	 * 简要说明： 或者sql
	 * 编写者：陈骑元
	 * 创建时间：2018年5月9日 下午10:25:14
	 * @param 说明 pDto参数集合
	 * @return 说明
	 */
	public static void or(EntityWrapper<?> wrapper,String tableField,Dto pDto,String valueKey){
		
		if(IMSUtil.isNotAnyEmpty(tableField,valueKey)){
			String value=pDto.getString(valueKey);
			if(IMSUtil.isNotEmpty(value)){
				wrapper.or(tableField, value);
			}
			
		}
		
	}

}
