package com.ims.system.service;

import com.ims.system.model.Param;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import com.ims.common.matatype.Dto;
import com.baomidou.mybatisplus.plugins.Page;
/**
 * <p>
 * 键值参数 服务类
 * </p>
 *
 * @author 陈骑元
 * @since 2018-04-09
 */
public interface ParamService extends IService<Param> {
      /**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<Param>
	 */
	List<Param> list(Dto pDto);
    /**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return Page<Param>
	 */
	Page<Param> listPage(Dto pDto);
		
	/**
	 * 根据Dto模糊查询并返回数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<Param>
	 */
	List<Param> like(Dto pDto);

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return Page<Param>
	 */
	Page<Param> likePage(Dto pDto);
}
