package com.ims.system.service;

import java.util.List;


import com.ims.system.model.Dict;
import com.ims.system.model.Param;

/**
 * 
 * 类名:com.ims.system.service.IResourceCacheService
 * 描述:资源缓存服务
 * 编写者:陈骑元
 * 创建时间:2018年5月1日 下午4:41:23
 * 修改说明:
 */
public interface ResourceCacheService {
	/**
	 * 
	 * 简要说明：获取键值参数缓存
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午4:42:47
	 * @param 说明
	 * @return 说明
	 */
	public Param getCacheParam(String paramKey);
	/**
	 * 
	 * 简要说明：根据缓存键缓存键值参数
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午4:46:15
	 * @param 说明
	 * @return 说明
	 */
	public Param cacheParam(String  paramKey);
	/**
	 * 
	 * 简要说明：缓存键值参数
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午4:46:15
	 * @param 说明
	 * @return 说明
	 */
	public Param cacheParam(Param param);
	/**
	 * 
	 * 简要说明：根据参数键值删除缓存
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午4:47:56
	 * @param 说明
	 * @return 说明
	 */
	public void  removeCacheParam(String paramKey);
	/**
	 * 
	 * 简要说明：缓存所有兼职参数
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午4:49:26
	 * @param 说明
	 * @return 说明
	 */
	public void cacheAllParam();
	/**
	 * 
	 * 简要说明：清空所有键值参数
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午4:50:46
	 * @param 说明
	 * @return 说明
	 */
	public void flushParam();
	/**
	 * 
	 * 简要说明：通过字典键获取字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午4:45:13
	 * @param 说明
	 * @return 说明
	 */
	public List<Dict> getCacheDict(String dictKey);
	/**
	 * 
	 * 简要说明：根据参数键缓存字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午4:51:51
	 * @param 说明
	 * @return 说明
	 */
	public List<Dict> cacheDict(String dictKey);
	/**
	 * 
	 * 简要说明：删除缓存字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午4:53:31
	 * @param 说明
	 * @return 说明
	 */
	public void removeCacheDict(String dictKey);
	/**
	 * 
	 * 简要说明：删除缓存字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午4:53:31
	 * @param 说明
	 * @return 说明
	 */
	public void cacheAllDict();
	/**
	 * 
	 * 简要说明：刷新字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午4:54:57
	 * @param 说明
	 * @return 说明
	 */
	public void flushDict();
	
}
