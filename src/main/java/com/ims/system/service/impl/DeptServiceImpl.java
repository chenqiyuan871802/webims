package com.ims.system.service.impl;

import com.ims.system.model.Dept;
import com.ims.system.model.TreeModel;
import com.ims.system.constant.SystemCons;
import com.ims.system.mapper.DeptMapper;
import com.ims.system.service.DeptService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.List;

import com.ims.common.constant.IMSCons;
import com.ims.common.matatype.Dto;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.util.IMSUtil;
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
	}
	
	@Override
	public TreeModel loadDeptTree(Dto pDto) {
		//查询 根节点 dept=0;
	    Dept rootDept=baseMapper.selectById(SystemCons.TREE_ROOT_ID);
	    //如果数据库没有根节点 则创建一个根节点,并保存到数据库中
	    if(IMSUtil.isEmpty(rootDept)){
	    	rootDept=new Dept();
	    	rootDept.setDeptId(SystemCons.TREE_ROOT_ID);
	    	rootDept.setParentId("-1");
	    	rootDept.setDeptName(SystemCons.DEPT_ROOT_NAME);
	    	rootDept.setIconName(SystemCons.DEPT_ROOT_ICONCLS);
	    	rootDept.setCascadeId(SystemCons.TREE_ROOT_CASCADE_ID);
	    	rootDept.setIsDel( IMSCons.IS.NO);
	    	rootDept.setIsAutoExpand(IMSCons.IS.YES);
	    	rootDept.setSortNo(1);
	    	rootDept.setRemark("顶级机构不能进行移动和删除操作，只能进行修改");
	    	rootDept.setCreateTime(IMSUtil.getDateTime());
	    	baseMapper.insert(rootDept);
	    	
	    }
		pDto.put("is_del",  IMSCons.IS.NO);  //查询有效的组织机构信息
		pDto.setOrder(" LENGTH(cascade_id) ASC,sort_no ASC ");
		List<Dept> deptList=baseMapper.list(pDto);
		TreeModel rootModel=new TreeModel();
		rootModel.setText(rootDept.getDeptName());
		rootModel.setId(rootDept.getDeptId());
		if(IMSUtil.isNotEmpty(rootDept.getIconName())){
			rootModel.setIconCls(rootDept.getIconName());
		}else{
			rootModel.setIconCls(SystemCons.DEPT_ROOT_ICONCLS);
		}
		rootModel.setCascadeId(rootDept.getCascadeId());
		if(IMSCons.IS.NO.equals(rootDept.getIsAutoExpand())){
			rootModel.setState(SystemCons.TREE_STATE_CLOSED);
			
		}
		for(int i=0;i<deptList.size();i++){
		    Dept dept=deptList.get(i);
			
			String parentId=dept.getParentId();
			String icon_name=dept.getIconName();
			TreeModel treeModel=new TreeModel();
			treeModel.setId(dept.getDeptId());
			treeModel.setParentId(parentId);
			treeModel.setCascadeId(dept.getCascadeId());
			treeModel.setText(dept.getDeptName());
			if (IMSUtil.isNotEmpty(icon_name)) {
				treeModel.setIconCls(icon_name);
			}
			
			rootModel.add(treeModel);
		}
		return rootModel;
			  
	}
	
	@Override
	public String calc(Dto pDto) {
		// TODO Auto-generated method stub
		return baseMapper.calc(pDto);
	};
}
