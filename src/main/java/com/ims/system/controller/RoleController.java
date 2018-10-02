package com.ims.system.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.Dtos;
import com.ims.common.util.IMSFormater;
import com.ims.common.util.IMSUtil;
import com.ims.common.util.PageDto;
import com.ims.common.util.R;
import java.util.List;
import com.ims.system.model.Role;
import com.ims.system.service.RoleService;
import org.springframework.stereotype.Controller;
import com.ims.common.web.BaseController;

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
	public PageDto list() {
		Dto pDto = Dtos.newDto(request);
		pDto.setOrder(" create_time DESC ");
		Page<Role> page =roleService.likePage(pDto);
		return new PageDto(page);
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
	@PostMapping("save")
	@ResponseBody
	public R save(Role role) {
		role.setCreateTime(IMSUtil.getDateTime());
		role.setUpdateTime(IMSUtil.getDateTime());
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
	@PostMapping("update")
	@ResponseBody
	public R update(Role role) {
		role.setUpdateTime(IMSUtil.getDateTime());
		boolean result = roleService.updateById(role);
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
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("showDetail")
	public String showDetail(String id,Model model) {
		Role role=roleService.selectById(id);
		model.addAttribute("role",role);
		return prefix + "showRole";
	}
	/**
	 * 
	 * 简要说明：删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
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
	 * 简要说明：批量删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-10-02
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("batchRemove")
	@ResponseBody
	public R batchRemove(String ids) {
		List<String> idList=IMSFormater.separatStringToList(ids);
		boolean result = roleService.deleteBatchIds(idList);
		if (result) {
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	
}

