package com.ims.system.controller;

import org.springframework.web.bind.annotation.*;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.constant.IMSCons;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.Dtos;
import com.ims.common.util.IMSCxt;
import com.ims.common.util.IMSFormater;
import com.ims.common.util.IMSUtil;
import com.ims.common.util.JsonUtil;
import com.ims.common.util.PageDto;
import com.ims.common.util.R;
import com.ims.common.util.SqlHelpUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ims.system.constant.SystemCons;
import com.ims.system.model.Menu;
import com.ims.system.model.TreeModel;
import com.ims.system.service.MenuService;
import org.springframework.stereotype.Controller;
import com.ims.common.web.BaseController;

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
	@RequestMapping(value = "loadTree")
	public void loadTree(HttpServletRequest request, HttpServletResponse response) {
		Dto pDto=Dtos.newDto(request);
		TreeModel treeModel=menuService.loadTree(pDto);
		String outString ="["+JsonUtil.toJson(treeModel)+"]";
		IMSCxt.write(response, outString);
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
	public PageDto list() {
		Dto pDto = Dtos.newDto(request);
		pDto.setOrder("LENGTH(cascade_id) ASC,sort_no ASC ");
		Page<Menu> page =menuService.likePage(pDto);
		return new PageDto(page);
	}

	/**
	 * 
	 * 简要说明： 跳转到新增父菜单页面
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("addParentMenu")
	public String addParentMenu() {
		
		return prefix + "addParentMenu";
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
		menuTypeList.add(SystemCons.MENU_TYPE_SUB);
		menuTypeList.add(SystemCons.MENU_TYPE_PARENT);
		SqlHelpUtil.eq(countWrapper, "menu_code", menu.getMenuCode());
		countWrapper.in("menu_type", menuTypeList);
		int count=menuService.selectCount(countWrapper);
		if(count>0){
			return R.warn("菜单编码已被占用，请使用其它编码。");
		}
		
		Dto calcDto = Dtos.newCalcDto("MAX(cascade_id)");
		calcDto.put("parentId", menu.getParentId());
		String maxCascadeId =menuService.calc(calcDto);
		if(IMSUtil.isEmpty(maxCascadeId)){
			Menu parentMenu=menuService.selectById(menu.getParentId());
			if(IMSUtil.isEmpty(parentMenu)){
					maxCascadeId="0.0000";
			}else{
				maxCascadeId=parentMenu.getCascadeId()+".0000";
			}
				
		}
		String curCascadeId=IMSUtil.createCascadeId(maxCascadeId, 9999);
		menu.setCascadeId(curCascadeId);
		menu.setCreateTime(IMSUtil.getDateTime());
		menu.setUpdateTime(IMSUtil.getDateTime());
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
		menuTypeList.add(SystemCons.MENU_TYPE_SUB);
		menuTypeList.add(SystemCons.MENU_TYPE_PARENT);
		SqlHelpUtil.eq(countWrapper, "menu_code", menu.getMenuCode());
		countWrapper.in("menu_type", menuTypeList);
		int count=menuService.selectCount(countWrapper);
		if(count>0){
			return R.warn("菜单编码已被占用，请使用其它编码。");
		}
		
		Dto calcDto = Dtos.newCalcDto("MAX(cascade_id)");
		calcDto.put("parentId", menu.getParentId());
		String maxCascadeId =menuService.calc(calcDto);
		if(IMSUtil.isEmpty(maxCascadeId)){
			Menu parentMenu=menuService.selectById(menu.getParentId());
			if(IMSUtil.isEmpty(parentMenu)){
				maxCascadeId="0.0000";
			}else{
				maxCascadeId=parentMenu.getCascadeId()+".0000";
			}
			
		}
		String curCascadeId=IMSUtil.createCascadeId(maxCascadeId, 9999);
		menu.setCascadeId(curCascadeId);
		menu.setCreateTime(IMSUtil.getDateTime());
		menu.setUpdateTime(IMSUtil.getDateTime());
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
	@PostMapping("save")
	@ResponseBody
	public R save(Menu menu) {
		boolean result = menuService.insert(menu);
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
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("edit")
	public String edit(String id,Model model) {
		Menu menu=menuService.selectById(id);
		model.addAttribute("menu", menu);
		String memuTyep=menu.getMenuType();
		if(SystemCons.MENU_TYPE_PARENT.equals(memuTyep)){
			return prefix + "editParentMenu";
		}else{
			return prefix + "editSubMenu";
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
	@PostMapping("updateParentMenu")
	@ResponseBody
	public R updateParentMenu(Menu menu) {
		Menu menuOld=menuService.selectById(menu.getMenuId());
		if(!menuOld.getMenuCode().equals(menu.getMenuCode())){  //菜单编码有改变
			EntityWrapper<Menu> countWrapper = new EntityWrapper<Menu>();
			List<String> menuTypeList=Lists.newArrayList();
			menuTypeList.add(SystemCons.MENU_TYPE_SUB);
			menuTypeList.add(SystemCons.MENU_TYPE_PARENT);
			SqlHelpUtil.eq(countWrapper, "menu_code", menu.getMenuCode());
			countWrapper.in("menu_type", menuTypeList);
			int count=menuService.selectCount(countWrapper);
			if(count>0){
				return R.warn("菜单编码已被占用，请使用其它编码。");
			}
		}
		menu.setUpdateTime(IMSUtil.getDateTime());
		boolean result = menuService.updateById(menu);
		if (result) {
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
			menuTypeList.add(SystemCons.MENU_TYPE_SUB);
			menuTypeList.add(SystemCons.MENU_TYPE_PARENT);
			SqlHelpUtil.eq(countWrapper, "menu_code", menu.getMenuCode());
			countWrapper.in("menu_type", menuTypeList);
			int count=menuService.selectCount(countWrapper);
			if(count>0){
				return R.warn("菜单编码已被占用，请使用其它编码。");
			}
		}
		menu.setUpdateTime(IMSUtil.getDateTime());
		boolean result = menuService.updateById(menu);
		if (result) {
			return R.ok();
		} else {
			return R.error("更新失败");
		}
		
	}
	
	/**
	 * 
	 * 简要说明： 展示详情
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("showDetail")
	public String showDetail(String id,Model model) {
		Menu menu=menuService.selectById(id);
		model.addAttribute("menu",menu);
		return prefix + "showMenu";
	}
	/**
	 * 
	 * 简要说明：删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("remove")
	@ResponseBody
	public R remove(String id) {
		Menu menu=menuService.selectById(id);
		String menuTye=menu.getMenuType();
		 //如果是父菜单则进行下面校验校验是否存在子菜单，存在子菜单不允许删除
		if(SystemCons.MENU_TYPE_PARENT.equals(menuTye)){ 
			EntityWrapper<Menu> countWrapper = new EntityWrapper<Menu>();
			SqlHelpUtil.eq(countWrapper, "parent_id", id);
			int row=menuService.selectCount(countWrapper);
			if(row>0){
				
				return R.warn("操作失败，当前菜单下存在子菜单，不允许删除，请先删除子菜单然后再删除。");
			}
		}
		if(SystemCons.MENU_TYPE_SUB.equals(menuTye)){ //如果是子菜单则删除按钮菜单
			EntityWrapper<Menu> removeWrapper = new EntityWrapper<Menu>();
			SqlHelpUtil.eq(removeWrapper, "parent_id", id);
			menuService.delete(removeWrapper);
		}
		boolean result = menuService.deleteById(id);
		if (result) {
			return R.ok("删除成功");
		} else {
			return R.error("删除失败");
		}
		
	}
	
	/**
	 * 
	 * 简要说明：批量删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-09-28
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("batchRemove")
	@ResponseBody
	public R batchRemove(String ids) {
		List<String> idList=IMSFormater.separatStringToList(ids);
		boolean result = menuService.deleteBatchIds(idList);
		if (result) {
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	
}

