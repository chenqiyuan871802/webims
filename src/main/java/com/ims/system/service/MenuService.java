package com.ims.system.service;

import com.ims.system.model.Menu;
import com.ims.system.model.TreeModel;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import com.ims.common.matatype.Dto;
import com.baomidou.mybatisplus.plugins.Page;
/**
 * <p>
 * 菜单配置 服务类
 * </p>
 *
 * @author 陈骑元
 * @since 2018-09-28
 */
public interface MenuService extends IService<Menu> {
      /**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<Menu>
	 */
	List<Menu> list(Dto pDto);
    /**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return Page<Menu>
	 */
	Page<Menu> listPage(Dto pDto);
		
	/**
	 * 根据Dto模糊查询并返回数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<Menu>
	 */
	List<Menu> like(Dto pDto);

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return Page<Menu>
	 */
	Page<Menu> likePage(Dto pDto);
	/**
	 * 根据数学表达式进行数学运算
	 * 
	 * @param pDto
	 * @return String
	 */
	 String calc(Dto pDto);
	 /**
	  * 
	  * 简要说明：加载菜单树
	  * 编写者：陈骑元
	  * 创建时间：2018年9月28日 下午3:50:41
	  * @param 说明
	  * @return 说明
	  */
	 TreeModel loadTree(Dto pDto);
	
}
