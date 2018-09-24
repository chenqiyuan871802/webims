package com.ims.system.service;

import com.ims.system.model.Dept;
import com.ims.system.model.TreeModel;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import com.ims.common.matatype.Dto;
import com.baomidou.mybatisplus.plugins.Page;
/**
 * <p>
 * 组织机构 服务类
 * </p>
 *
 * @author 陈骑元
 * @since 2018-05-14
 */
public interface DeptService extends IService<Dept> {
      /**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<Dept>
	 */
	List<Dept> list(Dto pDto);
    /**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return Page<Dept>
	 */
	Page<Dept> listPage(Dto pDto);
		
	/**
	 * 根据Dto模糊查询并返回数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<Dept>
	 */
	List<Dept> like(Dto pDto);

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return Page<Dept>
	 */
	Page<Dept> likePage(Dto pDto);
	/**
	 * 根据数学表达式进行数学运算
	 * 
	 * @param pDto
	 * @return String
	 */
	 String calc(Dto pDto);
	/**
	 * 
	 * 简要说明：加载树模型
	 * 编写者：陈骑元
	 * 创建时间：2018年9月16日 下午7:56:09
	 * @param 说明
	 * @return 说明
	 */
	TreeModel loadDeptTree(Dto pDto);
	/**
	 * 
	 * 简要说明：更新处理机构信息
	 * 编写者：陈骑元
	 * 创建时间：2018年9月23日 上午8:48:28
	 * @param 说明
	 * @return 说明
	 */
	boolean updateDept(Dept dept);
}
