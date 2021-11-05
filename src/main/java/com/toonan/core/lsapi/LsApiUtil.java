package com.toonan.core.lsapi;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import com.google.common.collect.Lists;
import com.toonan.core.cache.WebplusCache;
import com.toonan.core.constant.LsCons;
import com.toonan.core.constant.LsUri;
import com.toonan.core.constant.WebplusCons;
import com.toonan.core.matatype.Dto;
import com.toonan.core.matatype.Dtos;
import com.toonan.core.util.WebplusHttp;
import com.toonan.core.util.WebplusJson;
import com.toonan.core.util.WebplusUtil;
import com.toonan.core.vo.FileVo;
import com.toonan.core.vo.Item;
import com.toonan.core.vo.PageDto;
import com.toonan.core.vo.R;
import com.toonan.core.vo.ResultVo;




/**
 * 
 * 类名:com.toonan.system.util.LsApiUtil
 * 描述:来穗api调用
 * 编写者:陈骑元
 * 创建时间:2018年12月18日 上午8:53:45
 * 修改说明:
 */
public class LsApiUtil {
	
	
	private static Logger logger = LoggerFactory.getLogger(LsApiUtil.class);
	/**
	 * 来穗url
	 */
	public static String lsUrl="";
	/**
	 * 来穗cdkey
	 */
	public static String lsCdkey="";
	
	
	/**
	 * 
	 * 简要说明：通过行政区查询缓存接口
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年10月23日 上午10:59:31 
	 * @param 说明
	 * @return 说明
	 */
	public static List<Item> listJz(String xzq){
		if(WebplusUtil.isEmpty(xzq)) {
			xzq=WebplusCache.getParamValue(WebplusCons.XZQ_CODE_KEY);
		}
		
		List<Item> itemList=WebplusCache.getItemList(xzq);
		if(WebplusUtil.isEmpty(itemList)) {
			itemList=queryJzListByLs(xzq,"");
			if(WebplusUtil.isNotEmpty(itemList)) {
				String key=WebplusCons.CACHE_PREFIX.DICT+xzq;
				WebplusCache.rpush(key, itemList);
			}
		}
	    return itemList;
	}
	/**
	 * 
	 * 简要说明：通过行政区查询缓存接口
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年10月23日 上午10:59:31 
	 * @param 说明
	 * @return 说明
	 */
	public static List<Item> listJwh(String jz){
		if(WebplusUtil.isEmpty(jz)) {
			
			return Lists.newArrayList();
		}
		
		List<Item> itemList=WebplusCache.getItemList(jz);
		if(WebplusUtil.isEmpty(itemList)) {
			itemList=queryJwhListByLs("",jz,"");
			if(WebplusUtil.isNotEmpty(itemList)) {
				String key=WebplusCons.CACHE_PREFIX.DICT+jz;
				WebplusCache.rpush(key, itemList);
			}
		}
		return itemList;
	}
	/**
	 * 
	 * 简要说明：通过行政区查询缓存接口
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年10月23日 上午10:59:31 
	 * @param 说明
	 * @return 说明
	 */
	public static List<Item> listPcs(String jz){
		if(WebplusUtil.isEmpty(jz)) {
			
			return Lists.newArrayList();
		}
		String typeCode="pcs"+jz;
		List<Item> itemList=WebplusCache.getItemList(typeCode);
		if(WebplusUtil.isEmpty(itemList)) {
			itemList=queryPcsListByLs("",jz,"");
			if(WebplusUtil.isNotEmpty(itemList)) {
				String key=WebplusCons.CACHE_PREFIX.DICT+typeCode;
				WebplusCache.rpush(key, itemList);
			}
		}
		return itemList;
	}

	/**
	 * 
	 * 简要说明：通过行政区查询街镇代码数据
	 * 编写者：陈骑元
	 * 创建时间：2017年8月7日 上午10:12:57
	 * @param
	 * @return 说明
	 */
	public static List<Item> queryJzListByLs(String xzq, String itemName){
		
		return queryDmListByLS(xzq,xzq,itemName, LsCons.QUERY_MODE_JZ);
	}
	/**
	 * 
	 * 简要说明：通过街镇查询居委会代码数据
	 * 编写者：陈骑元
	 * 创建时间：2017年8月7日 上午10:12:57
	 * @param
	 * @return 说明
	 */
	public static List<Item> queryJwhListByLs(String xzq,String jz,String itemName){
		
		return queryDmListByLS(xzq,jz, itemName,LsCons.QUERY_MODE_JWH);
	}
	/**
	 * 
	 * 简要说明：通过街镇查询派出所数据
	 * 编写者：陈骑元
	 * 创建时间：2017年8月7日 上午10:12:57
	 * @param
	 * @return 说明
	 */
	public static List<Item> queryPcsListByLs(String xzq,String jz,String itemName){
		
		return queryDmListByLS(xzq,jz, itemName,LsCons.QUERY_MODE_PCS);
	}
	/**
	 * 
	 * 简要说明：通过行政区查询街镇代码数据
	 * 编写者：陈骑元
	 * 创建时间：2017年8月7日 上午10:12:57
	 * @param
	 * @return 说明
	 */
	public static List<Item> queryJddmListByLs(String xzq,String jz,String itemName){
		
		return queryDmListByLS(xzq,jz, itemName,LsCons.QUERY_MODE_JDDM);
	}
	/**
	 * 
	 * 简要说明：通过行政区查询街镇代码数据
	 * 编写者：陈骑元
	 * 创建时间：2017年8月7日 上午10:12:57
	 * @param
	 * @return 说明
	 */
	public  PageDto queryJzPageByLs(String xzq,String itemName,String currentPage, String pageSize){
		
		return queryDmPageByLS(xzq,xzq,itemName,currentPage,pageSize, LsCons.QUERY_MODE_JZ);
	}
	/**
	 * 
	 * 简要说明：通过街镇查询居委会代码数据
	 * 编写者：陈骑元
	 * 创建时间：2017年8月7日 上午10:12:57
	 * @param
	 * @return 说明
	 */
	public static PageDto queryJwhPageByLs(String xzq,String jz,String itemName,String currentPage, String pageSize){
		
		return queryDmPageByLS(xzq,jz, itemName,currentPage,pageSize,LsCons.QUERY_MODE_JWH);
	}
	/**
	 * 
	 * 简要说明：通过街镇查询派出所数据
	 * 编写者：陈骑元
	 * 创建时间：2017年8月7日 上午10:12:57
	 * @param
	 * @return 说明
	 */
	public PageDto queryPcsPageByLs(String xzq, String jz, String itemName, String currentPage, String pageSize){
		
		return queryDmPageByLS(xzq,jz, itemName,currentPage,pageSize,LsCons.QUERY_MODE_PCS);
	}
	/**
	 * 
	 * 简要说明：通过行政区查询街镇代码数据
	 * 编写者：陈骑元
	 * 创建时间：2017年8月7日 上午10:12:57
	 * @param
	 * @return 说明
	 */
	public static  PageDto queryJddmPageByLs(String xzq,String jz,String itemName,String currentPage, String pageSize){
		
		return queryDmPageByLS(xzq,jz, itemName,currentPage,pageSize,LsCons.QUERY_MODE_JDDM);
	}
	/**
	 * 
	 * 简要说明：从来穗查询获取街镇数据
	 * 编写者：陈骑元
	 * 创建时间：2017年8月3日 下午2:06:51
	 * @param
	 * @return 说明
	 */
	public static List<Item> queryDmListByLS( String xzq,String typeCode,String itemName,String queryMode){
		String reqUrl="";
		String msgTip="";
		Dto paramDto= Dtos.newDto();
		if(LsCons.QUERY_MODE_JZ.equals(queryMode)){
			if(WebplusUtil.isEmpty(typeCode)){
				 typeCode=xzq;
			};
			reqUrl=LsUri.LS_QUERY_JZ_URL.toString();
			msgTip="街镇代码";
		}else if(LsCons.QUERY_MODE_JWH.equals(queryMode)){
			reqUrl=LsUri.LS_QUERY_JWH_URL.toString();
			msgTip="居委会代码";
		}else if(LsCons.QUERY_MODE_PCS.equals(queryMode)){
			reqUrl=LsUri.LS_QUERY_PCS_URL.toString();
			msgTip="派出所代码";
		}else{
			reqUrl=LsUri.LS_QUERY_JDDM_URL.toString();
			msgTip="街路巷代码";
		}
		paramDto.put("typeCode", typeCode);
		paramDto.put("xzq", xzq);
		paramDto.put("jz", typeCode);
		paramDto.put("itemName", itemName);
		String jsonString=doPostLs(reqUrl,paramDto);
		logger.info("从来穗接口获取的代码["+typeCode+"]的"+msgTip+"数据:"+jsonString);
		List<Dto> dataList=fromJsonToList(jsonString);
		List<Item> itemList=Lists.newArrayList();
		for(Dto dataDto:dataList){
			Item item=new Item();
			WebplusUtil.copyProperties(dataDto, item);
			itemList.add(item);
		}
		return itemList;
		
	}
	/**
	 * 
	 * 简要说明：街路巷分页
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年10月23日 下午2:21:56 
	 * @param 说明
	 * @return 说明
	 */
	public static PageDto listJddmPage(String xzq,String jz,String jwh,String itemName,String currentPage, String pageSize) {
		Dto pDto=Dtos.newDto();
		pDto.put("xzq", xzq);
		pDto.put("jz", jz);
		pDto.put("jwh", jwh);
		pDto.put("itemName", itemName);
		pDto.put("currentPage",currentPage);
		pDto.put("pageSize",pageSize);
		return listJddmPage(pDto);
	}
	
	/**
	 * 
	 * 简要说明：街路巷分页
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2020年10月23日 下午2:21:56 
	 * @param 说明
	 * @return 说明
	 */
	public static PageDto listJddmPage(Dto pDto) {
		
		String jsonString=doPostLs(LsUri.LS_QUERY_JDDM_PAGE_URL.toString(),pDto);
		return fromJsonToPageDto(jsonString);
	}
	/**
	 * 
	 * 简要说明：从来穗查询获取街镇数据
	 * 编写者：陈骑元
	 * 创建时间：2017年8月3日 下午2:06:51
	 * @param
	 * @return 说明
	 */
	public static PageDto queryDmPageByLS(String xzq,String typeCode,String itemName,String currentPage, String pageSize,String queryMode){
		String queryDmUrl="";
		Dto paramDto=Dtos.newDto();
		paramDto.put("typeCode", typeCode);
		if(LsCons.QUERY_MODE_JZ.equals(queryMode)){
			queryDmUrl=LsUri.LS_QUERY_JZ_PAGE_URL.toString();
		}else if(LsCons.QUERY_MODE_JWH.equals(queryMode)){
			queryDmUrl=LsUri.LS_QUERY_JWH_PAGE_URL.toString();
		}else if(LsCons.QUERY_MODE_PCS.equals(queryMode)){
			queryDmUrl=LsUri.LS_QUERY_PCS_PAGE_URL.toString();
		}else{
			queryDmUrl=LsUri.LS_QUERY_JDDM_PAGE_URL.toString();
		}
		
		String reqUrl=queryDmUrl;
		paramDto.put("xzq", xzq);
		paramDto.put("jz", typeCode);
		paramDto.put("itemName", itemName);
		paramDto.put("currentPage",currentPage);
		paramDto.put("pageSize",pageSize);
		String jsonString=doPostLs(reqUrl,paramDto);
		return fromJsonToPageDto(jsonString);
		
	}
	/**
	 * 
	 * 简要说明：查询来穗数据库字典
	 * 编写者：陈骑元
	 * 创建时间：2018年1月2日 下午12:57:59
	 * @param
	 * @return 说明
	 */
	public static List<Item> queryDictListByLS(String typeCode,String itemName){
		List<Item> itemList=Lists.newArrayList();
		String jsonString=queryDictionaryListByLS(typeCode,itemName);
		List<Dto> dataList=fromJsonToList(jsonString);
		for(Dto dataDto:dataList){
			Item item=new Item();
			WebplusUtil.copyProperties(dataDto, item);
			itemList.add(item);
		}
		return itemList;
		
	}
	
	/**
	 * 
	 * 简要说明：查询来穗数据库字典
	 * 编写者：陈骑元
	 * 创建时间：2018年1月2日 下午12:57:59
	 * @param
	 * @return 说明
	 */
	public static String queryDictionaryListByLS(String typeCode,String itemName){
		Dto paramDto=Dtos.newDto();
		paramDto.put("typeCode", typeCode);
		paramDto.put("itemName", itemName);
		String reqUrl=LsUri.LS_QUERY_DIC_LIST_URL.toString();
		return doPostLs(reqUrl,paramDto);
		
	}
	
	/**
	 * 
	 * 简要说明：从来穗查询获取街镇数据
	 * 编写者：陈骑元
	 * 创建时间：2017年8月3日 下午2:06:51
	 * @param
	 * @return 说明
	 */
	public static String queryDmPageByLS(String typeCode,String itemName,String currentPage, String pageSize,String queryMode){
			String queryDmUrl="";
		String msgTip="";
		Dto paramDto=Dtos.newDto();
		paramDto.put("typeCode", typeCode);
		if(LsCons.QUERY_MODE_JZ.equals(queryMode)){
			queryDmUrl=LsUri.LS_QUERY_JZ_PAGE_URL.toString();
			msgTip="街镇代码";
			paramDto.put("xzq", typeCode);
		}else if(LsCons.QUERY_MODE_JWH.equals(queryMode)){
			queryDmUrl=LsUri.LS_QUERY_JWH_PAGE_URL.toString();
			msgTip="居委会代码";
		}else if(LsCons.QUERY_MODE_PCS.equals(queryMode)){
			queryDmUrl=LsUri.LS_QUERY_PCS_PAGE_URL.toString();
			msgTip="派出所代码";
		}else{
			queryDmUrl=LsUri.LS_QUERY_JDDM_PAGE_URL.toString();
			msgTip="街路巷代码";
		}
		
		paramDto.put("jz", typeCode);
		paramDto.put("itemName", itemName);
		paramDto.put("currentPage",currentPage);
		paramDto.put("pageSize",pageSize);
		String jsonString=doPostLs(queryDmUrl,paramDto);
		logger.info("从来穗接口获取的代码["+typeCode+"]的"+msgTip+"数据:"+jsonString);
		return jsonString;
		
	}

	/**
	 *
	 * 简要说明：查询街路巷分页接口
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static PageDto queryJddmPageByLs(Dto paramDto ){


		String reqUrl=LsUri.LS_QUERY_JDDM_PAGE_URL.toString();
		String jsonString=doPostLs(reqUrl,paramDto);
		return fromJsonToPageDto(jsonString);
	}
	/**
	 * 
	 * 简要说明：通过接口查询来门牌列表信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static String queryMpPageByLs(Dto paramDto ){
		
		String xzq=paramDto.getString("xzq");
		if(WebplusUtil.isNotEmpty(xzq)){
			paramDto.put("xzq", xzq);
		}
		String reqUrl=LsUri.LS_QUERY_MP_PAGE_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：通过接口查询来门牌列表信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static String queryMpPageByLs(String jz,String jddm,String mpmc,String sfhb,String currentPage, String pageSize ){
		Dto paramDto=Dtos.newDto();
		paramDto.put("jz",jz);
		paramDto.put("jddm",jddm);
		paramDto.put("mpmc",mpmc);
		paramDto.put("sfhb",sfhb);
		paramDto.put("currentPage",currentPage);
		paramDto.put("pageSize",pageSize);
	    String jsonString=queryMpPageByLs(paramDto);
	    return jsonString;
	}
	/**
	 * 
	 * 简要说明：通过
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static String queryXqxxByLs(String jz,String jddm,String xqmc,String currentPage, String pageSize ){
		Dto paramDto=Dtos.newDto();
		paramDto.put("jz",jz);
		paramDto.put("jddm",jddm);
		paramDto.put("xqmc",xqmc);
		paramDto.put("currentPage",currentPage);
		paramDto.put("pageSize",pageSize);
	    String jsonString=doPostLs(LsUri.LS_QUERY_XQXX_PAGE_URL.toString(),paramDto);
	    return jsonString;
	}
	/**
	 * 
	 * 简要说明：通过接口查询来穗房栋信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static  PageDto queryFwxxzPage(Dto paramDto ){
		String jsonString=queryFwxxzPageByLs(paramDto);
		return fromJsonToPageDto(jsonString);
	}
	/**
	 * 
	 * 简要说明：通过接口查询来穗房栋信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static String queryFwxxzPageByLs(String xzq,String jz,String jwh,String jddm,String mpmc,String czwxz, String currentPage, String pageSize ){
		Dto paramDto=Dtos.newDto();
		paramDto.put("xzq", xzq);
		paramDto.put("jz", jz);
		paramDto.put("jwh", jwh);
		paramDto.put("jddm",jddm);
		paramDto.put("mpmc",mpmc);
		paramDto.put("currentPage",currentPage);
		paramDto.put("pageSize",pageSize);
		return queryFwxxzPageByLs(paramDto);
	}
	
	/**
	 * 
	 * 简要说明：查询来穗房屋栋信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static List<Dto> queryFwxxzList(Dto paramDto){
		String jsonString=queryFwxxzListByLs(paramDto);
		return fromJsonToList(jsonString);
	}
	/**
	 * 
	 * 简要说明：查询来穗房屋栋信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @Param jz  
	 * @Param jwh  
	 * @Param jddm  
	 * @Param mpmc 
	 * @Param czwxz 			
	 * @Param latStart 
	 * @Param latEnd 
	 * @Param lngStart 
	 * @Param lngEnd 
	 * @Param sfcxsl  
	 * @Param cqzgdyt 			
	 * @Param currentPage   
	 * @Param  pageSize
	 * @return 说明
	 */
	public static String queryFwxxzListByLs(Dto paramDto){
	  
		String reqUrl=LsUri.LS_QUERY_FWXXZ_LIST_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	
	/**
	 * 
	 * 简要说明：通过接口查询来穗房屋套信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static String queryFwxxzPageByLs(Dto paramDto){
		String reqUrl=LsUri.LS_QUERY_FWXXZ_PAGE_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：通过接口查询来穗房屋套信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static String queryFwxxzDetailByLs(String zhid){
		Dto paramDto=Dtos.newDto();
		paramDto.put("zhid", zhid);
		String reqUrl=LsUri.LS_QUERY_FWXXZ_DETAIL_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：通过接口查询来穗房屋套信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static Dto queryFwxxzDetail(String zhid){
		
		String jsonString=queryFwxxzDetailByLs(zhid);
		return fromJsonToDto(jsonString);
	}
	/**
	 * 
	 * 简要说明：通过接口查询来穗房栋信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public  static PageDto queryFwxxfhPage(String zhid,String dy,String currentPage, String pageSize){
		String jsonString=queryFwxxfhPageByLs(zhid, dy, currentPage, pageSize);
		return fromJsonToPageDto(jsonString);
	}
	/**
	 * 
	 * 简要说明：通过接口查询来穗房屋套信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public  static String queryFwxxfhPageByLs(String zhid,String dy,String currentPage, String pageSize ){
		Dto paramDto=Dtos.newDto();
		paramDto.put("zhid",zhid);
		paramDto.put("dy",dy);
		paramDto.put("currentPage",currentPage);
		paramDto.put("pageSize",pageSize);
		return queryFwxxfhPageByLs(paramDto);
	}

	/**
	 * 查询分页房屋套信息
	 * @param paramDto
	 * @return
	 */
	public static PageDto queryFwxxfhPage(Dto paramDto){
		String jsonString=queryFwxxfhPageByLs(paramDto);
		return fromJsonToPageDto(jsonString);
	}
	/**
	 *
	 * 简要说明：通过接口查询来穗房屋套信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @Param zhid  
	 * @Param mpid 
	 * @Param jz 
	 * @Param jddm 
	 * @Param jwh  
	 * @Param dy  
	 * @Param czwxz  
	 * @Param czwzt  
	 * @Param dyJq 
	 * @Param sfzmqgl 
	 * @Param sjyt 
	 * @Param cqzgdyt 
	 * @Param cjsjStart 
	 * @Param cjsjEnd 
	 * @Param currentPage  
	 * @Param pageSize 
	 * @return 说明
	 */
	public static String queryFwxxfhPageByLs(Dto paramDto){
		String reqUrl=LsUri.LS_QUERY_FWXXFH_PAGE_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	
	
	/**
	 * 
	 * 简要说明：通过接口查询来穗房屋套信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @return 说明
	 */
	public  static String queryFwxxfhDetailByLs(String mpid,String dy){
		Dto paramDto=Dtos.newDto();
		paramDto.put("mpid", mpid);
		paramDto.put("dy", dy);
		String reqUrl=LsUri.LS_QUERY_FWXXFH_DETAIL_MPID_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：通过接口查询来穗房屋套信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public  static String queryFwxxfhDetailByLs(String fhid){
		Dto paramDto=Dtos.newDto();
		paramDto.put("fhid", fhid);
		String reqUrl=LsUri.LS_QUERY_FWXXFH_DETAIL_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	
	/**
	 * 
	 * 简要说明：分页查询流动人员信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static PageDto queryLdryPage(Dto pDto){
		String jsonString=queryLdryPageByLs(pDto);
		return fromJsonToPageDto(jsonString);
	}
	/**
	 * 
	 * 简要说明：分页查询流动人员信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static String queryLdryPageByLs(Dto paramDto){
		String reqUrl=LsUri.LS_QUERY_LDRY_PAGE_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：通过接口查询来穗房屋套信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static Dto queryFwxxfhDetail(String fhid){
		String jsonString=queryFwxxfhDetailByLs(fhid);
		return fromJsonToDto(jsonString);
		
	}
	/**
	 * 
	 * 简要说明：通过证件号码或姓名查询流动人员信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	@SuppressWarnings("unchecked")
	public  static List<Dto> queryLdryList(String zjhm,String xm){
		
		String jsonString=queryLdryListByLs(zjhm,xm);
		if(WebplusUtil.isNotEmpty(jsonString)){
			ResultVo resultVo=WebplusJson.fromJson(jsonString, ResultVo.class);
			if(resultVo.isSuccess()){
				
				return (List<Dto>)resultVo.getDataList();
			}
		}
		return Lists.newArrayList();
	}
	/**
	 * 
	 * 简要说明：查询房屋套下面的人员，列表
	 * 编写者：陈骑元（chenqiyuan@toonan.com）
	 * 创建时间： 2021年1月13日 下午3:05:15 
	 * @param 说明
	 * @return 说明
	 */
	public static List<Dto> queryLdryList(String fhid){
		Dto pDto=Dtos.newDto();
		pDto.put("fhid", fhid);
		String reqUrl=LsUri.APP_QUERY_LDRY_LIST_URL.toString();
		String jsonString=doPostLs(reqUrl,pDto);
		return fromJsonToList(jsonString);
	}
	/**
	 * 
	 * 简要说明：通过证件号码或姓名查询流动人员信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static String queryLdryListByLs(String zjhm,String xm){
		Dto paramDto=Dtos.newDto();
		paramDto.put("zjhm", zjhm);
		paramDto.put("xm", xm);
		String reqUrl=LsUri.LS_QUERY_LDRY_LIST_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：通过证件号码或姓名查询流动人员信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static Dto queryLdryDetail(String id){
		String jsonString=queryLdryDetailByLs(id);
	    return fromJsonToDto(jsonString);
	}
	/**
	 * 
	 * 简要说明：通过证件号码或姓名查询流动人员信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static String queryLdryDetailByLs(String id){
		Dto paramDto=Dtos.newDto();
		paramDto.put("id", id);
		String reqUrl=LsUri.LS_QUERY_LDRY_DETAIL_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	
	/**
	 * 
	 * 简要说明：通过证件号码或姓名查询流动人员信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public  static String queryLdryDetailByZjhm(Dto paramDto){
		paramDto.put("zjhm",paramDto.getString("zjhm"));
		paramDto.put("xm",paramDto.getString("xm"));
	
		String reqUrl=LsUri.QUERY_LDRY_DETAILBYZJHM.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：通过证件号码或姓名查询流动人员信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static Dto queryLdryDetail(Dto paramDto){
		String jsonString=queryLdryDetailByZjhm( paramDto);
		if(WebplusUtil.isNotEmpty(jsonString)){
			ResultVo resultVo=WebplusJson.fromJson(jsonString, ResultVo.class);
			 return resultVo.getResultDto();
		}
		return Dtos.newDto();
	}
	/**
	 * 
	 * 简要说明：检查房屋流动人员是否存在
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static  boolean  checkLdryExistFwxxfh(String fhid,String zjhm, String xm){
		Dto paramDto=Dtos.newDto();
		paramDto.put("zjhm",zjhm);
		paramDto.put("xm",xm);
		paramDto.put("fhid", fhid);
		
		String reqUrl=LsUri.QUERY_FWXXFH_LDRY_COUNT.toString();
		String jsonString= doPostLs(reqUrl,paramDto);
		
		if(WebplusUtil.isNotEmpty(jsonString)){
			ResultVo resultVo=WebplusJson.fromJson(jsonString, ResultVo.class);
			if(resultVo.isSuccess()){
				int count=((Double)resultVo.getData()).intValue();
				if(count>0){
					return true;
				}
				
			}
			
		}
		return false;
	}
	/**
	 * 
	 * 简要说明：通过接口查询来穗房栋信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static PageDto queryDwxxPage(Dto pDto){
		String jsonString=queryDwxxPageByLs(pDto);
		
		return fromJsonToPageDto(jsonString);
		
	}
	/**
	 * 
	 * 简要说明：通过接口查询来穗单位信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static String queryDwxxPageByLs(String dwmc,String jz,String jddm,String currentPage, String pageSize){
		Dto paramDto=Dtos.newDto();
		paramDto.put("dwmc",dwmc);
		paramDto.put("jz",jz);
		paramDto.put("jddm",jddm);
		paramDto.put("currentPage",currentPage);
		paramDto.put("pageSize",pageSize);
		return queryDwxxPageByLs(paramDto);
	}
	
	/**
	 * 
	 * 简要说明：通过接口查询网格化信息接口
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static String queryWgxxPageByLs(String xzq,String wgmc,String currentPage, String pageSize){
		Dto paramDto=Dtos.newDto();
		paramDto.put("wgmc",wgmc);
		paramDto.put("queryXzq",xzq);
		paramDto.put("currentPage",currentPage);
		paramDto.put("pageSize",pageSize);
		return doPostLs(LsUri.LS_QUERY_WGXX_PAGE_URL.toString(),paramDto);
	}
	
	/**
	 * 
	 * 简要说明：查询检查条目分页
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static String queryJctmPageByLs(String mc,String jcgs,String yzcd,String currentPage, String pageSize){
		Dto paramDto=Dtos.newDto();
		paramDto.put("mc",jcgs);
		paramDto.put("yzcd", yzcd);
		paramDto.put("jcgs", jcgs);
		paramDto.put("currentPage",currentPage);
		paramDto.put("pageSize",pageSize);
		return doPostLs(LsUri.LS_QUERY_JCTM_PAGE_URL.toString(),paramDto);
	}
	/**
	 * 
	 * 简要说明：通过接口查询来穗单位信息
	 * 编写者：陈骑元
	 * 创建时间：2017年8月19日 上午11:39:52
	 * @param
	 * @return 说明
	 */
	public static String queryDwxxPageByLs(Dto paramDto){
		
		String reqUrl=LsUri.LS_QUERY_DWXX_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	
	
	/**
	 * 简要说明：校验居住证是否可以办证
	 * 编写者：yangjian@toonan.com
	 * 创建时间：2017年12月27日 上午11:55:47
	 * @param
	 * @return 说明
	 */
	public static String liveCardCheck(Dto paramDto){
		String reqUrl=LsUri.LS_LIVE_CARD_CHECK_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	
	/**
	 * 简要说明：校验居住证是否可以办证
	 * 编写者：yangjian@toonan.com
	 * 创建时间：2017年12月27日 上午11:55:47
	 * @param
	 * @return 说明
	 */
	public static String checkJzz(Dto paramDto){
	
		String reqUrl=LsUri.LS_CHECK_JZZ_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	
	/**
	 * 简要说明：返回居住证的卡面信息
	 * 编写者：yangjian@toonan.com
	 * 创建时间：2017年12月27日 上午11:55:47
	 * @param
	 * @return 说明
	 */
	public static String jzzCardInfo(String xm,String zjhm){
		Dto paramDto=Dtos.newDto();
		paramDto.put("zjhm",zjhm);
		paramDto.put("xm",xm);
		String reqUrl=LsUri.LS_JZZ_CARD_INFO.toString();
		return doPostLs(reqUrl,paramDto);
	}
	
	/**
	 * 简要说明：返回居住证业务信息
	 */
	public static String jzzywCardInfo(String xm,String zjhm){
		Dto paramDto=Dtos.newDto();
		paramDto.put("zjhm",zjhm);
		paramDto.put("xm",xm);
		String reqUrl=LsUri.LS_JZZ_YW_CARD_INFO.toString();
		return doPostLs(reqUrl,paramDto);
	}
	
	
	/**
	 * 简要说明：证明材料查询接口
	 */
	public static String queryZmclList(String ywid,String ywtype){
		Dto paramDto=Dtos.newDto();
		paramDto.put("ywid",ywid);
		paramDto.put("ywtype",ywtype);
		String reqUrl=LsUri.LS_QUERY_ZMCL_LIST_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	
	
	public static List<Dto> queryZmclListDto(String ywid,String ywtype){
		String jsonString=queryZmclList(ywid,ywtype);
		return fromJsonToList(jsonString);
	}
	
	/**
	 * 简要说明：随行人员查询接口
	 */
	public static List<Dto> querySxryList(String fmryid,String fhid){
		String jsonString=querySxryListByLs(fmryid,fhid);
		return fromJsonToList(jsonString);
	}
	/**
	 * 简要说明：随行人员查询接口
	 */
	public static String querySxryListByLs(String fmryid,String fhid){
		Dto paramDto=Dtos.newDto();
		paramDto.put("fmryid",fmryid);
		paramDto.put("fhid",fhid);
		paramDto.put("queryWay",WebplusCons.WHETHER_YES);
		String reqUrl=LsUri.LS_QUERY_SXRY_LIST_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：分页查询随行人员
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:13:34
	 * @return 说明
	 */
	public static PageDto querySxryPage(Dto pDto){
		String jsonString=querySxryPageByLs(pDto);
		
		return fromJsonToPageDto(jsonString);
		
	}
	/**
	 * 
	 * 简要说明：分页查询随行人员信息
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:14:05
	 * @return 说明
	 */
	public static String querySxryPageByLs(Dto paramDto){
		
		String reqUrl=LsUri.LS_QUERY_SXRY_PAGE_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	
	/**
	 * 
	 * 简要说明：分页查询移动巡查的房屋套信息
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:13:34
	 * @return 说明
	 */
	public static PageDto queryYdcjFwxxfhPage(Dto pDto){
		String jsonString=queryYdcjFwxxfhPageByLs(pDto);
		
		return fromJsonToPageDto(jsonString);
		
	}
	/**
	 * 
	 * 简要说明：分页查询移动巡查的房屋套信息
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:14:05
	 * @return 说明
	 */
	public static String queryYdcjFwxxfhPageByLs(Dto paramDto){
		
		String reqUrl=LsUri.LS_QUERY_YDCJFWXXFH_PAGE_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：分页查询流动人人员历史数据
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:13:34
	 * @return 说明
	 */
	public static PageDto queryLdrylsPage(Dto pDto){
		
		String jsonString=queryLdrylsPageByLs(pDto);
		
		return fromJsonToPageDto(jsonString);
		
	}
	/**
	 * 
	 * 简要说明：分页查询流动人人员历史数据
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:13:34
	 * @return 说明
	 */
	public static PageDto queryLdrylsPage(String fhid,String rybh,String currentPage,String pageSize){
		Dto pDto=Dtos.newDto();
		pDto.put("fhid", fhid);
		pDto.put("rybh", rybh);
		pDto.put("currentPage", currentPage);
		pDto.put("pageSize", pageSize);
		String jsonString=queryLdrylsPageByLs(pDto);
		
		return fromJsonToPageDto(jsonString);
		
	}
	/**
	 * 
	 * 简要说明：分页查询劳动人员历史信息
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:14:05
	 * @return 说明
	 */
	public static String queryLdrylsPageByLs(Dto paramDto){
		
		String reqUrl=LsUri.LS_QUERY_LDRYLS_PAGE_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：分页查询居住证信息
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:13:34
	 * @return 说明
	 */
	public static PageDto queryJzzxxPage(Dto pDto){
		String jsonString=queryJzzxxPageByLs(pDto);
		
		return fromJsonToPageDto(jsonString);
		
	}
	/**
	 * 
	 * 简要说明：分页查询居住证信息
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:14:05
	 * @return 说明
	 */
	public static String queryJzzxxPageByLs(Dto paramDto){
		
		String reqUrl=LsUri.LS_QUERY_JZZXX_PAGE_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：分页查询房屋租赁合同信息
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:13:34
	 * @return 说明
	 */
	public static PageDto queryFwzlhtxxPage(Dto pDto){
		String jsonString=queryFwzlhtxxPageByLs(pDto);
		
		return fromJsonToPageDto(jsonString);
		
	}
	/**
	 * 
	 * 简要说明：分页查询房屋租赁合同信息
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:14:05
	 * @return 说明
	 */
	public static String queryFwzlhtxxPageByLs(Dto paramDto){
		
		String reqUrl=LsUri.LS_QUERY_FWZLHTXX_PAGE_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：查询房屋租赁合同信息列表
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:13:34
	 * @return 说明
	 */
	public static List<Dto> queryFwzlhtxxList(String fhid){
		Dto pDto=Dtos.newDto();
		pDto.put("fhid", fhid);
		String jsonString=queryFwzlhtxxListByLs(pDto);
		
		return fromJsonToList(jsonString);
		
	}
	/**
	 * 
	 * 简要说明：分页查询房屋租赁合同列表
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:14:05
	 * @return 说明
	 */
	public static String queryFwzlhtxxListByLs(Dto paramDto){
	
		String reqUrl=LsUri.LS_QUERY_FWZLHTXX_LIST_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	
	/**
	 * 
	 * 简要说明：分页查询房屋租赁合同列表
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:14:05
	 * @return 说明
	 */
	public static Dto queryFwzlhtxxDetail(String id){
	    Dto paramDto=Dtos.newDto("id", id);
		String reqUrl=LsUri.LS_QUERY_FWZLHTXX_DETAIL_URL.toString();
		String jsonString= doPostLs(reqUrl,paramDto);
	    return fromJsonToDto(jsonString);
	}
	/**
	 * 
	 * 简要说明：查询房屋租赁合同数量
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:14:05
	 * @return 说明
	 */
	public static int queryFwzlhtxxCount(Dto paramDto){
	   
		String reqUrl=LsUri.LS_QUERY_FWZLHTXX_COUNT_URL.toString();
		String jsonString= doPostLs(reqUrl,paramDto);
	    return fromJsonToInteger(jsonString);
	}
	/**
	 * 
	 * 简要说明：查询房屋租赁税数量
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:14:05
	 * @return 说明
	 */
	public static int queryFwzlqsxxCount(Dto paramDto){
	   
		String reqUrl=LsUri.LS_QUERY_FWZLQSXX_COUNT_URL.toString();
		String jsonString= doPostLs(reqUrl,paramDto);
	    return fromJsonToInteger(jsonString);
	}
	/**
	 * 
	 * 简要说明：查询居住证信息数量
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:14:05
	 * @return 说明
	 */
	public static int queryJzzxxCount(Dto paramDto){
	   
		String reqUrl=LsUri.LS_QUERY_JZZXX_COUNT_URL.toString();
		String jsonString= doPostLs(reqUrl,paramDto);
	    return fromJsonToInteger(jsonString);
	}
	/**
	 * 
	 * 简要说明：分页查询房屋租赁契税信息
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:13:34
	 * @return 说明
	 */
	public static PageDto queryFwzlqsxxPage(Dto pDto){
		String jsonString=queryFwzlqsxxPageByLs(pDto);
		
		return fromJsonToPageDto(jsonString);
		
	}
	/**
	 * 
	 * 简要说明：分页查询房屋租赁契税信息
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:14:05
	 * @return 说明
	 */
	public static String queryFwzlqsxxPageByLs(Dto paramDto){
		
		String reqUrl=LsUri.LS_QUERY_FWZLQSXX_PAGE_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：查询房屋租赁契税信息列表
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:13:34
	 * @return 说明
	 */
	public static List<Dto> queryFwzlqsxxList(String id,String fhid){
		Dto pDto=Dtos.newDto();
		pDto.put("id", id);
		pDto.put("fhid", fhid);
		String jsonString=queryFwzlqsxxListByLs(pDto);
		
		return fromJsonToList(jsonString);
		
	}
	/**
	 * 
	 * 简要说明：分页查询房屋租赁契税合同列表
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:14:05
	 * @return 说明
	 */
	public static String queryFwzlqsxxListByLs(Dto paramDto){
	
		String reqUrl=LsUri.LS_QUERY_FWZLQSXX_LIST_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：分页查询房屋租赁人员信息
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:13:34
	 * @return 说明
	 */
	public static PageDto queryFwzlryxxPage(Dto pDto){
		String jsonString=queryFwzlryxxPageByLs(pDto);
		
		return fromJsonToPageDto(jsonString);
		
	}
	/**
	 * 
	 * 简要说明：分页查询房屋租赁契税信息
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:14:05
	 * @return 说明
	 */
	public static String queryFwzlryxxPageByLs(Dto paramDto){
		
		String reqUrl=LsUri.LS_QUERY_FWZLRYXX_PAGE_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：查询房屋租租赁人员信息列表
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:13:34
	 * @return 说明
	 */
	public static List<Dto> queryFwzlryxxList(String id,String fhid){
		Dto pDto=Dtos.newDto();
		pDto.put("id", id);
		pDto.put("fhid", fhid);
		String jsonString=queryFwzlryxxListByLs(pDto);
		
		return fromJsonToList(jsonString);
		
	}
	/**
	 * 
	 * 简要说明：分页查询房屋租赁人员信息列表
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午7:14:05
	 * @return 说明
	 */
	public static String queryFwzlryxxListByLs(Dto paramDto){
	
		String reqUrl=LsUri.LS_QUERY_FWZLRYXX_LIST_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：产权人信息查询
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午5:01:21
	 * @return 说明
	 */
	public static List<Dto> querCqrList(Dto pDto){
		String jsonString=queryCqrListByLs(pDto);
		return fromJsonToList(jsonString);
	}
	/**
	 * 
	 * 简要说明：产权人信息查询
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午5:01:05
	 * @return 说明
	 */
	public static String queryCqrListByLs(Dto paramDto){
		String reqUrl=LsUri.LS_QUERY_CQR_LIST_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：产权人信息查询分页
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午5:01:05
	 * @return 说明
	 */
	public static String queryCqrPageByLs(Dto paramDto){
		String reqUrl=LsUri.LS_QUERY_CQR_PAGE_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：产权单位信息查询
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午5:01:21
	 * @return 说明
	 */
	public static List<Dto> querCqdwList(Dto pDto){
		String jsonString=queryCqdwListByLs(pDto);
		return fromJsonToList(jsonString);
	}
	/**
	 * 
	 * 简要说明：产权单位信息查询
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午5:00:47
	 * @return 说明
	 */
	public static String queryCqdwListByLs(Dto paramDto){
		String reqUrl=LsUri.LS_QUERY_CQDW_LIST_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：产权单位信息查询
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午5:00:47
	 * @return 说明
	 */
	public static String queryCqdwPageByLs(Dto paramDto){
		String reqUrl=LsUri.LS_QUERY_CQDW_PAGE_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：房屋信息证明材料
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午5:00:47
	 * @return 说明
	 */
	public static List<Dto> queryFwxxZmclList(String fwxxid,String fwxxlx){
		Dto paramDto=Dtos.newDto();
		paramDto.put("fwxxid", fwxxid);
		paramDto.put("fwxxlx", fwxxlx);
		String jsonString=queryFwxxZmclListByLs(paramDto);
		return fromJsonToList(jsonString);
	}
	/**
	 * 
	 * 简要说明：房屋信息照片查询
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午5:00:47
	 * @return 说明
	 */
	public static List<Dto> queryFwxxPhotoList(String fwxxid,String fwxxlx){
		Dto paramDto=Dtos.newDto();
		paramDto.put("fwxxid", fwxxid);
		paramDto.put("fwxxlx", fwxxlx);
		String jsonString=queryFwxxPhotoListByLs(paramDto);
		return fromJsonToList(jsonString);
	}
	/**
	 * 
	 * 简要说明：房屋信息证明材料
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午5:00:47
	 * @return 说明
	 */
	public static String queryFwxxZmclListByLs(Dto paramDto){
		String reqUrl=LsUri.LS_QUERY_FWXXZMCL_LIST_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：查询关注人员列表
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午5:00:47
	 * @return 说明
	 */
	public static String queryGzryList(Dto paramDto){
		String reqUrl=LsUri.LS_QUERY_CQDW_PAGE_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	
	/**
	 * 
	 * 简要说明：查询日常巡查分页
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午5:00:47
	 * @return 说明
	 */
	public static String queryRcxcPage(Dto paramDto){
		String reqUrl=LsUri.LS_QUERY_RCXC_PAGE_URL.toString()	;
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：查询日常巡查隐患照片
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午5:00:47
	 * @return 说明
	 */
	public static String queryRcxcYhzpList(Dto paramDto){
		String reqUrl=LsUri.LS_QUERY_RCXC_YHZP_LIST_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：更新房屋栋坐标
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午5:00:47
	 * @return 说明
	 */
	public static boolean  updateFwxxzZb(Dto paramDto){
		String reqUrl=LsUri.LS_UPDATE_FWXXZZB_URL.toString();
		String jsonString= doPostLs(reqUrl,paramDto);
		return fromJsonToUpadte(jsonString);
	}
	
	/**
	 * 
	 * 简要说明：房屋信息照片查询
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午5:00:47
	 * @return 说明
	 */
	public static String queryFwxxPhotoListByLs(Dto paramDto){
		String reqUrl=LsUri.LS_QUERY_FWXXPHOTO_LIST_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	/**
	 * 
	 * 简要说明：分页获取公安详址信息
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午5:00:47
	 * @return 说明
	 */
	public static String queryGaxzPageByLs(Dto paramDto){
		String reqUrl=LsUri.LS_QUERY_GAXZ_PAGE_URL.toString();
		return doPostLs(reqUrl,paramDto);
	}
	
	/**
	 * 
	 * <p>居住</p> 
	 * <p>编写者：黄健达 (lsr@sina.com)</p> 
	 * @date: 2020年5月28日 下午4:21:03 
	 * @param lsRequestUrl
	 * @param zhid
	 * @param fhid
	 * @param xm
	 * @param zjhm
	 * @param slyy
	 * @param ywhj,
	 * @param zhxgsj_start
	 * @param zhxgsj_end
	 * @param currentPage
	 * @param pageSize
	 * @return String
	 */
	 public static String  queryJzzywPage(Dto paramDto) {
		 return  doPostLs(LsUri.LS_QUERY_JZZYW_PAGE_URL.toString(),paramDto);
	 }
	 
	 /**
	  * 
	  * 简要说明：查询出租屋管理员小组
	  * 编写者：陈骑元（chenqiyuan@toonan.com）
	  * 创建时间： 2020年12月9日 下午2:35:25 
	  * @param 说明
	  * @return 说明
	  */
	 public static PageDto queryCzwglxzPage(Dto pDto) {
		 
		 String jsonString=queryCzwglxzPageByLs(pDto);
		 
		 return fromJsonToPageDto(jsonString);
	 }
	 /**
	  * 
	  * 简要说明：来穗查询出租屋管理员小组
	  * 编写者：陈骑元（chenqiyuan@toonan.com）
	  * 创建时间： 2020年12月9日 下午2:27:43 
	  * @param 说明
	  * @return 说明
	  */
	 public static String queryCzwglxzPageByLs(Dto paramDto){
		  
			String reqUrl=LsCons.LS_QUERY_CZWGLXZ_PAGE_URL;
			return doPostLs(reqUrl,paramDto);
     }
	 /**
	  * 
	  * 简要说明：查询出租屋管理员分页
	  * 编写者：陈骑元（chenqiyuan@toonan.com）
	  * 创建时间： 2020年12月9日 下午2:35:25 
	  * @param 说明
	  * @return 说明
	  */
	 public static PageDto queryCzwglyPage(Dto pDto) {
		 
		 String jsonString=queryCzwglyPageByLs(pDto);
		 
		 return fromJsonToPageDto(jsonString);
	 }
	 /**
	  * 
	  * 简要说明：来穗查询出租屋管理员
	  * 编写者：陈骑元（chenqiyuan@toonan.com）
	  * 创建时间： 2020年12月9日 下午2:27:43 
	  * @param 说明
	  * @return 说明
	  */
	 public static String queryCzwglyPageByLs(Dto paramDto){
		  
			String reqUrl=LsCons.LS_QUERY_CZWGLY_PAGE_URL;
			return doPostLs(reqUrl,paramDto);
     }
	 /**
	  * 
	  * 简要说明：查询房屋栋、套
	  * 编写者：陈骑元（chenqiyuan@toonan.com）
	  * 创建时间： 2020年12月9日 下午3:14:57 
	  * @param 说明
	  * @return 说明
	  */
	 public  static Dto queryFwxxParent(String fhid,String zhid){
		    Dto pDto=Dtos.newDto();
		    pDto.put("fhid", fhid);
		    pDto.put("zhid", zhid);
			String reqUrl=LsCons.LS_QUERY_FWXXTORCXC_PARENT_URL;
			String jsonString=  doPostLs(reqUrl,pDto);
			return fromJsonToDto(jsonString);
	}  
	 /**
	  * 
	  * 简要说明：
	  * 编写者：陈骑元（chenqiyuan@toonan.com）
	  * 创建时间： 2020年12月9日 下午9:11:36 
	  * @param 说明
	  * @return 说明
	  */
	 public static ResultVo addRcjc(Dto pDto,List<FileVo> fileList) {
		 pDto.put(LsCons.LS_CDKEY, lsCdkey);
		 String lsRequestUrl=lsUrl.replace("api", "app");
		 String reqUrl=lsRequestUrl+"/"+LsCons.LS_ADD_RCJC_URL;
		 String jsonString= WebplusHttp.uploadFile(reqUrl, pDto, fileList);
		 ResultVo resultVo=WebplusJson.fromJson(jsonString, ResultVo.class);
		 return resultVo;
	 }
	 
	 /**
	  * 
	  * 简要说明：
	  * 编写者：陈骑元（chenqiyuan@toonan.com）
	  * 创建时间： 2020年12月9日 下午9:11:36 
	  * @param 说明
	  * @return 说明
	  */
	 public static ResultVo saveRcjcShxx(Dto pDto) {
		 String jsonString=  doPostLs(LsCons.LS_RCXC_SHXX_URL,pDto);
		 ResultVo resultVo=WebplusJson.fromJson(jsonString, ResultVo.class);
		 return resultVo;
	 }
		
	 /**
	  * 
	  * 简要说明：更新同步流动人员信息
	  * 编写者：陈骑元（chenqiyuan@toonan.com）
	  * 创建时间： 2020年12月9日 下午9:11:36 
	  * @param 说明
	  * @return 说明
	  */
	 public static ResultVo updateSyncLdry(String ywid) {
		 Dto pDto=Dtos.newDto("ywid", ywid);
		 String jsonString=  doPostLs(LsCons.LS_UPDATE_SYNC_LDRY,pDto);
		 ResultVo resultVo=WebplusJson.fromJson(jsonString, ResultVo.class);
		 return resultVo;
	 }
	 /**
	  * 
	  * 简要说明：插入短信记录数据
	  * 编写者：陈骑元（chenqiyuan@toonan.com）
	  * 创建时间： 2021年1月15日 上午11:04:58 
	  * @param 说明
	  * @return 说明
	  */
	 public static ResultVo insertSmsRecord(String jzzid,String smsType) {
		 Dto pDto=Dtos.newDto("jzzid", jzzid);
		 pDto.put("smsType", smsType);
		 String jsonString=  doPostLs(LsCons.LS_INSERT_SMS_RECORD,pDto);
		 ResultVo resultVo=WebplusJson.fromJson(jsonString, ResultVo.class);
		 return resultVo;
	 }
	 
	 /**
	  * 
	  * 简要说明：查询居住证状态信息
	  * 编写者：陈骑元（chenqiyuan@toonan.com）
	  * 创建时间： 2021年1月15日 上午11:04:58 
	  * @param 说明
	  * @return 说明
	  */
	 public static List<Dto> listJzzzt(Dto pDto) {
		 String jsonString=  doPostLs(LsCons.LS_LIST_JZZZT_URL,pDto);
		 
		 return fromJsonToList(jsonString);
	 }
	 
	 /**
	  * 
	  * 简要说明：查询居住证业务列表
	  * 编写者：陈骑元（chenqiyuan@toonan.com）
	  * 创建时间： 2021年1月15日 上午11:04:58 
	  * @param 说明
	  * @return 说明
	  */
	 public static List<Dto> listJzzyw(Dto pDto) {
		 String jsonString=  doPostLs(LsCons.LS_LIST_JZZYW_URL,pDto);
		 
		 return fromJsonToList(jsonString);
	 }
	 /***
	  * 
	  * 简要说明：同步人员操作记录
	  * 编写者：陈骑元（chenqiyuan@toonan.com）
	  * 创建时间： 2021年1月14日 下午3:17:21 
	  * @param 说明
	  * @return 说明
	  */
	 public static List<Dto> listLdryOperationRecord(String czlx,String sort,Integer limit){
			
			Dto paramDto=Dtos.newDto();
			paramDto.put("czlx", czlx);
			paramDto.put("sort", sort);
			paramDto.put("limit", limit);
			String jsonString=doPostLs(LsCons.LS_LIST_SYNC_LDRY_RECORD,paramDto);
			return fromJsonToList(jsonString);
	 }
	 
	 /***
	  * 
	  * 简要说明：查询同步注销流动人员
	  * 编写者：陈骑元（chenqiyuan@toonan.com）
	  * 创建时间： 2021年1月14日 下午3:17:21 
	  * @param 说明
	  * @return 说明
	  */
	 public static Dto querySyncCancelLdry(String ywid,String xzq){
			if(WebplusUtil.isEmpty(xzq)) {
				xzq=WebplusCache.getParamValue(WebplusCons.XZQ_CODE_KEY);
			}
			Dto paramDto=Dtos.newDto();
			paramDto.put("ywid", ywid);
			paramDto.put("xzq", xzq);
			String jsonString=doPostLs(LsCons.LS_CANCEL_LDRY_QUERY,paramDto);
			return fromJsonToDto(jsonString);
		}
	 /**
		 * 
		 * 简要说明:获取人员信息统计接口
		 * 编写者：陈骑元
		 * 创建时间：2019年1月10日 下午5:00:47
		 * @return 说明
		 */
		public static List<Dto> listRyxxsbtj(String tjrq){
 			String xzq=WebplusCache.getParamValue(WebplusCons.XZQ_CODE_KEY);
			Dto paramDto=Dtos.newDto();
			paramDto.put("tjrq", tjrq);
			paramDto.put("xzq", xzq);
			String jsonString=doPostLs(LsCons.LS_RYXXSBTJ_LIST_URL,paramDto);
			return fromJsonToList(jsonString);
		}
		
		/**
		  * 
		  * 简要说明：通过行政区，街镇，居委会获取登录用户信息
		  * 编写者：陈骑元（chenqiyuan@toonan.com）
		  * 创建时间： 2021年1月15日 上午11:04:58 
		  * @param 说明
		  * @return 说明
		  */
		 public static List<Dto> listUser(String xzq,String jz,String jwh) {
			 Dto pDto=Dtos.newDto();
			 pDto.put("xzq", xzq);
			 pDto.put("jz", jz);
			 pDto.put("jwh", jwh);
			 String jsonString=  doPostLs(LsCons.LS_QUERY_USER_LIST,pDto);
			 
			 return fromJsonToList(jsonString);
		 }
		 /**
		 * 
		 * 简要说明:获取人员信息统计接口
		 * 编写者：陈骑元
		 * 创建时间：2019年1月10日 下午5:00:47
		 * @return 说明
		 */
		public static Dto getAccessTokenUser(String accessToken){
			Dto paramDto=Dtos.newDto();
		    paramDto.put("accessToken", accessToken);
			String jsonString=doPostLs(LsCons.LS_GET_ACCESS_TOKEN_USER_INFO,paramDto);
			return fromJsonToDto(jsonString);
		}
	/**
	 * 简要说明：随行人员查询接口
	 */
	public static boolean downloadImage(String fid,String zjhm,HttpServletResponse response){
		Dto paramDto=Dtos.newDto();
		paramDto.put("fid", fid);
		paramDto.put("zjhm", zjhm);
		paramDto.put(LsCons.LS_CDKEY,lsCdkey);
		String lsRequestUrl=lsUrl.replace("api", "app");
		String reqUrl=lsRequestUrl+"/"+LsUri.LS_DOWN_IMAGE_URL.toString();
		return WebplusHttp.download(reqUrl, paramDto,response);
	}
	
	/**
	 * 简要说明：随行人员查询接口
	 */
	public static String downloadImageBase64(String fid,String zjhm){
		Dto paramDto=Dtos.newDto();
		paramDto.put("fid", fid);
		paramDto.put("zjhm", zjhm);
		paramDto.put(LsCons.LS_CDKEY,lsCdkey);
		String lsRequestUrl=lsUrl.replace("api", "app");
		String reqUrl=lsRequestUrl+"/"+LsUri.LS_DOWN_IMAGE_URL.toString();
		return WebplusHttp.downloadBase64(reqUrl, paramDto);
	}

	
	/**
	 * 
	 * 简要说明：来穗发送POST请求接口
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午4:47:00
	 * @return 说明
	 */
	public static String doPostLs(String reqUrl, Dto paramDto) {
		paramDto.put(LsCons.LS_CDKEY, lsCdkey);
		return WebplusHttp.doPost(String.format(LsCons.URL_TEMPLE, lsUrl,reqUrl), paramDto);
	}
	
	
	/**
	 * 
	 * 简要说明：来穗发送POST请求接口
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午4:47:00
	 * @return 说明
	 */
	public static String doGetLs( String reqUrl, Dto paramDto) {
		paramDto.put(LsCons.LS_CDKEY, lsCdkey);
		return WebplusHttp.doGet(String.format(LsCons.URL_TEMPLE, lsUrl,reqUrl), paramDto);
	}
	
	public static String execute(HttpMethod method,String lsRequestUrl, LsUri uri, Dto paramDto) {
		String result = null ;
		switch (method) {
		case POST:
			doPostLs(uri.toString(), paramDto);
			break;
		case GET:
			doGetLs( uri.name().toString(), paramDto);
			break;
		default:
			result =R.error().toString();
			break;
		}
		return result;
	}

	
	/**
	 * 
	 * 简要说明：转换成List<Dto>;
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午4:47:00
	 * @return 说明
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Dto> fromJsonToList(String jsonString){
		if(WebplusUtil.isNotEmpty(jsonString)){
			ResultVo resultVo=WebplusJson.fromJson(jsonString, ResultVo.class);
			if(resultVo.isSuccess()){
				List<Dto> dataList=Lists.newArrayList();
				List<Map> mapList=(List<Map>)resultVo.getDataList();
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
	
	/**
	 * 
	 * 简要说明：转换PageDto
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午4:47:00
	 * @return 说明
	 */
	public static PageDto fromJsonToPageDto(String jsonString){
		if(WebplusUtil.isNotEmpty(jsonString)){
	    	ResultVo resultVo=WebplusJson.fromJson(jsonString, ResultVo.class);
			if(resultVo.isSuccess()){
				
				return new PageDto(resultVo.getPage().getCount(),resultVo.getDataList());
			}
	    }
	    return new PageDto(0,Lists.newArrayList());
	}
	
	/** 
	 * 简要说明：转换Dto
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午4:47:00
	 * @return 说明
	 */
	public static Boolean fromJsonToUpadte(String jsonString){
		if(WebplusUtil.isNotEmpty(jsonString)){
			ResultVo resultVo=WebplusJson.fromJson(jsonString, ResultVo.class);
			if(resultVo.isSuccess()){
				
				return true;
			}
		}
		return false;
	}
	/** 
	 * 简要说明：转换Dto
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午4:47:00
	 * @return 说明
	 */
	public static Dto fromJsonToDto(String jsonString){
		if(WebplusUtil.isNotEmpty(jsonString)){
			ResultVo resultVo=WebplusJson.fromJson(jsonString, ResultVo.class);
			if(resultVo.isSuccess()){
				return resultVo.getResultDto();
			}
			 
		}
		return Dtos.newDto();
	}
	
	/** 
	 * 简要说明：转换0
	 * 编写者：陈骑元
	 * 创建时间：2019年1月10日 下午4:47:00
	 * @return 说明
	 */
	public static Integer fromJsonToInteger(String jsonString){
		if(WebplusUtil.isNotEmpty(jsonString)){
			ResultVo resultVo=WebplusJson.fromJson(jsonString, ResultVo.class);
			if(resultVo.isSuccess()){
				int count=((Double)resultVo.getData()).intValue();
				
				return count;
				
			}
			
		}
		return 0;
	}
	

}
