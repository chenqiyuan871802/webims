package com.ims.system.service.impl;

import com.ims.system.model.Param;
import com.ims.system.mapper.ParamMapper;
import com.ims.system.service.ParamService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;
import com.ims.common.matatype.Dto;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.util.Query;

/**
 * <p>
 * 键值参数 服务实现类
 * </p>
 *
 * @author 陈骑元
 * @since 2018-04-09
 */
@Service
public class ParamServiceImpl extends ServiceImpl<ParamMapper, Param> implements ParamService {
     
   
     /**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<Param>
	 */
    @Override
	public List<Param> list(Dto pDto){
	    
	    return baseMapper.list(pDto);
	};
    /**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return Page<Param>
	 */
	@Override
	public Page<Param> listPage(Dto pDto){
	    Query<Param> query=new Query<Param>(pDto);
	    query.setRecords(baseMapper.listPage(query,pDto));
	    return query;
	};
		
	/**
	 * 根据Dto模糊查询并返回数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<Param>
	 */
	@Override
	public List<Param> like(Dto pDto){
	
	    return baseMapper.list(pDto);
	
	};

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return Page<Param>
	 */
	@Override
	public Page<Param> likePage(Dto pDto){
	    Query<Param> query=new Query<Param>(pDto);
	    query.setRecords(baseMapper.likePage(query,pDto));
	    return query;
	};
}
