package com.ims.system.service.impl;

import com.ims.system.model.Role;
import com.ims.system.mapper.RoleMapper;
import com.ims.system.mapper.RoleMenuMapper;
import com.ims.system.mapper.RoleUserMapper;
import com.ims.system.service.RoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.Dtos;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.util.Query;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 陈骑元
 * @since 2018-10-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
   
   @Autowired
   private RoleMenuMapper roleMenuMapper;
   @Autowired
   private RoleUserMapper roleUserMapper;
     /**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<Role>
	 */
    @Override
	public List<Role> list(Dto pDto){
	    
	    return baseMapper.list(pDto);
	};
    /**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return Page<Role>
	 */
	@Override
	public Page<Role> listPage(Dto pDto){
	    Query<Role> query=new Query<Role>(pDto);
	    query.setRecords(baseMapper.listPage(query,pDto));
	    return query;
	};
		
	/**
	 * 根据Dto模糊查询并返回数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<Role>
	 */
	@Override
	public List<Role> like(Dto pDto){
	
	    return baseMapper.like(pDto);
	
	};

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return Page<Role>
	 */
	@Override
	public Page<Role> likePage(Dto pDto){
	    Query<Role> query=new Query<Role>(pDto);
	    query.setRecords(baseMapper.likePage(query,pDto));
	    return query;
	};
	/**
	 * 根据数学表达式进行数学运算
	 * 
	 * @param pDto
	 * @return String
	 */
	 @Override
	 public String calc(Dto pDto){
	 
	     return baseMapper.calc(pDto);
	 }
	 /**
	  * 移除角色
	  */
	@Transactional
	@Override
	public boolean removeRole(String roleId) {
	    Dto columnDto=Dtos.newDto();
	    columnDto.put("role_id", roleId);
	    roleMenuMapper.deleteByMap(columnDto);
	    roleUserMapper.deleteByMap(columnDto);
	    int row=baseMapper.deleteById(roleId);
		return row>0;
	}
	 
}
