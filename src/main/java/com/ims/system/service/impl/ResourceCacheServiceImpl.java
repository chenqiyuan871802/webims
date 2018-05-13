package com.ims.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.ims.common.support.redis.JedisHelper;
import com.ims.common.util.IMSUtil;
import com.ims.common.util.JsonUtil;
import com.ims.system.constant.SystemCons;
import com.ims.system.mapper.DictIndexMapper;
import com.ims.system.mapper.DictMapper;
import com.ims.system.mapper.ParamMapper;
import com.ims.system.model.Dict;
import com.ims.system.model.DictIndex;
import com.ims.system.model.Param;
import com.ims.system.service.ResourceCacheService;

import redis.clients.jedis.Jedis;
/**
 * 
 * 类名:com.ims.system.service.impl.ResourceCacheServiceImpl
 * 描述:缓存资源服务处理
 * 编写者:陈骑元
 * 创建时间:2018年5月1日 下午4:57:07
 * 修改说明:
 */
@Service("resourceCacheServiceImpl")
public class ResourceCacheServiceImpl implements ResourceCacheService {
	
	private static Log log = LogFactory.getLog(ResourceCacheServiceImpl.class);
	 /**
	  * redis缓存服务
	  */
	@Autowired
	private JedisHelper jedisHelper;
	/**
	 * 键值参数数据库接口
	 */
	@Autowired
	private ParamMapper paramMapper;
	/**
	 * 字典数据库接口
	 */
	 @Autowired
	 private DictMapper dictMapper;
	/**
	 * 字典数据库接口
	 */
	 @Autowired
	 private DictIndexMapper dictIndexMapper;
    /**
     * 
     * 简要说明：获取缓存中的键值参数，没有数据库中查找
     * 编写者：陈骑元
     * 创建时间：2018年5月1日 下午9:22:38
     * @param 说明
     * @return 说明
     */
	@Override
	public Param getCacheParam(String paramKey) {
		Param param=null;
		if(IMSUtil.isNotEmpty(paramKey)){
			if(jedisHelper.isLive()){  //redis在线
				Jedis jedis = jedisHelper.getJedisClient();
				String paramJson = jedis.hget(SystemCons.CACHE_PREFIX.PARAM, paramKey);
				if (IMSUtil.isNotEmpty(paramJson)) {
					param= (Param) JsonUtil.fromJson(paramJson, Param.class);
				} else { // 如果redis 找不到就去数据库中查找，并存放redis中
					param = cacheParam(paramKey);

				}
				jedisHelper.close(jedis);
			}else{
				param = getParam(paramKey);
			}
		}else{
			log.error("获取键值参数失败：参数键为空");
		}
		return param;
	}
	/**
	 * 
	 * 简要说明：根据参数键进行键值参数缓存
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午9:23:28
	 * @param 说明
	 * @return 说明
	 */
	@Override
	public Param cacheParam(String paramKey) {
		Param param=getParam(paramKey);
		if(IMSUtil.isNotEmpty(param)){
			if(jedisHelper.isLive()){  //redis在线
				Jedis jedis =jedisHelper.getJedisClient();
				jedis.hset(SystemCons.CACHE_PREFIX.PARAM, paramKey, JsonUtil.toJson(param));
				jedisHelper.close(jedis);
			}
		
		}
		return param;
	}
	/**
	 * 
	 * 简要说明：
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午9:10:34
	 * @param 说明
	 * @return 说明
	 */
    private Param getParam(String paramKey){
    	Param param=null;
    	if(IMSUtil.isNotEmpty(paramKey)){
    		Param entity=new Param();
    		entity.setParamKey(paramKey);
    		entity.setStatus(SystemCons.ENABLED_YES);  //只查询启用
    		param=paramMapper.selectOne(entity);
    	}
    	return param;
    }
    /**
     * 
     * 简要说明：根据实体进行键值参数缓存
     * 编写者：陈骑元
     * 创建时间：2018年5月1日 下午9:24:46
     * @param 说明
     * @return 说明
     */
	@Override
	public Param cacheParam(Param param) {
		if(IMSUtil.isNotEmpty(param)){
		   if(jedisHelper.isLive()){  //redis在线
			Jedis jedis =jedisHelper.getJedisClient();
			jedis.hset(SystemCons.CACHE_PREFIX.PARAM, param.getParamKey(), JsonUtil.toJson(param));
			jedisHelper.close(jedis);
		  }
		}
		return param;
	}
    /**
     * 
     * 简要说明：
     * 编写者：陈骑元
     * 创建时间：2018年5月1日 下午9:25:42
     * @param 说明
     * @return 说明
     */
	@Override
	public void removeCacheParam(String paramKey) {
		if(IMSUtil.isNotEmpty(paramKey)){
			if(jedisHelper.isLive()){  //redis在线
				Jedis jedis =jedisHelper.getJedisClient();
				jedis.hdel(SystemCons.CACHE_PREFIX.PARAM, paramKey);
				jedisHelper.close(jedis);
			}
		}

	}

	@Override
	public void cacheAllParam() {
		if(jedisHelper.isLive()){  //redis在线
			List<Param> paramList=paramMapper.selectList(new EntityWrapper<Param>().eq("status", SystemCons.ENABLED_YES));
			Map<String, String> cacheMap = Maps.newHashMap();
			for (Param param : paramList) {
				cacheMap.put(param.getParamKey(), JsonUtil.toJson(param));
			}
			if (IMSUtil.isNotEmpty(cacheMap)) {
				Jedis jedis = jedisHelper.getJedisClient();
				jedis.del(SystemCons.CACHE_PREFIX.PARAM);//先清空在插入
				jedis.hmset(SystemCons.CACHE_PREFIX.PARAM, cacheMap);
				jedisHelper.close(jedis);
			}
		}
	
	}
	

	@Override
	public void flushParam() {
		if(jedisHelper.isLive()){  //redis在线
			Jedis jedis = jedisHelper.getJedisClient();
			jedis.del(SystemCons.CACHE_PREFIX.PARAM);
			jedisHelper.close(jedis);
		}

	}
    /**
     * 
     * 简要说明：获取缓存中的字典
     * 编写者：陈骑元
     * 创建时间：2018年5月1日 下午11:06:13
     * @param 说明
     * @return 说明
     */
	@Override
	public List<Dict> getCacheDict(String dictKey) {
		List<Dict> dictList=Lists.newArrayList();
		if(IMSUtil.isNotEmpty(dictKey)){
			if(jedisHelper.isLive()){  //redis在线
				Jedis jedis = jedisHelper.getJedisClient();
				List<String> dictRedisList = jedis.lrange(SystemCons.CACHE_PREFIX.DICT + dictKey, 0, -1);
				if (IMSUtil.isNotEmpty(dictRedisList)) {
					for (String dicString : dictRedisList) {
					 dictList.add((Dict) JsonUtil.fromJson(dicString, Dict.class));
					}
				}else{
					dictList=cacheDict(dictKey);
				}
				jedisHelper.close(jedis);
			}else{
				dictList=getDict(dictKey);  //从数据库中获取
			}
			
		}else{
			
			log.error("获取字典失败：字典标识键[" + dictKey + "]为空");
		}
		return dictList;
	}
    /**
     * 
     * 简要说明：缓存字典
     * 编写者：陈骑元
     * 创建时间：2018年5月1日 下午10:48:09
     * @param 说明
     * @return 说明
     */
	@Override
	public List<Dict> cacheDict(String dictKey) {
		List<Dict> dictList=Lists.newArrayList();
		if(IMSUtil.isNotEmpty(dictKey)){
			dictList=getDict(dictKey);
			if(jedisHelper.isLive()){
				Jedis jedis = jedisHelper.getJedisClient();
				jedis.del(SystemCons.CACHE_PREFIX.DICT + dictKey);
				if (IMSUtil.isNotEmpty(dictList)) {
					// 再插入redis 中
					for (Dict dict : dictList) {
						jedis.rpush(SystemCons.CACHE_PREFIX.DICT + dictKey, JsonUtil.toJson(dict));
					}

				} else {
					log.error("字典数据信息刷新到Redis中失败，字典标识键[" + dictKey + "]在系统中没有字典对照集合或者已经被停用");
				}
				jedisHelper.close(jedis);
			}
			
		}
		return dictList;
	}
    /**
     * 
     * 简要说明：根据键删除缓存字典
     * 编写者：陈骑元
     * 创建时间：2018年5月1日 下午11:08:34
     * @param 说明
     * @return 说明
     */
	@Override
	public void removeCacheDict(String dictKey) {
		if(IMSUtil.isNotEmpty(dictKey)){
			if(jedisHelper.isLive()){
				Jedis jedis = jedisHelper.getJedisClient();
				jedis.del(SystemCons.CACHE_PREFIX.DICT + dictKey);
				jedisHelper.close(jedis);
			}
		}

	}
    /**
     * 
     * 简要说明：根据字典键从数据库中获取字典
     * 编写者：陈骑元
     * 创建时间：2018年5月1日 下午10:24:07
     * @param 说明
     * @return 说明
     */
	private List<Dict> getDict(String dictKey){
		List<Dict> dictList=Lists.newArrayList();
		if(IMSUtil.isNotEmpty(dictKey)){
			DictIndex entity=new DictIndex();
			entity.setDictKey(dictKey);
			DictIndex dictIndex=dictIndexMapper.selectOne(entity);
			if(IMSUtil.isNotEmpty(dictIndex)){
				String dictIndexId=dictIndex.getDictIndexId();
				EntityWrapper<Dict> wrapper =new EntityWrapper<Dict>();
				wrapper.eq("status", SystemCons.ENABLED_YES);
				wrapper.eq("dict_index_id", dictIndexId);
				wrapper.orderBy("sort_no", true);
				dictList=dictMapper.selectList(wrapper);
				for(Dict dict:dictList){
					dict.setDictKey(dictKey);
				}

				
			}
			if (dictList.size() == 0) {
				log.error("字典标识键[" + dictKey + "]在系统中没有字典对照集合或者已经被停用");
			}
			
		}else{
			log.error("获取字典失败：字典标识键[" + dictKey + "]为空");
		}
		
		
		return dictList;
		
	}
	/**
	 * 
	 * 简要说明：缓存所有字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午11:09:55
	 * @param 说明
	 * @return 说明
	 */
	@Override
	public void cacheAllDict() {
		if(jedisHelper.isLive()){
			Jedis jedis =jedisHelper.getJedisClient();
			jedis.del(SystemCons.CACHE_PREFIX.DICT);
			List<DictIndex> dictIndexList=dictIndexMapper.list(null);
			Map<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put("status", SystemCons.ENABLED_YES);
			List<Dict> dictList=dictMapper.selectByMap(paramMap);
			for(Dict dict:dictList){
				String dictKey=getDictKey(dictIndexList,dict.getDictIndexId());
				if(IMSUtil.isNotEmpty(dictKey)){
					
					jedis.rpush(SystemCons.CACHE_PREFIX.DICT + dictKey, JsonUtil.toJson(dict));
				}
			}
			jedisHelper.close(jedis);
		}
		

	}
	/**
	 * 
	 * 简要说明：返回键值
	 * 编写者：陈骑元
	 * 创建时间：2018年5月1日 下午11:20:57
	 * @param 说明
	 * @return 说明
	 */
	private String getDictKey(List<DictIndex> dictIndexList,String dictIndexId){
		
		for(DictIndex dictIndex:dictIndexList){
			String tmpIndexId=dictIndex.getDictIndexId();
			if(dictIndexId.equals(tmpIndexId)){
				
				return dictIndex.getDictKey();
			}
		}
		return "";
	}
    /**
     * 
     * 简要说明：清空缓存字典
     * 编写者：陈骑元
     * 创建时间：2018年5月1日 下午11:09:19
     * @param 说明
     * @return 说明
     */
	@Override
	public void flushDict() {
		if(jedisHelper.isLive()){  //redis在线
			Jedis jedis = jedisHelper.getJedisClient();
			jedis.del(SystemCons.CACHE_PREFIX.DICT);
			jedisHelper.close(jedis);
		}
	}

	

}
