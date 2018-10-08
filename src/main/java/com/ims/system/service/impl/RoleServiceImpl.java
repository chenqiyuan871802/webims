package com.ims.system.service.impl;

import com.ims.system.model.Menu;
import com.ims.system.model.Role;
import com.ims.system.model.RoleMenu;
import com.ims.system.model.RoleUser;
import com.ims.system.model.TreeModel;
import com.ims.system.constant.SystemCons;
import com.ims.system.mapper.MenuMapper;
import com.ims.system.mapper.RoleMapper;
import com.ims.system.mapper.RoleMenuMapper;
import com.ims.system.mapper.RoleUserMapper;
import com.ims.system.service.RoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.ims.common.constant.IMSCons;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.Dtos;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.util.IMSUtil;
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
   @Autowired
   private MenuMapper menuMapper;
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
	/**
	 * 批量授权用户 每个用户只允许一个角色
	 */
	@Transactional
	@Override
	public boolean batchSaveRoleUser(String roleId, List<String> userIdList) {
		int row=0;
		for(String userId:userIdList){
			Dto columnDto=Dtos.newDto();
			columnDto.put("user_id", userId);
			roleUserMapper.deleteByMap(columnDto);  //删除原来角色授权
			RoleUser roleUser=new RoleUser();
			roleUser.setRoleId(roleId);
			roleUser.setUserId(userId);
			roleUser.setCreateTime(IMSUtil.getDateTime());
			row+=roleUserMapper.insert(roleUser);
		}
		return row>0;
	}
	/**
	 * 撤销用户授权
	 */
	@Override
	public boolean batchRemoveRoleUser(String roleId, List<String> userIdList) {
		int row=0;
		for(String userId:userIdList){
			Dto columnDto=Dtos.newDto();
			columnDto.put("user_id", userId);
			columnDto.put("role_id", roleId);
			row+=roleUserMapper.deleteByMap(columnDto);  //删除原来角色授权
		}
		return row>0;
	}

	/**
	 * 加载授权菜单树
	 */
	@Override
	public TreeModel loadGrentMenuTree(Dto pDto) {
		pDto.setOrder("LENGTH(cascade_id) ASC,sort_no ASC ");
		List<Menu> menuList=menuMapper.list(pDto);
		String roleId=pDto.getString("roleId");
		Dto columnDto=Dtos.newDto();
		columnDto.put("role_id", roleId);
		List<RoleMenu> grantMenuList=roleMenuMapper.selectByMap(columnDto);
		TreeModel rootModel=new TreeModel();
		rootModel.setText(SystemCons.MENU_ROOT_NAME);
		rootModel.setId(SystemCons.TREE_ROOT_ID);
		rootModel.setIconCls(SystemCons.MENU_ROOT_ICONCLS);
		rootModel.setCascadeId(SystemCons.TREE_ROOT_CASCADE_ID);
		for(int i=0;i<menuList.size();i++){
			Menu menu=menuList.get(i);
			String menuId=menu.getMenuId();
			String parentId=menu.getParentId();
			String iconName=menu.getIconName();
			TreeModel treeModel=new TreeModel();
			treeModel.setId(menuId);
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
			if(!SystemCons.TREE_ROOT_ID.equals(parentId)){
				if (checkGrantMenu(menuId, grantMenuList)) {
					treeModel.setChecked("true");
				}
			}
			
			rootModel.add(treeModel);
		}
		return rootModel;
	}
	
	/**
	 * 检查是否授权菜单
	 * 
	 * @return
	 */
	private boolean checkGrantMenu(String menuId, List<RoleMenu> grantMenuList) {
		for (RoleMenu roleMenu:grantMenuList) {
			 String grantMenuId=roleMenu.getMenuId();
			if (menuId.equals(grantMenuId)) {
				return true;
			}
		}
		return false;

	}
	/**
	 * 用户菜单授权
	 */
	@Transactional
	@Override
	public boolean batchSaveRoleMenu(String roleId, List<String> menuIdList) {
		int row=0;
		Dto columnDto=Dtos.newDto();
		columnDto.put("role_id",roleId);
		roleMenuMapper.deleteByMap(columnDto);  //清空原来授权菜单
		for(String menuId:menuIdList){
			RoleMenu roleMenu=new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);
			roleMenu.setCreateTime(IMSUtil.getDateTime());
			row+=roleMenuMapper.insert(roleMenu);
		}
		return row>0;
	}
	@Override
	public RoleUser selectRoleUser(String userId) {
		RoleUser entity=new RoleUser();
		entity.setUserId(userId);
		return roleUserMapper.selectOne(entity);
	}
}
