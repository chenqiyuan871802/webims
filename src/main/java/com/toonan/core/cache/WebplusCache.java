package com.toonan.core.cache;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.toonan.core.annotation.ItemTag;
import com.toonan.core.constant.WebplusCons;
import com.toonan.core.matatype.Dto;
import com.toonan.core.matatype.impl.BaseModel;
import com.toonan.core.redis.WebplusJedis;
import com.toonan.core.util.WebplusFormater;
import com.toonan.core.util.WebplusJson;
import com.toonan.core.util.WebplusSpringContext;
import com.toonan.core.util.WebplusUtil;
import com.toonan.core.vo.Item;
import com.toonan.core.vo.UserToken;

/**
 *
 * 类名:com.toonan.core.cache.WebplusCache
 * 描述:缓存支持
 * 编写者:陈骑元
 * 创建时间:2019年4月27日 上午12:40:32
 * 修改说明:
 */
public class WebplusCache {


	private static Logger logger = LoggerFactory.getLogger(WebplusCache.class);
	/**
	 * jedis帮助
	 */
	private static WebplusJedis webplusJedis = WebplusSpringContext.getBean("webplusJedis");


	/**
	 *
	 * 简要说明：获取键值参数整数，返回默认值
	 * 编写者：陈骑元
	 * 创建时间：2019年2月24日 上午1:44:55
	 * @param 说明
	 * @return 说明
	 */
	public static int getParamIntValue(String paramKey,int defaultValue) {
		int paramValue=getParamIntValue(paramKey);
		if(paramValue==-99){
			return defaultValue;
		}
		return paramValue;
	}
	/**
	 * 根据参数键获取参数值
	 *
	 * @param paramPO参数键
	 *
	 */
	public static int getParamIntValue(String paramKey) {
	    String paramValue=getParamValue(paramKey);
	    try {
			return Integer.parseInt(paramValue);
		} catch (Exception e) {

		}
		return -99;
	}

	/**
	 * 根据参数键获取参数值
	 *
	 * @param paramPO参数键
	 *
	 */
	public static String getParamValue(String paramKey) {
		String paramValue = "";
		Map<String,String> paramMap = getCacheParam(paramKey);
		if (WebplusUtil.isNotEmpty(paramMap)) {
			paramValue =paramMap.get("paramValue");
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
		if (WebplusUtil.isEmpty(valueString)) {
			valueString = defaultValue;
		}
		return valueString;
	}

	/**
	 *
	 * 简要说明：根据参数键获取键值参数Dto
	 * 编写者：陈骑元
	 * 创建时间：2017年1月24日 上午10:22:18
	 *
	 * @param 说明
	 * @return 说明
	 */
	public static Map<String,String> getCacheParam(String paramKey) {
		if(WebplusUtil.isEmpty(paramKey)){
			logger.error("获取缓存中键值参数失败：参数键为空");
			return null;
		}
		String paramJson = webplusJedis.hget(WebplusCons.CACHE_PREFIX.PARAM, paramKey);
		if(WebplusUtil.isEmpty(paramJson)){
			logger.error("缓存中没有获取到参数键【"+paramKey+"】,请确认参数键是否正确，或者键值参数表是否配置或启用，或是否刷新键值参数缓存");
			return null;
		}
		Map<String,String> paramDto=WebplusJson.fromJson(paramJson, HashMap.class);

		return paramDto;
	}

	/**
	 *
	 * 简要说明：获取过滤的字典集合
	 * 编写者：陈骑元
	 * 创建时间：2018年12月15日 下午5:33:41
	 * @param 说明
	 * @return 说明
	 */
	public static List<Item> getItemList(String typeCode,String filterCodestr) {
		List<Item> itemList=Lists.newArrayList();
		List<Map<String,String>> dictList=getCacheDict(typeCode);
		 for(Map<String,String> dictMap:dictList){
			 String itemCode="";
			 String itemName="";
			 if(dictMap.containsKey("itemCode")) {
				 itemCode=dictMap.get("itemCode");
				 itemName=dictMap.get("itemName"); 
			 }else {
				 itemCode=dictMap.get("dictCode");
				 itemName=dictMap.get("dictValue"); 
			 }
			
			 if(!WebplusUtil.contains(itemCode, filterCodestr)){
				 Item item=new Item();
				 item.setTypeCode(typeCode);
				 item.setItemCode(itemCode);
				 item.setItemName(itemName);
				 itemList.add(item);

			 }

	   }

		return itemList;
	}
	
    /**
     * 
     * 简要说明：设置字典缓存
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2021年9月23日 上午10:22:55 
     * @param 说明
     * @return 说明
     */
    public static void setItemCache(String typeCode,List<Item> itemList) {
    
        String key=WebplusCons.CACHE_PREFIX.DICT+typeCode;
        webplusJedis.delString(key);
        for(Item item:itemList) {
            webplusJedis.rpush(key, WebplusJson.toJson(item));
        }
    }

	/**
	 *
	 * 简要说明：获取字典值
	 * 编写者：陈骑元
	 * 创建时间：2018年12月15日 下午5:33:41
	 * @param 说明
	 * @return 说明
	 */
	public static List<Item> getItemList(String typeCode) {


		return getItemList(typeCode,"");

	}
	/**
	 *
	 * 简要说明：根据字典类型和字典对照码获取字典对照值
	 * 编写者：陈骑元
	 * 创建时间：2019年4月28日 下午9:20:59
	 * @param 说明
	 * @return 说明
	 */
	public static String getItemName(String typeCode,String itemCode) {
		List<Item> itemList=getItemList(typeCode);

		return getItemName(itemList,itemCode);
	}


	/**
	 *
	 * 简要说明：根据字典列表和字典对照码获取字典对照值
	 * 编写者：陈骑元
	 * 创建时间：2019年4月28日 下午9:20:05
	 * @param 说明
	 * @return 说明
	 */
	public static String getItemName(List<Item> itemList,String itemCode) {
		if(WebplusUtil.isNotEmpty(itemList)){
			List<String> itemCodeList=WebplusFormater.separatStringToList(itemCode);
			String itemName="";
			for (Item item:itemList) {
				if(WebplusUtil.checkStrExistInList(item.getItemCode(), itemCodeList)) {
					itemName+=item.getItemName()+",";
				}
			}
			return WebplusUtil.splitEndChar(itemName);
		}

		return "";
	}
	/**
	 *
	 * 简要说明：根据字典类型和字典对照值获取字典对照码
	 * 编写者：陈骑元
	 * 创建时间：2019年4月28日 下午9:20:59
	 * @param 说明
	 * @return 说明
	 */
	public static String getItemCode(String typeCode,String itemName) {
		List<Item> itemList=getItemList(typeCode);

		return getItemCode(itemList,itemName);
	}
	 /**
	 * 简要说明：根据字典列表和字典对照镇获取字典对照码
	 * 编写者：陈骑元
	 * 创建时间：2019年4月28日 下午9:20:05
	 * @param 说明
	 * @return 说明
	 */
	public static String getItemCode(List<Item> itemList,String itemName) {
		if(WebplusUtil.isNotEmpty(itemList)){
			for (Item item:itemList) {
				if (item.getItemName().equals(itemName)) {

					return  item.getItemCode();
				}
			}
		}

		return "";
	}
	/**
	 *
	 * 简要说明：获取缓存的字典
	 * 编写者：陈骑元
	 * 创建时间：2018年5月2日 下午10:57:15
	 * @param 说明
	 * @return 说明
	 */
	private static List<Map<String,String>> getCacheDict(String dictKey) {
		if(WebplusUtil.isEmpty(dictKey)){
			logger.error("获取缓存中字典失败：字典标识键为空");
			return Lists.newArrayList();
		}
		List<String> dictRedisList = webplusJedis.lrange(WebplusCons.CACHE_PREFIX.DICT + dictKey, 0, -1);
		if (WebplusUtil.isEmpty(dictRedisList)) {
			logger.error("缓存中没有获取到字典标识键【"+dictKey+"】字典,请确认字典标识键是否正确，或者通用字典是否配置或启用改字典标识键字典，或是否刷新通用字典缓存");
			return Lists.newArrayList();
		}
		List<Map<String,String>> dictList=Lists.newArrayList();
		for (String dicString : dictRedisList) {
			dictList.add(WebplusJson.fromJson(dicString, HashMap.class));
		}
		return dictList;
	}

	/**
	 *
	 * 简要说明：转换分页
	 * 编写者：陈骑元
	 * 创建时间：2018年12月17日 下午8:15:29
	 * @param 说明
	 * @return 说明
	 */
	public static void convertItem(Page<?> page){
		if(WebplusUtil.isNotEmpty(page)){
			convertItem(page.getRecords());
		}

	}
	/**
	 *
	 * 简要说明：转换字典
	 * 编写者：陈骑元
	 * 创建时间：2018年12月17日 下午7:02:43
	 * @param 说明
	 * @return 说明
	 */
	public static void convertItem(List<?> list){

		if(WebplusUtil.isNotEmpty(list)){
			Object dictBean=list.get(0);
			Map<String,String> itemDto=getConvertItemData(dictBean);
			for (Object bean : list) {
				convertItem(bean,itemDto);
	        }
		}
	}
	/**
	 *
	 * 简要说明：
	 * 编写者：陈骑元
	 * 创建时间：2018年12月18日 下午9:48:00
	 * @param 说明
	 * @return 说明
	 */
	public static void convertItem(Object  bean){

		Map<String,String> itemDto=getConvertItemData(bean);
		convertItem(bean, itemDto);
	}

	/**
	 *
	 * 简要说明：转换map字典
	 * 编写者：陈骑元
	 * 创建时间：2018年12月18日 下午9:47:55
	 * @param 说明
	 * @return 说明
	 */
    @SuppressWarnings("rawtypes")
	public static void convertMapDict(List<? extends Map> listMap,Dto keyDto){
		Map<String,String> itemDto=getItemMap(keyDto);
		for(Map<?,?> dataMap:listMap){
			convertItem(dataMap,keyDto,itemDto);
		}

	}
	/**
	 *
	 * 简要说明：转换map字典
	 * 编写者：陈骑元
	 * 创建时间：2018年12月18日 下午9:47:55
	 * @param 说明
	 * @return 说明
	 */
    public static void convertMapItem(Map<?, ?> dataMap,Dto keyDto){
		Map<String,String> itemDto=getItemMap(keyDto);
		convertItem(dataMap,keyDto,itemDto);
	}
    /**
     *
     * 简要说明：转换字典
     * 编写者：陈骑元
     * 创建时间：2018年12月18日 下午10:04:19
     * @param 说明
     * @return 说明
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private  static void convertItem(Map dataMap,Dto keyDto,Map<String,String> itemDto){
    	Set<String> keySet=keyDto.keySet();
		for(String key:keySet){
			if(WebplusUtil.isNotEmpty(key)){
				String dictValue="";
				Object obj=dataMap.get(key);
				if(WebplusUtil.isNotEmpty(obj)){
					String value=obj.toString();
					 String typeCode=keyDto.getString(key);
					 if(WebplusCons.JDDMALL.equals(typeCode)||WebplusCons.XZDZALL.equals(typeCode)) {
						dictValue=getHashItemName(typeCode,value,itemDto);
					 }else {
						 dictValue=getItemName(typeCode,value,itemDto);
					 }

				 }
				 dataMap.put(key+"_dict", dictValue);
			}

		}


    }
    /**
     *
     * 简要说明：获取哈希存储的值
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2020年4月22日 下午3:00:32
     * @param 说明
     * @return 说明
     */
    public static String getHashItemName(String typeCode,String itemCode,Map<String,String> itemDto) {
    	 String itemName="";
    	if(WebplusUtil.isNotAnyEmpty(typeCode,itemCode)) {
    		 String[] codeArray=itemCode.split(",");
  		   for(String code:codeArray){
  			   String key=typeCode+"_"+code;

  			   if(!itemDto.containsKey(key)){
  				   String hashItemName=getHashItemName(typeCode,code);
  				   itemDto.put(key, hashItemName);
  			   }
  			  String value=itemDto.get(key);
  			  if(WebplusUtil.isEmpty(value)) {
  				  value=itemCode;
  			  }
  			  itemName+=value+",";
  		   }
    	}
       if(WebplusUtil.isNotEmpty(itemName)){
  		   itemName=itemName.substring(0, itemName.length()-1);
  	   }
  	   return itemName;
    }
    /**
     *
     * 简要说明：返回字典
     * 编写者：陈骑元
     * 创建时间：2018年12月18日 下午9:52:50
     * @param 说明
     * @return 说明
     */
    private static Map<String,String> getItemMap(Dto keyDto){
    	Map<String,String> dictMap=new HashMap<String,String>();
    	if(WebplusUtil.isNotEmpty(keyDto)){
    		Set<String> keySet=keyDto.keySet();
    		for(String typeCode:keySet){

    			if(WebplusUtil.isNotEmpty(typeCode)){
    				if(!(WebplusCons.JDDMALL.equals(typeCode)||WebplusCons.XZDZALL.equals(typeCode))) {
    					dictMap.putAll(getItemMap(typeCode));
    				}


    			}

    		}
    	}
    	return dictMap;

    }

	/**
	 *
	 * 简要说明：转换字典
	 * 编写者：陈骑元
	 * 创建时间：2018年12月17日 下午7:02:43
	 * @param 说明
	 * @return 说明
	 */
	@SuppressWarnings("rawtypes")
	public static void convertItem(Object  bean,Map<String,String> itemDto){
	        try {
				BaseModel ext = (BaseModel) bean;
				Class c = ext.getClass();
				do {
					Field[] fields = c.getDeclaredFields();
					for (Field field : fields) {
						if (field.isAnnotationPresent(ItemTag.class)) {
							field.setAccessible(true);
							ItemTag dictTag = field.getAnnotation(ItemTag.class);
							String typeCode = dictTag.type();
							if (WebplusUtil.isNotEmpty(dictTag)) {

								String display = "";
								Object fieldValue = field.get(ext);
								if (WebplusUtil.isNotEmpty(fieldValue)) {
									  String redisType=dictTag.redisType();
		   	                           if(WebplusCons.REDIS_TYPE_HASH.equals(redisType)) {
		   	                        	display=getHashItemName(typeCode,fieldValue.toString(),itemDto);
		   	                           }else {
		   	                        	display=getItemName(typeCode,fieldValue.toString(),itemDto);
		   	                           }
								}

								ext.set(field.getName() + "_" + dictTag.suffix(), display);

							}

						}
						if (field.isAnnotationPresent(JsonFormat.class)) {
							field.setAccessible(true);
							JsonFormat jsonFormat = field.getAnnotation(JsonFormat.class);
							String pattern = jsonFormat.pattern();
							if (WebplusUtil.isEmpty(pattern)) {
								pattern = WebplusCons.DATETIME;
							}
							Object fieldValue = field.get(ext);
							String dateValue="";
							if(WebplusUtil.isNotEmpty(fieldValue)) {
								if(fieldValue  instanceof Date) {
									dateValue=WebplusUtil.date2String((Date)fieldValue, pattern);
								}
								
							}
							ext.set(field.getName()+"_format", dateValue);
						}
					}
					c = c.getSuperclass();
				} while (c != BaseModel.class);
			} catch (Exception e) {
				// TODO: handle exception
			}
	        

	}

	/**
	 *
	 * 简要说明：获取字典DICT集合Dto 使用键值typeCode_itemCode 组成
	 * 编写者：陈骑元
	 * 创建时间：2018年12月17日 下午7:43:06
	 * @param 说明
	 * @return 说明
	 */
	public static Map<String,String> getItemMap(String typeCode){
		Map<String,String> dictMap=new HashMap<String,String>();
		List<Item> itemList=getItemList(typeCode);
		for(Item item:itemList){
			String key=item.getTypeCode()+"_"+item.getItemCode();
			dictMap.put(key, item.getItemName());
		}


		return dictMap;
	}
	/**
	 *
	 * 简要说明：解析字典
	 * 编写者：陈骑元
	 * 创建时间：2018年12月17日 下午7:53:36
	 * @param 说明
	 * @return 说明
	 */
   private static String getItemName(String typeCode,String itemCode,Map<String,String> itemMap){
	   String itemName="";
	   if(WebplusUtil.isNotEmpty(itemCode)){
		   String[] codeArray=itemCode.split(",");
		   for(String code:codeArray){
			   String key= typeCode+"_"+code;

			   if(itemMap.containsKey(key)){
				   itemName+=itemMap.get(key)+",";
			   }else{
				   itemName+=itemCode+",";
			   }
		   }
	   }
	   if(WebplusUtil.isNotEmpty(itemName)){
		   itemName=itemName.substring(0, itemName.length()-1);
	   }
	   return itemName;
   }
	/**
	 *
	 * 简要说明：获取转换好的字典数据
	 * 编写者：陈骑元
	 * 创建时间：2018年12月17日 下午7:32:59
	 * @param 说明
	 * @return 说明
	 */
	@SuppressWarnings("rawtypes")
	private static Map<String,String> getConvertItemData(Object  bean){
		Map<String,String>  dictMap=new HashMap<String,String>();
		if (WebplusUtil.isEmpty(bean)) {
            return dictMap;
        }

        if(!(bean instanceof BaseModel)){
        	return dictMap;
        }

        BaseModel ext  = (BaseModel)bean;
        Class c = ext.getClass();
        do {
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(ItemTag.class)) {
                    field.setAccessible(true);
                    try {
						ItemTag dictTag = field.getAnnotation(ItemTag.class);
						String redisType=dictTag.redisType();
						if(WebplusCons.REDIS_TYPE_LIST.equals(redisType)) {
							String typeCode=dictTag.type();
							dictMap.putAll(getItemMap(typeCode));
						}

					} catch(Exception e) {
						// TODO Auto-generated catch block
					   logger.error("获取实体bean的值出错："+e);
					}

                }
            }
         c = c.getSuperclass();
        }while(c!=BaseModel.class);

        return dictMap;
	}

	/**
	 *
	 * 简要说明：获取字典值存数据库
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年4月22日 上午11:37:01
	 * @param 说明
	 * @return 说明
	 */
	public static void setMapDict(Map<String,String>  dictMap,ItemTag dictTag,Object fieldValue) {
		String typeCode=dictTag.type();
		String redisType=dictTag.redisType();
		setMapDict(dictMap,typeCode,redisType,fieldValue);
	}
	/**
	 *
	 * 简要说明：根据不同存储类型获取
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年4月22日 上午11:26:59
	 * @param 说明
	 * @return 说明
	 */
	public static void setMapDict(Map<String,String>  dictMap,String typeCode,String redisType,Object fieldValue) {
        if(WebplusUtil.isNotEmpty(typeCode)) {
		    if(WebplusCons.REDIS_TYPE_STRING.equals(redisType)) {

		    }else if(WebplusCons.REDIS_TYPE_HASH.equals(redisType)) {
		    	if(WebplusUtil.isNotEmpty(fieldValue)) {
		    		String field=fieldValue.toString();
		    		String key=typeCode+"_"+field;
		    		if(!dictMap.containsKey(key)) {
		    			String value=getHashItemName(typeCode,field);
		    			if(WebplusUtil.isNotEmpty(value)) {
		    				dictMap.put(key, value);
		    			}

		    		}
		    	}
		    }else {
		    	dictMap.putAll(getItemMap(typeCode));
		    }
		}
	}

	/**
	 *
	 * 简要说明：获取存储hash 的值
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年4月22日 上午11:40:46
	 * @param 说明
	 * @return 说明
	 */
	public static String  getHashItemName(String key,String field){
		key=WebplusCons.CACHE_PREFIX_HEAD+key	;
		String itemStr=hget(key,field);
		if(WebplusUtil.isNotEmpty(itemStr)) {
			Item item=WebplusJson.fromJson(itemStr, Item.class);
			if(WebplusUtil.isNotEmpty(item)) {
				return item.getItemName();
			}
		}
		return "";
	}
	/**
	 *
	 * 简要说明： 获取哈希的字典选项
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年6月15日 下午5:51:00
	 * @param 说明
	 * @return 说明
	 */
    public static Item getHashItem(String key,String field){
		key=WebplusCons.CACHE_PREFIX_HEAD+key	;
		String itemStr=hget(key,field);
		if(WebplusUtil.isNotEmpty(itemStr)) {
			Item item=WebplusJson.fromJson(itemStr, Item.class);
			return item;
		}
		return null;
	}

    /**
     *
     * 简要说明：创建token，并返回token字符串
     * 编写者：陈骑元
     * 创建时间：2018年12月22日 下午1:58:54
     * @param 说明
     *     tokenJson存储token数据的json数据串
     * @return 说明
     */
    public static String createToken(String token, String tokenJson) {
    	
    	String tokenKey = WebplusCons.CACHE_PREFIX.TOKEN + token;
    	webplusJedis.setString(tokenKey, tokenJson, WebplusCons.DEFAULT_TIMECOUT);
    	return token;
    }
	/**
	 *
	 * 简要说明：创建token，并返回token字符串
	 * 编写者：陈骑元
	 * 创建时间：2018年12月22日 下午1:58:54
	 * @param 说明
	 *     tokenJson存储token数据的json数据串
	 * @return 说明
	 */
	public static void refreshToken(UserToken userToken) {
        userToken.setRefreshTime(WebplusUtil.getDateTime());
		String tokenKey = WebplusCons.CACHE_PREFIX.TOKEN + userToken.getToken();
		String tokenJson=WebplusJson.toJson(userToken);
		webplusJedis.setString(tokenKey, tokenJson, WebplusCons.DEFAULT_TIMECOUT);
		
	}

	/**
	 *
	 * 简要说明：创建token，并返回token字符串 编写者：陈骑元 创建时间：2018年12月22日 下午1:58:54
	 *
	 * @param 说明
	 *            tokenJson存储token数据的json数据串
	 * @return 说明
	 */
	public static String createToken(UserToken userToken) {
		
		String token = userToken.getToken();
		if(WebplusUtil.isEmpty(token)) {
			token=WebplusUtil.uuid();
		}
		userToken.setToken(token);
		String tokenJson = WebplusJson.toJson(userToken);
		return createToken(token, tokenJson);
	}

	/**
	 *
	 * 简要说明：根据token 获取token 存储值 编写者：陈骑元 创建时间：2018年12月22日 下午1:58:54
	 *
	 * @param
	 * @return 说明
	 */
	public static String getToken(String token) {
		String tokenKey = WebplusCons.CACHE_PREFIX.TOKEN + token;
		String jsonStr = webplusJedis.getString(tokenKey);
		logger.info("当前token：" + token + ",返回值：" + jsonStr);
		return jsonStr;
	}
	 /**
     *
     * 简要说明：获取token用户
     * 编写者：陈骑元
     * 创建时间：2018年12月22日 下午2:46:39
     * @param 说明
     * @return 说明
     */
    public  static UserToken getUserToken(Dto pDto){
    	String token=pDto.getString(WebplusCons.TOKEN_PARAM);
    	return getUserToken(token);
    }
	/**
	 *
	 * 简要说明：创建token，并返回token字符串 编写者：陈骑元 创建时间：2018年12月22日 下午1:58:54
	 *
	 * @param 说明
	 *            tokenJson存储token数据的json数据串
	 * @return 说明
	 */
	public static UserToken getUserToken(String token) {
		UserToken userToken = null;
		String tokenJson = getToken(token);

		if (WebplusUtil.isNotEmpty(tokenJson)) {
			userToken = WebplusJson.fromJson(tokenJson, UserToken.class);

		} else {
			logger.error("当前token：" + token + ",无法获取用户信息");
		}
		return userToken;
	}

	/**
	 *
	 * 简要说明：刷新token的值 编写者：陈骑元 创建时间：2018年12月22日 下午2:32:48
	 *
	 * @param 说明
	 * @return 说明
	 */
	public static boolean checkAndRefreshToken(String token) {
		boolean result = false;

		UserToken userToken=getUserToken(token);
		if (WebplusUtil.isNotEmpty(userToken)) {
            Date now=WebplusUtil.getDateTime();
            int plusMinute= WebplusUtil.minutesBetween( userToken.getRefreshTime(),now);
            if(plusMinute>=20) { //当前时间大于刷新时间60分钟就失效
            	refreshToken(userToken);
            }
			
			result = true;
		}

		return result;
	}

	/**
	 *
	 * 简要说明：清空token 编写者：陈骑元 创建时间：2018年12月24日 下午9:22:15
	 *
	 * @param 说明
	 * @return 说明
	 */
	public static void removeToken(String token) {
		String tokenKey = WebplusCons.CACHE_PREFIX.TOKEN + token;
		webplusJedis.delString(tokenKey);
	}
	 /**
     *
     * 简要说明：获取数据范围
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2020年5月27日 下午5:15:50
     * @param 说明
     * @return 说明
     */
    public static List<String> getDataRange(Dto pDto){
    	List<String> dataRangeList=Lists.newArrayList();
    	UserToken userToken=getUserToken(pDto);
    	if(WebplusUtil.isNotEmpty(userToken)) {
    		String dyfw=userToken.getDyfw();
    		String dataRange=userToken.getDataRange();

    		if(WebplusUtil.isNotEmpty(dataRange) && !"ALL".equals(dataRange)) {

    			dataRangeList=WebplusFormater.separatStringToList(dataRange);
    		}else {
    			dataRangeList.add(dyfw);
    		}
    	}
    	 return dataRangeList;
    }
	/**
	 *
	 * 简要说明：获取token用户
	 * 编写者：陈骑元
	 * 创建时间：2018年12月22日 下午2:46:39
	 * @param 说明
	 * @return 说明
	 */
	public static UserToken getUserToken(HttpServletRequest httpRequest){
		String token=httpRequest.getParameter(WebplusCons.TOKEN_PARAM);
		return getUserToken(token);
	}

	/**
	 *
	 * 简要说明：获取用户编号
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年4月14日 下午7:21:23
	 * @param 说明
	 * @return 说明
	 */
	public static String getUserId(HttpServletRequest httpRequest) {
		UserToken userToken=getUserToken(httpRequest);
		if(WebplusUtil.isNotEmpty(userToken)) {

			return userToken.getUserId();
		}
		return "";
	}
    /**
  
	/**
	 *
	 * 简要说明：创建序列号
	 * 编写者：陈骑元
	 * 创建时间：2019年8月15日 下午4:18:02
	 * @param 说明
	 * @return 说明
	 */
	public static String createSeqNum() {

		String middle = WebplusUtil.getDateStr("yyMMdd");
		try {

			String redisKey = WebplusCons.CACHE_PREFIX.SEQ;
			Long incr = webplusJedis.incr(redisKey);
			if (incr >= 9999) {
				webplusJedis.getSet(redisKey, "0");
			}
			String end = StringUtils.leftPad(incr + "", 4, "0");
			String seqNumStr = middle + end;
			return seqNumStr;
		} catch (Exception e) {

			return WebplusUtil.getDateStr("yyMMddHHmm");

		}

	}
     
	
	/**
	*
	* 简要说明：获取字符串
	* 编写者：陈骑元
	* 创建时间：2019年8月4日 下午2:13:48
	* @param 说明
	* @return 说明
	*/
	public static String getString(String key) {

		return webplusJedis.getString(key);
	}

	/**
	 *
	 * 简要说明：
	 * 编写者：陈骑元
	 * 创建时间：2019年8月4日 下午2:14:23
	 * @param 说明
	 * @return 说明
	 */
	public static void setString(String key, String value, int timeout) {

		webplusJedis.setString(key, value, timeout);
	}

	/**
	 * 检查键是否存在
	 * @param key
	 * @return
	 */
	public static boolean exists(String key) {

		return webplusJedis.exists(key);
	}

	 /**
     *
     * 简要说明：hash赋值
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2020年4月22日 上午11:00:24
     * @param 说明
     * @return 说明
     */
    public static void  hset(String key,String field,String value) {
    	webplusJedis.hset(key, field, value);
    }
    
    /**
     * 
     * 简要说明：hash删除
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2021年1月8日 下午2:28:53 
     * @param 说明
     * @return 说明
     */
    public static void   hdel(String key,String... fields) {
    	
    	webplusJedis.hdel(key, fields);
    }
    /**
     *
     * 简要说明：设置hash缓存
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2020年4月22日 上午10:36:28
     * @param 说明
     * @return 说明
     */
    public static void hmset(String key,Map<String, String> hash) {

    	webplusJedis.hmset(key, hash);
    }
    /**
     *
     * 简要说明：hash取值
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2020年4月22日 上午10:37:02
     * @param 说明
     * @return 说明
     */
    public static  String hget(String key,String field) {

    	return webplusJedis.hget(key, field);
    }

    /**
	 * 随机设置lrange
	 *
	 * @param key
	 */
	public static List<String> lrange(String key,long start,  long end) {

		return webplusJedis.lrange(key, start, end);
	}

	/**
	 * 随机设置lrange
	 *
	 * @param key
	 */
	public static void rpush(String key,List<?> dataList) {

		webplusJedis.rpush(key, dataList);
	}
	/**
	 *
	 * 简要说明：通过键正则删除
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年4月27日 下午2:21:50
	 * @param 说明
	 * @return 说明
	 */
	public static  void delPattern(String keyPattern) {
		Set<String> keySet=webplusJedis.keys(keyPattern+"*");
		for(String key:keySet){
			webplusJedis.delString(key);
		}
	}

	/**
	 *
	 * 简要说明：删除键
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年4月14日 下午6:46:06
	 * @param 说明
	 * @return 说明
	 */
	public static void delString(String key) {


		webplusJedis.delString(key);
	}
	/**
	 * 
	 * 简要说明：返回包含的字典
	 * 编写者：陈骑元
	 * 创建时间：2019年1月25日 下午2:44:57
	 * @param 说明
	 * @return 说明
	 */
	public static List<Item> getContainItemList(List<Item> itemList,List<String> containCodeList) {
       
		if(WebplusUtil.isNotEmpty(containCodeList)) {
			 List<Item> itemNewList=Lists.newArrayList();
		        for(Item item:itemList){
		        	if(WebplusUtil.contains(item.getItemCode(), containCodeList)){
		        		itemNewList.add(item);
		        	}
		        }
				return itemNewList;
		}
       return itemList;

	}
	/**
	 * 
	 * 简要说明：返回包含的字典
	 * 编写者：陈骑元
	 * 创建时间：2019年1月25日 下午2:44:57
	 * @param 说明
	 * @return 说明
	 */
	public static List<Item> getContainItemList(String typeCode,List<String> containCodeList) {
        List<Item> itemList=getItemList(typeCode, "");
       
		return getContainItemList(itemList,containCodeList);

	}
	
	/**
	 * 
	 * 简要说明：设置数据范围
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2021年9月17日 下午5:50:44 
	 * @param 说明
	 * @return 说明
	 */
	public static void setDataRange(Dto pDto) {
		UserToken userToken=getUserToken(pDto);
    	if(WebplusUtil.isNotEmpty(userToken)) {
    		String dyfw=userToken.getDyfw();
    		String dymc=userToken.getDymc();
    		String dataRange=userToken.getDataRange();
    		List<String> dataRangeList=Lists.newArrayList();
    		String whetherRange=WebplusCons.WHETHER_NO;  //是否有数据范围
    		if(WebplusUtil.isNotEmpty(dataRange) && !WebplusCons.ALL.equals(dataRange)) {
    			whetherRange=WebplusCons.WHETHER_YES;  //是否有数据范围
    			List<String> rangeList=WebplusFormater.separatStringToList(dataRange);
    			dataRangeList.addAll(rangeList);
    		}else {
    			dataRangeList.add(dyfw);
    		}
    		String level=dymc;
    		if(WebplusCons.WHETHER_YES.equals(whetherRange)) {
    			if(WebplusCons.DYMC_XZQ.equals(dymc)) {
    				level=WebplusCons.DYMC_JZ;
    			}else if(WebplusCons.DYMC_JWH.equals(dymc)) {
    				level=WebplusCons.DYMC_JWH;
    			}
    		}
    		 if(!WebplusCons.DYMC_SHIJB.equals(level)){
            	 pDto.put("level", level);
                 pDto.put("dataRangeList",dataRangeList);
            }
    	}
	}
	/**
	 * 
	 * 简要说明：返回包含的字典
	 * 编写者：陈骑元
	 * 创建时间：2019年1月25日 下午2:44:57
	 * @param 说明
	 * @return 说明
	 */
	public static List<Item> getContainItemList(String typeCode,String containCode) {
        List<String> containCodeList=WebplusFormater.separatStringToList(containCode);
		return getContainItemList(typeCode,containCodeList);

	}

    /**
     * <p>
     * Set the string value as value of the key. The string can't be longer than 1073741824 bytes (1 GB). Specified by: set(...) in BinaryJedisCommands
     * </p>
     * 
     * @author 黄健达 (lsr@sina.com)
     * @date: Sep 9, 2021 3:21:03 PM
     * @param key
     * @param ttl
     * @param value
     * @return String
     */
    public static String setnx(String key, long ttl, String value) {
        return webplusJedis.setnx(key, ttl, value);
    }
    
    /**
     * SETNX works exactly like {@link #set(String, String) SET} with the only difference that if the key already exists no operation is performed. SETNX actually means "SET if Not eXists".
     * <p>
     * Time complexity: O(1)
     * 
     * @param key
     * @param value
     * @return Integer reply, specifically: 1 if the key was set 0 if the key was not set
     */
    public static String setnx(String key, String value) {
        Long setnx = webplusJedis.setnx(key, value);
        return setnx.toString();
    }
    public static long ttl(String key) {
        return webplusJedis.ttl(key);
    }
    public static void expire(String value, int i) {
        webplusJedis.expire(value, i);
    }
	
    /**
     * <p>简单缓存，被缓存对象必需实现Serializable接口</p> 
     * @author 黄健达 (lsr@sina.com)
     * @date: Sep 18, 2021 10:33:19 AM 
     * @param <T> 
     * @param sup  缓存查询
     * @param ttl 有效时间10S以上
     * @param key REDIS主键
     * @return T 
    */
    public static <T> T simpleCache(Supplier<Serializable> sup, long ttl, String key) {
        Assert.isTrue(StringUtils.isNotBlank(key), WebplusCons.ERR_MSG_01);
        String redisKey = WebplusCons.APP_ID_KEY + WebplusCons.COLON + DigestUtils.md5Hex(key);
        String content = webplusJedis.getString(redisKey);
        if (StringUtils.isNotBlank(content)) {
            if (WebplusCons.ANY_MORE.equals(content)) {
                return null;
            }
            return SerializationUtils.deserialize(Base64.decodeBase64(content));
        }
        Range<Long> range = Range.between(10L, Long.MAX_VALUE);
        ttl = range.contains(ttl) ? ttl : 10L;
        Serializable serializable = sup.get();
        if (Objects.nonNull(serializable)) {
            content = Base64.encodeBase64String(SerializationUtils.serialize(serializable));
        } else {
            content = WebplusCons.ANY_MORE;
            ttl = 10L;
        }
        WebplusCache.setnx(redisKey, ttl, content);
        return (T) serializable;
    }
    
    /**
     * <p>
     * Increments the number stored at key by one. If the key does not exist, it is set to 0 before performing the operation. An error is returned if the key contains a value of the wrong type or
     * contains a string that can not be represented as integer. This operation is limited to 64 bit signed integers. Note: this is a string operation because Redis does not have a dedicated
     * </p>
     * 
     * @author 黄健达 (lsr@sina.com)
     * @date: Sep 18, 2021 4:39:51 PM
     * @param redisKey
     * @return Long
     */
    public static Long incr(String redisKey) {
        return webplusJedis.incr(redisKey);
    }
    public static void setIncr(String redisKey, String valueOf, int i) {
        // TODO Auto-generated method stub
        
    }
    
}
