package com.toonan.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.toonan.core.cache.WebplusCache;
import com.toonan.core.constant.WebplusCons;
import com.toonan.core.matatype.Dto;
import com.toonan.core.matatype.Dtos;
import com.toonan.core.util.WebplusSqlHelp;
import com.toonan.core.util.WebplusUtil;
import com.toonan.core.vo.EasyuiTreeModel;
import com.toonan.core.vo.R;
import com.toonan.core.web.BaseController;
import com.toonan.system.constant.SystemCons;
import com.toonan.system.model.Menu;
import com.toonan.system.service.MenuService;
import com.toonan.system.util.SystemCache;

/**
 * <p>
 * 菜单配置 前端控制器
 * </p>
 *
 * @author 陈骑元
 * @since 2018-09-28
 */
@Controller
@RequestMapping("/system/menu")
public class MenuController extends BaseController {
   
	private String prefix = "system/menu/"; 
    @Autowired
    private MenuService menuService;
    
	/**
	 * 
	 * 简要说明：初始化页面 
	 * 编写者：陈骑元 
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("init")
	public String init() {

		return prefix + "menuList";
	}

	/**
	 * 
	 * 加载菜单树
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "loadMenuTree")
	@ResponseBody
	public List<EasyuiTreeModel> loadMenuTree(HttpServletRequest request, HttpServletResponse response) {
		Dto pDto=Dtos.newDto(request);
		EasyuiTreeModel treeModel=menuService.loadTree(pDto);
		List<EasyuiTreeModel> dataList=Lists.newArrayList();
		dataList.add(treeModel);
		return dataList;
	}
	/**
	 * 
	 * 简要说明：分页查询 
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@RequestMapping("list")
	@ResponseBody
	public R list() {
		Dto pDto = Dtos.newDto(request);
		pDto.setOrder("cascade_id  ASC,sort_no ASC ");
		Page<Menu> page =menuService.likePage(pDto);
		
		WebplusCache.convertItem(page);
		return R.toPage(page);
	}

	/**
	 * 
	 * 简要说明： 跳转新增菜单页面
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("add")
	public String add() {
		
		return prefix + "addMenu";
	}
	/**
	 * 
	 * 简要说明： 跳转到新增子菜单页面
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("addSubMenu")
	public String addSubMenu() {

		return prefix + "addSubMenu";
	}
	/**
	 * 
	 * 简要说明： 新增信息保存 
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("saveParentMenu")
	@ResponseBody
	public R saveParentMenu(Menu menu) {
		EntityWrapper<Menu> countWrapper = new EntityWrapper<Menu>();
		List<String> menuTypeList=Lists.newArrayList();
		menuTypeList.add(SystemCons.MODULE_TYPE_SUB);
		menuTypeList.add(SystemCons.MODULE_TYPE_PARENT);
		WebplusSqlHelp.eq(countWrapper, "menu_code", menu.getMenuCode());
		countWrapper.in("menu_type", menuTypeList);
		int count=menuService.selectCount(countWrapper);
		if(count>0){
			return R.warn("菜单编码已被占用，请使用其它编码。");
		}
		
		Dto calcDto = Dtos.newCalcDto("MAX(cascade_id)");
		calcDto.put("parentId", menu.getParentId());
		String maxCascadeId =menuService.calc(calcDto);
		if(WebplusUtil.isEmpty(maxCascadeId)){
			Menu parentMenu=menuService.selectById(menu.getParentId());
			if(WebplusUtil.isEmpty(parentMenu)){
					maxCascadeId="0.0000";
			}else{
				maxCascadeId=parentMenu.getCascadeId()+".0000";
			}
				
		}
		String curCascadeId=WebplusUtil.createCascadeId(maxCascadeId, 9999);
		menu.setCascadeId(curCascadeId);
		menu.setCreateTime(WebplusUtil.getDateTime());
		menu.setUpdateTime(WebplusUtil.getDateTime());
		boolean result = menuService.insert(menu);
		if (result) {
			return R.ok();
		} else {
			return R.error("保存失败");
		}
		
	}
	/**
	 * 
	 * 简要说明： 新增信息保存 
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("saveSubMenu")
	@ResponseBody
	public R saveSubMenu(Menu menu) {
		EntityWrapper<Menu> countWrapper = new EntityWrapper<Menu>();
		List<String> menuTypeList=Lists.newArrayList();
		menuTypeList.add(SystemCons.MODULE_TYPE_SUB);
		menuTypeList.add(SystemCons.MODULE_TYPE_PARENT);
		WebplusSqlHelp.eq(countWrapper, "menu_code", menu.getMenuCode());
		countWrapper.in("menu_type", menuTypeList);
		int count=menuService.selectCount(countWrapper);
		if(count>0){
			return R.warn("菜单编码已被占用，请使用其它编码。");
		}
		
		Dto calcDto = Dtos.newCalcDto("MAX(cascade_id)");
		calcDto.put("parentId", menu.getParentId());
		String maxCascadeId =menuService.calc(calcDto);
		if(WebplusUtil.isEmpty(maxCascadeId)){
			Menu parentMenu=menuService.selectById(menu.getParentId());
			if(WebplusUtil.isEmpty(parentMenu)){
				maxCascadeId="0.0000";
			}else{
				maxCascadeId=parentMenu.getCascadeId()+".0000";
			}
			
		}
		String curCascadeId=WebplusUtil.createCascadeId(maxCascadeId, 9999);
		menu.setCascadeId(curCascadeId);
		menu.setCreateTime(WebplusUtil.getDateTime());
		menu.setUpdateTime(WebplusUtil.getDateTime());
		boolean result = menuService.insert(menu);
		if (result) {
			return R.ok();
		} else {
			return R.error("保存失败");
		}
		
	}
	/**
	 * 
	 * 简要说明： 新增信息保存 
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	//@RequiresPermissions("system:menu:add")
	@PostMapping("save")
	@ResponseBody
	public R save(Menu menu) {
		String userId=this.getUserId();
		
		if(!SystemCons.MODULE_TYPE_PARENT.equals(menu.getModuleType())){
			EntityWrapper<Menu> countWrapper = new EntityWrapper<Menu>();
			WebplusSqlHelp.eq(countWrapper, "parent_id", menu.getParentId());
			WebplusSqlHelp.eq(countWrapper, "menu_code", menu.getMenuCode());
			int count=menuService.selectCount(countWrapper);
			if(count>0){
				return R.warn("菜单编码已被占用，请使用其它编码。");
			}
		}
		Dto calcDto = Dtos.newCalcDto("MAX(cascade_id)");
		calcDto.put("parentId", menu.getParentId());
		String maxCascadeId =menuService.calc(calcDto);
		if(WebplusUtil.isEmpty(maxCascadeId)){
			Menu parentMenu=menuService.selectById(menu.getParentId());
			if(WebplusUtil.isEmpty(parentMenu)){
				maxCascadeId="0.0000";
			}else{
				maxCascadeId=parentMenu.getCascadeId()+".0000";
			}
			
		}
		
		Date now=WebplusUtil.getDateTime();
		String curCascadeId=WebplusUtil.createCascadeId(maxCascadeId, 9999);
		menu.setCascadeId(curCascadeId);
		menu.setMenuId(WebplusUtil.uuid());
		menu.setCreateBy(userId);
		menu.setUpdateBy(userId);
		menu.setCreateTime(now);
		menu.setUpdateTime(now);
		boolean result =menuService.insert(menu);
		if (result) {
			SystemCache.romveCacheMenu();
			return R.ok();
		} else {
			return R.error("保存失败");
		}
	

	}
	/**
	 * 
	 * 简要说明： 跳转到编辑页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	//@RequiresPermissions("system:menu:edit")
	@GetMapping("edit")
	public String edit(String id,Model model) {
		Menu menu=menuService.selectById(id);
		model.addAttribute("menu", menu);
	    return prefix + "editMenu";
	}
	
	/**
	 * 
	 * 简要说明：修改信息
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
  //  @RequiresPermissions("system:menu:edit")
	@PostMapping("update")
	@ResponseBody
	public R update(Menu menu) {
    	String userId=this.getUserId();
    	Date updateTime=WebplusUtil.getDateTime();
		Menu menuOld=menuService.selectById(menu.getMenuId());
		String menuCode=menu.getMenuCode();
		if(WebplusUtil.isNotEmpty(menuCode)){
			if(!SystemCons.MODULE_TYPE_PARENT.equals(menu.getModuleType())){
				if(!menuOld.getMenuCode().equals(menuCode)){  //菜单编码有改变
					EntityWrapper<Menu> countWrapper = new EntityWrapper<Menu>();
					WebplusSqlHelp.eq(countWrapper, "parent_id", menuOld.getParentId());
					WebplusSqlHelp.eq(countWrapper, "menu_code", menu.getMenuCode());
					int count=menuService.selectCount(countWrapper);
					if(count>0){
						return R.warn("菜单编码已被占用，请使用其它编码。");
					}
				}
			}
			
		}
		if(SystemCons.MODULE_TYPE_PARENT.equals(menu.getModuleType())){
			if(!menuOld.getMenuType().equals(menu.getMenuType())){  //菜单类型变化了，级联更新下级菜单
				EntityWrapper<Menu> menuWrapper = new EntityWrapper<Menu>();
				WebplusSqlHelp.rlike(menuWrapper, "cascade_id", menuOld.getCascadeId());
				Menu menuEntity=new Menu();
				menuEntity.setMenuType(menu.getMenuType());
				menuEntity.setUpdateBy(userId);
				menuEntity.setUpdateTime(updateTime);
				menuService.update(menuEntity, menuWrapper);
			}
		}
		if(!SystemCons.MODULE_TYPE_SUB.equals(menu.getModuleType())){
			String status=menu.getStatus();
			if(!menuOld.getStatus().equals(status)){  //启用是否发生变化，如果发生变化级联锁定下级
				if(WebplusCons.ENABLED_NO.equals(status)){  //只有禁用的时候在级联更新
					EntityWrapper<Menu> menuWrapper = new EntityWrapper<Menu>();
					WebplusSqlHelp.rlike(menuWrapper, "cascade_id", menuOld.getCascadeId());
					Menu menuEntity=new Menu();
					menuEntity.setStatus(status);
					menuEntity.setUpdateBy(userId);
					menuEntity.setUpdateTime(updateTime);
					menuService.update(menuEntity, menuWrapper);
				}
				
			}
		}
		menu.setUpdateBy(userId);
		menu.setUpdateTime(updateTime);
		boolean result = menuService.updateById(menu);
		if (result) {
			SystemCache.romveCacheMenu();
			return R.ok();
		} else {
			return R.error("更新失败");
		}
		
	}
    /**
	 * 
	 * 简要说明：修改信息
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("updateSubMenu")
	@ResponseBody
	public R updateSubMenu(Menu menu) {
		Menu menuOld=menuService.selectById(menu.getMenuId());
		if(!menuOld.getMenuCode().equals(menu.getMenuCode())){  //菜单编码有改变
			EntityWrapper<Menu> countWrapper = new EntityWrapper<Menu>();
			List<String> menuTypeList=Lists.newArrayList();
			menuTypeList.add(SystemCons.MODULE_TYPE_SUB);
			menuTypeList.add(SystemCons.MODULE_TYPE_PARENT);
			WebplusSqlHelp.eq(countWrapper, "menu_code", menu.getMenuCode());
			countWrapper.in("menu_type", menuTypeList);
			int count=menuService.selectCount(countWrapper);
			if(count>0){
				return R.warn("菜单编码已被占用，请使用其它编码。");
			}
		}
		menu.setUpdateTime(WebplusUtil.getDateTime());
		boolean result = menuService.updateById(menu);
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
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	//@RequiresPermissions("system:menu:remove")
	@PostMapping("remove")
	@ResponseBody
	public R remove(String id) {
		Menu menu=menuService.selectById(id);
		String moduleType=menu.getModuleType();
		 //如果是父菜单则进行下面校验校验是否存在子菜单，存在子菜单不允许删除
		if(SystemCons.MODULE_TYPE_PARENT.equals(moduleType)){ 
			EntityWrapper<Menu> countWrapper = new EntityWrapper<Menu>();
			WebplusSqlHelp.eq(countWrapper, "parent_id", id);
			int row=menuService.selectCount(countWrapper);
			if(row>0){
				
				return R.warn("操作失败，当前菜单下存在子菜单，不允许删除，请先删除子菜单然后再删除。");
			}
		}
		if(SystemCons.MODULE_TYPE_SUB.equals(moduleType)){ //如果是子菜单则删除按钮菜单
			EntityWrapper<Menu> removeWrapper = new EntityWrapper<Menu>();
			WebplusSqlHelp.eq(removeWrapper, "parent_id", id);
			menuService.delete(removeWrapper);
		}
		boolean result = menuService.deleteById(id);
		if (result) {
			SystemCache.romveCacheMenu();
			return R.ok("删除成功");
		} else {
			return R.error("删除失败");
		}
		
	}
	

	/**
	 * 
	 * 简要说明：刷新菜单缓存
	 * 编写者：陈骑元
	 * 创建时间：2018年5月13日 下午11:09:04
	 * @param 说明
	 * @return 说明
	 */
	//@RequiresPermissions("system:menu:refreshCache")
	@PostMapping("refreshCache")
	@ResponseBody
	public R refreshCache() {
		SystemCache.romveCacheMenu();
	    
		return R.ok("刷新菜单缓存操作成功");
	}
}

