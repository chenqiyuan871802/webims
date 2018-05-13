package com.ims.system.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.baomidou.mybatisplus.plugins.Page;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.Dtos;
import com.ims.common.util.IMSFormater;
import com.ims.common.util.PageDto;
import com.ims.common.util.R;
import java.util.List;
import com.ims.system.model.Param;
import com.ims.system.service.ParamService;
import org.springframework.stereotype.Controller;
import com.ims.common.web.BaseController;

/**
 * <p>
 * 键值参数 前端控制器
 * </p>
 *
 * @author 陈骑元
 * @since 2018-04-12
 */
@Controller
@RequestMapping("/system/param")
public class ParamController extends BaseController {
	
    private String prefix = "system/param/"; 
    @Autowired
    private ParamService paramService;
	/**
	 * 
	 * 简要说明：初始化页面 
	 * 编写者：陈骑元 
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("init")
	public String init() {
		return prefix + "paramList";
	}

	/**
	 * 
	 * 简要说明：分页查询 
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@RequestMapping("list")
	@ResponseBody
	public PageDto list() {
		Dto pDto = Dtos.newDto(request);
		Page<Param> page =paramService.likePage(pDto);
		return new PageDto(page);
	}

	/**
	 * 
	 * 简要说明： 跳转到新增页面 
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("add")
	public String add() {

		return prefix + "addparam";
	}

	/**
	 * 
	 * 简要说明： 新增信息保存 
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("save")
	@ResponseBody
	public R save(Param param) {
		boolean result = paramService.insert(param);
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
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("edit")
	public String edit(String id,Model model) {
		Param param=paramService.selectById(id);
		model.addAttribute("param", param);
		return prefix + "editparam";
	}
	
	/**
	 * 
	 * 简要说明：修改信息
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("update")
	@ResponseBody
	public R update(Param param) {
		boolean result = paramService.updateById(param);
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
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@GetMapping("showDetail")
	public String showDetail(String id,Model model) {
		Param param=paramService.selectById(id);
		model.addAttribute("param",param);
		return prefix + "showparam";
	}
	/**
	 * 
	 * 简要说明：删除信息
	 * 编写者：陈骑元
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("remove")
	@ResponseBody
	public R remove(String id) {
		boolean result = paramService.deleteById(id);
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
	 * 创建时间：2018-04-12
	 * @param 说明
	 * @return 说明
	 */
	@PostMapping("batchRemove")
	@ResponseBody
	public R batchRemove(String ids) {
		List<String> idList=IMSFormater.separatStringToList(ids);
		boolean result = paramService.deleteBatchIds(idList);
		if (result) {
			return R.ok();
		} else {
			return R.error("删除失败");
		}
		
	}
	
}

