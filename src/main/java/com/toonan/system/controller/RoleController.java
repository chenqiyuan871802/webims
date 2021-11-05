package com.toonan.system.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.toonan.core.cache.WebplusCache;
import com.toonan.core.matatype.Dto;
import com.toonan.core.matatype.Dtos;
import com.toonan.core.util.WebplusFormater;
import com.toonan.core.util.WebplusUtil;
import com.toonan.core.vo.EasyuiTreeModel;
import com.toonan.core.vo.R;
import com.toonan.core.vo.UserToken;
import com.toonan.core.web.BaseController;
import com.toonan.system.constant.SystemCons;
import com.toonan.system.model.Role;
import com.toonan.system.service.RoleService;
import com.toonan.system.util.SystemCache;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author 陈骑元
 * @since 2018-10-02
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {
   
	private String prefix = "system/role/"; 
    @Autowired
    private RoleService roleService;
   
    /**
	 * 
	 * 简要说明：初始化页面 
	 * 编写者：陈骑元 
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("init")
	public String init() {

		return prefix + "roleList";
	}

	/**
	 * 
	 * 简要说明：分页查询 
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@RequestMapping("list")
	@ResponseBody
	public R list() {
		Dto pDto = Dtos.newDto(request);
		pDto.setOrder(" create_time DESC ");
		UserToken userToken=this.getUserToken();
		if(!SystemCons.SUPER_ADMIN.equals(userToken.getAccount())){  //如果不是超级管理员，只能查看自己创建角色
			pDto.put("createBy", userToken.getUserId());
		}
		Page<Role> page =roleService.likePage(pDto);
		WebplusCache.convertItem(page);
		return R.toPage(page);
	}

	/**
	 * 
	 * 简要说明： 跳转到新增页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("add")
	public String add() {

		return prefix + "addRole";
	}
	/**
	 * 
	 * 简要说明： 新增信息保存 
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
    //@RequiresPermissions("system:role:add")
	@PostMapping("save")
	@ResponseBody
	public R save(Role role) {
    	String userId=this.getUserId();
    	role.setCreateBy(userId);
    	role.setUpdateBy(userId);
		role.setCreateTime(WebplusUtil.getDateTime());
		role.setUpdateTime(WebplusUtil.getDateTime());
		boolean result = roleService.insert(role);
		if (result) {
			return R.ok();
		} else {
			return R.error("保存失败");
		}

	}
	/**
	 * 
	 * 简要说明： 跳转到编辑页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
    //@RequiresPermissions("system:role:edit")
	@GetMapping("edit")
	public String edit(String id,Model model) {
		Role role=roleService.selectById(id);
		model.addAttribute("role", role);
		return prefix + "editRole";
	}
	
	/**
	 * 
	 * 简要说明：修改信息
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
    //@RequiresPermissions("system:role:edit")
	@PostMapping("update")
	@ResponseBody
	public R update(Role role) {
    	String userId=this.getUserId();
    	role.setUpdateBy(userId);
		role.setUpdateTime(WebplusUtil.getDateTime());
		boolean result = roleService.updateById(role);
		if (result) {
			return R.ok();
		} else {
			return R.error("更新失败");
		}
		
	}
	
	/**
	 * 
	 * 简要说明：删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@RequiresPermissions("system:role:remove")
	@PostMapping("remove")
	@ResponseBody
	public R remove(String id) {
		boolean result = roleService.removeRole(id);
		if (result) {
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	
	/**
	 * 
	 * 简要说明： 授权用户
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("grantMenu")
	public String grantMenu(String id,Model model) {
		model.addAttribute("roleId",id);
		return prefix + "grantMenu";
	}
	
	/**
	 * 
	 * 简要说明：展示权限菜单树
	 * 编写者：陈骑元
	 * 创建时间：2018-12-18
	 * @param 说明
	 * @return 说明
	 */
	//@RequiresPermissions("system:role:roleMenu")
	@GetMapping("listRoleMenu")
	@ResponseBody
	public List<EasyuiTreeModel> listRoleMenu(String roleId) {
		UserToken userToken=this.getUserToken();
		List<EasyuiTreeModel> roleMenuList=  roleService.loadGrentMenuTree(roleId, userToken);
		return roleMenuList;
		
	}
	/**
	 * 
	 * 简要说明： 保存授权菜单
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	//@RequiresPermissions("system:role:roleMenu")
	@PostMapping("saveRoleMenu")
	@ResponseBody
	public R saveRoleMenu(String roleId,String menuIds) {
		String userId=this.getUserId();
		List<String> menuIdList=WebplusFormater.separatStringToList(menuIds);
		boolean result=roleService.batchSaveRoleMenu(roleId, menuIdList,userId);
		if (result) {
			return R.ok("授权菜单成功");
		} else {
			return R.error("授权菜单失败");
		}
	}
	/**
	 * 
	 * 简要说明： 授权用户
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("grantUser")
	public String grantUser(String id,Model model) {
		model.addAttribute("roleId",id);
		return prefix + "grantUser";
	}
	/**
	 * 
	 * 简要说明： 保存授权用户
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	//@RequiresPermissions("system:role:roleUser")
	@PostMapping("saveRoleUser")
	@ResponseBody
	public R saveRoleUser(String roleId,String userIds) {
		String createBy=this.getUserId();
		List<String> userIdList=WebplusFormater.separatStringToList(userIds);
		boolean result=roleService.batchSaveRoleUser(roleId, userIdList,createBy);
		if (result) {
			return R.ok("用户授权成功");
		} else {
			return R.error("用户授权失败");
		}
	}
	/**
	 * 
	 * 简要说明： 撤销授权用户
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("removeRoleUser")
	@ResponseBody
	public R removeRoleUser(String roleId,String userIds) {
		List<String> userIdList=WebplusFormater.separatStringToList(userIds);
		boolean result=roleService.batchRemoveRoleUser(roleId, userIdList);
		if (result) {
			return R.ok("撤销用户授权成功");
		} else {
			return R.error("撤销用户授权失败");
		}
	}
	/**
	 * 
	 * 简要说明：清空字典缓存
	 * 编写者：陈骑元
	 * 创建时间：2018年5月13日 下午11:09:04
	 * @param 说明
	 * @return 说明
	 */
	//@RequiresPermissions("system:role:refreshCache")
	@PostMapping("refreshCache")
	@ResponseBody
	public R refreshCache() {
		
		SystemCache.flushRoleMenu();
		
		return R.ok("清空缓存操作成功");
	}
}

