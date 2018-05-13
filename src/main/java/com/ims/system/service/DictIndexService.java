package com.ims.system.service;

import com.ims.system.model.DictIndex;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import com.ims.common.matatype.Dto;
import com.baomidou.mybatisplus.plugins.Page;
/**
 * <p>
 * 数据字典索引表 服务类
 * </p>
 *
 * @author 陈骑元
 * @since 2018-05-01
 */
public interface DictIndexService extends IService<DictIndex> {
      /**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<DictIndex>
	 */
	List<DictIndex> list(Dto pDto);
    /**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return Page<DictIndex>
	 */
	Page<DictIndex> listPage(Dto pDto);
		
	/**
	 * 根据Dto模糊查询并返回数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<DictIndex>
	 */
	List<DictIndex> like(Dto pDto);

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return Page<DictIndex>
	 */
	Page<DictIndex> likePage(Dto pDto);
}
