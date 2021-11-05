package com.toonan.system.service;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.toonan.core.constant.WebplusCons;
import com.toonan.core.matatype.Dto;
import com.toonan.core.matatype.Dtos;
import com.toonan.core.util.WebplusUtil;
import com.toonan.core.vo.EasyuiTreeModel;
import com.toonan.core.vo.Query;
import com.toonan.core.vo.TreeModel;
import com.toonan.core.vo.UserToken;
import com.toonan.system.constant.SystemCons;
import com.toonan.system.mapper.RoleMapper;
import com.toonan.system.mapper.RoleMenuMapper;
import com.toonan.system.mapper.RoleUserMapper;
import com.toonan.system.model.Menu;
import com.toonan.system.model.Role;
import com.toonan.system.model.RoleMenu;
import com.toonan.system.model.RoleUser;
import com.toonan.system.util.SystemCache;


/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 陈骑元
 * @since 2018-10-02
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role>{
   
   @Autowired
   private RoleMenuMapper roleMenuMapper;
   @Autowired
   private RoleUserMapper roleUserMapper;
     /**
	 * 根据Dto查询并返回数据持久化对象集合
	 * 
	 * @return List<Role>
	 */
	public List<Role> list(Dto pDto){
	    
	    return baseMapper.list(pDto);
	};
    /**
	 * 根据Dto查询并返回分页数据持久化对象集合
	 * 
	 * @return Page<Role>
	 */
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
	public List<Role> like(Dto pDto){
	
	    return baseMapper.like(pDto);
	
	};

	/**
	 * 根据Dto模糊查询并返回分页数据持久化对象集合(字符型字段模糊匹配，其余字段精确匹配)
	 * 
	 * @return Page<Role>
	 */
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
	 public String calc(Dto pDto){
	 
	     return baseMapper.calc(pDto);
	 }
	 /**
	  * 移除角色
	  */
	@Transactional
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
	public boolean batchSaveRoleUser(String roleId, List<String> userIdList,String createBy) {
		int row=0;
		for(String userId:userIdList){
			RoleUser roleUser=new RoleUser();
			roleUser.setRoleId(roleId);
			roleUser.setUserId(userId);
			roleUser.setCreateBy(createBy);
			roleUser.setCreateTime(WebplusUtil.getDateTime());
			row+=roleUserMapper.insert(roleUser);
		}
		return row>0;
	}
	/**
	 * 撤销用户授权
	 */
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
     * 
     * 简要说明：获取授权角色菜单树
     * 编写者：陈骑元（chenqiyuan@toonan.com）
     * 创建时间： 2021年8月18日 下午10:40:36 
     * @param 说明
     * @return 说明
     */
	public List<EasyuiTreeModel> loadGrentMenuTree(String roleId,UserToken userToken){
		List<Menu> menuList =Lists.newArrayList();
		if(SystemCons.SUPER_ADMIN.equals(userToken.getAccount())){  //如果是超级管理员具有所有的菜单授权
			
			menuList =SystemCache.getCacheMenu(); 
		}else{  //普通管理员只具有他具有菜单授权
		    menuList = SystemCache.getCacheRoleMenu(userToken.getUserId());
		}
		List<EasyuiTreeModel> treeList=Lists.newArrayList();
		EntityWrapper<RoleMenu> roleMenuWrapper = new EntityWrapper<RoleMenu>();
		roleMenuWrapper.eq("role_id", roleId);
		List<RoleMenu> roleMenuList=roleMenuMapper.selectList(roleMenuWrapper);
		EasyuiTreeModel rootModel=new EasyuiTreeModel();
		rootModel.setText(SystemCons.MENU_ROOT_NAME);
		rootModel.setId(SystemCons.TREE_ROOT_ID);
		rootModel.setIconCls(SystemCons.MENU_ROOT_ICONCLS);
		rootModel.setCascadeId(SystemCons.TREE_ROOT_CASCADE_ID);
		for(int i=0;i<menuList.size();i++){
			Menu menu=menuList.get(i);
			String menuId=menu.getMenuId();
			String parentId=menu.getParentId();
			String iconName=menu.getIconName();
			EasyuiTreeModel treeModel=new EasyuiTreeModel();
			treeModel.setId(menuId);
			treeModel.setParentId(parentId);
			treeModel.setCascadeId(menu.getCascadeId());
			treeModel.setText(menu.getMenuName());
			if (WebplusUtil.isNotEmpty(iconName)) {
				treeModel.setIconCls(iconName);
			}
			String is_auto_expand = menu.getIsAutoExpand();
			if(WebplusCons.WHETHER_NO.equals(is_auto_expand)){
				if(!SystemCons.TREE_ROOT_ID.equals(parentId)){
					treeModel.setState(SystemCons.TREE_STATE_CLOSED);
				}
			}
			if(!SystemCons.TREE_ROOT_ID.equals(parentId)){
				if (checkRoleMenu(menuId, roleMenuList)) {
					treeModel.setChecked("true");
				}
			}
			
			rootModel.add(treeModel);
		}
		treeList.add(rootModel);
		return treeList;
	}
	 /**
	  * 
	  * 简要说明：获取授权菜单
	  * 编写者：陈骑元
	  * 创建时间：2018年12月28日 上午11:01:06
	  * @param 说明
	  * @return 说明
	  */
	 public List<TreeModel> listRoleMenu(String roleId,UserToken userToken){
		 List<Menu> menuList =Lists.newArrayList();
			if(SystemCons.SUPER_ADMIN.equals(userToken.getAccount())){  //如果是超级管理员具有所有的菜单授权
				
				menuList =SystemCache.getCacheMenu(); 
			}else{  //普通管理员只具有他具有菜单授权
			    menuList = SystemCache.getCacheRoleMenu(userToken.getUserId());
			}
		  
			EntityWrapper<RoleMenu> roleMenuWrapper = new EntityWrapper<RoleMenu>();
			roleMenuWrapper.eq("role_id", roleId);
			List<RoleMenu> roleMenuList=roleMenuMapper.selectList(roleMenuWrapper);
			List<TreeModel> ztreeModelList=Lists.newArrayList();
			TreeModel rootZtree=new TreeModel();
			rootZtree.setId(SystemCons.TREE_ROOT_ID);
			rootZtree.setName(SystemCons.MENU_ROOT_NAME);
			rootZtree.setOpen(true);//展开节点
			if(roleMenuList.size()>0){
				rootZtree.setChecked(true);
			}
			ztreeModelList.add(rootZtree);
			for(Menu menu:menuList){
				TreeModel ztree=new TreeModel();
				String menuId=menu.getMenuId();
				ztree.setId(menuId);
				ztree.setName(menu.getMenuName());
				ztree.setpId(menu.getParentId());
				ztree.setParent(true);
				if(WebplusCons.WHETHER_YES.equals(menu.getIsAutoExpand())){
					ztree.setOpen(true);//展开节点
				}else{
					ztree.setOpen(false);//展开节点
				}
				if(checkRoleMenu(menuId,roleMenuList)){
					ztree.setChecked(true);
				}
				ztree.setChildren(null);
				ztreeModelList.add(ztree);
			}
			
		 return ztreeModelList;
		 
	 }
	
	/**
	 * 检查是否授权菜单
	 * 
	 * @return
	 */
	private boolean checkRoleMenu(String menuId, List<RoleMenu> grantMenuList) {
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
	public boolean batchSaveRoleMenu(String roleId, List<String> menuIdList,String userId) {
		int row=0;
		Dto columnDto=Dtos.newDto();
		columnDto.put("role_id",roleId);
		roleMenuMapper.deleteByMap(columnDto);  //清空原来授权菜单
		for(String menuId:menuIdList){
			RoleMenu roleMenu=new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);
			roleMenu.setCreateBy(userId);
			roleMenu.setCreateTime(WebplusUtil.getDateTime());
			row+=roleMenuMapper.insert(roleMenu);
		}
		return row>0;
	}
	/**
	 * 
	 * 简要说明：选择角色用户
	 * 编写者：陈骑元
	 * 创建时间：2019年1月21日 下午10:27:39
	 * @param 说明
	 * @return 说明
	 */
	public RoleUser selectRoleUser(String userId) {
		RoleUser entity=new RoleUser();
		entity.setUserId(userId);
		return roleUserMapper.selectOne(entity);
	}
}
