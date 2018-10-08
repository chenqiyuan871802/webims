package com.ims.system.service;

import com.ims.system.model.Role;
import com.ims.system.model.RoleUser;
import com.ims.system.model.TreeModel;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import com.ims.common.matatype.Dto;
import com.baomidou.mybatisplus.plugins.Page;
/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 陈骑元
 * @since 2018-10-02
 */
public interface RoleService extends IService<Role> {
      /**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<Role>
	 */
	List<Role> list(Dto pDto);
    /**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return Page<Role>
	 */
	Page<Role> listPage(Dto pDto);
		
	/**
	 * 根据Dto模糊查询并返回数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<Role>
	 */
	List<Role> like(Dto pDto);

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return Page<Role>
	 */
	Page<Role> likePage(Dto pDto);
	/**
	 * 根据数学表达式进行数学运算
	 * 
	 * @param pDto
	 * @return String
	 */
	 String calc(Dto pDto);
	 /**
	  * 
	  * 简要说明：移除角色
	  * 编写者：陈骑元
	  * 创建时间：2018年10月2日 上午10:47:11
	  * @param 说明
	  * @return 说明
	  */
	 boolean removeRole(String roleId);
	 /**
	  * 
	  * 简要说明：移除角色
	  * 编写者：陈骑元
	  * 创建时间：2018年10月2日 上午10:47:11
	  * @param 说明
	  * @return 说明
	  */
	 boolean batchSaveRoleUser(String roleId,List<String> userIdList);
	 /**
	  * 
	  * 简要说明：移除角色
	  * 编写者：陈骑元
	  * 创建时间：2018年10月2日 上午10:47:11
	  * @param 说明
	  * @return 说明
	  */
	 boolean batchRemoveRoleUser(String roleId,List<String> userIdList);
	 
	 /**
	  * 
	  * 简要说明：移除角色
	  * 编写者：陈骑元
	  * 创建时间：2018年10月2日 上午10:47:11
	  * @param 说明
	  * @return 说明
	  */
	 boolean batchSaveRoleMenu(String roleId,List<String> menuIdList);
	 /**
	  * 
	  * 简要说明：加载授权菜单树
	  * 编写者：陈骑元
	  * 创建时间：2018年9月28日 下午3:50:41
	  * @param 说明
	  * @return 说明
	  */
	 TreeModel loadGrentMenuTree(Dto pDto);
	 /**
	  * 
	  * 简要说明：获取授权的角色
	  * 编写者：陈骑元
	  * 创建时间：2018年10月7日 下午9:43:52
	  * @param 说明
	  * @return 说明
	  */
	 RoleUser selectRoleUser(String userId);
}
