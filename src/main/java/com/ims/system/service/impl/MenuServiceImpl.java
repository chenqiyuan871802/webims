package com.ims.system.service.impl;

import com.ims.system.model.Menu;
import com.ims.system.model.TreeModel;
import com.ims.system.constant.SystemCons;
import com.ims.system.mapper.MenuMapper;
import com.ims.system.service.MenuService;
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
 * 菜单配置 服务实现类
 * </p>
 *
 * @author 陈骑元
 * @since 2018-09-28
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
     
   
     /**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<Menu>
	 */
    @Override
	public List<Menu> list(Dto pDto){
	    
	    return baseMapper.list(pDto);
	};
    /**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return Page<Menu>
	 */
	@Override
	public Page<Menu> listPage(Dto pDto){
	    Query<Menu> query=new Query<Menu>(pDto);
	    query.setRecords(baseMapper.listPage(query,pDto));
	    return query;
	};
		
	/**
	 * 根据Dto模糊查询并返回数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return List<Menu>
	 */
	@Override
	public List<Menu> like(Dto pDto){
	
	    return baseMapper.like(pDto);
	
	};

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return Page<Menu>
	 */
	@Override
	public Page<Menu> likePage(Dto pDto){
	    Query<Menu> query=new Query<Menu>(pDto);
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
	  * 加载菜单树
	  */
	@Override
	public TreeModel loadTree(Dto pDto) {
		pDto.setOrder("LENGTH(cascade_id) ASC,sort_no ASC ");
		List<Menu> menuList=this.list(pDto);
		TreeModel rootModel=new TreeModel();
		rootModel.setText(SystemCons.MENU_ROOT_NAME);
		rootModel.setId(SystemCons.TREE_ROOT_ID);
		rootModel.setIconCls(SystemCons.MENU_ROOT_ICONCLS);
		rootModel.setCascadeId(SystemCons.TREE_ROOT_CASCADE_ID);
		for(int i=0;i<menuList.size();i++){
			Menu menu=menuList.get(i);
			String parentId=menu.getParentId();
			String iconName=menu.getIconName();
			TreeModel treeModel=new TreeModel();
			treeModel.setId(menu.getMenuId());
			treeModel.setParentId(parentId);
			treeModel.setCascadeId(menu.getCascadeId());
			treeModel.setText(menu.getMenuName());
			if (IMSUtil.isNotEmpty(iconName)) {
				treeModel.setIconCls(iconName);
			}
			String is_auto_expand = menu.getIsAutoExpand();
			if(IMSCons.IS.NO.equals(is_auto_expand)){
				if(!SystemCons.TREE_ROOT_ID.equals(parentId)){
					treeModel.setState(SystemCons.TREE_STATE_CLOSED);
				}
			}
			
			rootModel.add(treeModel);
		}
		return rootModel;
	}
	 
}
