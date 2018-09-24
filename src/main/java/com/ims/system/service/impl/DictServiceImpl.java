package com.ims.system.service.impl;

import com.ims.system.model.Dict;
import com.ims.system.mapper.DictMapper;
import com.ims.system.service.DictService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;
import com.ims.common.matatype.Dto;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.util.Query;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author 陈骑元
 * @since 2018-05-01
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
     
   
     /**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<Dict>
	 */
    @Override
	public List<Dict> list(Dto pDto){
	    
	    return baseMapper.list(pDto);
	};
    /**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return Page<Dict>
	 */
	@Override
	public Page<Dict> listPage(Dto pDto){
	    Query<Dict> query=new Query<Dict>(pDto);
	    query.setRecords(baseMapper.listPage(query,pDto));
	    return query;
	};
		
	/**
	 * 根据Dto模糊查询并返回数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<Dict>
	 */
	@Override
	public List<Dict> like(Dto pDto){
	
	    return baseMapper.like(pDto);
	
	};

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return Page<Dict>
	 */
	@Override
	public Page<Dict> likePage(Dto pDto){
	    Query<Dict> query=new Query<Dict>(pDto);
	    query.setRecords(baseMapper.likePage(query,pDto));
	    return query;
	};
}
