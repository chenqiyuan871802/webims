package com.ims.system.service.impl;

import com.ims.system.model.Dept;
import com.ims.system.mapper.DeptMapper;
import com.ims.system.service.DeptService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;
import com.ims.common.matatype.Dto;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.util.Query;

/**
 * <p>
 * 组织机构 服务实现类
 * </p>
 *
 * @author 陈骑元
 * @since 2018-05-14
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {
     
   
     /**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<Dept>
	 */
    @Override
	public List<Dept> list(Dto pDto){
	    
	    return baseMapper.list(pDto);
	};
    /**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return Page<Dept>
	 */
	@Override
	public Page<Dept> listPage(Dto pDto){
	    Query<Dept> query=new Query<Dept>(pDto);
	    query.setRecords(baseMapper.listPage(query,pDto));
	    return query;
	};
		
	/**
	 * 根据Dto模糊查询并返回数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<Dept>
	 */
	@Override
	public List<Dept> like(Dto pDto){
	
	    return baseMapper.list(pDto);
	
	};

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return Page<Dept>
	 */
	@Override
	public Page<Dept> likePage(Dto pDto){
	    Query<Dept> query=new Query<Dept>(pDto);
	    query.setRecords(baseMapper.likePage(query,pDto));
	    return query;
	};
}
