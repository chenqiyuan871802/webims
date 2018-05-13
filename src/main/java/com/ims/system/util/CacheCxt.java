package com.ims.system.util;

import java.util.List;

import com.google.common.collect.Lists;
import com.ims.common.util.IMSFormater;
import com.ims.common.util.IMSUtil;
import com.ims.common.util.SpringContextHolder;
import com.ims.system.model.Dict;
import com.ims.system.model.Param;
import com.ims.system.service.ResourceCacheService;

/**
 * 
 * 类名:com.ims.system.util.CacheCxt 描述:缓存上下文 编写者:陈骑元 创建时间:2018年5月2日 下午10:15:08
 * 修改说明:
 */
public class CacheCxt {

	private static ResourceCacheService resourceCacheService = SpringContextHolder.getBean("resourceCacheServiceImpl");

	/**
	 * 根据参数键获取参数值
	 * 
	 * @param paramPO参数键
	 * 
	 */
	public static String getParamValue(String paramKey) {
		String paramValue = "";
		Param param = getCacheParam(paramKey);
		if (IMSUtil.isNotEmpty(param)) {
			paramValue = param.getParamValue();
		}
		return paramValue;
	}

	/**
	 * 从数据库参数表中根据参数键获取参数值
	 * 
	 * @param paramKey
	 *            参数键
	 * @param defaultValue
	 *            缺省值
	 * @return
	 */
	public static String getParamValue(String paramKey, String defaultValue) {
		String valueString = getParamValue(paramKey);
		if (IMSUtil.isEmpty(valueString)) {
			valueString = defaultValue;
		}
		return valueString;
	}

	/**
	 * 
	 * 简要说明：根据参数键获取键值参数实体 
	 * 编写者：陈骑元
	 * 创建时间：2017年1月24日 上午10:22:18
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static Param getCacheParam(String paramKey) {
		Param param = null;
		if (IMSUtil.isNotEmpty(paramKey)) {
			param = resourceCacheService.getCacheParam(paramKey);
		}
		return param;
	}
	/**
	 * 
	 * 简要说明：根据参数键缓存进行缓存并返回参数实体
	 * 编写者：陈骑元
	 * 创建时间：2017年1月24日 上午10:22:18
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static Param cacheParam(String paramKey) {
		Param param = null;
		if (IMSUtil.isNotEmpty(paramKey)) {
			param = resourceCacheService.cacheParam(paramKey);
		}
		return param;
	}
	/**
	 * 
	 * 简要说明：缓存所有键值参数
	 * 编写者：陈骑元
	 * 创建时间：2017年1月24日 上午10:22:18
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static void cacheAllParam() {
		
		resourceCacheService.cacheAllParam();
		
		
	}
	/**
	 * 
	 * 简要说明：根据参数键移除参数缓存
	 * 编写者：陈骑元
	 * 创建时间：2017年1月24日 上午10:22:18
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static void removeCacheParam(String paramKey) {

       resourceCacheService.removeCacheParam(paramKey);
         
		
	}
	/**
	 * 
	 * 简要说明：根据参数键移除参数缓存
	 * 编写者：陈骑元
	 * 创建时间：2017年1月24日 上午10:22:18
	 * 
	 * @param 说明
	 * @return 说明
	 */
	public static void flushParam() {
		
		resourceCacheService.flushParam();
		
	}
	
	/**
	 * 
	 * 简要说明：获取缓存的字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月2日 下午10:57:15
	 * @param 说明
	 * @return 说明
	 */
	public static List<Dict> getCacheDict(String dictKey) {
		
		List<Dict> dictList=resourceCacheService.getCacheDict(dictKey);
		return dictList;
		
	}
	/**
	 * 
	 * 简要说明：根据字典键缓存字典并返回字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月2日 下午10:57:15
	 * @param 说明
	 * @return 说明
	 */
	public static List<Dict> cacheDict(String dictKey) {
		
		List<Dict> dictList=resourceCacheService.cacheDict(dictKey);
		return dictList;
		
	}
	/**
	 * 
	 * 简要说明：缓存所有字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月2日 下午10:57:15
	 * @param 说明
	 * @return 说明
	 */
	public static void cacheAllDict() {
		
		resourceCacheService.cacheAllDict();
		
	}
	/**
	 * 
	 * 简要说明：删除缓存字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月2日 下午10:57:15
	 * @param 说明
	 * @return 说明
	 */
	public static void  removeCacheDict(String dictKey) {
		
		resourceCacheService.removeCacheDict(dictKey);
	}
	/**
	 * 
	 * 简要说明：清空缓存字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月2日 下午10:57:15
	 * @param 说明
	 * @return 说明
	 */
	public static void  flushDict() {
		
		resourceCacheService.flushDict();
	}
	/**
	 * 根据数据字典标识键和字典对照代码获取字典对照值
	 * 
	 * @param dictKey
	 *            数据字典标识键
	 * @param dictCode
	 *            数据字典对照代码
	 * @return
	 */
	public static String getDictValue(String dictKey, String dictCode) {
		String dictValue = "";
		List<Dict> dictList =getCacheDict(dictKey);
		for (Dict dict: dictList) {
			if (dict.getDictCode().equals(dictCode)) {
				dictValue = dict.getDictValue();
				break;
			}
		}
		return dictValue;
	}
	/**
	 * 根据数据字典标识键和字典对照代码获取字典对照值
	 * 
	 * @param dictKey
	 *            数据字典标识键
	 * @param dictCode
	 *            数据字典对照代码
	 * @return
	 */
	public static String getDicValue(List<Dict>  dicList, String dictCode) {
		String dictValue = "";
		for (Dict dict: dicList) {
			if (dict.getDictCode().equals(dictCode)) {
				dictValue = dict.getDictValue();
				break;
			}
		}
		return dictValue;
	}
	
	/**
	 * 
	 * 简要说明：根据字典集合过滤出需要的的字典
	 * 编写者：陈骑元
	 * 创建时间：2018年3月22日 下午3:43:19
	 * @param 说明 dictKey 获取的键， key需要集合
	 * @return 说明
	 */
	public static List<Dict> getFilterDictList(String dictKey,List<String> filterCodeList) {
		List<Dict> filterDictList=Lists.newArrayList();
		List<Dict>  dictList=getCacheDict(dictKey);
		for(Dict dict:dictList){
			String dictCode=dict.getDictCode();
			for(String code:filterCodeList){
				if(dictCode.equals(code)){
					filterDictList.add(dict);
				}
			}
		}
		
		return filterDictList;
	}
	/**
	 * 
	 * 简要说明：根据字典集合过滤出需要的的字典
	 * 编写者：陈骑元
	 * 创建时间：2018年3月22日 下午3:43:19
	 * @param 说明 dic_key 获取的键，keyString 逗号分隔的字典代码串
	 * @return 说明
	 */
	public static List<Dict> getFilterDictList(String dictKey,String filterCodestr) {
		List<Dict> dicList=Lists.newArrayList();
		if(IMSUtil.isNotEmpty(filterCodestr)){
			List<String> keyList=IMSFormater.separatStringToList(filterCodestr);
			dicList=getFilterDictList(dictKey,keyList);
		}else{
			dicList=getCacheDict(dictKey);
		}
		
		return dicList;
	}
}
