package com.ims.system.service.impl;

import com.ims.system.model.DictIndex;
import com.ims.system.mapper.DictIndexMapper;
import com.ims.system.service.DictIndexService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;
import com.ims.common.matatype.Dto;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.util.Query;

/**
 * <p>
 * 数据字典索引表 服务实现类
 * </p>
 *
 * @author 陈骑元
 * @since 2018-05-01
 */
@Service
public class DictIndexServiceImpl extends ServiceImpl<DictIndexMapper, DictIndex> implements DictIndexService {
     
   
     /**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<DictIndex>
	 */
    @Override
	public List<DictIndex> list(Dto pDto){
	    
	    return baseMapper.list(pDto);
	};
    /**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return Page<DictIndex>
	 */
	@Override
	public Page<DictIndex> listPage(Dto pDto){
	    Query<DictIndex> query=new Query<DictIndex>(pDto);
	    query.setRecords(baseMapper.listPage(query,pDto));
	    return query;
	};
		
	/**
	 * 根据Dto模糊查询并返回数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<DictIndex>
	 */
	@Override
	public List<DictIndex> like(Dto pDto){
	
	    return baseMapper.list(pDto);
	
	};

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return Page<DictIndex>
	 */
	@Override
	public Page<DictIndex> likePage(Dto pDto){
	    Query<DictIndex> query=new Query<DictIndex>(pDto);
	    query.setRecords(baseMapper.likePage(query,pDto));
	    return query;
	};
}
