package com.toonan.system.mapper;

import com.toonan.system.model.Log;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import com.toonan.core.matatype.Dto;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 系统日志表 Mapper 接口
 * </p>
 *
 * @author 陈骑元
 * @since 2020-11-30
 */
public interface LogMapper extends BaseMapper<Log> {

    /**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<Log>
	 */
	List<Log> list(Dto pDto);
    /**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return List<Log>
	 */
	List<Log> listPage(Pagination page,Dto pDto);
		
	/**
	 * 根据Dto模糊查询并返回数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<Log>
	 */
	List<Log> like(Dto pDto);

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<Log>
	 */
	List<Log> likePage(Pagination page,Dto pDto);
	
	/**
	 * 根据数学表达式进行数学运算
	 * 
	 * @param pDto
	 * @return String
	 */
	 String calc(Dto pDto);


}
