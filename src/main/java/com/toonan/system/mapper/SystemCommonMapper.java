package com.toonan.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.toonan.core.matatype.Dto;
import com.toonan.core.vo.Item;
import com.toonan.system.model.Menu;


/**
 * 
 * 类名:com.toonan.system.mapper.SystemCommonMapper
 * 描述:系统通用查询接口
 * 编写者:陈骑元
 * 创建时间:2018年12月17日 下午2:34:46
 * 修改说明:
 */
public interface SystemCommonMapper{
   
	
	/**
	 * 
	 * 简要说明：查询授权菜单
	 * 编写者：陈骑元
	 * 创建时间：2019年1月7日 下午8:46:47
	 * @param 说明
	 * @return 说明
	 */
	 List<Menu> listRoleMenu(Dto pDto);
	 
	 /**
	  * 
	  * 简要说明：查询角色授权菜单编号
	  * 编写者：陈骑元
	  * 创建时间：2019年1月8日 上午10:43:49
	  * @param 说明
	  * @return 说明
	  */
     List<String> listRoleMenuId(String userId);
     
     /**
 	 * 
 	 * 简要说明：通过行政区查询街镇信息
 	 * 编写者：陈骑元
 	 * 创建时间：2018年12月17日 下午2:35:18
 	 * @param 说明
 	 * @return 说明
 	 */
 	List<Item> listJz(String xzq);
 	
 	/**
 	 * 
 	 * 简要说明：通过街镇查询
 	 * 编写者：陈骑元
 	 * 创建时间：2018年12月17日 下午2:35:18
 	 * @param 说明
 	 * @return 说明
 	 */
 	List<Item> listJwh(String jz);
 	/**
 	 * 
 	 * 简要说明：通过街镇查询
 	 * 编写者：陈骑元
 	 * 创建时间：2018年12月17日 下午2:35:18
 	 * @param 说明
 	 * @return 说明
 	 */
 	List<Item> listPcs(String jz);
 	/**
 	 * 
 	 * 简要说明：分页查询街路巷
 	 * 编写者：陈骑元
 	 * 创建时间：2018年12月17日 下午4:32:49
 	 * @param 说明
 	 * @return 说明
 	 */
 	List<Item> listJddm(Pagination page,Dto pDto);
 	/**
 	 * 
 	 * 简要说明：查询单个街路巷
 	 * 编写者：陈骑元
 	 * 创建时间：2020年1月9日 下午9:31:23
 	 * @param 说明
 	 * @return 说明
 	 */
 	Item selectJddm(String jddm);
}
